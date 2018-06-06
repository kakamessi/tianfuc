package com.piano.android.http;

import com.piano.android.bean.event.TokenInvalidEvent;
import com.piano.android.common.exception.EmptyException;
import com.piano.android.common.exception.TokenInvalidException;
import com.piano.android.common.exception.ServerException;
import org.greenrobot.eventbus.EventBus;
import io.reactivex.observers.DisposableObserver;

/**
 * @author: 陈国权
 * @date: 2018/4/27 下午10:10
 */
public abstract class ResultDisposable<T> extends DisposableObserver<T> {

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof EmptyException) {
            onSuccess(null);
        } else if (e instanceof ServerException) {
            onFail(e.getMessage());
        } else if (e instanceof TokenInvalidException) {
            // 处理被踢出登录情况
            EventBus.getDefault().post(new TokenInvalidEvent());
            onFail(null);
        } else {
            onFail("网络异常，请稍后再试...");
        }
    }

    @Override
    public void onComplete() {
    }

    public abstract void onSuccess(T t);

    public abstract void onFail(String msg);
}
