package gnnt.practice.dailypage.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import gnnt.practice.dailypage.R;
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
public class CurrentAdapter extends BaseQuickAdapter<TodayWorkInfo, BaseViewHolder> {

    private CurChildAdapter.CurWorkOnclik mCurWorkOnclik;

    public CurrentAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder holder, TodayWorkInfo item) {
        holder.setText(R.id.title, item.project.optionName);
        //处理内部recycler view
        CurChildAdapter adapter = new CurChildAdapter(R.layout.item_current_child, item.workList);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        RecyclerView recyclerView = holder.getView(R.id.recyclerView_cur);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setCurWorkOnclik(mCurWorkOnclik);
    }

    public void setCurWorkOnclik(CurChildAdapter.CurWorkOnclik curWorkOnclik) {
        mCurWorkOnclik = curWorkOnclik;
    }
}
