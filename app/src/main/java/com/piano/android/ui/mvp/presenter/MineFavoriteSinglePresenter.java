package com.piano.android.ui.mvp.presenter;

import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.MineFavoriteSingleConstract;
import com.piano.android.ui.mvp.repository.FavoriteModel;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午9:20
 * @describe:
 */

public class MineFavoriteSinglePresenter extends RxPresenter<MineFavoriteSingleConstract.View> implements MineFavoriteSingleConstract.Presenter {

    private FavoriteModel favoriteModel;

    @Inject
    public MineFavoriteSinglePresenter(FavoriteModel favoriteModel) {
        this.favoriteModel = favoriteModel;
    }

    @Override
    public void getMineFavoriteSingleList() {
        Disposable disposable = favoriteModel.getFavoriteSingleList()
                .subscribeWith(new ResultDisposable<List<SingleBean>>() {

                    @Override
                    public void onSuccess(List<SingleBean> list) {
                        mView.setMineFavoriteSingleList(list);
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
}
