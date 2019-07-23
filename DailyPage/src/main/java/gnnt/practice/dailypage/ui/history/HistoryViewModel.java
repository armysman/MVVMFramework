package gnnt.practice.dailypage.ui.history;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import gnnt.mebs.base.component.page.BasePageViewModel;
import gnnt.practice.dailypage.MemeoryData;
import gnnt.practice.dailypage.vo.Config;
import gnnt.practice.dailypage.vo.DailyPageApi;
import gnnt.practice.dailypage.vo.HistoryWorkInfo;
import gnnt.practice.dailypage.vo.reqvo.HistoryReqVO;
import gnnt.practice.dailypage.vo.rspvo.HistoryRepVO;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**********************************************************
 *  HistoryViewModel.java  2019-07-10
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class HistoryViewModel extends BasePageViewModel<HistoryWorkInfo> {

    public MutableLiveData<String> mStartDate = new MutableLiveData<>();
    public MutableLiveData<String> mEndDate = new MutableLiveData<>();

//    private PublishSubject<String> mPublishSubject = PublishSubject.create();

    public HistoryViewModel(Application application) {
        super(application);
//        mStartDate.observeForever(s ->
//                mPublishSubject.onNext(s));
//        mPublishSubject.debounce(100, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(s -> loadRefresh());

        //默认查询最近十天
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = simpleDateFormat.format(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -60);
        String preDate = simpleDateFormat.format(calendar.getTime());
        mStartDate.setValue(preDate);
        mEndDate.setValue(curDate);
    }

    @Override
    public Single<ListResponse<HistoryWorkInfo>> onLoad(int pageNO, int pageSize) {
        DailyPageApi api = getApplication().getRetrofitManager().getApi(Config.NET_HOST, DailyPageApi.class);
        HistoryReqVO reqVO = new HistoryReqVO();
        reqVO.cookie = MemeoryData.getInstance().getCookie();
        reqVO.pageNO = pageNO;
        reqVO.pageSize = pageSize;
        reqVO.startDate = mStartDate.getValue();
        reqVO.endDate = mEndDate.getValue();
        return api.history(reqVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(historyRepVO -> {
                    List<HistoryWorkInfo> data = formatProject(historyRepVO.dailyList);
                    return new ListResponse<>(Integer.MAX_VALUE, data);
                });
    }

    @Override
    public void refreshDataIfNeed() {
        //super.refreshDataIfNeed();
        loadRefresh();
    }

    public MutableLiveData<String> getStartDate() {
        return mStartDate;
    }

    public MutableLiveData<String> getEndDate() {
        return mEndDate;
    }

    public  List<HistoryWorkInfo> formatProject(List<HistoryRepVO.HisDailyWork> data) {
        List<HistoryWorkInfo> result = new ArrayList<>();
        if (data != null && data.size() > 0) {
            for (HistoryRepVO.HisDailyWork datum : data) {
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
                for (HistoryRepVO.HisDailyWork datum : data) {
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
