package gnnt.mebs.base.http;

/*******************************************************************
 * LoadException.java  2018/11/29
 * <P>
 * Http异常<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class LoadException extends Exception {

    /**
     * 错误码
     */
    private int retCode = -1;

    public LoadException(String message, int retCode) {
        super(message);
        this.retCode = retCode;
    }

    public LoadException(String message) {
        super(message);
    }

    /**
     * 获取返回码
     *
     * @return 返回码
     */
    public int getRetCode() {
        return retCode;
    }
}
