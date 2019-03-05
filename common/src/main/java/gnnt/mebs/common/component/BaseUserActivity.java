package gnnt.mebs.common.component;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import gnnt.mebs.base.component.BaseActivity;
import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.common.CommonApp;
import gnnt.mebs.common.vo.LoginUser;

/*******************************************************************
 * BaseUserActivity.java  2019/3/5
 * <P>
 * 可以监听登录用户的Activity<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public abstract class BaseUserActivity<T extends BaseViewModel> extends BaseActivity<T> {

    @Override
    protected void setupViewModel(@Nullable T viewModel) {
        super.setupViewModel(viewModel);
        CommonApp commonApp = (CommonApp) viewModel.getApplication();
        commonApp.getLoginUserEvent().observeLoginUser(this, new Observer<LoginUser>() {
            @Override
            public void onChanged(@Nullable LoginUser loginUser) {
                onLoginUserChanged(loginUser);
            }
        });
    }

    /**
     * 登录用户改变监听
     *
     * @param loginUser 登录用户
     */
    protected void onLoginUserChanged(LoginUser loginUser) {
    }
}
