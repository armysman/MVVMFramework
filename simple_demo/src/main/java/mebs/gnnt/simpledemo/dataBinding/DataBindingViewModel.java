package mebs.gnnt.simpledemo.dataBinding;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import gnnt.mebs.base.component.BaseViewModel;
import mebs.gnnt.simpledemo.loadData.LoadDataViewModel;

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
    public MutableLiveData<String> mName = new MutableLiveData<>();


    public DataBindingViewModel(@NonNull Application application) {
        super(application);
    }

    public void onButtonClick(View view) {
        mName.setValue("按钮设置的名字");
    }
}
