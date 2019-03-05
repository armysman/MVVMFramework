package gnnt.mebs.appdemo;

import gnnt.mebs.base.component.BaseActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/*******************************************************************
 * TestActivity.java  2019/3/4
 * <P>
 * <br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class TestActivity extends BaseActivity<TestViewModel> {
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_test;
    }

    @NonNull
    @Override
    protected Class getViewModelClass() {
        return TestViewModel.class;
    }

    @Override
    protected void setupViewModel(@Nullable TestViewModel viewModel) {
        super.setupViewModel(viewModel);
        viewModel.login();
    }
}
