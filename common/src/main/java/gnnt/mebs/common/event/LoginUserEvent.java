package gnnt.mebs.common.event;


import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import gnnt.mebs.base.liveData.SingleLiveData;
import gnnt.mebs.common.util.SharedPreferenceUtils;
import gnnt.mebs.common.vo.LoginUser;

/*******************************************************************
 * LoginUserEvent.java  2018/11/29
 * <P>
 * 登录用户管理类<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class LoginUserEvent {

    /**
     * 本地用户键
     */
    public static final String LOCAL_USER_KEY = "LoginUser";

    /**
     * 触发的事件
     */
    public enum Event {
        Login, // 登录
        Logout, // 退出登录
        Invalid, // 登录失效
        InfoChanged, // 信息改变
    }

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 当前登录用户
     */
    private MutableLiveData<LoginUser> mLoginUser = new MutableLiveData<>();


    /**
     * 登录事件
     */
    private SingleLiveData<Event> mLoginEvent = new SingleLiveData<>();

    public LoginUserEvent(@NonNull Context context) {
        mContext = context.getApplicationContext();
        // 加载本地用户
        mLoginUser.setValue(loadLocalUser());
    }

    /**
     * 初始化本地登录用户
     */
    private LoginUser loadLocalUser() {
        LoginUser loginUser = null;
        String loginUserJson = (String) SharedPreferenceUtils
                .getParam(mContext, LOCAL_USER_KEY, "");
        if (!TextUtils.isEmpty(loginUserJson)) {
            loginUser = new Gson().fromJson(loginUserJson, LoginUser.class);
        }
        return loginUser;
    }

    /**
     * 保存登录用户
     *
     * @param user 用户
     */
    private void saveLocalUser(LoginUser user) {
        String loginUserJson = "";
        if (user != null) {
            loginUserJson = new Gson().toJson(user);
        }
        SharedPreferenceUtils.setParam(mContext, LOCAL_USER_KEY, loginUserJson);
    }

    /**
     * 判断当前是否登录
     *
     * @return
     */
    @MainThread
    public boolean isLogged() {
        return mLoginUser.getValue() != null;
    }

    /**
     * 观察登录用户
     *
     * @param owner    所属生命周期
     * @param observer 观察者
     */
    public void observeLoginUser(@NonNull LifecycleOwner owner, @NonNull Observer<LoginUser> observer) {
        mLoginUser.observe(owner, observer);
    }

    /**
     * 观察登录事件
     *
     * @param owner    所属生命周期
     * @param observer 观察者
     */
    public void observeLoginEvent(@NonNull LifecycleOwner owner, @NonNull Observer<Event> observer) {
        mLoginEvent.observe(owner, observer);
    }

    /**
     * 设置登录
     *
     * @param user 登录用户信息
     */
    public void logged(@NonNull LoginUser user) {
        mLoginUser.postValue(user);
        // 保存到本地
        saveLocalUser(user);
        // 发送事件
        mLoginEvent.postValue(Event.Login);
    }

    /**
     * 设置退出登录
     */
    public void logout() {
        mLoginUser.postValue(null);
        // 保存到本地
        saveLocalUser(null);
        // 发送事件
        mLoginEvent.postValue(Event.Logout);
    }

    /**
     * 设置登录失效
     */
    public void loginInvalid() {
        // 如果已经是未登录状态，则不处理
        if (mLoginUser.getValue() == null) {
            return;
        }
        mLoginUser.postValue(null);
        // 保存到本地
        saveLocalUser(null);
        // 发送事件
        mLoginEvent.postValue(Event.Invalid);
    }

    /**
     * 信息改变
     *
     * @param user 登录用户
     */
    public void changeInfo(@NonNull LoginUser user) {
        mLoginUser.postValue(user);
        // 保存到本地
        saveLocalUser(user);
        // 发送事件
        mLoginEvent.postValue(Event.InfoChanged);
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public LoginUser getLoginUser() {
        return mLoginUser.getValue();
    }


}
