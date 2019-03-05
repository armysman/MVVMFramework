package gnnt.mebs.common.util;

import android.content.Context;
import android.content.SharedPreferences;

/*******************************************************************
 * SharedPreferenceUtils.java  2018/11/30
 * <P>
 * 本地数据存储<br/>
 * <br/>
 * </p>
 * Copyright2018 by GNNT Company. All Rights Reserved.
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class SharedPreferenceUtils {
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "shareData";


    /**
     * 根据类型保存数据
     *
     * @param context 上下文
     * @param key     保存的键
     * @param object  保存的值
     */
    public static void setParam(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }


    /**
     * 根据类型获取数据
     *
     * @param context       上下文
     * @param key           保存的键
     * @param defaultObject 默认值
     * @return 返回保存内容
     */
    public static Object getParam(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return defaultObject;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context 上下文
     * @param key     保存的键
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清除所有数据
     *
     * @param context 上下文
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context 上下文
     * @param key     保存的键
     * @return true 存在，false 不存在
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }
}
