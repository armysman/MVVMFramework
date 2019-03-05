package mebs.gnnt.simpledemo.model;

import io.reactivex.Single;
import mebs.gnnt.simpledemo.model.dto.ZhuHuDTO;
import retrofit2.http.GET;

/*******************************************************************
 * LoadDataApi.java  2019/3/5
 * <P>
 * <br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public interface LoadDataApi {

    /**
     * 知乎地址
     */
    String ZHU_HU_HOST = "https://zhuanlan.zhihu.com/";

    @GET("api/columns/zhihuadmin")
    Single<ZhuHuDTO.Response> getZhuHuData();
}
