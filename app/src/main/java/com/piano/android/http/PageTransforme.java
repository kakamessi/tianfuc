package com.piano.android.http;


import com.piano.android.bean.PageResultBean;
import com.piano.android.common.exception.EmptyException;
import com.piano.android.common.exception.ServerException;
import com.piano.android.common.exception.TokenInvalidException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author: 陈国权
 * @date: 2018/4/27 下午10:10
 */
public class PageTransforme<T> implements ObservableTransformer<PageResultBean, T> {

    public static final int SUCCESS_CODE = 0; //成功
    public static final int TOKEN_INVALID = 401; //token 失效
    public static final int ERROR_CODE = 500; //系统错我


    @Override
    public ObservableSource<T> apply(@NonNull Observable<PageResultBean> upstream) {
        return upstream.flatMap(
                new Function<PageResultBean, Observable<T>>() {
                    @Override
                    public Observable<T> apply(@NonNull PageResultBean result) throws Exception {
                        if (result.code == SUCCESS_CODE) {
                            if (result.page == null) {
                                return Observable.error(new EmptyException());
                            } else {
                                if (result.page.list == null) {
                                    return Observable.error(new EmptyException());
                                } else {
                                    return (Observable<T>) Observable.just(result.page.list);
                                }
                            }
                        } else if (result.code == TOKEN_INVALID) {
                            return Observable.error(new TokenInvalidException());
                        } else {
                            return Observable.error(new ServerException(result.msg));
                        }
                    }
                }
        );
    }
}
