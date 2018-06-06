package com.piano.android.ui.mvp.constract;

import com.piano.android.bean.course.CourseBean;
import com.piano.android.ui.mvp.BasePresenter;
import com.piano.android.ui.mvp.BaseView;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午9:18
 * @describe:
 */

public interface MineFavoriteCourseConstract {

    interface View extends BaseView {
        void setMineFavoriteCourseList(List<CourseBean> list);

        void cancelFavoriteSuccess();

        void cancelFavoriteFail();
    }

    interface Presenter extends BasePresenter<View> {
        void getMineFavoriteCourseList();

        void cancelFavorite(long id);
    }
}
