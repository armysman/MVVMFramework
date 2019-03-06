package mebs.gnnt.simpledemo.loadData;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.base.http.HttpException;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mebs.gnnt.simpledemo.model.Config;
import mebs.gnnt.simpledemo.model.PoemApi;
import mebs.gnnt.simpledemo.model.dto.Response;
import mebs.gnnt.simpledemo.model.vo.Poem;

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
        PoemApi api = getApplication().getRetrofitManager().getApi(Config.HOST, PoemApi.class);
        api.getRandomPoem()
                .delay(3, TimeUnit.SECONDS) // 延迟3秒
                .onErrorResumeNext(Single.<Response<Poem>>error(new HttpException("网络错误")))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mDataObserver);
    }

    protected ViewModelLoadObserver<Response<Poem>> mDataObserver = new ViewModelLoadObserver<Response<Poem>>() {
        @Override
        public void onSuccess(Response<Poem> response) {
            super.onSuccess(response);
            if (response.code == 200 && response.result != null) {
                String result = String.format("%s\n%s\n%s", response.result.title,
                        response.result.authors,
                        response.result.content.replaceAll("\\|", "\n"));
                LoadDataViewModel.this.result.setValue(result);
            }
        }

    };

    public MutableLiveData<String> getResult() {
        return result;
    }
}
