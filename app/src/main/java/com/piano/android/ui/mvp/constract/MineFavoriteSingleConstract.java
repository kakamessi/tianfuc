package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午9:18
 * @describe:
 */

public interface MineFavoriteSingleConstract {

    interface View extends BaseView {

        void setMineFavoriteSingleList(List<SingleBean> list);

        void cancelFavoriteSuccess();

        void cancelFavoriteFail();
    }

    interface Presenter extends BasePresenter<View> {
        void getMineFavoriteSingleList();

        void cancelFavorite(long id);
    }
}
