package gnnt.practice.dailypage.ui.login;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import gnnt.mebs.base.component.BaseViewModel;
import gnnt.mebs.common.util.ResponseTransformer;
import gnnt.mebs.common.util.SharedPreferenceUtils;
import gnnt.practice.dailypage.MemeoryData;
import gnnt.practice.dailypage.vo.Config;
import gnnt.practice.dailypage.vo.DailyPageApi;
import gnnt.practice.dailypage.vo.reqvo.LoginReqVO;
import gnnt.practice.dailypage.vo.reqvo.VerityCodeReqVO;
import gnnt.practice.dailypage.vo.rspvo.LoginRepVO;
import gnnt.practice.dailypage.vo.rspvo.VerityCodeRepVO;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**********************************************************
 *  LoginViewModel.java  2019-07-08
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class LoginViewModel extends BaseViewModel {

    private MutableLiveData<Bitmap> mVerityCode = new MutableLiveData<>();
    private MutableLiveData<LoginReqVO> mUserData = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void requestVerityCode() {
        DailyPageApi api = getApplication().getRetrofitManager().getApi(Config.NET_HOST, DailyPageApi.class);
        api.reqverityCode(new VerityCodeReqVO())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new ResponseTransformer<>())
                .subscribe(new ViewModelSingleObserver<VerityCodeRepVO>() {
                    @Override
                    public void onSuccess(VerityCodeRepVO verityCodeRepVO) {
                        super.onSuccess(verityCodeRepVO);
                        mVerityCode.setValue(decode(verityCodeRepVO.imageBase64));
                        MemeoryData.getInstance().setCookie(verityCodeRepVO.cookie);
                    }
                });
    }

    public void login() {
        DailyPageApi api = getApplication().getRetrofitManager().getApi(Config.NET_HOST, DailyPageApi.class);
        LoginReqVO reqVO = new LoginReqVO();
        LoginReqVO mValue = mUserData.getValue();
        reqVO.cookie = MemeoryData.getInstance().getCookie();
        reqVO.username = mValue.username;
        reqVO.password = mValue.password;
        reqVO.verifyCode = mValue.verifyCode;
        api.login(reqVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new ResponseTransformer<>())
                .subscribe(new ViewModelSingleObserver<LoginRepVO>() {
                    @Override
                    public void onSuccess(LoginRepVO loginRepVO) {
                        super.onSuccess(loginRepVO);
                        //保存用户信息
                        SharedPreferenceUtils.setParam(getApplication(), "username", mValue.username);
                    }

                    @Override
                    public int getRequestCode() {
                        return 1;
                    }
                });
    }

    private Bitmap decode(String res) {
        Bitmap result = null;
        try {
            if (!TextUtils.isEmpty(res)) {
                byte[] bytes = Base64.decode(res, Base64.DEFAULT);
                result = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public MutableLiveData<Bitmap> getVerityCode() {
        return mVerityCode;
    }

    public MutableLiveData<LoginReqVO> getUserData() {
        return mUserData;
    }
}
