package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.songbook.SearchResultBean;
import com.piano.android.bean.songbook.SearchWordBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/5/5 下午5:05
 * @describe:
 */

public interface SearchConstract {

    interface View extends BaseView {
        void setSearchHotWord(List<SearchWordBean> list);

        void setHistoryWord(List<String> list);

        void setSearchResult(SearchResultBean bean);

        void setSearchError();
    }

    interface Presenter extends BasePresenter<View> {
        void getSearchHotWord();

        void getHistoryWord();

        void getSearchResult(String keyword);
    }
}
