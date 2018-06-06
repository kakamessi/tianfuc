package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;
import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午4:52
 * @describe:
 */

public interface SingleConstract {

    interface View extends BaseView {

        void setAllSingle(List<SingleBean> list);
    }

    interface Presenter extends BasePresenter<View> {

        void getAllRecommendSingle(int page, int limit);

        void getAllLatestSingle(int page, int limit);
    }
}
