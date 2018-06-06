package com.piano.android.ui.mvp.presenter;

import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.SingleConstract;
import com.piano.android.ui.mvp.repository.SongbookModel;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午4:55
 * @describe:
 */

public class SinglePresenter extends RxPresenter<SingleConstract.View> implements SingleConstract.Presenter {


    private SongbookModel songbookModel;

    @Inject
    public SinglePresenter(SongbookModel songbookModel) {
        this.songbookModel = songbookModel;
    }

    @Override
    public void getAllRecommendSingle(int page, int limit) {
        Disposable disposable = songbookModel.getAllRecommendSingle(page, limit)
                .subscribeWith(new ResultDisposable<List<SingleBean>>() {

                    @Override
                    public void onSuccess(List<SingleBean> list) {
                        mView.setAllSingle(list);
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });
        addSubscribe(disposable);
    }

    @Override
    public void getAllLatestSingle(int page, int limit) {
        Disposable disposable = songbookModel.getAllLatestSingle(page, limit)
                .subscribeWith(new ResultDisposable<List<SingleBean>>() {

                    @Override
                    public void onSuccess(List<SingleBean> list) {
                        mView.setAllSingle(list);
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });
        addSubscribe(disposable);
    }
}
