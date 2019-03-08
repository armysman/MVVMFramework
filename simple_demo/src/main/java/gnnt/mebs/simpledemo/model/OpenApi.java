package gnnt.mebs.simpledemo.model;

import java.util.List;

import io.reactivex.Single;
import gnnt.mebs.simpledemo.model.dto.Response;
import gnnt.mebs.simpledemo.model.vo.Poetry;
import gnnt.mebs.simpledemo.model.vo.User;
import retrofit2.http.POST;
import retrofit2.http.Query;

/*******************************************************************
 * OpenApi.java  2019/3/5
 * <P>
 * 开放Api<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public interface OpenApi {

    /**
     * 获取随机古诗
     *
     * @return
     */
    @POST("recommendPoetry")
    Single<Response<Poetry>> getRandomPoetry();

    /**
     * 搜索古诗
     *
     * @param name 名称
     * @return
     */
    @POST("searchPoetry")
    Single<Response<List<Poetry>>> searchPoetry(@Query("name") String name);

    /**
     * 获取唐诗
     *
     * @param page  页码
     * @param count 每页条数
     * @return
     */
    @POST("getTangPoetry")
    Single<Response<List<Poetry>>> getTangPoetry(@Query("page") int page, @Query("count") int count);

    /**
     * 注册用户
     *
     * @param name     用户名
     * @param password 密码
     * @param nick     昵称
     * @param sign     签名
     * @return 注册结果
     */
    @POST("registerUser?apikey=5ad480ce76b57d3c918861a314d78f3c")
    Single<Response<Object>> registerUser(@Query("name") String name, @Query("passwd") String password, @Query("nikeName") String nick, @Query("autograph") String sign);

    /**
     * 登录用户
     * @param name 用户名
     * @param password 密码
     * @return
     */
    @POST("loginUser?apikey=5ad480ce76b57d3c918861a314d78f3c")
    Single<Response<User>> login(@Query("name") String name, @Query("passwd") String password);
}
