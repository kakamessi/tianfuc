package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午4:52
 * @describe:
 */

public interface AlbumConstract {

    interface View extends BaseView {

        void setAllAlbum(List<AlbumBean> list);
    }

    interface Presenter extends BasePresenter<View> {

        void getAllRecommendAlbum(int page, int limit);

        void getAllLatestAlbum(int page, int limit);
    }
}
