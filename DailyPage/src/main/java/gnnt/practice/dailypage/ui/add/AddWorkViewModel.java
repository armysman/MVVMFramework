package gnnt.practice.dailypage.ui.add;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.common.util.ResponseTransformer;
import gnnt.practice.dailypage.MemeoryData;
import gnnt.practice.dailypage.vo.Config;
import gnnt.practice.dailypage.vo.DailyPageApi;
import gnnt.practice.dailypage.vo.reqvo.AddReqVO;
import gnnt.practice.dailypage.vo.reqvo.ConfigReqVO;
import gnnt.practice.dailypage.vo.reqvo.TodayWorkReqVO;
import gnnt.practice.dailypage.vo.reqvo.UpdateReqVO;
import gnnt.practice.dailypage.vo.rspvo.ConfigRepVO;
import gnnt.practice.dailypage.vo.rspvo.TodayWorkRepVO;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**********************************************************
 *  AddWorkViewModel.java  2019-07-09
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class AddWorkViewModel extends BaseViewModel {

    private MutableLiveData<TodayWorkRepVO.DailyWork> mLastWork = new MutableLiveData<>();

    private MutableLiveData<ConfigRepVO> mConfig = new MutableLiveData<>();
    private MutableLiveData<AddReqVO> mReqVO = new MutableLiveData<>();
    private MutableLiveData<UpdateReqVO> mUpdateReqVO = new MutableLiveData<>();

    private MutableLiveData<Boolean> mSuccess = new MutableLiveData<>();

    public AddWorkViewModel(@NonNull Application application) {
        super(application);
    }

    public void queryCurrent() {
        DailyPageApi api = getApplication().getRetrofitManager().getApi(Config.NET_HOST, DailyPageApi.class);
        TodayWorkReqVO reqVO = new TodayWorkReqVO();
        reqVO.cookie = MemeoryData.getInstance().getCookie();
        api.today(reqVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new ResponseTransformer<>())
                .subscribe(todayWorkRepVO ->
                        mLastWork.setValue(todayWorkRepVO.dailyList.get(todayWorkRepVO.dailyList.size() - 1)));
    }

    public void queryConfig() {
        DailyPageApi api = getApplication().getRetrofitManager().getApi(Config.NET_HOST, DailyPageApi.class);
        ConfigReqVO reqVO = new ConfigReqVO();
        reqVO.cookie = MemeoryData.getInstance().getCookie();
        api.config(reqVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new ResponseTransformer<>())
                .subscribe(configRepVO ->
                        mConfig.setValue(configRepVO));
    }

    public void addWork() {
        DailyPageApi api = getApplication().getRetrofitManager().getApi(Config.NET_HOST, DailyPageApi.class);
        AddReqVO reqVO = mReqVO.getValue();
        api.add(reqVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new ResponseTransformer<>())
                .subscribe((addRepVO ->
                        mSuccess.setValue(true)));
    }

    public void updateWork() {
        DailyPageApi api = getApplication().getRetrofitManager().getApi(Config.NET_HOST, DailyPageApi.class);
        UpdateReqVO reqVO = mUpdateReqVO.getValue();
        api.update(reqVO).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new ResponseTransformer<>())
                .subscribe((updateRepVO ->
                        mSuccess.setValue(true)));
    }

    public MutableLiveData<TodayWorkRepVO.DailyWork> getLastWork() {
        return mLastWork;
    }

    public MutableLiveData<ConfigRepVO> getConfig() {
        return mConfig;
    }

    public MutableLiveData<AddReqVO> getReqVO() {
        return mReqVO;
    }

    public MutableLiveData<UpdateReqVO> getUpdateReqVO() {
        return mUpdateReqVO;
    }

    public MutableLiveData<Boolean> getSuccess() {
        return mSuccess;
    }

    public List<String> getFormatData(List<ConfigRepVO.Project> data) {
        List<String> result = new ArrayList<>();
        if (data == null || data.size() == 0) {
            return result;
        }
        for (ConfigRepVO.Project project : data) {
            result.add(project.projectName);
        }
        return result;

    }

    public List<String> getFormatWorkData(List<ConfigRepVO.WorkType> data) {
        List<String> result = new ArrayList<>();
        if (data == null || data.size() == 0) {
            return result;
        }
        for (ConfigRepVO.WorkType workType : data) {
            result.add(workType.optionName);
        }
        return result;

    }
}
