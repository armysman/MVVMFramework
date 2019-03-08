package gnnt.mebs.base.component;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.ButterKnife;
import gnnt.mebs.base.BaseApp;
import gnnt.mebs.base.event.NetworkStatusEvent;
import gnnt.mebs.base.util.StatusBarUtils;

/*******************************************************************
 * BaseFragment.java  2018/11/30
 * <P>
 * Fragment 基类<br/>
 * <br/>
 * </p>
 *
 * @author:zhoupeng
 *
 ******************************************************************/
public abstract class BaseDialogFragment<T extends BaseViewModel> extends DialogFragment {

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
    protected abstract @NonNull
    Class<? extends T> getViewModelClass();

    /**
     * 默认错误处理
     */
    private Toast mToast;

    /**
     * 进度弹窗
     */
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 绑定布局
        View rootView = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, rootView);

        // 先初始化View
        setupView(rootView);
        // 初始化 ViewModel
        setupViewModel(createViewModel());
        return rootView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            initStatusBar();
        }
    }

    /**
     * 初始化View相关
     */
    protected void setupView(View rootView) {

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
    protected void setupViewModel(@NonNull T viewModel) {
        if (viewModel == null) {
            return;
        }
        mViewModel = viewModel;
        // 刷新数据
        mViewModel.refreshDataIfNeed();
        // 用户登录状态改变提示
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
     * 初始化状态栏
     */
    protected void initStatusBar() {
        if (getStatusBarColor() != Integer.MAX_VALUE) {
            StatusBarUtils.setStatusBar(getActivity(), getStatusBarColor(), isDarkStatusBarText());
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
            mToast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
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
            showProgress(true);
        } else {
            hideProgress();
        }
    }

    /**
     * 显示进度
     *
     * @param cancelable 是否可取消
     */
    public void showProgress(boolean cancelable) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
        }
        mProgressDialog.setCancelable(cancelable);
        // 如果没有显示，则显示
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 隐藏进度
     */
    public void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 请求权限
     *
     * @param permission 权限
     */
    public void requestPermission(String permission) {
        requestPermissions(new String[]{permission}, REQUEST_PERMISSION_CODE);
    }
}
