package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.songbook.HistoryBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;
import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/5/5 下午10:13
 * @describe:
 */

public interface HistoryConstract {

    interface View extends BaseView {
        void setHistoryList(List<HistoryBean> list);
    }

    interface Presenter extends BasePresenter<View> {
        void getHistoryList(int type);
    }
}
