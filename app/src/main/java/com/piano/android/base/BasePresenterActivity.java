package com.piano.android.base;

import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;

import javax.inject.Inject;

/**
 * @author: 陈国权
 * @date: 2018/4/21 下午10:03
 * @describe:
 */

public abstract class BasePresenterActivity<T extends BasePresenter> extends BaseActivity implements BaseView {

    @Inject
    protected T presenter;

    @Override
    public void initActivity() {
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
