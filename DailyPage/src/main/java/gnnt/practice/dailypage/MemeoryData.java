package gnnt.practice.dailypage;

/**********************************************************
 *  MemeoryData.java  2019-07-09
 *  <p>
 *  内存缓存
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class MemeoryData {

    private String mCookie;

    private static MemeoryData sInstance;

    public static MemeoryData getInstance() {
        if (sInstance == null) {
            synchronized (MemeoryData.class) {
                if (sInstance == null) {
                    sInstance = new MemeoryData();
                }
            }
        }
        return sInstance;
    }

    public String getCookie() {
        return mCookie;
    }

    public void setCookie(String cookie) {
        mCookie = cookie;
    }

    private void destroy() {
        mCookie = "";
    }

    public static void destroyInstance() {
        if (sInstance != null) {
            sInstance.destroy();
        }
        sInstance = null;
    }
}
