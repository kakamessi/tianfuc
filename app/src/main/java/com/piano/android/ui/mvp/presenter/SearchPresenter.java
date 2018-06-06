package com.piano.android.ui.mvp.presenter;

import com.piano.android.App;
import com.piano.android.bean.songbook.SearchResultBean;
import com.piano.android.bean.songbook.SearchWordBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.SearchConstract;
import com.piano.android.ui.mvp.repository.SongbookModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/5/5 下午5:11
 * @describe:
 */

public class SearchPresenter extends RxPresenter<SearchConstract.View> implements SearchConstract.Presenter {

    private SongbookModel songbookModel;

    @Inject
    public SearchPresenter(SongbookModel songbookModel) {
        this.songbookModel = songbookModel;
    }

    @Override
    public void getSearchHotWord() {
        Disposable disposable = songbookModel.getSearchHotWord()
                .subscribeWith(new ResultDisposable<List<SearchWordBean>>() {

                    @Override
                    public void onSuccess(List<SearchWordBean> list) {
                        mView.setSearchHotWord(list);
                    }

                    @Override
                    public void onFail(String msg) {
                    }
                });
        addSubscribe(disposable);
    }

    @Override
    public void getHistoryWord() {
        mView.setHistoryWord(App.getInstance().getSearchWord());
    }

    @Override
    public void getSearchResult(String keyword) {
        Disposable disposable = songbookModel.getSearchResult(keyword)
                .subscribeWith(new ResultDisposable<SearchResultBean>() {

                    @Override
                    public void onSuccess(SearchResultBean bean) {
                        mView.setSearchResult(bean);
                    }

                    @Override
                    public void onFail(String msg) {
                        mView.setSearchError();
                    }
                });
        addSubscribe(disposable);
    }
}
