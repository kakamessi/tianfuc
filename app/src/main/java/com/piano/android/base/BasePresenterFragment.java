package com.piano.android.base;

import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;
import javax.inject.Inject;

/**
 * @author 陈国权
 * @date 2018/3/2 0002
 */

public abstract class BasePresenterFragment<T extends BasePresenter> extends BaseFragment implements BaseView {

    @Inject
    protected T presenter;

    @Override
    protected void initFragment() {
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
