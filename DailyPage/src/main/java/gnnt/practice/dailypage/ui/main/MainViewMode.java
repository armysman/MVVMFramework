package gnnt.practice.dailypage.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import gnnt.mebs.base.component.BaseViewModel;

/**********************************************************
 *  MainViewMode.java  2019-07-08
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class MainViewMode extends BaseViewModel {
    private MutableLiveData<String> mCurToEditDailyID = new MutableLiveData<>();

    public MainViewMode(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getCurToEditDailyID() {
        return mCurToEditDailyID;
    }
}
