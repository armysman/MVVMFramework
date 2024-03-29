package gnnt.mebs.common;

/*******************************************************************
 * RouteMap.java  2018/12/13
 * <P>
 * 路由表<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public interface RouteMap {

    interface SimpleDemo {

        /**
         * 数据加载演示
         */
        String LOAD_DATA_PAGE = "/simple/LoadDataPage";

        /**
         * 数据绑定演示
         */
        String DATA_BINDING_PAGE = "/simple/DataBindingPage";

        /**
         * 文件缓存演示
         */
        String FILE_REPOSITORY_PAGE = "/simple/FileRepositoryPage";

        /**
         * Room数据缓存页
         */
        String ROOM_REPOSITORY_PAGE = "/simple/RoomRepositoryPage";

        /**
         * 分页演示
         */
        String PAGE_PAGE = "/simple/PagePage";

        /**
         * 全局事件监听
         */
        String GLOBAL_EVENT_PAGE = "/simple/GlobalEventPage";

        /**
         * 登录
         */
        String LOGIN_PAGE = "/simple/LoginPage";

        /**
         * 注册
         */
        String REGISTER_PAGE = "/simple/RegisterPage";

        /**
         * 动态传参
         */
        String DYNAMIC_PARAMS_PAGE = "/simple/DynamicPage";
    }


}
