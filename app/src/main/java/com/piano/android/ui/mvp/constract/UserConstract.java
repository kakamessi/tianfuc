package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.user.Portrait;
import com.piano.android.bean.user.UserBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午11:33
 * @describe:
 */

public interface UserConstract {

    interface View extends BaseView {

        void setUserInfo(UserBean bean);

        void updateSuccess();

        void cleanSuccess();

        void uploadPortraitSuccess(Portrait bean);
    }

    interface Presenter extends BasePresenter<View> {

        void getUserInfo(boolean force);

        void updateUserInfo(HashMap<String, Object> map);

        void cleanCache();

        void uploadPortrait(MultipartBody.Part file);
    }
}
