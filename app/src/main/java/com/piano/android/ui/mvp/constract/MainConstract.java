package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.songbook.AdvertBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/5/8 下午11:13
 * @describe:
 */

public interface MainConstract {

    interface View extends BaseView {
        void setAdvert(List<AdvertBean> list);
    }

    interface Presenter extends BasePresenter<View>{
        void getAdvert();
    }

}
