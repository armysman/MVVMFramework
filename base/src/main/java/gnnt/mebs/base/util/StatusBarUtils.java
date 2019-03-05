package gnnt.mebs.base.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/*******************************************************************
 * StatusBarUtils.java  2018/12/13
 * <P>
 * 状态栏设置帮助类<br/>
 * <br/>
 * </p>
 * Copyright2018 by GNNT Company. All Rights Reserved.
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class StatusBarUtils {

    private static final int OTHER = -1;
    private static final int MIUI = 1;
    private static final int FLYME = 2;
    private static final int ANDROID_M = 3;


    /**
     * 设置状态栏颜色
     *
     * @param activity   所属activity
     * @param color      颜色值
     * @param isDarkText 是否黑色字体
     */
    public static void setStatusBar(Activity activity, int color, boolean isDarkText) {
        if (activity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // 设置背景颜色
            window.setStatusBarColor(color);
            // 设置字体颜色
            if (isDarkText) {
                StatusBarUtils.statusBarLightMode(activity);
            } else {
                StatusBarUtils.statusBarDarkMode(activity);
            }
        }
    }

    /**
     * 设置状态栏黑色字体图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @return 1:MIUI 2:Flyme 3:android6.0
     */
    public static int statusBarLightMode(Activity activity) {
        int result = OTHER;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUIHelper.setStatusBarLightMode(activity, true)) {
                result = MIUI;
            } else if (FlymeHelper.setStatusBarLightMode(activity, true)) {
                result = FLYME;
            } else if (AndroidMHelper.setStatusBarLightMode(activity, true)) {
                result = ANDROID_M;
            }
        }
        return result;
    }

    /**
     * 设置状态栏白色字体图标
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @return 1:MIUI 2:Flyme 3:android6.0
     */
    public static int statusBarDarkMode(Activity activity) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUIHelper.setStatusBarLightMode(activity, false)) {
                result = MIUI;
            } else if (FlymeHelper.setStatusBarLightMode(activity, false)) {
                result = FLYME;
            } else if (AndroidMHelper.setStatusBarLightMode(activity, false)) {
                result = ANDROID_M;
            }
        }
        return result;
    }


    public static class AndroidMHelper {
        /**
         * @return if version is lager than M
         */
        public static boolean setStatusBarLightMode(Activity activity, boolean isFontColorDark) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (isFontColorDark) {
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                }
                return true;
            }
            return false;
        }

    }

    public static class FlymeHelper {

        /**
         * 设置状态栏图标为深色和魅族特定的文字风格
         * 可以用来判断是否为Flyme用户
         *
         * @param isFontColorDark 是否把状态栏字体及图标颜色设置为深色
         * @return boolean 成功执行返回true
         */
        public static boolean setStatusBarLightMode(Activity activity, boolean isFontColorDark) {
            Window window = activity.getWindow();
            boolean result = false;
            if (window != null) {
                try {
                    WindowManager.LayoutParams lp = window.getAttributes();
                    Field darkFlag = WindowManager.LayoutParams.class
                            .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                    Field meizuFlags = WindowManager.LayoutParams.class
                            .getDeclaredField("meizuFlags");
                    darkFlag.setAccessible(true);
                    meizuFlags.setAccessible(true);
                    int bit = darkFlag.getInt(null);
                    int value = meizuFlags.getInt(lp);
                    if (isFontColorDark) {
                        value |= bit;
                    } else {
                        value &= ~bit;
                    }
                    meizuFlags.setInt(lp, value);
                    window.setAttributes(lp);
                    result = true;
                } catch (Exception e) {
                }
            }
            return result;
        }
    }

    public static class MIUIHelper {

        /**
         * 设置状态栏字体图标为深色，需要MIUI6以上
         *
         * @param isFontColorDark 是否把状态栏字体及图标颜色设置为深色
         * @return boolean 成功执行返回true
         */
        public static boolean setStatusBarLightMode(Activity activity, boolean isFontColorDark) {
            Window window = activity.getWindow();
            boolean result = false;
            if (window != null) {
                Class clazz = window.getClass();
                try {
                    int darkModeFlag = 0;
                    Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                    Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                    darkModeFlag = field.getInt(layoutParams);
                    Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                    if (isFontColorDark) {
                        extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                    } else {
                        extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                    }
                    result = true;
                } catch (Exception e) {
                }
            }
            return result;
        }
    }
}