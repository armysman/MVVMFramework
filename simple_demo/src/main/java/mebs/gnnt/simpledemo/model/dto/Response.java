package mebs.gnnt.simpledemo.model.dto;

/*******************************************************************
 * Response.java  2019/3/6
 * <P>
 * 返回包<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class Response<T> {
    /**
     * 返回码
     */
    public int code;

    /**
     * 消息
     */
    public String message;

    /**
     * 返回内容
     */
    public T result;
}
