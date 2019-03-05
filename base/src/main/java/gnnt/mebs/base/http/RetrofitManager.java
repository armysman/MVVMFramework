package gnnt.mebs.base.http;


import android.text.TextUtils;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*******************************************************************
 * RetrofitInstance.java 2017/4/21
 * <P>
 * Retrofit请求管理类<br/>
 * <br/>
 * </p>
 * Copyright2017 by GNNT CompanyVO. All Rights Reserved.
 *
 * @author:Zhoupeng
 ******************************************************************/
public class RetrofitManager {

    /**
     * 主机列表
     */
    private Map<String, Retrofit> mHostMap = new HashMap<>();
    /**
     * 保存各个 api 实例
     */
    private Map<String, Object> mApiMap = new HashMap<>();

    /**
     * 协议转换工厂
     */
    private Converter.Factory mConvertFactory;

    /**
     * 锁
     */
    private Object mLock = new Object();


    /**
     * 构造方法
     */
    public RetrofitManager(Converter.Factory converterFactory) {
        if (converterFactory == null) {
            converterFactory = GsonConverterFactory.create();
        }
        mConvertFactory = converterFactory;
    }


    /**
     * 获取 API
     *
     * @return 返回 api 实例
     */
    public <T> T getApi(Class<T> apiClass) {
        return getApi(null, apiClass);
    }

    /**
     * 获取Api
     *
     * @param host     主机地址
     * @param apiClass api类
     * @param <T>
     * @return
     */
    public <T> T getApi(String host, Class<T> apiClass) {
        host = parseToUrl(host);
        String apiKey = String.format("%s-%s", host, apiClass.getName());
        // 加上线程锁，避免重复创建
        synchronized (mLock) {
            Object api = mApiMap.get(apiKey);
            // 如果api实例为空或api类型不是 T
            if (api == null || !api.getClass().equals(apiClass)) {
                api = getRetrofit(host).create(apiClass);
                mApiMap.put(apiKey, api);
            }
            return (T) api;
        }
    }

    /**
     * 获取对应Retrofit实例
     *
     * @param host 主机地址
     * @return 返回Retrofit实例
     */
    public Retrofit getRetrofit(String host) {
        host = parseToUrl(host);
        Retrofit retrofit = mHostMap.get(host);
        if (retrofit == null) {
            retrofit = createHostRetrofit(host);
            mHostMap.put(host, retrofit);
        }
        return retrofit;
    }


    /**
     * 创建对应Host的通讯对象
     *
     * @param host 主机地址
     * @return Retrofit
     */
    private Retrofit createHostRetrofit(String host) {
        // 初始化 OKHttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(HttpConfig.REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();


        return new Retrofit.Builder()
                .client(client)
                .baseUrl(host)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(mConvertFactory)
                .build();
    }

    /**
     * 解析host为url格式
     *
     * @param host
     * @return
     */
    private String parseToUrl(String host) {
        if (TextUtils.isEmpty(host)) {
            host = "http://127.0.0.1/";
        } else if (!host.startsWith("http://") && !host.startsWith("https://")) {
            host = String.format("http://%s", host);
        }
        return host;
    }

}
