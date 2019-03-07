package mebs.gnnt.simpledemo.model.local;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import mebs.gnnt.simpledemo.model.vo.Poetry;

/*******************************************************************
 * PoetryDao.java  2019/3/7
 * <P>
 * 诗词数据库操作类<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
@Dao
public abstract class PoetryDao {


    /**
     * 获取所有古诗,按id倒序排列
     *
     * @return
     */
    @Query("SELECT * FROM Poetry ORDER BY Poetry.id DESC")
    public abstract LiveData<List<Poetry>> getAllPoetry();

    /**
     * 添加诗词列表
     *
     * @param poetry 诗词
     * @return 诗词ID
     */
    @Insert()
    public abstract void insertPoetry(List<Poetry> poetry);
}
