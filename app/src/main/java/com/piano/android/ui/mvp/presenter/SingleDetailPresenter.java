package com.piano.android.ui.mvp.presenter;

import com.piano.android.bean.songbook.SingleDetailBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.SingleDetailConstract;
import com.piano.android.ui.mvp.repository.FavoriteModel;
import com.piano.android.ui.mvp.repository.HistoryModel;
import com.piano.android.ui.mvp.repository.SongbookModel;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午7:56
 * @describe:
 */

public class SingleDetailPresenter extends RxPresenter<SingleDetailConstract.View> implements SingleDetailConstract.Presenter {

    private SongbookModel songbookModel;
    private FavoriteModel favoriteModel;
    private HistoryModel historyModel;

    @Inject
    public SingleDetailPresenter(SongbookModel songbookModel, FavoriteModel favoriteModel, HistoryModel historyModel) {
        this.songbookModel = songbookModel;
        this.favoriteModel = favoriteModel;
        this.historyModel = historyModel;
    }

    @Override
    public void getSingleDetail(long id) {
        Disposable disposable = songbookModel.getSingleDetail(id)
                .subscribeWith(new ResultDisposable<SingleDetailBean>() {

                    @Override
                    public void onSuccess(SingleDetailBean bean) {
                        mView.setSingleDetail(bean);
                    }

                    @Override
                    public void onFail(String msg) {
                        mView.showError(msg);
                    }
                });
        addSubscribe(disposable);
    }

    @Override
    public void addFavorite(long id) {
        Disposable disposable = favoriteModel.addFavoriteSingle(id)
                .subscribeWith(new ResultDisposable<String>() {

                    @Override
                    public void onSuccess(String string) {
                        mView.addFavoriteSuccess();
                    }

                    @Override
                    public void onFail(String msg) {
                        mView.addFavoriteFail();
                    }
                });
        addSubscribe(disposable);
    }

    @Override
    public void cancelFavorite(long id) {
        Disposable disposable = favoriteModel.cancelFavoriteSingle(id)
                .subscribeWith(new ResultDisposable<String>() {

                    @Override
                    public void onSuccess(String string) {
                        mView.cancelFavoriteSuccess();
                    }

                    @Override
                    public void onFail(String msg) {
                        mView.cancelFavoriteFail();
                    }
                });
        addSubscribe(disposable);
    }

    @Override
    public void addHistory(long id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("songId", id);
        Disposable disposable = historyModel.addHistory(map)
                .subscribeWith(new ResultDisposable<String>() {

                    @Override
                    public void onSuccess(String s) {

                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });
        addSubscribe(disposable);
    }
}
