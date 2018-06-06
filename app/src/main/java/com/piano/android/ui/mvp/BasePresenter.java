package com.piano.android.ui.mvp;

/**
 * @author 陈国权
 * @date 2018/3/2 0002
 */

public interface BasePresenter<T extends BaseView> {

    /**
     * 关联View
     * @param view
     */
    void attachView(T view);

    /**
     * 取消关联
     */
    void detachView();
}
