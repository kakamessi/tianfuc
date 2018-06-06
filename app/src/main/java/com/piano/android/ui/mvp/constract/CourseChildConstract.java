package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.course.CourseBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;
import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午10:39
 * @describe:
 */

public interface CourseChildConstract {

    interface View extends BaseView {

        void setCourseList(List<CourseBean> list);

    }

    interface Presenter extends BasePresenter<View> {

        void getCourseList(int category);
    }
}
