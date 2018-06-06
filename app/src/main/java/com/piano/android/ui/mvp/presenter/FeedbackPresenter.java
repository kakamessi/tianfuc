package com.piano.android.ui.mvp.presenter;

import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.FeedbackConstract;
import com.piano.android.ui.mvp.repository.UserModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author: chicochen
 * @date: 2018/4/30 上午1:15
 * @describe:
 */

public class FeedbackPresenter extends RxPresenter<FeedbackConstract.View> implements FeedbackConstract.Presenter {

    private UserModel userModel;

    @Inject
    public FeedbackPresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void feedback(String type, String content) {
        Disposable disposable = userModel.feedback(type, content)
                .subscribeWith(new ResultDisposable<String>() {

                    @Override
                    public void onSuccess(String s) {
                        mView.feedbackSuccess();
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });
        addSubscribe(disposable);
    }
}
