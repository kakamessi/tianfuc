package com.piano.android.ui.mvp.presenter;

import com.piano.android.bean.course.CourseDetailBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.CourseDetailConstract;
import com.piano.android.ui.mvp.repository.CourseModel;
import com.piano.android.ui.mvp.repository.FavoriteModel;
import com.piano.android.ui.mvp.repository.HistoryModel;
import java.util.HashMap;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午7:56
 * @describe:
 */

public class CourseDetailPresenter extends RxPresenter<CourseDetailConstract.View> implements CourseDetailConstract.Presenter {


    private CourseModel courseModel;
    private FavoriteModel favoriteModel;
    private HistoryModel historyModel;

    @Inject
    public CourseDetailPresenter(CourseModel courseModel, FavoriteModel favoriteModel, HistoryModel historyModel) {
        this.courseModel = courseModel;
        this.favoriteModel = favoriteModel;
        this.historyModel = historyModel;
    }

    @Override
    public void getCourseDetail(long id) {
        Disposable disposable = courseModel.getCourseDetail(id)
                .subscribeWith(new ResultDisposable<CourseDetailBean>() {

                    @Override
                    public void onSuccess(CourseDetailBean bean) {
                        mView.setCourseDetail(bean);
                    }

                    @Override
                    public void onFail(String msg) {
                        mView.showError(msg);
                    }
                });
        addSubscribe(disposable);
    }

    @Override
    public void addFavorite(long id) {
        Disposable disposable = favoriteModel.addFavoriteCourse(id)
                .subscribeWith(new ResultDisposable<String>() {

                    @Override
                    public void onSuccess(String string) {
                        mView.addFavoriteSuccess();
                    }

                    @Override
                    public void onFail(String msg) {
                        mView.addFavoriteFail();
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

    @Override
    public void addHistory(long teachId,long videoId) {
        HashMap<String, Object> map = new HashMap<>();
        if(teachId!=-1) {
            map.put("teachId", teachId);
        }

        if(videoId!=-1) {
            map.put("vedioId", videoId);
        }
        Disposable disposable = historyModel.addHistory(map)
                .subscribeWith(new ResultDisposable<String>() {
                    @Override
                    public void onSuccess(String string) {
                    }

                    @Override
                    public void onFail(String msg) {
                    }
                });
        addSubscribe(disposable);
    }
}
