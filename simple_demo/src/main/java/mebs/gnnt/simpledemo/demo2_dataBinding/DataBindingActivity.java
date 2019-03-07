package mebs.gnnt.simpledemo.demo2_dataBinding;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import gnnt.mebs.base.component.BaseActivity;
import gnnt.mebs.common.RouteMap;
import mebs.gnnt.simpledemo.R;
import mebs.gnnt.simpledemo.databinding.ActivityDataBindingBinding;

/*******************************************************************
 * DataBindingActivity.java  2019/3/5
 * <P>
 * 数据绑定演示界面<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
@Route(path = RouteMap.SimpleDemo.DATA_BINDING_PAGE)
public class DataBindingActivity extends BaseActivity<DataBindingViewModel> {

    /**
     * 数据绑定
     */
    ActivityDataBindingBinding mBinding;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_data_binding;
    }

    @Override
    protected void setupLayout(int layoutRes) {
        mBinding = DataBindingUtil.setContentView(this, layoutRes);
        mBinding.setLifecycleOwner(this);
        setTitle("DataBinding的使用");
    }

    @NonNull
    @Override
    protected Class<? extends DataBindingViewModel> getViewModelClass() {
        return DataBindingViewModel.class;
    }

    @Override
    protected void setupViewModel(@Nullable DataBindingViewModel viewModel) {
        super.setupViewModel(viewModel);
        mBinding.setViewModel(viewModel);
    }
}
