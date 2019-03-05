package gnnt.mebs.appdemo.main;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import gnnt.mebs.base.component.BaseActivity;
import gnnt.mebs.common.RouteMap;
import gnnt.mebs.appdemo.R;

/*******************************************************************
 * MainActivity.java  2019/3/5
 * <P>
 * <br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class MainActivity extends BaseActivity<MainViewModel> {
    /**
     * 菜单列表
     */
    @BindView(R.id.rv_menu)
    protected RecyclerView mRvMenu;

    /**
     * 适配器
     */
    private MenuAdapter mAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @NonNull
    @Override
    protected Class<? extends MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected void setupView() {
        super.setupView();
        mRvMenu.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MenuAdapter();
        mRvMenu.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build(RouteMap.SimpleDemo.LOAD_DATA_PAGE).navigation();
            }
        });
    }

    @Override
    protected void setupViewModel(@Nullable MainViewModel viewModel) {
        super.setupViewModel(viewModel);
        viewModel.getMainMenu().observe(this, new Observer<List<MainViewModel.MainMenu>>() {
            @Override
            public void onChanged(List<MainViewModel.MainMenu> mainMenus) {
                mAdapter.setNewData(mainMenus);
            }
        });
    }
}
