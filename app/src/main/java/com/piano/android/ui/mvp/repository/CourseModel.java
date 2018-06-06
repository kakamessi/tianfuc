package com.piano.android.ui.mvp.repository;

import com.piano.android.bean.course.CourseBean;
import com.piano.android.bean.course.CourseDetailBean;
import com.piano.android.http.ResultTransforme;
import com.piano.android.http.ScheduleTransformer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午5:13
 * @describe: 教程
 */

public class CourseModel extends BaseModel {

    @Inject
    public CourseModel() {
    }


    public Observable<List<CourseBean>> getCourseList(int category, String keyword) {
        return apiService.getCourseList(category, keyword)
                .compose(new ResultTransforme<List<CourseBean>>())
                .compose(new ScheduleTransformer<List<CourseBean>>());
    }


    public Observable<CourseDetailBean> getCourseDetail(long id) {
        return apiService.getCourseDetail(id)
                .compose(new ResultTransforme<CourseDetailBean>())
                .compose(new ScheduleTransformer<CourseDetailBean>());
    }

    public Observable<List<CourseBean>> getMineCourseList() {
        return apiService.getMyCourse()
                .compose(new ResultTransforme<List<CourseBean>>())
                .compose(new ScheduleTransformer<List<CourseBean>>());
    }
}
