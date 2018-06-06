package com.piano.android.ui.mvp.constract;

import android.widget.TextView;

import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;

/**
 * @author: 陈国权
 * @date: 2018/4/27 下午10:33
 * @describe:
 */

public interface RegisterConstract {

    interface View extends BaseView {

        void getSMSCodeSuccess();

        void getSMSCodeFail(String msg);

        void registerSuccess();
    }

    interface Presenter extends BasePresenter<View> {

        void timer(TextView view);

        void getSMSCode(String mobile);

        void register(String mobile, String password, String smsCode);

    }

}
