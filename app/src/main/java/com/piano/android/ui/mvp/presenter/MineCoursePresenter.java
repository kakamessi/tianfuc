package com.piano.android.ui.mvp.presenter;

import com.piano.android.bean.course.CourseBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.MineCourseConstract;
import com.piano.android.ui.mvp.repository.CourseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午9:11
 * @describe:
 */

public class MineCoursePresenter extends RxPresenter<MineCourseConstract.View> implements MineCourseConstract.Presenter {

    private CourseModel courseModel;

    @Inject
    public MineCoursePresenter(CourseModel courseModel) {
        this.courseModel = courseModel;
    }

    @Override
    public void getMineCourseList() {
        Disposable disposable = courseModel.getMineCourseList()
                .subscribeWith(new ResultDisposable<List<CourseBean>>() {

                    @Override
                    public void onSuccess(List<CourseBean> list) {
                        mView.setMineCourseList(list);
                    }

                    @Override
                    public void onFail(String msg) {
                        mView.showError(msg);
                    }
                });
        addSubscribe(disposable);

    }
}
