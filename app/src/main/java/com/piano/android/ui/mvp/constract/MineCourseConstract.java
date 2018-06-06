package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.course.CourseBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午9:10
 * @describe:
 */

public interface MineCourseConstract {


    interface View extends BaseView {

        void setMineCourseList(List<CourseBean> list);

    }

    interface Presenter extends BasePresenter<View> {

        void getMineCourseList();
    }
}
