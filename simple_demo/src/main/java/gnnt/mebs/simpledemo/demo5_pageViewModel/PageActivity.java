package gnnt.mebs.simpledemo.demo5_pageViewModel;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import gnnt.mebs.common.RouteMap;
import gnnt.mebs.base.component.page.BasePageActivity;
import gnnt.mebs.simpledemo.PoetryAdapter;
import gnnt.mebs.simpledemo.R;
import gnnt.mebs.simpledemo.R2;
import gnnt.mebs.simpledemo.model.vo.Poetry;

/*******************************************************************
 * RoomRepositoryActivity.java  2019/3/7
 * <P>
 * 演示分页<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
@Route(path = RouteMap.SimpleDemo.PAGE_PAGE)
public class PageActivity extends BasePageActivity<PageViewModel> {

    /**
     * 唐诗列表
     */
    @BindView(R2.id.rv_poetry)
    protected RecyclerView mRvPoetry;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_page;
    }


    @NonNull
    @Override
    protected Class<? extends PageViewModel> getViewModelClass() {
        return PageViewModel.class;
    }

    @NonNull
    @Override
    public BaseQuickAdapter createAdapter() {
        return new PoetryAdapter();
    }

    @Override
    public void setupView(BaseQuickAdapter adapter) {
        setTitle("PageViewModel分页演示");
        mRvPoetry.setLayoutManager(new LinearLayoutManager(this));
        mRvPoetry.setAdapter(adapter);
        adapter.bindToRecyclerView(mRvPoetry);
    }

    @Override
    protected void setupViewModel(@Nullable PageViewModel viewModel) {
        super.setupViewModel(viewModel);
        viewModel.getData().observe(this, new Observer<List<Poetry>>() {
            @Override
            public void onChanged(List<Poetry> poetryList) {
                mAdapter.setNewData(poetryList);
            }
        });
    }
}
