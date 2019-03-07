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
public class Response<T extends Object> {
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

    public Response() {
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 返回一个默认错误的返回包
     *
     * @param message 错误消息
     * @param <T>
     * @return
     */
    public static <T> Response<T> defaultError(String message) {
        return new Response<T>(-1, message);
    }
}
