package gnnt.mebs.base.model;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*******************************************************************
 * SimpleRespository.java 2019/3/5
 * <P>
 * 基于Room的数据仓库基类<br/>
 * <br/>
 * </p>
 * Copyright2017 by GNNT Company. All Rights Reserved.
 *
 * @author:Zhoupeng
 ******************************************************************/
public abstract class RoomRespository<Data> {

    /**
     * 远端数据请求开关
     */
    private Disposable mRemoteDisposable;

    /**
     * 加载本地数据
     *
     * @return 本地数据异步操作类
     */
    public abstract LiveData<Data> loadLocalData();

    /**
     * 保存数据到本地
     *
     * @param data 数据
     */
    public abstract void saveLocalData(Data data);

    /**
     * 加载远端数据
     *
     * @return 远端数据操作类
     */
    public abstract Single<Data> loadRemoteData();

    /**
     * 数据是否过期，默认如果远端数据没有加载中，则可以刷新
     *
     * @return true 已过期 false 未过期
     */
    public boolean isDirty() {
        return mRemoteDisposable == null;
    }

    /**
     * 获取数据
     *
     * @return 返回数据LiveData
     */
    public LiveData<Data> getData() {
        return getData(null);
    }

    /**
     * 获取数据
     *
     * @param callback 回调接口
     * @return 返回数据LiveData
     */
    @MainThread
    public LiveData<Data> getData(@Nullable final RemoteLoadCallback callback) {
        refreshDataIfNeed(callback);
        return loadLocalData();
    }

    /**
     * 如果数据过期，则刷新数据
     *
     * @param callback 回调接口
     */
    @MainThread
    public void refreshDataIfNeed(RemoteLoadCallback callback) {
        if (isDirty()) {
            loadRemoteData()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.io()) // 在IO线程写入数据，避免多线程操作数据库或文件
                    .doOnSuccess(new Consumer<Data>() { // 将数据保存到本地
                        @Override
                        public void accept(Data data) throws Exception {
                            saveLocalData(data);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new RemoteObserver(callback));
        }
    }

    /**
     * 远端数据观察者
     */
    public class RemoteObserver implements SingleObserver<Data> {

        RemoteLoadCallback callback;

        public RemoteObserver(RemoteLoadCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onSubscribe(Disposable d) {
            mRemoteDisposable = d;
            if (callback != null) {
                callback.onLoading();
            }
        }

        @Override
        public void onSuccess(Data data) {
            mRemoteDisposable = null;
            if (callback != null) {
                callback.onComplete();
            }
        }

        @Override
        public void onError(Throwable e) {
            mRemoteDisposable = null;
            if (callback != null) {
                callback.onError(e);
            }
        }
    }

    /**
     * 远端数据加载回调
     */
    public interface RemoteLoadCallback {

        /**
         * 加载中
         */
        void onLoading();

        /**
         * 加载完成
         */
        void onComplete();

        /**
         * 加载失败
         */
        void onError(Throwable e);
    }
}
