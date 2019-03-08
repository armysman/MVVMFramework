package gnnt.mebs.simpledemo.demo7_dynamicParams;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import gnnt.mebs.base.component.BaseFragment;
import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.simpledemo.R;
import gnnt.mebs.simpledemo.R2;

/*******************************************************************
 * DynamicParamsFragment.java  2019/3/8
 * <P>
 * 动态传参页面<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class DynamicParamsFragment extends BaseFragment<BaseViewModel> {

    @BindView(R2.id.tv_search)
    protected TextView mTvSearch;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_dynamic_params;
    }

    @NonNull
    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return BaseViewModel.class;
    }

    @Override
    protected void setupViewModel(@NonNull BaseViewModel viewModel) {
        super.setupViewModel(viewModel);
        // 获取activity的ViewModel
        SearchViewModel searchViewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);

        // 监听关键字改变
        searchViewModel.getSearchKey().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mTvSearch.setText(s);
            }
        });
    }
}
