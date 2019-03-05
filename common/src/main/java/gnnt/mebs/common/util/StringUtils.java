package gnnt.mebs.common.util;

import android.text.TextUtils;

/*******************************************************************
 * StringUtils.java  2019/1/3
 * <P>
 * <br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class StringUtils {

    /**
     * 当字符串为null或者empty，则返回默认字符串
     *
     * @param string        字符串
     * @param defaultString 默认字符
     * @return
     */
    public static final String emptyDefaultString(String string, String defaultString) {
        if (TextUtils.isEmpty(string)) {
            return defaultString;
        }
        return string;
    }

    public static final String nonNullString(String string) {
        return emptyDefaultString(string, "");
    }
}
