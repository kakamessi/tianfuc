package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.login.LoginBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;

/**
 * @author: 陈国权
 * @date: 2018/4/21 下午10:20
 * @describe:
 */

public interface LoginConstract {

    interface View extends BaseView {

        void loginSuccess(LoginBean bean);
    }

    interface Presenter extends BasePresenter<View> {

        void login(String mobile, String password);

    }
}
