package gnnt.mebs.base.http;

/*******************************************************************
 * HttpConfig.java  2018/12/13
 * <P>
 * Model 配置类<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public interface HttpConfig {
    /**
     * 主机编码
     */
    String HOST_ENCODE = "GBK";

    /**
     * 超时时间
     */
    long REQUEST_TIMEOUT = 1000 * 10;

}
