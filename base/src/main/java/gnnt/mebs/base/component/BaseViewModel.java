package gnnt.mebs.base.component;


import android.app.Application;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import gnnt.mebs.base.R;
import gnnt.mebs.base.liveData.SingleLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/*******************************************************************
 * BaseViewModel.java  2018/11/29
 * <P>
 * ViewModel 基类<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class BaseViewModel extends AndroidViewModel {

    /**
     * 未加载
     */
    public static final int NOT_LOADED = 0;

    /**
     * 加载中
     */
    public static final int LOADING = 1;

    /**
     * 加载完成
     */
    public static final int LOAD_COMPLETE = 2;

    /**
     * 加载错误
     */
    public static final int LOAD_ERROR = 3;

    /**
     * 异步任务 disposable 集合
     */
    protected CompositeDisposable mCompositeDisposable;

    /**
     * 错误消息
     */
    protected SingleLiveData<String> mMessage = new SingleLiveData<>();

    /**
     * 是否正在加载中
     */
    protected SingleLiveData<Integer> mLoadStatusSpec = new SingleLiveData<>();


    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 界面一般用于非用户操作触发的刷新方法，例如网络状态改变
     */
    public void refreshDataIfNeed() {

    }

    /**
     * 添加一个异步操作开关，该方法主线程调用
     *
     * @param disposable 异步操作开关
     */
    @MainThread
    protected void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // 销毁时清除所有开关
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    /**
     * 设置错误消息资源
     *
     * @param msgResource 错误消息资源ID
     */
    public void setMessage(@StringRes int msgResource) {
        this.setMessage(getApplication().getString(msgResource));
    }

    /**
     * 设置错误消息
     *
     * @param message 消息内容
     */
    public void setMessage(String message) {
        mMessage.setValue(message);
    }

    /**
     * 获取错误消息监听
     *
     * @return mMessage
     */
    public LiveData<String> getMessage() {
        return mMessage;
    }


    /**
     * 设置加载状态
     *
     * @param status      加载状态
     * @param requestCode 请求码
     */
    public void setLoadStatus(int status, int requestCode) {
        this.mLoadStatusSpec.setValue(LoadStatusSpec.makeStatusSpec(status, requestCode));
    }

    /**
     * 获取加载状态
     *
     * @return 加载状态
     */
    public LiveData<Integer> getLoadStatusSpec() {
        return mLoadStatusSpec;
    }

    /**
     * ViewModel 异步加载使用的observer,可以获取加载状态。
     * 该类时为了防止重复操作导致多次加载的问题，通过对loadStatus判断，可以避免重复创建加载
     *
     * @param <T>
     */
    public abstract class ViewModelLoadObserver<T> implements SingleObserver<T> {

        /**
         * 加载状态
         */
        public int loadStatus = NOT_LOADED;

        @Override
        public void onSubscribe(Disposable d) {
            addDisposable(d);
            loadStatus = LOADING;
            if (dispatchStatusToView()) {
                setLoadStatus(loadStatus, getRequestCode());
            }
        }

        @Override
        public void onSuccess(T t) {
            loadStatus = LOAD_COMPLETE;
            if (dispatchStatusToView()) {
                setLoadStatus(loadStatus, getRequestCode());
            }
        }

        @Override
        public void onError(Throwable e) {
            loadStatus = LOAD_ERROR;
            if (dispatchErrorToView()) {
                setMessage(getErrorMessage());
            }
            if (dispatchStatusToView()) {
                setLoadStatus(loadStatus, getRequestCode());
            }
        }

        /**
         * 是否将状态分发给View，默认为true
         *
         * @return true 是 false 否
         */
        public boolean dispatchStatusToView() {
            return true;
        }

        /**
         * 是否将错误消息分发给View，默认为true
         *
         * @return true 是 false 否
         */
        public boolean dispatchErrorToView() {
            return true;
        }

        /**
         * 加载请求代码，注意必须返回正整数，否则获取请求码将有问题
         *
         * @return 正整数，范围为 0 ~ (Integer.MAX_VALUE >> 4 - 1)
         */
        public int getRequestCode() {
            return LoadStatusSpec.DEFAULT_REQUEST_CODE;
        }

        /**
         * 返回网络错误消息
         *
         * @return 错误消息
         */
        public String getErrorMessage() {
            return getApplication().getString(R.string.network_error_default);
        }
    }

    @NonNull
    @Override
    public BaseApp getApplication() {
        return super.getApplication();
    }
}
