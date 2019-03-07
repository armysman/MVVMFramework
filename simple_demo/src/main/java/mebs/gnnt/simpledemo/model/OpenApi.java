package mebs.gnnt.simpledemo.model;

import java.util.List;

import io.reactivex.Single;
import mebs.gnnt.simpledemo.model.dto.Response;
import mebs.gnnt.simpledemo.model.vo.Poetry;
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
}
