package gnnt.mebs.simpledemo.demo4_roomRepository;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.base.model.BaseRepository;
import gnnt.mebs.simpledemo.model.Config;
import gnnt.mebs.simpledemo.model.OpenApi;
import gnnt.mebs.simpledemo.model.PoetryRoomRepository;
import gnnt.mebs.simpledemo.model.local.CacheDatabase;
import gnnt.mebs.simpledemo.model.vo.Poetry;

/*******************************************************************
 * RoomRepositoryViewModel.java  2019/3/7
 * <P>
 * Room缓存ViewModel<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class RoomRepositoryViewModel extends BaseViewModel {

    /**
     * 数据仓库
     */
    private PoetryRoomRepository mRepository;

    public RoomRepositoryViewModel(@NonNull Application application) {
        super(application);
        CacheDatabase cacheDatabase = CacheDatabase.getInstance(application);
        OpenApi openApi = getApplication().getRetrofitManager().getApi(Config.HOST, OpenApi.class);
        mRepository = new PoetryRoomRepository(cacheDatabase.getPoetryDao(), openApi);
    }

    @Override
    public void refreshDataIfNeed() {
        mRepository.refreshDataIfNeed(loadCallback);
    }

    public LiveData<List<Poetry>> getData() {
        return mRepository.getData();
    }

    BaseRepository.RemoteLoadCallback loadCallback = new BaseRepository.RemoteLoadCallback() {
        @Override
        public void onLoading() {
            setLoadStatus(LOADING, 0);
        }

        @Override
        public void onComplete() {
            setLoadStatus(LOAD_COMPLETE, 0);
        }

        @Override
        public void onError(Throwable e) {
            setLoadStatus(LOAD_ERROR, 0);
            setMessage(e.getMessage());
        }
    };
}
