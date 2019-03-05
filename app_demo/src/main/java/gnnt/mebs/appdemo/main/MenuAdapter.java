package gnnt.mebs.appdemo.main;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import gnnt.mebs.appdemo.R;

/*******************************************************************
 * MenuAdapter.java  2019/3/5
 * <P>
 * <br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public class MenuAdapter extends BaseQuickAdapter<MainViewModel.MainMenu, BaseViewHolder> {
    public MenuAdapter() {
        super(R.layout.item_menu);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainViewModel.MainMenu item) {
        helper.setText(R.id.tv_menu, item.menuName);
    }
}
