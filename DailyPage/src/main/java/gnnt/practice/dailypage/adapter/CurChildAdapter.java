package gnnt.practice.dailypage.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import gnnt.practice.dailypage.R;
import gnnt.practice.dailypage.vo.TodayWorkInfo;

/**********************************************************
 *  CurChildAdapter.java  2019-07-11
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class CurChildAdapter extends BaseQuickAdapter<TodayWorkInfo.Work, BaseViewHolder> {

    private CurWorkOnclik mCurWorkOnclik;

    public CurChildAdapter(int layoutResId, @Nullable List<TodayWorkInfo.Work> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TodayWorkInfo.Work item) {
        helper.setText(R.id.tv_time, item.startTime.substring(10) + "~" + item.endTime.substring(10));
        helper.setText(R.id.tv_type, item.workType.optionName);
        helper.setText(R.id.content, item.content);
        helper.getView(R.id.delete).setOnClickListener((View v) -> {
            if (mCurWorkOnclik != null) {
                mCurWorkOnclik.delete(item.dailyId);
            }
        });
        helper.getView(R.id.edit).setOnClickListener((View v) -> {
            if (mCurWorkOnclik != null) {
                mCurWorkOnclik.edit(item);
            }
        });
    }

    public void setCurWorkOnclik(CurWorkOnclik curWorkOnclik) {
        mCurWorkOnclik = curWorkOnclik;
    }

    public interface CurWorkOnclik {
        void delete(String dailyID);

        void edit(TodayWorkInfo.Work work);
    }
}
