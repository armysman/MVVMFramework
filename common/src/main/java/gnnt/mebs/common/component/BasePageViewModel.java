package gnnt.mebs.common.component;


import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import gnnt.mebs.base.component.BaseViewModel;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/*******************************************************************
 * BasePageViewModel.java  2019/1/3
 * <P>
 * 基础分页列表ViewModel<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public abstract class BasePageViewModel<Response, Data> extends BaseViewModel {


    /**
     * 页码
     */
    private int mPageNO;

    /**
     * 每页条数
     */
    private int mPageSize = 20;

    /**
     * 数据源
     */
    private MutableLiveData<List<Data>> mData = new MutableLiveData<>();

    /**
     * 是否有更多数据
     */
    private MutableLiveData<Boolean> mHasMoreData = new MutableLiveData<>();

    /**
     * 总条数
     */
    private MutableLiveData<Integer> mTotalCount = new MutableLiveData<>();

    public BasePageViewModel(@NonNull Application application) {
        super(application);
        // 设置默认值
        mData.setValue(new ArrayList<Data>());
        mTotalCount.setValue(0);
        mHasMoreData.setValue(false);
    }

    /**
     * 加载更多
     *
     * @param pageNO   页码
     * @param pageSize 每页条数
     */
    public abstract Single<Response> onLoad(int pageNO, int pageSize);

    /**
     * 从返回包中解析数据
     *
     * @param response 返回包
     * @return 返回包解析结果
     */
    public abstract ListResponse<Data> parseResponse(Response response);


    /**
     * 获取数据
     *
     * @return 数据LiveData
     */
    @NonNull
    public LiveData<List<Data>> getData() {
        return mData;
    }

    /**
     * 是否有更多数据
     *
     * @return 监听是否有更多数据
     */
    @NonNull
    public LiveData<Boolean> hasMoreData() {
        return mHasMoreData;
    }

    /**
     * 获取总条数
     *
     * @return 总条数
     */
    public LiveData<Integer> getTotalCount() {
        return mTotalCount;
    }

    @Override
    public void refreshDataIfNeed() {
        // 如果当前页码为初始状态，且没有正在加载中，则刷新数据
        if (mPageNO == 0 && mRequestObserver.loadStatus != LOADING) {
            loadRefresh();
        }
    }

    /**
     * 刷新加载
     */
    public void loadRefresh() {
        // 重置页码
        mPageNO = 0;
        // 加载
        loadMore();
    }

    /**
     * 加载更多
     */
    public void loadMore() {
        // 加载下一页数据
        Single<Response> task = onLoad(mPageNO + 1, mPageSize);
        if (task != null && mRequestObserver.loadStatus != BaseViewModel.LOADING) {
            task.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mRequestObserver);
        }
    }

    /**
     * 请求结果监听
     */
    public ViewModelLoadObserver<Response> mRequestObserver = new ViewModelLoadObserver<Response>() {

        @Override
        public void onSuccess(Response response) {
            super.onSuccess(response);
            // 将返回的数据解析后添加到末尾
            List<Data> dataList = mData.getValue();
            ListResponse listResponse = parseResponse(response);
            if (listResponse != null) {
                // 如果请求页码为0，则说明是刷新
                if (mPageNO == 0) {
                    dataList.clear();
                }
                // 返回列表不为空
                if (listResponse.resultList != null && listResponse.resultList.size() > 0) {
                    // 增加页码
                    mPageNO++;
                    // 将返回数据添加末尾
                    dataList.addAll(listResponse.resultList);
                }
                // 通知更新
                mData.setValue(dataList);
                // 更新总条数
                mTotalCount.setValue(listResponse.total);
                // 判断是否有更多数据
                if (hasMoreData(listResponse)) {
                    mHasMoreData.setValue(false);
                } else {
                    mHasMoreData.setValue(true);
                }
            }
        }
    };

    /**
     * 判断是否还有更多数据
     *
     * @param listResponse 返回数据
     * @return true 有更多数据，false 没有更多数据
     */
    public boolean hasMoreData(ListResponse listResponse) {
        return mData.getValue().size() >= listResponse.total || listResponse.resultList == null ||
                listResponse.resultList.isEmpty();
    }

    public static class ListResponse<Data> {

        /**
         * 总条数，默认取最大值
         */
        public int total = Integer.MAX_VALUE;
        /**
         * 返回列表
         */
        public List<Data> resultList;

        public ListResponse(int total, List<Data> resultList) {
            this.total = total;
            this.resultList = resultList;
        }
    }
}
