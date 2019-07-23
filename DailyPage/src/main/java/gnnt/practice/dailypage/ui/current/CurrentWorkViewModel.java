package gnnt.practice.dailypage.ui.current;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.common.util.ResponseTransformer;
import gnnt.practice.dailypage.MemeoryData;
import gnnt.practice.dailypage.vo.Config;
import gnnt.practice.dailypage.vo.DailyPageApi;
import gnnt.practice.dailypage.vo.TodayWorkInfo;
import gnnt.practice.dailypage.vo.reqvo.DeleteReqVO;
import gnnt.practice.dailypage.vo.reqvo.TodayWorkReqVO;
import gnnt.practice.dailypage.vo.rspvo.TodayWorkRepVO;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**********************************************************
 *  CurrentWorkViewModel.java  2019-07-09
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class CurrentWorkViewModel extends BaseViewModel {

    private MutableLiveData<List<TodayWorkInfo>> mData = new MutableLiveData<>();

    private MutableLiveData<String> mDailyID = new MutableLiveData<>();

    private MutableLiveData<Boolean> mDeleteState = new MutableLiveData<>();

    public CurrentWorkViewModel(Application application) {
        super(application);
    }

    public void loadData() {
        DailyPageApi api = getApplication().getRetrofitManager().getApi(Config.NET_HOST, DailyPageApi.class);
        TodayWorkReqVO reqVO = new TodayWorkReqVO();
        reqVO.cookie = MemeoryData.getInstance().getCookie();
        api.today(reqVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new ResponseTransformer<>())
                .subscribe(new ViewModelSingleObserver<TodayWorkRepVO>() {
                    @Override
                    public void onSuccess(TodayWorkRepVO todayWorkRepVO) {
                        super.onSuccess(todayWorkRepVO);
                        List<TodayWorkInfo> data = formatProject(todayWorkRepVO.dailyList);
                        mData.setValue(data);
                    }
                });
    }

    public void delete() {
        DailyPageApi api = getApplication().getRetrofitManager().getApi(Config.NET_HOST, DailyPageApi.class);
        DeleteReqVO reqVO = new DeleteReqVO();
        reqVO.dailyId = mDailyID.getValue();
        reqVO.cookie = MemeoryData.getInstance().getCookie();
        api.delete(reqVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.compose(new ResponseTransformer<>())
                .subscribe(deleteRepVO -> {
                    //提示删除成功
                    if (deleteRepVO.resultCode >= 0) {
                        mDeleteState.setValue(true);
                    } else {
                        mDeleteState.setValue(false);
                    }
                    //刷新数据
                    loadData();

                });
    }

/*    @Override
    public Single<ListResponse<TodayWorkInfo>> onLoad(int pageNO, int pageSize) {
        DailyPageApi api = getApplication().getRetrofitManager().getApi(Config.NET_HOST, DailyPageApi.class);
        TodayWorkReqVO reqVO = new TodayWorkReqVO();
        reqVO.cookie = MemeoryData.getInstance().getCookie();
        return api.today(reqVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new ResponseTransformer<>())
                .map(todayWorkRepVO -> {
                    //转换
                    List<TodayWorkInfo> data = formatProject(todayWorkRepVO.dailyList);
                    return new ListResponse<>(data.size(), data);
                });
    }*/

/*    @Override
    public void refreshDataIfNeed() {
        loadRefresh();
    }*/

    public List<TodayWorkInfo> formatProject(List<TodayWorkRepVO.DailyWork> data) {
        List<TodayWorkInfo> result = new ArrayList<>();
        if (data != null && data.size() > 0) {
            for (TodayWorkRepVO.DailyWork datum : data) {
                TodayWorkInfo info = new TodayWorkInfo();
                info.project = datum.project;
                info.workList = new ArrayList<>();
                if (!result.contains(info)) {
                    result.add(info);
                }

            }
            for (TodayWorkInfo info : result) {
                for (TodayWorkRepVO.DailyWork datum : data) {
                    if (info.project.optionId.equals(datum.project.optionId)) {
                        TodayWorkInfo.Work work = new TodayWorkInfo.Work();
                        work.dailyId = datum.dailyId;
                        work.content = datum.content;
                        work.startTime = datum.startTime;
                        work.endTime = datum.endTime;
                        work.workType = datum.workType;
                        work.project = datum.project;
                        info.workList.add(work);
                    }
                }
            }
        }
        return result;
    }

    public MutableLiveData<List<TodayWorkInfo>> getData() {
        return mData;
    }

    public MutableLiveData<String> getDailyID() {
        return mDailyID;
    }

    public MutableLiveData<Boolean> getDeleteState() {
        return mDeleteState;
    }
}
