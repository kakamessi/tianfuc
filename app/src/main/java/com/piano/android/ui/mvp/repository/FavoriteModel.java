package com.piano.android.ui.mvp.repository;

import com.piano.android.bean.course.CourseBean;
import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.http.ResultTransforme;
import com.piano.android.http.ScheduleTransformer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author: 陈国权
 * @date: 2018/4/30 上午1:36
 * @describe: 收藏
 */

public class FavoriteModel extends BaseModel {

    @Inject
    public FavoriteModel() {
    }

    public Observable<List<AlbumBean>> getFavoriteAlbumList() {
        return apiService.getFavoriteAlbumList()
                .compose(new ResultTransforme<List<AlbumBean>>())
                .compose(new ScheduleTransformer<List<AlbumBean>>());
    }

    public Observable<String> addFavariteAlbum(long songsetId) {
        return apiService.addFavoriteAlbum(songsetId)
                .compose(new ResultTransforme<String>())
                .compose(new ScheduleTransformer<String>());
    }

    public Observable<String> cancleFavariteAlbum(long songsetId) {
        return apiService.cancelFavoriteAlbum(songsetId)
                .compose(new ResultTransforme<String>())
                .compose(new ScheduleTransformer<String>());
    }


    public Observable<List<SingleBean>> getFavoriteSingleList() {
        return apiService.getFavoriteSingleList()
                .compose(new ResultTransforme<List<SingleBean>>())
                .compose(new ScheduleTransformer<List<SingleBean>>());
    }

    public Observable<String> addFavoriteSingle(long songId) {
        return apiService.addFavoriteSingle(songId)
                .compose(new ResultTransforme<String>())
                .compose(new ScheduleTransformer<String>());
    }

    public Observable<String> cancelFavoriteSingle(long songId) {
        return apiService.cancelFavoriteSingle(songId)
                .compose(new ResultTransforme<String>())
                .compose(new ScheduleTransformer<String>());
    }


    public Observable<List<CourseBean>> getFavoriteCourseList() {
        return apiService.getFavoriteCourseList()
                .compose(new ResultTransforme<List<CourseBean>>())
                .compose(new ScheduleTransformer<List<CourseBean>>());
    }

    public Observable<String> addFavoriteCourse(long teachId) {
        return apiService.addFavoriteCourse(teachId)
                .compose(new ResultTransforme<String>())
                .compose(new ScheduleTransformer<String>());
    }

    public Observable<String> cancelFavoriteCourse(long teachId) {
        return apiService.cancelFavoriteCourse(teachId)
                .compose(new ResultTransforme<String>())
                .compose(new ScheduleTransformer<String>());
    }
}
