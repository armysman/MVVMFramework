package gnnt.mebs.simpledemo.utils;

/*******************************************************************
 * PoetryUtil.java  2019/3/7
 * <P>
 * <br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class PoetryUtil {

    /**
     * 格式化诗词内容
     *
     * @param content 内容
     * @return 格式化结果
     */
    public static final String formatContent(String content) {
        if (content == null){
            return "";
        }
        return content.replaceAll("\\|", "\n\n");
    }

    /**
     * 格式化诗词内容
     *
     * @param content 内容
     * @return 格式化结果
     */
    public static final String formaShortContent(String content) {
        if (content == null){
            return "";
        }
        return content.replaceAll("\\|", "\n");
    }
}
