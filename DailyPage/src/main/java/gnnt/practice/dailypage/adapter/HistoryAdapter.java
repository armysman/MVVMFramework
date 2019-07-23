package gnnt.practice.dailypage.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import gnnt.practice.dailypage.R;
import gnnt.practice.dailypage.vo.HistoryWorkInfo;
import gnnt.practice.dailypage.vo.TodayWorkInfo;

/**********************************************************
 *  CurrentAdapter.java  2019-07-10
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class HistoryAdapter extends BaseQuickAdapter<HistoryWorkInfo, BaseViewHolder> {

    public HistoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder holder, HistoryWorkInfo item) {
        holder.setText(R.id.tv_date, item.date);
        holder.setText(R.id.tv_week, item.week);
        //处理内部recycler view
        HisChildAdapter adapter = new HisChildAdapter(R.layout.item_history_child, item.hisWorkList);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        RecyclerView recyclerView = holder.getView(R.id.recyclerview_his);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
