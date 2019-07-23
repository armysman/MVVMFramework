package gnnt.practice.dailypage.vo.reqvo;

import gnnt.mebs.common.vo.BaseRequest;

/**********************************************************
 *  LoginReqVO.java  2019-07-08
 *  <p>
 *  登录
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class LoginReqVO extends BaseRequest {
    public String username;
    public String password;
    public String verifyCode;
}
