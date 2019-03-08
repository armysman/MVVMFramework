package gnnt.mebs.simpledemo.demo6_globalEvent;

import android.app.Application;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.common.CommonApp;
import gnnt.mebs.common.RouteMap;
import gnnt.mebs.common.vo.LoginUser;

/*******************************************************************
 * GlobalEventViewModel.java  2019/3/7
 * <P>
 * <br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class GlobalEventViewModel extends BaseViewModel {

    /**
     * 当前用户
     */
    public MutableLiveData<LoginUser> mUser = new MutableLiveData<>();

    public GlobalEventViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 登录点击
     *
     * @param view
     */
    public void onLoginClick(View view) {
        ARouter.getInstance().build(RouteMap.SimpleDemo.LOGIN_PAGE).navigation();
    }

    /**
     * 退出登录点击
     *
     * @param view
     */
    public void onLogoutClick(View view) {
        // 全局通知退出登录
        ((CommonApp) getApplication()).getLoginUserEvent().logout();
    }
}
