package gnnt.practice.dailypage.ui.week;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import gnnt.mebs.base.component.BaseFragment;
import gnnt.practice.dailypage.R;
import gnnt.practice.dailypage.adapter.WeekTimeAdapter;

/**********************************************************
 *  WeekChildFragment.java  2019-07-12
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class WeekChildFragment extends BaseFragment<WeekChildViewModel> {

    public static final String LAST_WEEK = "last_week";
    public static final String THIS_WEEK = "this_week";
    public static final String TIME_TAG = "time";
    public static final String PROJECT_TAG = "project";

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private WeekTimeAdapter mTimeAdapter;

    //    private String tag;
    private String week;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_week_child;
    }

    @NonNull
    @Override
    protected Class getViewModelClass() {
        return WeekChildViewModel.class;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
//            tag = bundle.getString("tag");
            week = bundle.getString("week");
        }
    }

    @Override
    protected void setupView(View rootView) {
        super.setupView(rootView);
        mTimeAdapter = new WeekTimeAdapter(R.layout.item_week);
        recyclerview.setAdapter(mTimeAdapter);
        mTimeAdapter.bindToRecyclerView(recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        //添加数据为空布局
        View emptyView = getLayoutInflater().inflate(R.layout.item_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT));
        mTimeAdapter.setEmptyView(emptyView);
        emptyView.setOnClickListener(v ->
                mViewModel.loadWeek());
    }

    @Override
    protected void setupViewModel(@NonNull WeekChildViewModel viewModel) {
        super.setupViewModel(viewModel);
        mViewModel.getTime().setValue(week);

        mViewModel.loadWeek();

        mViewModel.getData().observe(this, historyWorkInfos ->
                mTimeAdapter.setNewData(historyWorkInfos));

    }

    /**
     * @param tag  时间/项目
     * @param date 上周/本周
     * @return
     */
    public static WeekChildFragment getWeekChildFragment(String tag, String date) {
        WeekChildFragment fragment = new WeekChildFragment();
        Bundle bundle = new Bundle();
//        bundle.putString("tag", tag);
        bundle.putString("week", date);
        fragment.setArguments(bundle);
        return fragment;
    }

}
