package gnnt.mebs.appdemo.model.dto;

import gnnt.mebs.base.http.xml.ApiRequest;
import gnnt.mebs.base.http.xml.ApiResponse;
import gnnt.mebs.base.http.xml.XMLSerializedName;

/*******************************************************************
 * LoginDTO.java  2019/3/5
 * <P>
 * <br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public interface LoginDTO {

    class Request extends ApiRequest {

        /**
         * 登录用户ID
         */
        @XMLSerializedName("U")
        public String userID;
        /**
         * 登录密码
         */
        @XMLSerializedName("PASSWORD")
        public String password;
        /**
         * 加密字符串,暂不需要传
         */
        @XMLSerializedName("IC")
        public String encrypt = "";
        /**
         * 随机串
         */
        @XMLSerializedName("RANDOM_KEY")
        public String randomKey;

        @Override
        protected String getProtocolName() {
            return "user_login";
        }
    }


    class Response extends ApiResponse {

        @XMLSerializedName("RESULT")
        public Result result;

        public class Result {
            @XMLSerializedName("RETCODE")
            public String retCode;
            @XMLSerializedName("MESSAGE")
            public String message;
            @XMLSerializedName("MODULE_ID")
            public String MODULE_ID;
            @XMLSerializedName("LAST_TIME")
            public String lastTime;
            @XMLSerializedName("LAST_IP")
            public String lastIP;
            @XMLSerializedName("CHG_PWD")
            public String needChangePassword;
            @XMLSerializedName("NAME")
            public String name;
            @XMLSerializedName("U")
            public String U;
        }

    }
}
