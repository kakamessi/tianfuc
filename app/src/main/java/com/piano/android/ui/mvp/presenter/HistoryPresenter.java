package com.piano.android.ui.mvp.presenter;

import com.piano.android.bean.songbook.HistoryBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.HistoryConstract;
import com.piano.android.ui.mvp.repository.HistoryModel;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/5/5 下午10:15
 * @describe:
 */

public class HistoryPresenter extends RxPresenter<HistoryConstract.View> implements HistoryConstract.Presenter {

    private HistoryModel historyModel;

    @Inject
    public HistoryPresenter(HistoryModel historyModel) {
        this.historyModel = historyModel;
    }

    @Override
    public void getHistoryList(int type) {
        Disposable disposable = historyModel.getHistoryList(type)
                .subscribeWith(new ResultDisposable<List<HistoryBean>>() {

                    @Override
                    public void onSuccess(List<HistoryBean> list) {
                        mView.setHistoryList(list);
                    }

                    @Override
                    public void onFail(String msg) {
                        mView.showError(msg);
                    }
                });
        addSubscribe(disposable);
    }
}
