package mebs.gnnt.simpledemo.model;

import android.content.Context;

import androidx.annotation.NonNull;
import gnnt.mebs.base.http.HttpException;
import gnnt.mebs.base.model.FileRepository;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import mebs.gnnt.simpledemo.model.dto.Response;
import mebs.gnnt.simpledemo.model.vo.Poetry;

/*******************************************************************
 * PoetryFileRepository.java  2019/3/7
 * <P>
 * 古诗仓库类<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class PoetryFileRepository extends FileRepository<Poetry> {

    /**
     * 缓存名称
     */
    private static final String RESPOSITORY_NAME = "PoetryFileRepository";

    /**
     * 远端数据接口
     */
    OpenApi mOpenApi;

    public PoetryFileRepository(@NonNull Context context, @NonNull OpenApi openApi) {
        super(context, RESPOSITORY_NAME);
        mOpenApi = openApi;
    }

    @Override
    public Single<Poetry> loadRemoteData() {
        return mOpenApi.getRandomPoetry()
                .onErrorResumeNext(Single.<Response<Poetry>>error(new HttpException("网络出错了")))
                .map(new Function<Response<Poetry>, Poetry>() {
                    @Override
                    public Poetry apply(Response<Poetry> poetryResponse) throws Exception {
                        if (poetryResponse.code == 200) {
                            return poetryResponse.result;
                        }
                        throw new HttpException(poetryResponse.message);
                    }
                });
    }
}
