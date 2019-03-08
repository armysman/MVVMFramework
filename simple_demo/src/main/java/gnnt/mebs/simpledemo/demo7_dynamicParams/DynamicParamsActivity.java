package gnnt.mebs.simpledemo.demo7_dynamicParams;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import gnnt.mebs.base.component.BaseActivity;
import gnnt.mebs.common.RouteMap;
import gnnt.mebs.simpledemo.R;
import gnnt.mebs.simpledemo.databinding.ActivityDynamicParamsBinding;

/*******************************************************************
 * DynamicParamsActivity.java  2019/3/8
 * <P>
 * 动态传参的Activity<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
@Route(path = RouteMap.SimpleDemo.DYNAMIC_PARAMS_PAGE)
public class DynamicParamsActivity extends BaseActivity<SearchViewModel> {

    ActivityDynamicParamsBinding mBinding;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_dynamic_params;
    }

    @Override
    protected void setupLayout(int layoutRes) {
        mBinding = DataBindingUtil.setContentView(this, layoutRes);
        mBinding.setLifecycleOwner(this);
        setTitle("Activity与Fragment动态传参");
    }

    @Override
    protected void setupView() {
        super.setupView();
        // 将Fragment添加到布局
        DynamicParamsFragment fragment = new DynamicParamsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_container, fragment)
                .commit();
    }

    @NonNull
    @Override
    protected Class<? extends SearchViewModel> getViewModelClass() {
        return SearchViewModel.class;
    }

    @Override
    protected void setupViewModel(@Nullable SearchViewModel viewModel) {
        super.setupViewModel(viewModel);
        mBinding.setViewModel(viewModel);
    }
}
