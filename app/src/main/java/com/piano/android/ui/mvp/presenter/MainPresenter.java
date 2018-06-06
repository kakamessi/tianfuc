package com.piano.android.ui.mvp.presenter;

import com.piano.android.bean.songbook.AdvertBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.MainConstract;
import com.piano.android.ui.mvp.repository.AdvertModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/5/8 下午11:14
 * @describe:
 */

public class MainPresenter extends RxPresenter<MainConstract.View> implements MainConstract.Presenter {

    private AdvertModel advertModel;

    @Inject
    public MainPresenter(AdvertModel advertModel) {
        this.advertModel = advertModel;
    }

    @Override
    public void getAdvert() {
        Disposable disposable = advertModel.getAdvertList()
                .subscribeWith(new ResultDisposable<List<AdvertBean>>() {

                    @Override
                    public void onSuccess(List<AdvertBean> list) {
                        mView.setAdvert(list);
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });
        addSubscribe(disposable);
    }
}
