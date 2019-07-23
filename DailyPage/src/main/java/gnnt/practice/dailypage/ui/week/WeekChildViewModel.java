package gnnt.practice.dailypage.ui.week;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.common.util.ResponseTransformer;
import gnnt.practice.dailypage.MemeoryData;
import gnnt.practice.dailypage.vo.Config;
import gnnt.practice.dailypage.vo.DailyPageApi;
import gnnt.practice.dailypage.vo.HistoryWorkInfo;
import gnnt.practice.dailypage.vo.reqvo.WeekReqVO;
import gnnt.practice.dailypage.vo.rspvo.WeekRepVO;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**********************************************************
 *  WeekChildViewModel.java  2019-07-12
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class WeekChildViewModel extends BaseViewModel {

    private MutableLiveData<String> mTime = new MutableLiveData<>();
    private MutableLiveData<List<HistoryWorkInfo>> mData = new MutableLiveData<>();

    public WeekChildViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadWeek() {
        DailyPageApi api = getApplication().getRetrofitManager().getApi(Config.NET_HOST, DailyPageApi.class);
        WeekReqVO reqVO = new WeekReqVO();
        reqVO.cookie = MemeoryData.getInstance().getCookie();
        api.week(reqVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new ResponseTransformer<>())
                .subscribe(new ViewModelSingleObserver<WeekRepVO>() {
                    @Override
                    public void onSuccess(WeekRepVO weekRepVO) {
                        super.onSuccess(weekRepVO);
                        if (mTime.getValue().equals(WeekChildFragment.THIS_WEEK)) {
                            mData.setValue(formatThisProject(weekRepVO.thisWeek));
                        } else {
                            mData.setValue(formatLastProject(weekRepVO.lastWeek));
                        }
                    }
                });
    }


    public MutableLiveData<String> getTime() {
        return mTime;
    }

    public MutableLiveData<List<HistoryWorkInfo>> getData() {
        return mData;
    }

    public List<HistoryWorkInfo> formatLastProject(List<WeekRepVO.LastWeek> data) {
        List<HistoryWorkInfo> result = new ArrayList<>();
        if (data != null && data.size() > 0) {
            for (WeekRepVO.LastWeek datum : data) {
                HistoryWorkInfo info = new HistoryWorkInfo();
                //处理时间
                SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE");
                SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    info.date = dateFormat.format(timeFormat.parse(datum.startTime));
                    info.week = weekFormat.format(timeFormat.parse(datum.startTime));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                info.hisWorkList = new ArrayList<>();
                if (!result.contains(info)) {
                    result.add(info);
                }
            }
            for (HistoryWorkInfo info : result) {
                for (WeekRepVO.LastWeek datum : data) {
                    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String date = "";
                    try {
                        date = dateFormat.format(timeFormat.parse(datum.startTime));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (info.date.equals(date)) {
                        HistoryWorkInfo.HisWork work = new HistoryWorkInfo().new HisWork();
                        work.dailyId = datum.dailyId;
                        work.content = datum.content;
                        work.startTime = datum.startTime;
                        work.endTime = datum.endTime;
                        work.workType = datum.workType;
                        work.project = datum.project;
                        info.hisWorkList.add(work);
                    }
                }
            }
        }
        return result;
    }

    public List<HistoryWorkInfo> formatThisProject(List<WeekRepVO.ThisWeek> data) {
        List<HistoryWorkInfo> result = new ArrayList<>();
        if (data != null && data.size() > 0) {
            for (WeekRepVO.ThisWeek datum : data) {
                HistoryWorkInfo info = new HistoryWorkInfo();
                //处理时间
                SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE");
                SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    info.date = dateFormat.format(timeFormat.parse(datum.startTime));
                    info.week = weekFormat.format(timeFormat.parse(datum.startTime));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                info.hisWorkList = new ArrayList<>();
                if (!result.contains(info)) {
                    result.add(info);
                }
            }
            for (HistoryWorkInfo info : result) {
                for (WeekRepVO.ThisWeek datum : data) {
                    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String date = "";
                    try {
                        date = dateFormat.format(timeFormat.parse(datum.startTime));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (info.date.equals(date)) {
                        HistoryWorkInfo.HisWork work = new HistoryWorkInfo().new HisWork();
                        work.dailyId = datum.dailyId;
                        work.content = datum.content;
                        work.startTime = datum.startTime;
                        work.endTime = datum.endTime;
                        work.workType = datum.workType;
                        work.project = datum.project;
                        info.hisWorkList.add(work);
                    }
                }
            }
        }
        return result;
    }
}
