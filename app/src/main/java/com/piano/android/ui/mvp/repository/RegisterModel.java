package com.piano.android.ui.mvp.repository;

import com.piano.android.http.ResultTransforme;
import com.piano.android.http.ScheduleTransformer;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author: 陈国权
 * @date: 2018/4/27 下午10:43
 * @describe: 注册
 */

public class RegisterModel extends BaseModel {

    @Inject
    public RegisterModel() {
    }

    public Observable<String> register(String mobile, String password, String smsCode) {
        return apiService.register(mobile, password, smsCode)
                .compose(new ResultTransforme<String>())
                .compose(new ScheduleTransformer<String>());
    }
}
