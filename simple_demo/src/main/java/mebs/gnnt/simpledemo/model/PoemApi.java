package mebs.gnnt.simpledemo.model;

import io.reactivex.Single;
import mebs.gnnt.simpledemo.model.dto.Response;
import mebs.gnnt.simpledemo.model.vo.Poem;
import retrofit2.http.POST;

/*******************************************************************
 * PoemApi.java  2019/3/5
 * <P>
 * 古诗Api<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public interface PoemApi {

    /**
     * 获取随机古诗
     *
     * @return
     */
    @POST("recommendPoetry")
    Single<Response<Poem>> getRandomPoem();
}
