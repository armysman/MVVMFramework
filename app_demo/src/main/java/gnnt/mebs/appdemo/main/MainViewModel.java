package gnnt.mebs.appdemo.main;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.common.RouteMap;

/*******************************************************************
 * MainViewModel.java  2019/3/5
 * <P>
 * ViewModel基类<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class MainViewModel extends BaseViewModel {

    /**
     * 主界面菜单
     */
    private MutableLiveData<List<MainMenu>> mMainMenu = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        mMainMenu.setValue(Arrays.asList(
                new MainMenu(RouteMap.SimpleDemo.LOAD_DATA_PAGE, "基本数据加载与网络状态处理"),
                new MainMenu(RouteMap.SimpleDemo.DATA_BINDING_PAGE, "DataBinding的使用"),
                new MainMenu(RouteMap.SimpleDemo.FILE_REPOSITORY_PAGE,"FileRepository缓存"),
                new MainMenu(RouteMap.SimpleDemo.ROOM_REPOSITORY_PAGE,"RoomRepository缓存"),
                new MainMenu(RouteMap.SimpleDemo.PAGE_PAGE,"分页演示")
        ));
    }

    /**
     * 获取主界面菜单
     *
     * @return 主界面菜单
     */
    public MutableLiveData<List<MainMenu>> getMainMenu() {
        return mMainMenu;
    }

    public static class MainMenu {

        /**
         * 路由路径
         */
        public String routePath;

        /**
         * 菜单名称
         */
        public String menuName;

        public MainMenu(String routePath, String menuName) {
            this.routePath = routePath;
            this.menuName = menuName;
        }
    }
}
