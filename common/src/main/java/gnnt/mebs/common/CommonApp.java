package gnnt.mebs.common;

import com.alibaba.android.arouter.launcher.ARouter;
import com.squareup.leakcanary.LeakCanary;

import gnnt.mebs.base.BaseApp;
import gnnt.mebs.common.event.LoginUserEvent;

/*******************************************************************
 * CommonApp.java  2019/3/5
 * <P>
 * <br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class CommonApp extends BaseApp {

    /**
     * 登录用户事件类
     */
    private LoginUserEvent mLoginUserEvent;

    @Override
    public void onCreate() {
        super.onCreate();

        // 内存泄露检测
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        // 内存泄露检测
        LeakCanary.install(this);

        // ARouter 初始化
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug(); // 没用这句不能在debug模式运行
        }
        ARouter.init(this);

        // 初始化事件类
        mLoginUserEvent = new LoginUserEvent(this);
    }

    /**
     * 获取登录用户事件监听
     *
     * @return 登录用户事件
     */
    public LoginUserEvent getLoginUserEvent() {
        return mLoginUserEvent;
    }


}
