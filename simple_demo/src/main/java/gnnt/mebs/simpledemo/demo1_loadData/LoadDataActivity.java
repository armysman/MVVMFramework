package gnnt.mebs.simpledemo.demo1_loadData;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.OnClick;
import gnnt.mebs.base.component.BaseActivity;
import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.base.event.NetworkStatusEvent;
import gnnt.mebs.common.RouteMap;
import gnnt.mebs.simpledemo.R;
import gnnt.mebs.simpledemo.R2;
import gnnt.mebs.simpledemo.model.vo.Poetry;
import gnnt.mebs.simpledemo.utils.PoetryUtil;

/*******************************************************************
 * LoadDataActivity.java  2019/3/5
 * <P>
 * <br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
@Route(path = RouteMap.SimpleDemo.LOAD_DATA_PAGE)
public class LoadDataActivity extends BaseActivity<LoadDataViewModel> {

    /**
     * 网络状态
     */
    @BindView(R2.id.tv_status)
    protected TextView mTvStatus;
    /**
     * 标题
     */
    @BindView(R2.id.tv_title)
    protected TextView mTvTitle;

    /**
     * 作者
     */
    @BindView(R2.id.tv_author)
    protected TextView mTvAuthor;

    /**
     * 内容
     */
    @BindView(R2.id.tv_content)
    protected TextView mTvContent;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_load_data;
    }

    @NonNull
    @Override
    protected Class<? extends LoadDataViewModel> getViewModelClass() {
        return LoadDataViewModel.class;
    }

    @Override
    protected void setupView() {
        super.setupView();
        setTitle("基本数据加载与网络状态处理");
    }

    @Override
    protected void setupViewModel(@Nullable LoadDataViewModel viewModel) {
        super.setupViewModel(viewModel);
        viewModel.getResult().observe(this, new Observer<Poetry>() {
            @Override
            public void onChanged(Poetry poetry) {
                if (poetry != null) {
                    mTvTitle.setText(poetry.title);
                    mTvAuthor.setText(poetry.authors);
                    mTvContent.setText(PoetryUtil.formatContent(poetry.content));
                }
            }
        });
    }

    @Override
    protected void onLoadStatusChanged(int status, int requestCode) {
        super.onLoadStatusChanged(status, requestCode);
        if (status == BaseViewModel.LOAD_COMPLETE) {
            showMessage("加载成功");
        }
    }

    @Override
    protected void onNetworkStatusChanged(int status) {
        super.onNetworkStatusChanged(status);
        if (status == NetworkStatusEvent.TYPE_NONE) {
            mTvStatus.setText("网络状态：未连接");
        } else {
            String type = status == NetworkStatusEvent.TYPE_WIFI ? "WIFI" : "数据流量";
            mTvStatus.setText(String.format("网络状态：%s", type));
        }
    }

    @OnClick(R2.id.btn_load_zhuhu)
    protected void onLoadZhuhuClick() {
        mViewModel.loadPoetryData();
    }
}
