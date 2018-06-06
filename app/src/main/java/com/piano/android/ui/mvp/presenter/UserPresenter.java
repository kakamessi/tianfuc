package com.piano.android.ui.mvp.presenter;

import android.util.Log;

import com.chico.common.CacheUtils;
import com.piano.android.App;
import com.piano.android.bean.user.Portrait;
import com.piano.android.bean.user.UserBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.UserConstract;
import com.piano.android.ui.mvp.repository.UserModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午11:34
 * @describe:
 */

public class UserPresenter extends RxPresenter<UserConstract.View> implements UserConstract.Presenter {

    private UserModel userModel;

    @Inject
    public UserPresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void getUserInfo(boolean force) {
        UserBean userBean = App.getInstance().getUserInfo();
        if (userBean != null && !force) {
            mView.setUserInfo(userBean);
        } else {
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
        }
    }

    @Override
    public void updateUserInfo(HashMap<String, Object> map) {
        Disposable disposable = userModel.updateUserInfo(map)
                .subscribeWith(new ResultDisposable<String>() {

                    @Override
                    public void onSuccess(String result) {
                        mView.updateSuccess();
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });
        addSubscribe(disposable);
    }


    @Override
    public void cleanCache() {
        Disposable disposable = Observable
                .create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                        CacheUtils.clearAllCache(App.getInstance());
                        emitter.onNext(true);
                        emitter.onComplete();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean b) throws Exception {
                        mView.cleanSuccess();
                    }
                });
        addSubscribe(disposable);
    }

    @Override
    public void uploadPortrait(MultipartBody.Part file) {
        Disposable disposable = userModel.uploadPortrait(file)
                .subscribeWith(new ResultDisposable<Portrait>() {

                    @Override
                    public void onSuccess(Portrait result) {
                        mView.uploadPortraitSuccess(result);
                    }

                    @Override
                    public void onFail(String msg) {
                        mView.showError(msg);

                    }
                });
        addSubscribe(disposable);
    }
}
