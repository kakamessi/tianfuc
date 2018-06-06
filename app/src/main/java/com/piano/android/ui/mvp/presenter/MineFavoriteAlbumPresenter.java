package com.piano.android.ui.mvp.presenter;

import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.MineFavoriteAlbumConstract;
import com.piano.android.ui.mvp.repository.FavoriteModel;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午9:20
 * @describe:
 */

public class MineFavoriteAlbumPresenter extends RxPresenter<MineFavoriteAlbumConstract.View> implements MineFavoriteAlbumConstract.Presenter {

    private FavoriteModel favoriteModel;

    @Inject
    public MineFavoriteAlbumPresenter(FavoriteModel favoriteModel) {
        this.favoriteModel = favoriteModel;
    }


    @Override
    public void getMineFavoriteAlbumList() {
        Disposable disposable = favoriteModel.getFavoriteAlbumList()
                .subscribeWith(new ResultDisposable<List<AlbumBean>>() {

                    @Override
                    public void onSuccess(List<AlbumBean> list) {
                        mView.setMineFavoriteAlbumList(list);
                    }

                    @Override
                    public void onFail(String msg) {
                        mView.showError(msg);
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
