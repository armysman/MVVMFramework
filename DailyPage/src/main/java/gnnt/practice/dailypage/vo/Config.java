package gnnt.practice.dailypage.vo;

/**********************************************************
 *  Config.java  2019-07-08
 *  <p>
 *  网络配置类
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public interface Config {
    /**
     * host_path
     */
    String NET_PATH = "/DailyServer";
    /**
     * host地址
     */
    String NET_HOST = "http://124.207.182.165:4022";

    /**
     * 验证码
     */
    String VERIFY_CODE = "/VerifyCode";
    /**
     * 登录
     */
    String LOGIN = "/Login";
    /**
     * 配置信息
     */
    String CONFIG = "/Config";
    /**
     * 今天已写日报
     */
    String TODAY = "/TodayDaily";
    /**
     * 历史日报
     */
    String HISTORY = "/HistoryDaily";
    /**
     * 周报
     */
    String WEEK = "/WeekDaily";
    /**
     * 周报
     */
    String INSERT = "/InsertDaily";
    /**
     * 周报
     */
    String DELETE = "/DeleteDaily";
    /**
     * 周报
     */
    String UDATE = "/UpdateDaily";
}
