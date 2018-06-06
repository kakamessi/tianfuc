package com.piano.android.ui.mvp.constract;

import android.widget.TextView;

import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;

/**
 * @author: 陈国权
 * @date: 2018/4/30 上午12:56
 * @describe:
 */

public interface UpdatePasswordConstract {

    interface View extends BaseView {

        void getSMSCodeSuccess();

        void getSMSCodeFail(String msg);

        void updateSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void timer(TextView view);

        void getSMSCode(String mobile);

        void updatePassword(String password, String smsCode);
    }
}
