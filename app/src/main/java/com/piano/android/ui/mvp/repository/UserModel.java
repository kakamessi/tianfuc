package com.piano.android.ui.mvp.repository;

import com.piano.android.bean.ResultBean;
import com.piano.android.bean.user.Portrait;
import com.piano.android.bean.user.UserBean;
import com.piano.android.common.exception.EmptyException;
import com.piano.android.common.exception.ServerException;
import com.piano.android.common.exception.TokenInvalidException;
import com.piano.android.http.ResultTransforme;
import com.piano.android.http.ScheduleTransformer;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午11:05
 * @describe: 用户相关
 */

public class UserModel extends BaseModel {

    @Inject
    public UserModel() {
    }


    public Observable<UserBean> getUserInfo() {
        return apiService.getUserInfo()
                .compose(new ResultTransforme<UserBean>())
                .compose(new ScheduleTransformer<UserBean>());
    }

    public Observable<String> updateUserInfo(HashMap<String, Object> map) {
        return apiService.updateUserInfo(map)
                .compose(new ResultTransforme<String>())
                .compose(new ScheduleTransformer<String>());
    }

    public Observable<String> updatePassword(String password, String smsCode) {
        return apiService.updatePassword(password, smsCode)
                .compose(new ResultTransforme<String>())
                .compose(new ScheduleTransformer<String>());
    }

    public Observable<String> forgetPassword(String mobile, String password, String smsCode) {
        return apiService.forgetPassword(mobile, password, smsCode)
                .compose(new ResultTransforme<String>())
                .compose(new ScheduleTransformer<String>());
    }

    public Observable<String> feedback(String type, String content) {
        return apiService.feedback(type, type)
                .compose(new ResultTransforme<String>())
                .compose(new ScheduleTransformer<String>());
    }

    public Observable<Portrait> uploadPortrait(MultipartBody.Part file) {
        return apiService.uploadPortrait(file)
                .flatMap(
                        new Function<Portrait, Observable<Portrait>>() {
                            @Override
                            public Observable<Portrait> apply(@NonNull Portrait result) throws Exception {
                                if (result.getCode() == 0) {
                                    return Observable.just(result);
                                } else if (result.getCode() == 401) {
                                    return Observable.error(new TokenInvalidException());
                                } else {
                                    return Observable.error(new ServerException(result.getMsg()));
                                }
                            }
                        })
                .compose(new ScheduleTransformer<Portrait>());
    }
}
