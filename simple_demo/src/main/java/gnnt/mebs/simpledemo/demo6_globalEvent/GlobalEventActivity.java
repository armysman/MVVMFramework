package gnnt.mebs.simpledemo.demo6_globalEvent;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import gnnt.mebs.common.RouteMap;
import gnnt.mebs.common.component.BaseUserActivity;
import gnnt.mebs.common.vo.LoginUser;
import gnnt.mebs.simpledemo.R;
import gnnt.mebs.simpledemo.databinding.ActivityGlobalEventBinding;

/*******************************************************************
 * GlobalEventActivity.java  2019/3/7
 * <P>
 * <br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
@Route(path = RouteMap.SimpleDemo.GLOBAL_EVENT_PAGE)
public class GlobalEventActivity extends BaseUserActivity<GlobalEventViewModel> {

    ActivityGlobalEventBinding mBinding;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_global_event;
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
        return GlobalEventViewModel.class;
    }

    @Override
    protected void setupViewModel(@Nullable GlobalEventViewModel viewModel) {
        super.setupViewModel(viewModel);
        mBinding.setViewModel(viewModel);
    }


    @Override
    protected void onLoginUserChanged(LoginUser loginUser) {
        super.onLoginUserChanged(loginUser);
        mViewModel.mUser.setValue(loginUser);
    }
}
