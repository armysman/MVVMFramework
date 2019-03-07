package mebs.gnnt.simpledemo.demo6_globalEvent;

import android.app.Application;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import gnnt.mebs.base.component.BaseViewModel;
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

    // 登录点击
    public void onLoginClick(View view) {
        ARouter.getInstance().build(RouteMap.SimpleDemo.LOGIN_PAGE).navigation();
    }
}
