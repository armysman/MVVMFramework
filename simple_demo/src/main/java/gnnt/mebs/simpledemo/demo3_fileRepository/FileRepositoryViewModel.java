package gnnt.mebs.simpledemo.demo3_fileRepository;

import android.app.Application;

import androidx.annotation.NonNull;
import gnnt.mebs.base.model.BaseRepository;
import gnnt.mebs.simpledemo.demo1_loadData.LoadDataViewModel;
import gnnt.mebs.simpledemo.model.Config;
import gnnt.mebs.simpledemo.model.OpenApi;
import gnnt.mebs.simpledemo.model.PoetryFileRepository;

/*******************************************************************
 * FileRepositoryViewModel.java  2019/3/6
 * <P>
 * 文件仓库ViewModel类<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class FileRepositoryViewModel extends LoadDataViewModel {

    /**
     * 数据仓库
     */
    private PoetryFileRepository mRepository;

    public FileRepositoryViewModel(@NonNull Application application) {
        super(application);
        OpenApi api = getApplication().getRetrofitManager().getApi(Config.HOST, OpenApi.class);
        mRepository = new PoetryFileRepository(application, api);
        mResult = mRepository.getData(loadCallback);
    }

    @Override
    public void loadPoetryData() {
        mRepository.refreshDataIfNeed(loadCallback);
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
