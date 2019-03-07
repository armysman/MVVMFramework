package mebs.gnnt.simpledemo.demo1_loadData;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.base.http.HttpException;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mebs.gnnt.simpledemo.model.Config;
import mebs.gnnt.simpledemo.model.OpenApi;
import mebs.gnnt.simpledemo.model.dto.Response;
import mebs.gnnt.simpledemo.model.vo.Poetry;

/*******************************************************************
 * LoadDataViewModel.java  2019/3/5
 * <P>
 * 加载数据，展示基础功能<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class LoadDataViewModel extends BaseViewModel {

    /**
     * 保存结果数据
     */
    protected LiveData<Poetry> mResult = new MutableLiveData<>();

    public LoadDataViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void refreshDataIfNeed() {
        // 如果数据结果不为空，不再重复加载
        if (mResult.getValue() == null || mDataObserver.loadStatus == LOAD_ERROR) {
            loadPoetryData();
        }
    }

    /**
     * 加载古诗数据
     */
    public void loadPoetryData() {
        // 如果当前状态是正在加载中，则不处理
        if (mDataObserver.loadStatus == BaseViewModel.LOADING) {
            return;
        }
        OpenApi api = getApplication().getRetrofitManager().getApi(Config.HOST, OpenApi.class);
        api.getRandomPoetry()
                .onErrorResumeNext(Single.<Response<Poetry>>error(new HttpException("网络错误"))) // 统一错误提示语
                .subscribeOn(Schedulers.newThread()) // 异步执行
                .observeOn(AndroidSchedulers.mainThread()) // 主线程回调
                .subscribe(mDataObserver);
    }

    protected ViewModelSingleObserver<Response<Poetry>> mDataObserver = new ViewModelSingleObserver<Response<Poetry>>() {
        @Override
        public void onSuccess(Response<Poetry> response) {
            super.onSuccess(response);
            if (response.code == 200 && response.result != null) {
                ((MutableLiveData<Poetry>) mResult).setValue(response.result);
            }
        }

    };

    public LiveData<Poetry> getResult() {
        return mResult;
    }
}
