package mebs.gnnt.simpledemo.demo2_dataBinding;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import gnnt.mebs.base.component.BaseViewModel;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import mebs.gnnt.simpledemo.model.Config;
import mebs.gnnt.simpledemo.model.OpenApi;
import mebs.gnnt.simpledemo.model.dto.Response;
import mebs.gnnt.simpledemo.model.vo.Poetry;

/*******************************************************************
 * DataBindingViewModel.java  2019/3/5
 * <P>
 * 演示如何在框架中如何使用DataBinding<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class DataBindingViewModel extends BaseViewModel {

    /**
     * 姓名
     */
    public MutableLiveData<String> mPoetryName = new MutableLiveData<>();

    /**
     * 姓名RxJava发送者
     */
    private PublishSubject<String> mPoetryNameSubject = PublishSubject.create();

    /**
     * 对应古诗对象
     */
    public MutableLiveData<Poetry> mPoetry = new MutableLiveData<>();


    public DataBindingViewModel(@NonNull Application application) {
        super(application);
        // 将LiveData转换为RxJava
        mPoetryName.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mPoetryNameSubject.onNext(s);
            }
        });

        final OpenApi api = getApplication().getRetrofitManager().getApi(Config.HOST, OpenApi.class);
        mPoetryNameSubject.debounce(500, TimeUnit.MILLISECONDS) // 过滤快于500耗秒的触发
                .filter(new Predicate<String>() { // 过滤为空的关键字
                    @Override
                    public boolean test(String s) throws Exception {
                        return !TextUtils.isEmpty(s);
                    }
                })
                .flatMapSingle(new io.reactivex.functions.Function<String, SingleSource<Response<List<Poetry>>>>() {
                    @Override
                    public SingleSource<Response<List<Poetry>>> apply(String s) throws Exception {
                        // 这里要捕获网络错误转换为正常返回值，否则连续事件会结束
                        return api.searchPoetry(s).onErrorReturnItem(new Response<List<Poetry>>(-1,"网络错误"));
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSearchResultObserver);
    }

    /**
     * 搜索点击
     *
     * @param view
     */
    public void onClearClick(View view) {
        mPoetryName.setValue("");
    }

    /**
     * 搜索结果监听
     */
    ViewModelObserver<Response<List<Poetry>>> mSearchResultObserver = new ViewModelObserver<Response<List<Poetry>>>() {

        @Override
        public void onNext(Response<List<Poetry>> response) {
            // 将结果第一首古诗回填
            if (response.code == 200) {
                if (response.result != null && !response.result.isEmpty()){
                    mPoetry.setValue(response.result.get(0));
                } else {
                    setMessage("没有搜索结果");
                }
            } else {
                setMessage(response.message);
            }
        }

        /**
         * 不触发界面加载状态
         * @return 是否触发界面状态
         */
        @Override
        public boolean dispatchStatusToView() {
            return false;
        }
    };

}
