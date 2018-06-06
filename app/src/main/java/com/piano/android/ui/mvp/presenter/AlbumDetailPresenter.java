package com.piano.android.ui.mvp.presenter;

import com.piano.android.bean.songbook.AlbumDetailBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.AlbumDetailConstract;
import com.piano.android.ui.mvp.repository.FavoriteModel;
import com.piano.android.ui.mvp.repository.SongbookModel;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午7:56
 * @describe:
 */

public class AlbumDetailPresenter extends RxPresenter<AlbumDetailConstract.View> implements AlbumDetailConstract.Presenter {

    private SongbookModel songbookModel;
    private FavoriteModel favoriteModel;

    @Inject
    public AlbumDetailPresenter(SongbookModel songbookModel, FavoriteModel favoriteModel) {
        this.songbookModel = songbookModel;
        this.favoriteModel = favoriteModel;
    }

    @Override
    public void getAlbumDetail(long id) {
        Disposable disposable = songbookModel.getAlbumDetail(id)
                .subscribeWith(new ResultDisposable<AlbumDetailBean>() {

                    @Override
                    public void onSuccess(AlbumDetailBean bean) {
                        mView.setAlbumDetail(bean);
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
        Disposable disposable = favoriteModel.addFavariteAlbum(id)
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
        Disposable disposable = favoriteModel.cancleFavariteAlbum(id)
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
}
