package com.piano.android.ui.mvp.constract;

import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;

/**
 * @author: 陈国权
 * @date: 2018/4/30 上午1:13
 * @describe:
 */

public interface FeedbackConstract {

    interface View extends BaseView {
        void feedbackSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void feedback(String type, String content);
    }
}
