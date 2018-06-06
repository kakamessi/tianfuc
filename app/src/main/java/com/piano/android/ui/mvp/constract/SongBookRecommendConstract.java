package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.bean.songbook.BannerBean;
import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/4/27 下午11:32
 * @describe:
 */

public interface SongBookRecommendConstract {


    interface View extends BaseView {

        void setBannerList(List<BannerBean> list);

        void setSongbookRecommendAlbum(List<AlbumBean> list);

        void setSongbookRecommendSingle(List<SingleBean> list);
    }

    interface Presenter extends BasePresenter<View> {

        void getBannerList();

        void getSongbookRecommendAlbum();

        void getSongbookRecommendSingle();
    }
}
