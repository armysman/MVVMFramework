package mebs.gnnt.simpledemo.loadData;

import android.widget.TextView;
import android.widget.Toast;

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
import mebs.gnnt.simpledemo.R;
import mebs.gnnt.simpledemo.R2;

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

    @BindView(R2.id.tv_result)
    protected TextView mTvResult;

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
    protected void setupViewModel(@Nullable LoadDataViewModel viewModel) {
        super.setupViewModel(viewModel);
        viewModel.getResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mTvResult.setText(s);
            }
        });
    }

    @Override
    protected void onLoadStatusChanged(int status, int requestCode) {
        super.onLoadStatusChanged(status, requestCode);
        if (status == BaseViewModel.LOADING) {
            mTvResult.setText("正在加载");
        } else if (status == BaseViewModel.LOAD_COMPLETE) {
            showMessage("加载成功");
        } else if (status == BaseViewModel.LOAD_ERROR) {
            mTvResult.setText("加载失败");
        }
    }

    @Override
    protected void onNetworkStatusChanged(int status) {
        super.onNetworkStatusChanged(status);
        if (status == NetworkStatusEvent.TYPE_NONE) {
            showMessage("网络似乎断开了");
        } else {
            showMessage("网络连接了，触发数据刷新");
        }
    }

    @OnClick(R2.id.btn_load_zhuhu)
    protected void onLoadZhuhuClick() {
        mViewModel.loadZhuHuData();
    }
}
