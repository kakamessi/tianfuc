package com.piano.android.ui.mvp.presenter;

import android.util.Log;

import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.AlbumConstract;
import com.piano.android.ui.mvp.repository.SongbookModel;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午4:55
 * @describe:
 */

public class AlbumPresenter extends RxPresenter<AlbumConstract.View> implements AlbumConstract.Presenter {


    private SongbookModel songbookModel;

    @Inject
    public AlbumPresenter(SongbookModel songbookModel) {
        this.songbookModel = songbookModel;
    }

    @Override
    public void getAllRecommendAlbum(int page, int limit) {
        Disposable disposable = songbookModel.getAllRecommendAlbum(page, limit)
                .subscribeWith(new ResultDisposable<List<AlbumBean>>() {

                    @Override
                    public void onSuccess(List<AlbumBean> list) {
                        mView.setAllAlbum(list);
                    }

                    @Override
                    public void onFail(String msg) {
                        Log.e("===>",msg);
                    }
                });
        addSubscribe(disposable);
    }

    @Override
    public void getAllLatestAlbum(int page, int limit) {
        Disposable disposable = songbookModel.getAllLatestAlbum(page, limit)
                .subscribeWith(new ResultDisposable<List<AlbumBean>>() {

                    @Override
                    public void onSuccess(List<AlbumBean> list) {
                        mView.setAllAlbum(list);
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });
        addSubscribe(disposable);
    }
}
