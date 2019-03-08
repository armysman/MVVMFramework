package gnnt.mebs.simpledemo.demo4_roomRepository;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import gnnt.mebs.base.component.BaseActivity;
import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.common.RouteMap;
import gnnt.mebs.simpledemo.PoetryAdapter;
import gnnt.mebs.simpledemo.R;
import gnnt.mebs.simpledemo.R2;
import gnnt.mebs.simpledemo.model.vo.Poetry;

/*******************************************************************
 * RoomRepositoryActivity.java  2019/3/7
 * <P>
 * 演示分页<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
@Route(path = RouteMap.SimpleDemo.ROOM_REPOSITORY_PAGE)
public class RoomRepositoryActivity extends BaseActivity<RoomRepositoryViewModel> {

    /**
     * 唐诗列表
     */
    @BindView(R2.id.rv_poetry)
    protected RecyclerView mRvPoetry;

    /**
     * 下拉刷新控件
     */
    @BindView(R2.id.layout_refresh)
    protected SwipeRefreshLayout mLayoutRefresh;

    /**
     * 适配器
     */
    private PoetryAdapter mAdapter;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_room_repository;
    }


    @NonNull
    @Override
    protected Class<? extends RoomRepositoryViewModel> getViewModelClass() {
        return RoomRepositoryViewModel.class;
    }

    @Override
    public void setupView() {
        setTitle("RoomRepository演示");
        mAdapter = new PoetryAdapter();
        mRvPoetry.setLayoutManager(new LinearLayoutManager(this));
        mRvPoetry.setAdapter(mAdapter);
        mAdapter.bindToRecyclerView(mRvPoetry);

        mLayoutRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.refreshDataIfNeed();
            }
        });
    }

    @Override
    protected void setupViewModel(@Nullable RoomRepositoryViewModel viewModel) {
        super.setupViewModel(viewModel);
        viewModel.getData().observe(this, new Observer<List<Poetry>>() {
            @Override
            public void onChanged(List<Poetry> poetryList) {
                mAdapter.setNewData(poetryList);
            }
        });
    }

    @Override
    protected void onLoadStatusChanged(int status, int requestCode) {
        mLayoutRefresh.setRefreshing(status == BaseViewModel.LOADING);
    }
}