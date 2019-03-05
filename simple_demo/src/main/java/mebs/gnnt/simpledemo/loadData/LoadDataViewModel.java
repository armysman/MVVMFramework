package mebs.gnnt.simpledemo.loadData;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import gnnt.mebs.base.component.BaseViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mebs.gnnt.simpledemo.model.LoadDataApi;
import mebs.gnnt.simpledemo.model.dto.ZhuHuDTO;

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
    protected MutableLiveData<String> result = new MutableLiveData<>();

    public LoadDataViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void refreshDataIfNeed() {
        // 如果数据结果不为空，不再重复加载
        if (result.getValue() == null) {
            loadZhuHuData();
        }
    }

    /**
     * 知乎专栏
     */
    public void loadZhuHuData() {
        // 如果当前状态是正在加载中，则不处理
        if (mDataObserver.loadStatus == BaseViewModel.LOADING) {
            return;
        }
        LoadDataApi api = getApplication().getRetrofitManager().getApi(LoadDataApi.ZHU_HU_HOST, LoadDataApi.class);
        api.getZhuHuData()
                .delay(3, TimeUnit.SECONDS) // 延迟3秒
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mDataObserver);
    }

    protected ViewModelLoadObserver<ZhuHuDTO.Response> mDataObserver = new ViewModelLoadObserver<ZhuHuDTO.Response>() {
        @Override
        public void onSuccess(ZhuHuDTO.Response response) {
            super.onSuccess(response);
            String result = String.format("请求成功，专栏所属人姓名：%s 关注人数:%d", response.author.name, response.followers);
            LoadDataViewModel.this.result.setValue(result);
        }

    };

    public MutableLiveData<String> getResult() {
        return result;
    }
}
