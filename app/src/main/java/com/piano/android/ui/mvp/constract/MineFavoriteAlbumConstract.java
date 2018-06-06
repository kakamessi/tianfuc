package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;
import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午9:18
 * @describe:
 */

public interface MineFavoriteAlbumConstract {

    interface View extends BaseView {
        void setMineFavoriteAlbumList(List<AlbumBean> list);

        void cancelFavoriteSuccess();

        void cancelFavoriteFail();
    }

    interface Presenter extends BasePresenter<View> {
        void getMineFavoriteAlbumList();

        void cancelFavorite(long id);
    }
}
