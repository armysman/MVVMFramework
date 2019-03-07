package mebs.gnnt.simpledemo.model.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import mebs.gnnt.simpledemo.model.vo.Poetry;

/*******************************************************************
 * CacheDatabase.java  2019/3/7
 * <P>
 * 缓存数据库，这个类一般用单例<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
@Database(entities = {Poetry.class}, version = 1, exportSchema = false)
public abstract class CacheDatabase extends RoomDatabase {

    /**
     * 单例
     */
    private static CacheDatabase sInstance;

    /**
     * 获取诗词数据库操作类
     *
     * @return 数据库操作类
     */
    public abstract PoetryDao getPoetryDao();

    /**
     * 获取数据库连接单例
     *
     * @param context 上下文
     * @return 单例
     */
    public static final CacheDatabase getInstance(@NonNull Context context) {
        if (sInstance == null) {
            synchronized (CacheDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            CacheDatabase.class, "CacheDataBase").build();
                }
            }
        }
        return sInstance;
    }
}
