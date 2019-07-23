package gnnt.practice.dailypage.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import gnnt.practice.dailypage.R;
import gnnt.practice.dailypage.vo.HistoryWorkInfo;

/**********************************************************
 *  WeekTimeAdapter.java  2019-07-12
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class WeekTimeAdapter extends BaseQuickAdapter<HistoryWorkInfo, BaseViewHolder> {

    public WeekTimeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryWorkInfo item) {
        helper.setText(R.id.tv_week,item.week);
        helper.setText(R.id.tv_date,item.date);
        //处理内部recycler view
        WeekTimeChildAdapter adapter = new WeekTimeChildAdapter(R.layout.item_week_child, item.hisWorkList);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        RecyclerView recyclerView = helper.getView(R.id.recyclerview_week);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
