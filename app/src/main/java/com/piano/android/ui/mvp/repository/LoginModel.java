package com.piano.android.ui.mvp.repository;

import com.piano.android.bean.login.LoginBean;
import com.piano.android.http.ResultTransforme;
import com.piano.android.http.ScheduleTransformer;
import javax.inject.Inject;
import io.reactivex.Observable;

/**
 * @author: 陈国权
 * @date: 2018/4/27 下午10:35
 * @describe: 登陆
 */

public class LoginModel extends BaseModel {

    @Inject
    public LoginModel() {

    }

    public Observable<LoginBean> login(String mobile, String password) {
        return apiService.login(mobile, password)
                .compose(new ResultTransforme<LoginBean>())
                .compose(new ScheduleTransformer<LoginBean>());
    }
}
