package gnnt.mebs.simpledemo.demo7_dynamicParams;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import gnnt.mebs.base.component.BaseViewModel;

/*******************************************************************
 * SearchViewModel.java  2019/3/8
 * <P>
 * 搜索ViewModel<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class SearchViewModel extends BaseViewModel {

    public MutableLiveData<String> mSearchKey = new MutableLiveData<>();

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 获取搜索关键字LiveData
     *
     * @return
     */
    public LiveData<String> getSearchKey() {
        return mSearchKey;
    }
}
