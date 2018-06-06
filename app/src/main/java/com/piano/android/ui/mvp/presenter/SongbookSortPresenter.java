package com.piano.android.ui.mvp.presenter;

import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.SongbookSortConstract;
import com.piano.android.ui.mvp.repository.SongbookModel;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/5/5 下午2:26
 * @describe:
 */

public class SongbookSortPresenter extends RxPresenter<SongbookSortConstract.View> implements SongbookSortConstract.Presenter {


    private SongbookModel songbookModel;

    @Inject
    public SongbookSortPresenter(SongbookModel songbookModel) {
        this.songbookModel = songbookModel;
    }

    @Override
    public void getFilterAlbum(HashMap map) {
        Disposable disposable = songbookModel.getFilterAlbum(map)
                .subscribeWith(new ResultDisposable<List<AlbumBean>>() {

                    @Override
                    public void onSuccess(List<AlbumBean> list) {
                        mView.setFilterAlbum(list);
                    }

                    @Override
                    public void onFail(String msg) {
                        mView.showError(msg);
                    }
                });
        addSubscribe(disposable);
    }
}
