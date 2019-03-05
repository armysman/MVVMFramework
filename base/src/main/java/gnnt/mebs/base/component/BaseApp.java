package gnnt.mebs.base.component;


import android.app.Application;


import androidx.annotation.NonNull;
import gnnt.mebs.base.BuildConfig;
import gnnt.mebs.base.event.NetworkStatusEvent;
import gnnt.mebs.base.http.RetrofitManager;
import gnnt.mebs.base.http.xml.XMLConverterFactory;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

/*******************************************************************
 * BaseApp.java  2018/11/29
 * <P>
 * 应用类，所以需要单例管理的对象，都在此处初始化<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public abstract class BaseApp extends Application {

    /**
     * 网络请求管理类
     */
    private RetrofitManager mRetrofitManager;

    /**
     * 网络状态事件
     */
    private NetworkStatusEvent mNetworkStatusEvent;

    /**
     * 获取http通信转换工厂类
     *
     * @return 通信协议转换工厂类
     */
    public @NonNull Converter.Factory getHttpConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 设置RxJava执行错误默认处理
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        // 初始化网络请求类
        mRetrofitManager = new RetrofitManager(getHttpConverterFactory());

        // 初始化网络状态事件
        mNetworkStatusEvent = new NetworkStatusEvent(this);
    }

    /**
     * 获取网络请求类
     *
     * @return 网络请求实例
     */
    public RetrofitManager getRetrofitManager() {
        return mRetrofitManager;
    }


    /**
     * 获取网络状态改变事件
     *
     * @return 网络状态改变事件
     */
    public NetworkStatusEvent getNetworkStatusEvent() {
        return mNetworkStatusEvent;
    }
}


