package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.songbook.SingleDetailBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;

public interface MusicConstract {


    interface View extends BaseView {

        void setSingleDetail(SingleDetailBean bean);

        void addFavoriteSuccess();

        void addFavoriteFail();

        void cancelFavoriteSuccess();

        void cancelFavoriteFail();

    }

    interface Presenter extends BasePresenter<SingleDetailConstract.View> {

        void getSingleDetail(long id);

        void addFavorite(long id);

        void cancelFavorite(long id);

        void addHistory(long id);
    }


}
