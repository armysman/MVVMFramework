package mebs.gnnt.simpledemo.model;

import android.content.Context;

import androidx.annotation.NonNull;
import gnnt.mebs.base.http.LoadException;
import gnnt.mebs.base.model.FileRepository;
import io.reactivex.Single;
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
                .onErrorResumeNext(Single.<Response<Poetry>>error(new LoadException("网络出错了")))
                .map(new UnpackHandler<Poetry>());
    }
}
