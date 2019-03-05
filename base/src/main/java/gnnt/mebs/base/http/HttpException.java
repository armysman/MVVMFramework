package gnnt.mebs.base.http;

/*******************************************************************
 * HttpException.java  2018/11/29
 * <P>
 * Http异常<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class HttpException extends Exception {

    /**
     * 错误码
     */
    private int retCode = -1;

    public HttpException(String message, int retCode) {
        super(message);
        this.retCode = retCode;
    }

    public HttpException(String message) {
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
