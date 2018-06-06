package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.bean.songbook.SingleDetailBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午7:54
 * @describe:
 */

public interface SingleDetailConstract {

    interface View extends BaseView {

        void setSingleDetail(SingleDetailBean bean);

        void addFavoriteSuccess();

        void addFavoriteFail();

        void cancelFavoriteSuccess();

        void cancelFavoriteFail();

    }

    interface Presenter extends BasePresenter<View> {

        void getSingleDetail(long id);

        void addFavorite(long id);

        void cancelFavorite(long id);

        void addHistory(long id);
    }
}
