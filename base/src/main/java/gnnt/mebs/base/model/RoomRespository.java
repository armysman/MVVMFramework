package gnnt.mebs.base.model;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

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
public abstract class RoomRespository<Data> extends SimpleRespository<Data> {


    /**
     * 是否初始化Data数据
     */
    private boolean mIsInitData = false;

    /**
     * 加载Room数据库
     *
     * @return 获取Room中的LiveData数据
     */
    public @NonNull abstract LiveData<Data> getRoomData();

    @Override
    public final Data loadLocalData() {
        // 这里不需要做任何处理，使用Room数据库返回的LiveData
        return null;
    }

    /**
     * 获取数据
     *
     * @param callback 回调接口
     * @return 返回数据LiveData
     */
    @MainThread
    public LiveData<Data> getData(@Nullable final RemoteLoadCallback callback) {
        if (mIsInitData) {
            refreshDataIfNeed(callback);
        } else {
            mIsInitData = true;
            // 将Data转换为RoomData
            mData = getRoomData();
            // 本地数据加载完成时，尝试从远端刷新数据
            mData.observeForever(new Observer<Data>() {
                @Override
                public void onChanged(Data data) {
                    mData.removeObserver(this);
                    refreshDataIfNeed(callback);
                }
            });
        }
        return mData;
    }

    @Override
    protected final void updateFromLocal(Data data) {
        // 这里不需要做任何处理，Room数据库直接返回LiveData
    }

    @Override
    protected final void updateFromRemote(Data data) {
        // 这里不需要做任何处理，子类实现 saveLocalData 来将数据插入Room数据库中，Room会自动更新
    }


}
