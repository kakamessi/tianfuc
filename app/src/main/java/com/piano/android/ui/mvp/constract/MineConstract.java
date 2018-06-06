package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.user.UserBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午11:12
 * @describe:
 */

public interface MineConstract {

    interface View extends BaseView {
        void setUserInfo(UserBean bean);
    }

    interface Presenter extends BasePresenter<View> {
        void getUserInfo();
    }
}
