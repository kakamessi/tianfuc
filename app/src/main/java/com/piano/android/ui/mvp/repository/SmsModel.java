package com.piano.android.ui.mvp.repository;

import com.piano.android.http.ResultTransforme;
import com.piano.android.http.ScheduleTransformer;
import javax.inject.Inject;
import io.reactivex.Observable;

/**
 * @author: 陈国权
 * @date: 2018/4/27 下午10:40
 * @describe: 短信验证
 */

public class SmsModel extends BaseModel {

    @Inject
    public SmsModel() {
    }

    public Observable<String> getSMSCodeRegister(String mobile) {
        return apiService.getSMSCodeRegister(mobile)
                .compose(new ResultTransforme<String>())
                .compose(new ScheduleTransformer<String>());
    }

    public Observable<String> getSMSCodeForget(String mobile) {
        return apiService.getSMSCodeForget(mobile)
                .compose(new ResultTransforme<String>())
                .compose(new ScheduleTransformer<String>());
    }

    public Observable<String> getSMSCodePassword(String mobile) {
        return apiService.getSMSCodePassword(mobile)
                .compose(new ResultTransforme<String>())
                .compose(new ScheduleTransformer<String>());
    }
}
