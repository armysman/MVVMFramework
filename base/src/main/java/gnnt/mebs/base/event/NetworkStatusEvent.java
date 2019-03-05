package gnnt.mebs.base.event;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import gnnt.mebs.base.liveData.RealChangeLiveData;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/*******************************************************************
 * NetworkBroadcastReceiver.java  2018/12/29
 * <P>
 * 网络状态广播监听<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class NetworkStatusEvent extends BroadcastReceiver {

    /**
     * 没有网络
     */
    public static final int TYPE_NONE = -1;

    /**
     * WIFI
     */
    public static final int TYPE_MOBILE = 0;

    /**
     * 移动数据
     */
    public static final int TYPE_WIFI = 1;

    /**
     * 网络状态数据
     */
    private RealChangeLiveData<Integer> mNetworkStatus = new RealChangeLiveData<>();

    public NetworkStatusEvent(Context context) {
        context.registerReceiver(this, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        mNetworkStatus.setValue(getNetworkStatus(context));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果是网络发生改变
        if (intent != null && intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            //获取联网状态
            int type = getNetworkStatus(context);
            mNetworkStatus.setValue(type);
        }
    }

    /**
     * 检测手机网络状态
     *
     * @param context
     * @return
     */
    public static int getNetworkStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return TYPE_WIFI;
            } else if (networkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return TYPE_MOBILE;
            } else {
                return TYPE_NONE;
            }
        } else {
            return TYPE_NONE;
        }
    }

    /**
     * 监听网络状态改变
     *
     * @param owner    生命周期
     * @param observer 观察者
     */
    public void observeNetworkStatus(LifecycleOwner owner, Observer<Integer> observer) {
        mNetworkStatus.observe(owner, observer);
    }
}
