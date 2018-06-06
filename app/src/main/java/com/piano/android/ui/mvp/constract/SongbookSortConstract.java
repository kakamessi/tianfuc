package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;
import java.util.HashMap;
import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/5/5 下午2:24
 * @describe:
 */

public interface SongbookSortConstract {


    interface View extends BaseView {

        void setFilterAlbum(List<AlbumBean> list);

    }

    interface Presenter extends BasePresenter<View> {
        void getFilterAlbum(HashMap map);
    }

}
