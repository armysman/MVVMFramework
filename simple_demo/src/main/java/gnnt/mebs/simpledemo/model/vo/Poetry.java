package gnnt.mebs.simpledemo.model.vo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*******************************************************************
 * Poetry.java  2019/3/6
 * <P>
 * 古诗<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
@Entity
public class Poetry {

    /**
     * 主键自增
     */
    @PrimaryKey(autoGenerate = true)
    public Long id;
    /**
     * 标题
     */
    public String title;

    /**
     * 内容
     */
    public String content;

    /**
     * 作者
     */
    public String authors;
}
