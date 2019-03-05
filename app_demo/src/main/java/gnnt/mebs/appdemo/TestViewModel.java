package gnnt.mebs.appdemo;


import android.app.Application;

import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.appdemo.model.UserApi;
import gnnt.mebs.appdemo.model.dto.LoginDTO;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/*******************************************************************
 * TestViewModel.java  2019/3/4
 * <P>
 * <br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class TestViewModel extends BaseViewModel {
    public TestViewModel(@NonNull Application application) {
        super(application);
    }

    public void login() {
        LoginDTO.Request loginReq = new LoginDTO.Request();
        loginReq.userID = "xxxxx";
        loginReq.password = "yyyyyy";
        getApplication().getRetrofitManager().getApi("http://182.150.46.242:40212/", UserApi.class)
                .login(loginReq)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ViewModelLoadObserver<LoginDTO.Response>() {
                    @Override
                    public void onSuccess(LoginDTO.Response response) {
                        super.onSuccess(response);
                        setMessage(response.result.message);
                    }
                });

    }
}
