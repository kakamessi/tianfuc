package com.piano.android.ui.mvp.presenter;

import com.piano.android.bean.login.LoginBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.http.ResultTransforme;
import com.piano.android.http.ScheduleTransformer;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.LoginConstract;
import com.piano.android.ui.mvp.repository.LoginModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/4/21 下午10:21
 * @describe:
 */

public class LoginPresenter extends RxPresenter<LoginConstract.View> implements LoginConstract.Presenter {

    private LoginModel loginModel;

    @Inject
    public LoginPresenter(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    @Override
    public void login(String mobile, String password) {
        Disposable disposable = loginModel.login(mobile, password)
                .subscribeWith(new ResultDisposable<LoginBean>() {

                    @Override
                    public void onSuccess(LoginBean bean) {
                        mView.loginSuccess(bean);
                    }

                    @Override
                    public void onFail(String msg) {
                        mView.showError(msg);

                    }
                });
        addSubscribe(disposable);
    }
}
