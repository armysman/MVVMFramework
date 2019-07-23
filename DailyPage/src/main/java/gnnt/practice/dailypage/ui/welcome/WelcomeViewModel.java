package gnnt.practice.dailypage.ui.welcome;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import gnnt.mebs.base.component.BaseViewModel;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**********************************************************
 *  WelcomeViewModel.java  2019-07-08
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class WelcomeViewModel extends BaseViewModel {

    public WelcomeViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void refreshDataIfNeed() {
        super.refreshDataIfNeed();
    }


    public void requestWelcomeRes() {
        Single.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ViewModelSingleObserver<Long>());
    }

}
