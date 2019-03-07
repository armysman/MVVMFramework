package mebs.gnnt.simpledemo.demo5_pageViewModel;

import android.app.Application;

import java.util.List;

import gnnt.mebs.base.http.HttpException;
import gnnt.mebs.common.component.BasePageViewModel;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import mebs.gnnt.simpledemo.model.Config;
import mebs.gnnt.simpledemo.model.OpenApi;
import mebs.gnnt.simpledemo.model.dto.Response;
import mebs.gnnt.simpledemo.model.vo.Poetry;

/*******************************************************************
 * PageViewModel.java  2019/3/7
 * <P>
 * 分页ViewModel<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class PageViewModel extends BasePageViewModel<Poetry> {

    /**
     * 这个条数代表总共多少条，因为这个接口没返回总条数
     */
    public static final int TOTAL_COUNT = 100;

    public PageViewModel(Application application) {
        super(application);
    }

    /**
     * 重写该方法，表示每页请求多少条
     *
     * @return 每页条数
     */
    @Override
    public int getPageSize() {
        return 8;
    }

    @Override
    public Single<ListResponse<Poetry>> onLoad(int pageNO, int pageSize) {
        OpenApi api = getApplication().getRetrofitManager().getApi(Config.HOST, OpenApi.class);
        return api.getTangPoetry(pageNO, pageSize)
                .map(new Function<Response<List<Poetry>>, ListResponse<Poetry>>() {
                    @Override
                    public ListResponse<Poetry> apply(Response<List<Poetry>> response) throws Exception {
                        if (response.code != 200) {
                            throw new HttpException(response.message);
                        }
                        return new ListResponse<>(TOTAL_COUNT, response.result);
                    }
                });
    }
}
