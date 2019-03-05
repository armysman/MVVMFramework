package gnnt.mebs.base.util;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/*******************************************************************
 * PermissionUtils.java  2018/12/19
 * <P>
 * 权限检测工具<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class PermissionUtils {

    /**
     * 判断是否有权限
     *
     * @param permission 权限String
     * @return true 有权限，false 没有权限
     */
    public static boolean hadPermission(Context context, String permission) {
        int result = ContextCompat.checkSelfPermission(context, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 是否有写文件权限
     *
     * @param context 上下文
     * @return true 有权限，false 没有权限
     */
    public static boolean hadWriteExternalPermission(Context context) {
        return hadPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 是否有读文件权限
     *
     * @param context 上下文
     * @return true 有权限，false 没有权限
     */
    public static boolean hadReadExternalPermission(Context context) {
        return hadPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    /**
     * 是否有读写文件权限
     *
     * @param context 上下文
     * @return true 有权限，false 没有权限
     */
    public static boolean hadReadWritePermission(Context context) {
        return hadReadExternalPermission(context) && hadWriteExternalPermission(context);
    }

    /**
     * 是否可以再次请求权限
     *
     * @param activity activity
     * @return true 可以 false 不可用
     */
    public static boolean shouldRequestReadWritePermission(Activity activity) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                && ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
}
