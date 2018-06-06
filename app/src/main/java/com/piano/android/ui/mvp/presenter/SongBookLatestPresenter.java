package com.piano.android.ui.mvp.presenter;

import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.SongBookLatestConstract;
import com.piano.android.ui.mvp.repository.SongbookModel;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午4:28
 * @describe:
 */

public class SongBookLatestPresenter extends RxPresenter<SongBookLatestConstract.View> implements SongBookLatestConstract.Presenter{

    private SongbookModel songbookModel;


    @Inject
    public SongBookLatestPresenter(SongbookModel songbookModel) {
        this.songbookModel = songbookModel;
    }

    @Override
    public void getSongbookLatestAlbum() {
        Disposable disposable = songbookModel.getSongbookLatestAlbum()
                .subscribeWith(new ResultDisposable<List<AlbumBean>>() {

                    @Override
                    public void onSuccess(List<AlbumBean> list) {
                        mView.setSongbookLatestAlbum(list);
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });
        addSubscribe(disposable);
    }

    @Override
    public void getSongbookLatestSingle() {
        Disposable disposable = songbookModel.getSongbookLatestSingle()
                .subscribeWith(new ResultDisposable<List<SingleBean>>() {

                    @Override
                    public void onSuccess(List<SingleBean> list) {
                        mView.setSongbookLatestSingle(list);
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });
        addSubscribe(disposable);
    }
}
