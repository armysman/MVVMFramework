package gnnt.practice.dailypage.ui.login;


import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.OnClick;
import gnnt.mebs.base.component.BaseActivity;
import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.common.RouteMap;
import gnnt.practice.dailypage.MemeoryData;
import gnnt.practice.dailypage.R;
import gnnt.practice.dailypage.vo.reqvo.LoginReqVO;

/**********************************************************
 *  LoginActivity.java  2019-07-08
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
@Route(path = RouteMap.DailyPage.LOGIN)
public class LoginActivity extends BaseActivity<LoginViewModel> {


    @BindView(R.id.username)
    TextInputEditText username;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.veritycode)
    TextInputEditText veritycode;
    @BindView(R.id.iv_verity)
    ImageView ivVerity;
    @BindView(R.id.iv_login)
    ImageView ivLogin;

    @Override
    protected int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimaryDark);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @NonNull
    @Override
    protected Class<? extends LoginViewModel> getViewModelClass() {
        return LoginViewModel.class;
    }

    @Override
    protected void setupView() {
        super.setupView();
    }

    @Override
    protected void setupViewModel(@Nullable LoginViewModel viewModel) {
        super.setupViewModel(viewModel);
        mViewModel.requestVerityCode();
        mViewModel.getVerityCode().observe(this, bitmap -> {
            if (bitmap != null) {
                ivVerity.setImageBitmap(bitmap);
            }
        });
    }

    @OnClick(R.id.iv_verity)
    public void verity() {
        mViewModel.requestVerityCode();
    }

    @OnClick(R.id.iv_login)
    public void login() {
        if (TextUtils.isEmpty(username.getText().toString())) {
            showMessage("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            showMessage("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(veritycode.getText().toString())) {
            showMessage("请输入验证码");
            return;
        }

        LoginReqVO reqVO = new LoginReqVO();
        reqVO.username = username.getText().toString();
        reqVO.password = password.getText().toString();
        reqVO.verifyCode = veritycode.getText().toString();

        mViewModel.getUserData().setValue(reqVO);
        mViewModel.login();

    }

    @Override
    protected void onLoadStatusChanged(int status, int requestCode) {
        super.onLoadStatusChanged(status, requestCode);
        if (requestCode == 1 && status == BaseViewModel.LOAD_COMPLETE) {
            ARouter.getInstance().build(RouteMap.DailyPage.MAIN).navigation(this);
            finish();
        }
    }
}
