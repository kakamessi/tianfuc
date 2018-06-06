package com.piano.android.ui.mvp.presenter;

import com.piano.android.bean.course.CourseBean;
import com.piano.android.http.ResultDisposable;
import com.piano.android.ui.mvp.RxPresenter;
import com.piano.android.ui.mvp.constract.CourseChildConstract;
import com.piano.android.ui.mvp.repository.CourseModel;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午10:41
 * @describe:
 */

public class CourseChildPresenter extends RxPresenter<CourseChildConstract.View> implements CourseChildConstract.Presenter {

    private CourseModel courseModel;

    @Inject
    public CourseChildPresenter(CourseModel courseModel) {
        this.courseModel = courseModel;
    }

    @Override
    public void getCourseList(int category) {

        Disposable disposable = courseModel.getCourseList(category, null)
                .subscribeWith(new ResultDisposable<List<CourseBean>>() {

                    @Override
                    public void onSuccess(List<CourseBean> list) {
                        mView.setCourseList(list);
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });
        addSubscribe(disposable);
    }
}
