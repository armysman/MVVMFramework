package gnnt.mebs.base.component;


import androidx.annotation.IntRange;

/*******************************************************************
 * LoadStatusSpec.java  2018/12/18
 * <P>
 * 加载状态规格类，规格生成规则为(requestCode << 4 | status)<br/>
 * 所以requestCode要求必须为正整数，且小于Integer.MAX_VALUE >> 4<br/>
 * </p>
 * Copyright2018 by GNNT Company. All Rights Reserved.
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public final class LoadStatusSpec {

    /**
     * 默认请求码，取取值范围内的最大值
     */
    public static final int DEFAULT_REQUEST_CODE = Integer.MAX_VALUE >> 4;

    /**
     * 生成加载状态规格
     *
     * @param status      状态
     * @param requestCode 请求码
     * @return 合并后的结果
     */
    public static int makeStatusSpec(int status, @IntRange(from = 0, to = Integer.MAX_VALUE >> 4 - 1) int requestCode) {
        // 将请求码左移 4 位，然后最低4位将 status 合并上
        return requestCode << 4 | status;
    }

    /**
     * 根据规格获取状态
     *
     * @param statusSpec 状态规格
     * @return 状态值
     */
    public static int getStatus(int statusSpec) {
        // 只取最低4位即为状态值
        return statusSpec & 0b1111;
    }

    /**
     * 根据规格获取请求码
     *
     * @param statusSpec 状态规格
     * @return 请求码
     */
    public static int getRequestCode(int statusSpec) {
        // 将规格右移4位既是请求码
        return statusSpec >> 4;
    }
}