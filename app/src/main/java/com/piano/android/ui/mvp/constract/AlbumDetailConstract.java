package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.songbook.AlbumDetailBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午7:54
 * @describe:
 */

public interface AlbumDetailConstract {

    interface View extends BaseView {

        void setAlbumDetail(AlbumDetailBean bean);

        void addFavoriteSuccess();

        void addFavoriteFail();

        void cancelFavoriteSuccess();

        void cancelFavoriteFail();

    }

    interface Presenter extends BasePresenter<View> {

        void getAlbumDetail(long id);

        void addFavorite(long id);

        void cancelFavorite(long id);
    }
}
