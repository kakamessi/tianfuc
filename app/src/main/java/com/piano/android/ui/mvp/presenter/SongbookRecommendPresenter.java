package com.piano.android.ui.mvp.presenter;

import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.bean.songbook.BannerBean;
import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.SongBookRecommendConstract;
import com.piano.android.ui.mvp.repository.BannerModel;
import com.piano.android.ui.mvp.repository.SongbookModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/4/27 下午11:34
 * @describe:
 */

public class SongbookRecommendPresenter extends RxPresenter<SongBookRecommendConstract.View> implements SongBookRecommendConstract.Presenter {

    private BannerModel bannerModel;
    private SongbookModel songbookModel;

    @Inject
    public SongbookRecommendPresenter(BannerModel bannerModel, SongbookModel songbookModel) {
        this.bannerModel = bannerModel;
        this.songbookModel = songbookModel;
    }

    @Override
    public void getBannerList() {

        Disposable disposable = bannerModel.getBannerList()
                .subscribeWith(new ResultDisposable<List<BannerBean>>() {

                    @Override
                    public void onSuccess(List<BannerBean> list) {
                        mView.setBannerList(list);
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });
        addSubscribe(disposable);
    }

    @Override
    public void getSongbookRecommendAlbum() {
        Disposable disposable = songbookModel.getSongbookRecommendAlbum()
                .subscribeWith(new ResultDisposable<List<AlbumBean>>() {

                    @Override
                    public void onSuccess(List<AlbumBean> list) {
                        mView.setSongbookRecommendAlbum(list);
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });
        addSubscribe(disposable);

    }

    @Override
    public void getSongbookRecommendSingle() {

        Disposable disposable = songbookModel.getSongbookRecommendSingle()
                .subscribeWith(new ResultDisposable<List<SingleBean>>() {

                    @Override
                    public void onSuccess(List<SingleBean> list) {
                        mView.setSongbookRecommendSingle(list);
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });
        addSubscribe(disposable);

    }
}
