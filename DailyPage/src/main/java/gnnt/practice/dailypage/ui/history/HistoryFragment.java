package gnnt.practice.dailypage.ui.history;

import android.app.DatePickerDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.base.component.page.BasePageFragment;
import gnnt.practice.dailypage.R;
import gnnt.practice.dailypage.adapter.HistoryAdapter;

/**********************************************************
 *  HistoryFragment.java  2019-07-10
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class HistoryFragment extends BasePageFragment<HistoryViewModel> {

    public static final String TAG = "HistoryFragment";

    @BindView(R.id.et_start_date)
    EditText etStartDate;
    @BindView(R.id.et_end_date)
    EditText etEndDate;
    @BindView(R.id.recyclerview_history)
    RecyclerView recyclerviewHistory;
    @BindView(R.id.SwipeRefresh)
    SwipeRefreshLayout SwipeRefresh;
    @BindView(R.id.search)
    ImageView search;


    private int mStartYear;
    private int mStartMouth;
    private int mStartDay;
    private int mEndYear;
    private int mEndMouth;
    private int mEndDay;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_history;
    }

    @NonNull
    @Override
    protected Class<? extends HistoryViewModel> getViewModelClass() {
        return HistoryViewModel.class;
    }

    @NonNull
    @Override
    public BaseQuickAdapter createAdapter() {
        return new HistoryAdapter(R.layout.item_history);
    }

    @Override
    public void setupView(BaseQuickAdapter adapter) {
        recyclerviewHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerviewHistory.setAdapter(adapter);
        adapter.bindToRecyclerView(recyclerviewHistory);
        SwipeRefresh.setOnRefreshListener(() ->
                mViewModel.loadRefresh());

        //添加数据为空布局
        View emptyView = getLayoutInflater().inflate(R.layout.item_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT));
        mAdapter.setEmptyView(emptyView);
        emptyView.setOnClickListener(v ->
                mViewModel.loadRefresh());
    }

    @Override
    protected void setupViewModel(@Nullable HistoryViewModel viewModel) {
        super.setupViewModel(viewModel);
        mViewModel.loadRefresh();
        mViewModel.getData().observe(this, historyWorkInfos ->
                mAdapter.setNewData(historyWorkInfos));
        //初始化日期
        initCurDate();
    }

    private void initCurDate() {
        Calendar calendar = Calendar.getInstance();
        mStartYear = calendar.get(Calendar.YEAR);
        mStartMouth = calendar.get(Calendar.MONTH);
        mStartDay = calendar.get(Calendar.DAY_OF_MONTH);
        mEndYear = calendar.get(Calendar.YEAR);
        mEndMouth = calendar.get(Calendar.MONTH);
        mEndDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void initDate() {
        //默认查询最近十天
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = simpleDateFormat.format(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -10);
        String preDate = simpleDateFormat.format(calendar.getTime());
        mViewModel.getStartDate().setValue(curDate);
        mViewModel.getEndDate().setValue(preDate);
    }

    @OnClick(R.id.et_start_date)
    public void startDateOnClick() {
        new DatePickerDialog(getActivity(),
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    mStartYear = year;
                    mStartMouth = month;
                    mStartDay = dayOfMonth;
                    String curMouth = "";
                    String curDay = "";
                    if (month < 9) {
                        curMouth = "0" + (month + 1);
                    } else {
                        curMouth = "" + (month + 1);
                    }
                    if (dayOfMonth < 10) {
                        curDay = "0" + dayOfMonth;
                    } else {
                        curDay = "" + dayOfMonth;
                    }
                    etStartDate.setText(year + "-" + curMouth + "-" + curDay);

                }, mStartYear, mStartMouth, mStartDay).show();
    }

    @OnClick(R.id.et_end_date)
    public void endDateOnClick() {
        new DatePickerDialog(getActivity(),
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    mEndYear = year;
                    mEndMouth = month;
                    mEndDay = dayOfMonth;
                    String curMouth = "";
                    String curDay = "";
                    if (month < 9) {
                        curMouth = "0" + (month + 1);
                    } else {
                        curMouth = "" + (month + 1);
                    }
                    if (dayOfMonth < 10) {
                        curDay = "0" + dayOfMonth;
                    } else {
                        curDay = "" + dayOfMonth;
                    }
                    etEndDate.setText(year + "-" + curMouth + "-" + curDay);
                }, mEndYear, mEndMouth, mEndDay).show();
    }

    @OnClick(R.id.search)
    public void search() {
        String startDate = etStartDate.getText().toString();
        String endDate = etEndDate.getText().toString();
        if (TextUtils.isEmpty(startDate)) {
            showMessage("请选择开始日期");
            return;
        }
        if (TextUtils.isEmpty(endDate)) {
            showMessage("请选择结束日期");
            return;
        }
        mViewModel.getStartDate().setValue(startDate);
        mViewModel.getEndDate().setValue(endDate);
        mViewModel.loadRefresh();
    }

    @Override
    protected void onLoadStatusChanged(int status, int requestCode) {
        super.onLoadStatusChanged(status, requestCode);
        if (status == BaseViewModel.LOAD_COMPLETE || status == BaseViewModel.LOAD_ERROR) {
            SwipeRefresh.setRefreshing(false);
        }
    }
}
