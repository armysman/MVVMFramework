package mebs.gnnt.simpledemo.demo6_globalEvent;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import gnnt.mebs.base.component.BaseViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mebs.gnnt.simpledemo.model.Config;
import mebs.gnnt.simpledemo.model.OpenApi;
import mebs.gnnt.simpledemo.model.UnpackHandler;

/*******************************************************************
 * RegisterViewModel.java  2019/3/7
 * <P>
 * 注册ViewModel<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class RegisterViewModel extends BaseViewModel {

    /**
     * 登录请求码
     */
    public static final int REQUEST_REGISTER = 2;

    /**
     * 用户名
     */
    public MutableLiveData<String> mUserName = new MutableLiveData<>();

    /**
     * 密码
     */
    public MutableLiveData<String> mPassword = new MutableLiveData<>();

    /**
     * 昵称
     */
    public MutableLiveData<String> mNickName = new MutableLiveData<>();

    /**
     * 签名
     */
    public MutableLiveData<String> mSign = new MutableLiveData<>();

    /**
     * 数据接口
     */
    private OpenApi mApi;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        mApi = getApplication().getRetrofitManager().getApi(Config.HOST, OpenApi.class);
    }

    /**
     * 提交表单
     */
    public void submit(View view) {
        if (validForm()) {
            // 发送登录请求
            mApi.registerUser(mUserName.getValue(), mPassword.getValue(), mNickName.getValue(), mSign.getValue())
                    .map(new UnpackHandler<>())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ViewModelSingleObserver<Object>() {
                        @Override
                        public void onSuccess(Object o) {
                            super.onSuccess(o);
                            setMessage("注册成功");
                        }

                        @Override
                        public int getRequestCode() {
                            // 此处返回自定义登录请求码，便于界面判断
                            return REQUEST_REGISTER;
                        }
                    });
        }
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

        // 校验昵称
        if (TextUtils.isEmpty(mNickName.getValue())) {
            setMessage("请输入昵称");
            return false;
        }

        return true;
    }
}
