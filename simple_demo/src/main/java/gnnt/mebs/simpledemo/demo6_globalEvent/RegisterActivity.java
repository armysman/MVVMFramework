package gnnt.mebs.simpledemo.demo6_globalEvent;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import gnnt.mebs.base.component.BaseActivity;
import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.common.RouteMap;
import gnnt.mebs.simpledemo.R;
import gnnt.mebs.simpledemo.databinding.ActivityRegisterBinding;

/*******************************************************************
 * RegisterActivity.java  2019/3/7
 * <P>
 * <br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
@Route(path = RouteMap.SimpleDemo.REGISTER_PAGE)
public class RegisterActivity extends BaseActivity<RegisterViewModel> {

    ActivityRegisterBinding mBinding;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_register;
    }

    @Override
    protected void setupLayout(int layoutRes) {
        mBinding = DataBindingUtil.setContentView(this, layoutRes);
        mBinding.setLifecycleOwner(this);
        setTitle("全局事件监听");
    }

    @NonNull
    @Override
    protected Class<? extends RegisterViewModel> getViewModelClass() {
        return RegisterViewModel.class;
    }

    @Override
    protected void setupViewModel(@Nullable RegisterViewModel viewModel) {
        super.setupViewModel(viewModel);
        mBinding.setViewModel(viewModel);
    }

    @Override
    protected void onLoadStatusChanged(int status, int requestCode) {
        super.onLoadStatusChanged(status, requestCode);
        // 注册成功后关闭页面
        if (requestCode == RegisterViewModel.REQUEST_REGISTER && status == BaseViewModel.LOAD_COMPLETE) {
            finish();
        }
    }
}
