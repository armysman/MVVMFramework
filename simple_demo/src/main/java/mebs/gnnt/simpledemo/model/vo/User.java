package mebs.gnnt.simpledemo.model.vo;

import com.google.gson.annotations.SerializedName;

/*******************************************************************
 * User.java  2019/3/7
 * <P>
 * 用户信息<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class User {

    /**
     * 用户名
     */
    @SerializedName("name")
    public String userName;


    /**
     * 昵称
     */
    @SerializedName("nikeName")
    public String nickName;

    /**
     * 签名
     */
    public String sign;
}
