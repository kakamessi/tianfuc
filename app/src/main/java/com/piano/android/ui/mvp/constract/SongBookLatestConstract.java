package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;
import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午4:21
 * @describe:
 */

public interface SongBookLatestConstract {

    interface View extends BaseView {

        void setSongbookLatestAlbum(List<AlbumBean> list);

        void setSongbookLatestSingle(List<SingleBean> list);
    }

    interface Presenter extends BasePresenter<View> {

        void getSongbookLatestAlbum();

        void getSongbookLatestSingle();
    }
}
