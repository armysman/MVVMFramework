package gnnt.mebs.common.vo;

/*******************************************************************
 * LoginUser.java  2019/2/21
 * <P>
 * 登录用户<br/>
 * <br/>
 * </p>
 * Copyright2018 by GNNT Company. All Rights Reserved.
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class LoginUser {

    /**
     * 用户ID
     */
    public long userId;
    /**
     * 手机号
     */
    public String phone;
    /**
     * 头像
     */
    public String headImageUrl;

    /**
     * 昵称
     */
    public String nickName;

    /**
     * 用户token
     */
    public String token;

    public LoginUser(long userId, String phone, String headImageUrl, String nickName, String token) {
        this.userId = userId;
        this.phone = phone;
        this.headImageUrl = headImageUrl;
        this.nickName = nickName;
        this.token = token;
    }
}