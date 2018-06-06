package com.piano.android.ui.mvp.presenter;

import com.piano.android.App;
import com.piano.android.bean.user.UserBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.MineConstract;
import com.piano.android.ui.mvp.repository.UserModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午11:13
 * @describe:
 */

public class MinePresenter extends RxPresenter<MineConstract.View> implements MineConstract.Presenter {

    private UserModel userModel;

    @Inject
    public MinePresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void getUserInfo() {
        UserBean userBean = App.getInstance().getUserInfo();
        if (userBean == null) {
            Disposable disposable = userModel.getUserInfo()
                    .subscribeWith(new ResultDisposable<UserBean>() {

                        @Override
                        public void onSuccess(UserBean bean) {
                            if (bean != null) {
                                App.mInstance.setUserInfo(bean);
                                mView.setUserInfo(bean);
                            }
                        }

                        @Override
                        public void onFail(String msg) {

                        }
                    });
            addSubscribe(disposable);
        } else {
            mView.setUserInfo(userBean);
        }
    }
}
