package gnnt.mebs.common.util;


import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/*******************************************************************
 * GlideOptionHelper.java  2018/11/30
 * <P>
 * 图片加载进一步的封装，便于切换图片加载库<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public final class GlideOptionHelper {

    /**
     * 头像配置
     */
    public static RequestOptions HeadOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

}
