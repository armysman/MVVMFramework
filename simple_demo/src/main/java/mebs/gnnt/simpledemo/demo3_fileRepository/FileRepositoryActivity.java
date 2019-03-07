package mebs.gnnt.simpledemo.demo3_fileRepository;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.annotation.NonNull;
import gnnt.mebs.common.RouteMap;
import mebs.gnnt.simpledemo.demo1_loadData.LoadDataActivity;
import mebs.gnnt.simpledemo.demo1_loadData.LoadDataViewModel;

/*******************************************************************
 * FileRepositoryActivity.java  2019/3/7
 * <P>
 * 文件缓存演示<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
@Route(path = RouteMap.SimpleDemo.FILE_REPOSITORY_PAGE)
public class FileRepositoryActivity extends LoadDataActivity {

    @NonNull
    @Override
    protected Class<? extends LoadDataViewModel> getViewModelClass() {
        return FileRepositoryViewModel.class;
    }

    @Override
    protected void setupView() {
        super.setupView();
        setTitle("FileRepository");
    }
}
