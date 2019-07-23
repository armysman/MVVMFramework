package gnnt.practice.dailypage.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import gnnt.practice.dailypage.R;
import gnnt.practice.dailypage.vo.HistoryWorkInfo;
import gnnt.practice.dailypage.vo.TodayWorkInfo;

/**********************************************************
 *  CurChildAdapter.java  2019-07-11
 *  <p>
 *  历史日报子adapter
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class HisChildAdapter extends BaseQuickAdapter<HistoryWorkInfo.HisWork, BaseViewHolder> {

    public HisChildAdapter(int layoutResId, @Nullable List<HistoryWorkInfo.HisWork> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryWorkInfo.HisWork item) {
        helper.setText(R.id.tv_time, item.startTime.substring(10) + "~" + item.endTime.substring(10));
        helper.setText(R.id.tv_type, item.workType.optionName);
        helper.setText(R.id.tv_project,item.project.optionName);
        helper.setText(R.id.content, item.content);
    }
}
