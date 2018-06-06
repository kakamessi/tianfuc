package com.piano.android.ui.mvp.presenter;

import android.widget.TextView;

import com.piano.android.R;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.ForgetPasswordConstract;
import com.piano.android.ui.mvp.repository.SmsModel;
import com.piano.android.ui.mvp.repository.UserModel;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

/**
 * @author: 陈国权
 * @date: 2018/4/30 上午1:01
 * @describe:
 */

public class ForgetPasswordPresenter extends RxPresenter<ForgetPasswordConstract.View> implements ForgetPasswordConstract.Presenter {

    private SmsModel smsModel;
    private UserModel userModel;
    private int countTime = 60;

    @Inject
    public ForgetPasswordPresenter(SmsModel smsModel, UserModel userModel) {
        this.smsModel = smsModel;
        this.userModel = userModel;
    }

    @Override
    public void timer(final TextView view) {
        Disposable disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(@io.reactivex.annotations.NonNull Long increaseTime) throws Exception {
                        return countTime - increaseTime.intValue();
                    }
                })
                .take(59)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Disposable disposable) throws Exception {
                        view.setEnabled(false);
                    }
                })
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        unSubscribe();
                    }
                })
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer result) {
                        view.setText(result + "s");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        view.setEnabled(true);
                        view.setText(R.string.send_again);
                    }
                });
        addSubscribe(disposable);
    }

    @Override
    public void getSMSCode(String mobile) {
        Disposable disposable = smsModel.getSMSCodeForget(mobile)
                .subscribeWith(new ResultDisposable<String>() {

                    @Override
                    public void onSuccess(String bean) {
                        mView.getSMSCodeSuccess();
                    }

                    @Override
                    public void onFail(String msg) {
                        mView.getSMSCodeFail(msg);

                    }
                });
        addSubscribe(disposable);
    }

    @Override
    public void forgetPassword(String mobile, String password, String smsCode) {
        Disposable disposable = userModel.forgetPassword(mobile, password, smsCode)
                .subscribeWith(new ResultDisposable<String>() {

                    @Override
                    public void onSuccess(String bean) {
                        mView.forgetSuccess();
                    }

                    @Override
                    public void onFail(String msg) {
                        mView.showError(msg);

                    }
                });
        addSubscribe(disposable);
    }
}
