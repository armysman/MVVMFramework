package gnnt.mebs.base.component;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.ButterKnife;
import gnnt.mebs.base.BaseApp;
import gnnt.mebs.base.event.NetworkStatusEvent;
import gnnt.mebs.base.util.StatusBarUtils;

/*******************************************************************
 * BaseActivity.java  2018/11/30
 * <P>
 * Activity 基类<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public abstract class BaseActivity<T extends BaseViewModel> extends AppCompatActivity {

    /**
     * 权限请求码
     */
    public static final int REQUEST_PERMISSION_CODE = 233;
    /**
     * 当前页面 ViewModel
     */
    protected T mViewModel;

    /**
     * 获取布局资源
     *
     * @return 布局资源ID
     */
    protected abstract int getLayoutResource();

    /**
     * 获取要创建的ViewModel.class
     *
     * @return 返回需要创建的ViewModel类
     */
    @NonNull
    protected abstract Class<? extends T> getViewModelClass();

    /**
     * 默认错误处理
     */
    protected Toast mToast;

    /**
     * 进度弹窗
     */
    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化状态栏
        initStatusBar();

        // 绑定布局
        setupLayout(getLayoutResource());
        ButterKnife.bind(this);

        // 先初始化View
        setupView();

        // 初始化 ViewModel
        setupViewModel(createViewModel());
    }

    /**
     * 设置布局
     *
     * @param layoutRes 布局资源
     */
    protected void setupLayout(int layoutRes) {
        setContentView(layoutRes);
    }

    /**
     * 初始化状态栏
     */
    protected void initStatusBar() {
        if (getStatusBarColor() != Integer.MAX_VALUE) {
            StatusBarUtils.setStatusBar(this, getStatusBarColor(), isDarkStatusBarText());
        }
    }

    /**
     * 获取状态栏颜色
     *
     * @return 返回状态栏颜色色值
     */
    protected int getStatusBarColor() {
        return Integer.MAX_VALUE;
    }

    /**
     * 是否是深色文字
     *
     * @return true 深色文字 false 浅色文字
     */
    protected boolean isDarkStatusBarText() {
        return true;
    }

    /**
     * 初始化View相关内容，在setupViewModel前调用
     */
    protected void setupView() {

    }

    /**
     * 创建ViewModel
     *
     * @return 返回指定类型的ViewModel
     */
    protected T createViewModel() {
        return ViewModelProviders.of(this).get(getViewModelClass());
    }

    /**
     * 初始化ViewModel
     */
    protected void setupViewModel(@Nullable T viewModel) {
        if (viewModel == null) {
            return;
        }
        mViewModel = viewModel;
        // 刷新数据
        mViewModel.refreshDataIfNeed();

        BaseApp app = mViewModel.getApplication();
        // 网络状态改变
        app.getNetworkStatusEvent().observeNetworkStatus(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer status) {
                onNetworkStatusChanged(status);
            }
        });
        // 信息提示
        mViewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showMessage(s);
            }
        });
        // 是否加载中
        mViewModel.getLoadStatusSpec().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer statusSpec) {
                if (statusSpec == null) {
                    onLoadStatusChanged(BaseViewModel.NOT_LOADED, 0);
                } else {
                    onLoadStatusChanged(LoadStatusSpec.getStatus(statusSpec), LoadStatusSpec.getRequestCode(statusSpec));
                }
            }
        });
    }

    /**
     * 网络状态改变
     *
     * @param status 网络状态
     */
    protected void onNetworkStatusChanged(int status) {
        if (mViewModel == null) {
            return;
        }
        if (status != NetworkStatusEvent.TYPE_NONE) {
            mViewModel.refreshDataIfNeed();
        }
    }

    /**
     * 信息默认处理方法
     */
    protected void showMessage(String message) {
        // 子类重写该方法处理错误信息
        if (TextUtils.isEmpty(message)) {
            return;
        }
        // 如果Toast为空，则初始化
        if (mToast == null) {
            mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        }
        // 显示Toast
        mToast.setText(message);
        mToast.show();
    }


    /**
     * 加载状态改变
     *
     * @param status      状态，{@link BaseViewModel}
     * @param requestCode 请求码
     */
    protected void onLoadStatusChanged(int status, int requestCode) {
        // 如果是加载中，则显示
        if (status == BaseViewModel.LOADING) {
            showProgress(requestCode, true);
        } else {
            hideProgress(requestCode);
        }
    }

    /**
     * 显示进度
     *
     * @param requestCode 请求码
     * @param cancelable  是否可取消
     */
    public void showProgress(int requestCode, boolean cancelable) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setCancelable(cancelable);
        // 如果没有显示，则显示
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 隐藏进度
     *
     * @param requestCode 请求码
     */
    public void hideProgress(int requestCode) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 如果需要则请求权限
     *
     * @param permissions 权限
     */
    public void requestPermission(String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION_CODE);
    }


    /**
     * 设置当前界面是否可以触摸
     *
     * @param touchable true 可以触摸，false，不可触摸
     */
    public void setTouchable(boolean touchable) {
        if (touchable) {
            // 允许用户点击
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            // 默认不允许点击
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

}
