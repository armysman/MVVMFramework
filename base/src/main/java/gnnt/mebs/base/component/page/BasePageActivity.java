package gnnt.mebs.base.component.page;


import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import gnnt.mebs.base.component.BaseActivity;
import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.base.util.Preconditions;

/*******************************************************************
 * BasePageActivity.java  2019/1/4
 * <P>
 * 基础信息分页界面<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public abstract class BasePageActivity<T extends BasePageViewModel> extends BaseActivity<T> {

    /**
     * 适配器
     */
    protected BaseQuickAdapter mAdapter;

    /**
     * 创建适配器
     *
     * @return 适配器
     */
    public @NonNull
    abstract BaseQuickAdapter createAdapter();

    /**
     * 子类实现绑定适配器等操作
     *
     * @param adapter 适配器
     */
    public abstract void setupView(BaseQuickAdapter adapter);

    @Override
    protected final void setupView() {
        // 创建适配器
        mAdapter = createAdapter();
        Preconditions.checkNotNull(mAdapter, "适配器不能为空");
        setupView(mAdapter);
    }

    @Override
    protected void setupViewModel(@Nullable T viewModel) {
        super.setupViewModel(viewModel);

        // 设置加载更多
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mViewModel.loadMore();
            }
        });

        // 监听数据改变
        viewModel.getData().observe(this, new Observer<List>() {
            @Override
            public void onChanged(@Nullable List infoItems) {
                mAdapter.setNewData(infoItems);
            }
        });

        // 是否有更多数据状态监听
        viewModel.hasMoreData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean hasMore) {
                // 如果没有数据了，则通知适配器已经没有数据
                if (!hasMore) {
                    mAdapter.loadMoreEnd();
                }
            }
        });
    }

    @Override
    protected void onLoadStatusChanged(int status, int requestCode) {
        if (mAdapter.getItemCount() > 0) {
            if (status == BaseViewModel.LOAD_ERROR) {
                mAdapter.loadMoreFail();
            } else if (status == BaseViewModel.LOAD_COMPLETE) {
                mAdapter.loadMoreComplete();
            }
        }
    }
}
