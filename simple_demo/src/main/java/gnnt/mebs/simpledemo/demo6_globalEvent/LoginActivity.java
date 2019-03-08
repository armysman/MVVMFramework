package gnnt.mebs.simpledemo.demo6_globalEvent;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import gnnt.mebs.base.component.BaseActivity;
import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.common.RouteMap;
import gnnt.mebs.simpledemo.R;
import gnnt.mebs.simpledemo.databinding.ActivityLoginBinding;

/*******************************************************************
 * LoginActivity.java  2019/3/7
 * <P>
 * <br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
@Route(path = RouteMap.SimpleDemo.LOGIN_PAGE)
public class LoginActivity extends BaseActivity<LoginViewModel> {
    ActivityLoginBinding mBinding;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void setupLayout(int layoutRes) {
        mBinding = DataBindingUtil.setContentView(this, layoutRes);
        mBinding.setLifecycleOwner(this);
        setTitle("全局事件监听");
    }

    @NonNull
    @Override
    protected Class getViewModelClass() {
        return LoginViewModel.class;
    }

    @Override
    protected void setupViewModel(@Nullable LoginViewModel viewModel) {
        super.setupViewModel(viewModel);
        mBinding.setViewModel(viewModel);
    }

    @Override
    protected void onLoadStatusChanged(int status, int requestCode) {
        super.onLoadStatusChanged(status, requestCode);
        // 登录成功，关闭页面
        if (requestCode == LoginViewModel.REQUEST_LOGIN && status == BaseViewModel.LOAD_COMPLETE) {
            finish();
        }
    }
}
