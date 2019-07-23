package gnnt.practice.dailypage.ui.current;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.OnClick;
import gnnt.mebs.base.component.BaseFragment;
import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.common.RouteMap;
import gnnt.practice.dailypage.R;
import gnnt.practice.dailypage.adapter.CurChildAdapter;
import gnnt.practice.dailypage.adapter.CurrentAdapter;
import gnnt.practice.dailypage.vo.TodayWorkInfo;

import static android.app.Activity.RESULT_OK;

/**********************************************************
 *  CurrentWorkFragment.java  2019-07-09
 *  <p>
 *  当前日报
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class CurrentWorkFragment extends BaseFragment<CurrentWorkViewModel> {
    public static final String TAG = "CurrentWorkFragment";
    public static final int REQUEST_CODE = 5;
    public static final String UPDATE = "update";
    public static final String ADD = "add";
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swiperefreah)
    SwipeRefreshLayout swiperefreah;
    @BindView(R.id.add)
    ImageButton add;
    private CurrentAdapter mAdapter;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_current;
    }

    @NonNull
    @Override
    protected Class<? extends CurrentWorkViewModel> getViewModelClass() {
        return CurrentWorkViewModel.class;
    }

    @Override
    protected void setupView(View rootView) {
        super.setupView(rootView);
        mAdapter = new CurrentAdapter(R.layout.item_current);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(mAdapter);
        mAdapter.bindToRecyclerView(recyclerview);
        swiperefreah.setOnRefreshListener(() ->
                mViewModel.loadData());
        mAdapter.setCurWorkOnclik(mCurWorkOnclik);

        //添加数据为空布局
        View emptyView = getLayoutInflater().inflate(R.layout.item_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT));
        mAdapter.setEmptyView(emptyView);
        emptyView.setOnClickListener(v ->
                mViewModel.loadData());
    }

    private CurChildAdapter.CurWorkOnclik mCurWorkOnclik = new CurChildAdapter.CurWorkOnclik() {
        @Override
        public void delete(String dailyID) {
            mViewModel.getDailyID().setValue(dailyID);
            mViewModel.delete();
        }

        @Override
        public void edit(TodayWorkInfo.Work work) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("work", work);
            //跳转编辑页面
            ARouter.getInstance().build(RouteMap.DailyPage.ADD).withBundle("work", bundle)
                    .withString("tag", UPDATE).navigation(getActivity(), REQUEST_CODE);
//            ARouter.getInstance().build(RouteMap.DailyPage.ADD).withString("test","11111").withObject("work", work).navigation();
//            ARouter.getInstance().build(RouteMap.DailyPage.ADD).withSerializable("work",work).navigation(getContext());
        }
    };

  /*    @NonNull
    @Override
    public BaseQuickAdapter createAdapter() {
        return new CurrentAdapter(R.layout.item_current);
    }

    @Override
    public void setupView(BaseQuickAdapter adapter) {
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(adapter);
        adapter.bindToRecyclerView(recyclerview);

        swiperefreah.setOnRefreshListener(() ->
                mViewModel.loadRefresh());
    }*/

    @Override
    protected void setupViewModel(@Nullable CurrentWorkViewModel viewModel) {
        super.setupViewModel(viewModel);
        mViewModel.loadData();

        mViewModel.getData().observe(this, dailyWorks -> {
            //更新adapter
            mAdapter.setNewData(dailyWorks);
        });

        mViewModel.getDeleteState().observe(this, isSuccess -> {
            if (isSuccess) {
                showMessage("删除成功");
            } else {
                showMessage("删除失败，请重新操作");
            }
        });
    }


    @OnClick(R.id.add)
    public void add() {
        ARouter.getInstance().build(RouteMap.DailyPage.ADD).withString("tag", ADD).navigation(getActivity(), REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mViewModel.loadData();
            }
        }
    }

    @Override
    protected void onLoadStatusChanged(int status, int requestCode) {
        super.onLoadStatusChanged(status, requestCode);
        if (status == BaseViewModel.LOAD_COMPLETE || status == BaseViewModel.LOAD_ERROR) {
            swiperefreah.setRefreshing(false);
        }
    }
}
