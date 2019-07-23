package gnnt.practice.dailypage.ui.welcome;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import gnnt.mebs.base.component.BaseActivity;
import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.common.RouteMap;
import gnnt.practice.dailypage.R;
import permissions.dispatcher.RuntimePermissions;

/**********************************************************
 *  WelcomeActivity.java  2019-07-08
 *  <p>
 *  欢迎页
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
@RuntimePermissions
@Route(path = RouteMap.DailyPage.WELCOME)
public class WelcomeActivity extends BaseActivity<WelcomeViewModel> {


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_welcome;
    }

    @Override
    protected int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimaryDark);
    }

    @NonNull
    @Override
    protected Class<? extends WelcomeViewModel> getViewModelClass() {
        return WelcomeViewModel.class;
    }

    @Override
    protected void setupView() {
        super.setupView();
    }

    @Override
    protected void setupViewModel(@Nullable WelcomeViewModel viewModel) {
        super.setupViewModel(viewModel);
        //请求权限
        requestPermission();
    }

    public void requestPermission() {
        RxPermissions repermission = new RxPermissions(this);
        repermission.requestEach(Manifest.permission.READ_PHONE_STATE).
                subscribe(permission -> {
                    if (permission.granted) {
                        doRequestModel();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        showMessage("权限被拒绝，退出系统");
                        doRequestModel();
                    } else {
                        //goto setting
                    }
                });

    }

    private void doRequestModel() {
        mViewModel.requestWelcomeRes();
    }

    @Override
    protected void onLoadStatusChanged(int status, int requestCode) {
        if (status == BaseViewModel.LOAD_COMPLETE) {
            ARouter.getInstance().build(RouteMap.DailyPage.LOGIN).navigation(this);
            finish();
        }
    }
}
