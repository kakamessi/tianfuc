package com.piano.android.ui.mvp.presenter;

import com.piano.android.bean.course.CourseBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.MineFavoriteCourseConstract;
import com.piano.android.ui.mvp.repository.FavoriteModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午9:20
 * @describe:
 */

public class MineFavoriteCoursePresenter extends RxPresenter<MineFavoriteCourseConstract.View> implements MineFavoriteCourseConstract.Presenter {

    private FavoriteModel favoriteModel;

    @Inject
    public MineFavoriteCoursePresenter(FavoriteModel favoriteModel) {
        this.favoriteModel = favoriteModel;
    }

    @Override
    public void getMineFavoriteCourseList() {
        Disposable disposable = favoriteModel.getFavoriteCourseList()
                .subscribeWith(new ResultDisposable<List<CourseBean>>() {

                    @Override
                    public void onSuccess(List<CourseBean> list) {
                        mView.setMineFavoriteCourseList(list);
                    }

                    @Override
                    public void onFail(String msg) {
                        mView.showError(msg);
                    }
                });
        addSubscribe(disposable);
    }

    @Override
    public void cancelFavorite(long id) {
        Disposable disposable = favoriteModel.cancelFavoriteCourse(id)
                .subscribeWith(new ResultDisposable<String>() {

                    @Override
                    public void onSuccess(String string) {
                        mView.cancelFavoriteSuccess();
                    }

                    @Override
                    public void onFail(String msg) {
                        mView.cancelFavoriteFail();
                    }
                });
        addSubscribe(disposable);
    }
}
