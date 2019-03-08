package gnnt.mebs.simpledemo.demo6_globalEvent;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.common.CommonApp;
import gnnt.mebs.common.RouteMap;
import gnnt.mebs.common.event.LoginUserEvent;
import gnnt.mebs.common.vo.LoginUser;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import gnnt.mebs.simpledemo.model.Config;
import gnnt.mebs.simpledemo.model.OpenApi;
import gnnt.mebs.simpledemo.model.UnpackHandler;
import gnnt.mebs.simpledemo.model.vo.User;

/*******************************************************************
 * LoginViewModel.java  2019/3/7
 * <P>
 * ViewModel基类<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class LoginViewModel extends BaseViewModel {

    /**
     * 请求登录
     */
    public static final int REQUEST_LOGIN = 2;

    /**
     * 用户名
     */
    public MutableLiveData<String> mUserName = new MutableLiveData<>();

    /**
     * 密码
     */
    public MutableLiveData<String> mPassword = new MutableLiveData<>();

    /**
     * 数据接口
     */
    private OpenApi mApi;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mApi = getApplication().getRetrofitManager().getApi(Config.HOST, OpenApi.class);
    }

    /**
     * 提交表单
     */
    public void submit(View view) {
        if (validForm()) {
            // 发送登录请求
            mApi.login(mUserName.getValue(), mPassword.getValue())
                    .map(new UnpackHandler<User>())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ViewModelSingleObserver<User>() {
                        @Override
                        public void onSuccess(User user) {
                            super.onSuccess(user);
                            setMessage("登录成功");
                            // 全局事件通知
                            LoginUserEvent loginUserEvent = ((CommonApp) getApplication()).getLoginUserEvent();
                            loginUserEvent.logged(new LoginUser(user.userName, user.nickName, user.sign));

                        }

                        @Override
                        public int getRequestCode() {
                            // 此处返回自定义登录请求码，便于界面判断
                            return REQUEST_LOGIN;
                        }
                    });
        }
    }

    // 注册点击
    public void onRegisterClick(View view) {
        ARouter.getInstance().build(RouteMap.SimpleDemo.REGISTER_PAGE).navigation();
    }

    /**
     * 表单校验
     *
     * @return
     */
    public boolean validForm() {

        // 校验用户名
        if (TextUtils.isEmpty(mUserName.getValue())) {
            setMessage("请输入用户名");
            return false;
        }

        // 校验密码
        if (TextUtils.isEmpty(mPassword.getValue())) {
            setMessage("请输入密码");
            return false;
        }

        return true;
    }
}
