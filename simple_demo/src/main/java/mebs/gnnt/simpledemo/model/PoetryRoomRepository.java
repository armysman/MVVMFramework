package mebs.gnnt.simpledemo.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import gnnt.mebs.base.http.HttpException;
import gnnt.mebs.base.model.RoomRepository;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import mebs.gnnt.simpledemo.model.dto.Response;
import mebs.gnnt.simpledemo.model.local.PoetryDao;
import mebs.gnnt.simpledemo.model.vo.Poetry;

/*******************************************************************
 * PoetryRoomRepository.java  2019/3/7
 * <P>
 * 诗词数据库缓存仓库<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class PoetryRoomRepository extends RoomRepository<List<Poetry>> {

    /**
     * 数据库操作类
     */
    private PoetryDao mDao;

    /**
     * 远端数据请求api
     */
    private OpenApi mApi;

    public PoetryRoomRepository(PoetryDao poetryDao, OpenApi openApi) {
        this.mDao = poetryDao;
        this.mApi = openApi;
    }

    @NonNull
    @Override
    public LiveData<List<Poetry>> getRoomData() {
        return mDao.getAllPoetry();
    }

    @Override
    public void saveLocalData(List<Poetry> poetryList) {
        if (poetryList != null && !poetryList.isEmpty()) {
            mDao.insertPoetry(poetryList);
        }
    }

    @Override
    public Single<List<Poetry>> loadRemoteData() {
        return mApi.getRandomPoetry()
                .map(new Function<Response<Poetry>, List<Poetry>>() {
                    @Override
                    public List<Poetry> apply(Response<Poetry> poetryResponse) throws Exception {
                        if (poetryResponse.code == 200 && poetryResponse.result != null) {
                            return Collections.singletonList(poetryResponse.result);
                        }
                        throw new HttpException("网络出错了");
                    }
                });
    }
}
