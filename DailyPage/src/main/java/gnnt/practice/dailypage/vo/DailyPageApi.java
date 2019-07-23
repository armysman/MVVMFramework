package gnnt.practice.dailypage.vo;

import gnnt.practice.dailypage.vo.reqvo.AddReqVO;
import gnnt.practice.dailypage.vo.reqvo.ConfigReqVO;
import gnnt.practice.dailypage.vo.reqvo.DeleteReqVO;
import gnnt.practice.dailypage.vo.reqvo.HistoryReqVO;
import gnnt.practice.dailypage.vo.reqvo.LoginReqVO;
import gnnt.practice.dailypage.vo.reqvo.TodayWorkReqVO;
import gnnt.practice.dailypage.vo.reqvo.UpdateReqVO;
import gnnt.practice.dailypage.vo.reqvo.VerityCodeReqVO;
import gnnt.practice.dailypage.vo.reqvo.WeekReqVO;
import gnnt.practice.dailypage.vo.rspvo.AddRepVO;
import gnnt.practice.dailypage.vo.rspvo.ConfigRepVO;
import gnnt.practice.dailypage.vo.rspvo.DeleteRepVO;
import gnnt.practice.dailypage.vo.rspvo.HistoryRepVO;
import gnnt.practice.dailypage.vo.rspvo.LoginRepVO;
import gnnt.practice.dailypage.vo.rspvo.TodayWorkRepVO;
import gnnt.practice.dailypage.vo.rspvo.UpdateRepVO;
import gnnt.practice.dailypage.vo.rspvo.VerityCodeRepVO;
import gnnt.practice.dailypage.vo.rspvo.WeekRepVO;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**********************************************************
 *  DailyPageApi.java  2019-07-08
 *  <p>
 *  网络请求API
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public interface DailyPageApi {
    @POST(Config.NET_PATH + Config.VERIFY_CODE)
    Single<VerityCodeRepVO> reqverityCode(@Body VerityCodeReqVO params);

    @POST(Config.NET_PATH + Config.LOGIN)
    Single<LoginRepVO> login(@Body LoginReqVO params);

    @POST(Config.NET_PATH + Config.INSERT)
    Single<AddRepVO> add(@Body AddReqVO params);

    @POST(Config.NET_PATH + Config.CONFIG)
    Single<ConfigRepVO> config(@Body ConfigReqVO params);

    @POST(Config.NET_PATH + Config.DELETE)
    Single<DeleteRepVO> delete(@Body DeleteReqVO params);

    @POST(Config.NET_PATH + Config.HISTORY)
    Single<HistoryRepVO> history(@Body HistoryReqVO params);

    @POST(Config.NET_PATH + Config.TODAY)
    Single<TodayWorkRepVO> today(@Body TodayWorkReqVO params);

    @POST(Config.NET_PATH + Config.UDATE)
    Single<UpdateRepVO> update(@Body UpdateReqVO params);

    @POST(Config.NET_PATH + Config.WEEK)
    Single<WeekRepVO> week(@Body WeekReqVO params);

}
