package gnnt.mebs.appdemo.model;

import gnnt.mebs.appdemo.model.dto.LoginDTO;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/*******************************************************************
 * UserApi.java  2018/11/29
 * <P>
 * 用户相关API<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public interface UserApi {

    /**
     * 登录
     *
     * @param params
     * @return
     */
    @POST("espot-frontend/mobileHttpServlet")
    Single<LoginDTO.Response> login(@Body LoginDTO.Request params);

}
