package mebs.gnnt.simpledemo;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import mebs.gnnt.simpledemo.R;
import mebs.gnnt.simpledemo.model.vo.Poetry;
import mebs.gnnt.simpledemo.utils.PoetryUtil;

/*******************************************************************
 * PoetryAdapter.java  2019/3/7
 * <P>
 * 诗词列表适配器<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class PoetryAdapter extends BaseQuickAdapter<Poetry, BaseViewHolder> {

    public PoetryAdapter() {
        super(R.layout.item_poetry);
    }

    @Override
    protected void convert(BaseViewHolder helper, Poetry item) {
        helper.setText(R.id.tv_title, item.title);
        helper.setText(R.id.tv_author, item.authors);
        helper.setText(R.id.tv_content, PoetryUtil.formaShortContent(item.content));
    }
}
