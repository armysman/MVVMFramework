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
    private MutableLiveData<String> mResult = new MutableLiveData<>();

    public LoadDataViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void refreshDataIfNeed() {
        loadZhuHuData();
    }

    /**
     * 知乎专栏
     */
    public void loadZhuHuData() {
        LoadDataApi api = getApplication().getRetrofitManager().getApi(LoadDataApi.ZHU_HU_HOST, LoadDataApi.class);
        api.getZhuHuData()
                .delay(3, TimeUnit.SECONDS) // 延迟3秒
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ViewModelLoadObserver<ZhuHuDTO.Response>() {
                    @Override
                    public void onSuccess(ZhuHuDTO.Response response) {
                        super.onSuccess(response);
                        String result = String.format("请求成功，专栏所属人姓名：%s 关注人数:%d", response.author.name, response.followers);
                        mResult.setValue(result);
                    }
                });
    }

    public MutableLiveData<String> getResult() {
        return mResult;
    }
}
