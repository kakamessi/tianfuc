package com.piano.android.ui.mvp.repository;

import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.bean.songbook.AlbumDetailBean;
import com.piano.android.bean.songbook.SearchResultBean;
import com.piano.android.bean.songbook.SearchWordBean;
import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.bean.songbook.SingleDetailBean;
import com.piano.android.http.PageTransforme;
import com.piano.android.http.ResultTransforme;
import com.piano.android.http.ScheduleTransformer;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author: 陈国权
 * @date: 2018/4/27 下午11:48
 * @describe: 曲库
 */

public class SongbookModel extends BaseModel {

    @Inject
    public SongbookModel() {
    }

    public Observable<List<AlbumBean>> getSongbookRecommendAlbum() {
        return apiService.getSongbookRecommendAlbum()
                .compose(new ResultTransforme<List<AlbumBean>>())
                .compose(new ScheduleTransformer<List<AlbumBean>>());
    }


    public Observable<List<SingleBean>> getSongbookRecommendSingle() {
        return apiService.getSongbookRecommendSingle()
                .compose(new ResultTransforme<List<SingleBean>>())
                .compose(new ScheduleTransformer<List<SingleBean>>());
    }

    public Observable<List<AlbumBean>> getAllRecommendAlbum(int page, int limit) {
        return apiService.getAllRecommendAlbum(page, limit)
                .compose(new PageTransforme<List<AlbumBean>>())
                .compose(new ScheduleTransformer<List<AlbumBean>>());
    }

    public Observable<List<SingleBean>> getAllRecommendSingle(int page, int limit) {
        return apiService.getAllRecommendSingle(page, limit)
                .compose(new PageTransforme<List<SingleBean>>())
                .compose(new ScheduleTransformer<List<SingleBean>>());
    }


    public Observable<List<AlbumBean>> getSongbookLatestAlbum() {
        return apiService.getSongbookLatestAlbum()
                .compose(new ResultTransforme<List<AlbumBean>>())
                .compose(new ScheduleTransformer<List<AlbumBean>>());
    }


    public Observable<List<SingleBean>> getSongbookLatestSingle() {
        return apiService.getSongbookLatestSingle()
                .compose(new ResultTransforme<List<SingleBean>>())
                .compose(new ScheduleTransformer<List<SingleBean>>());
    }

    public Observable<List<AlbumBean>> getAllLatestAlbum(int page, int limit) {
        return apiService.getAllLatestAlbum(page, limit)
                .compose(new PageTransforme<List<AlbumBean>>())
                .compose(new ScheduleTransformer<List<AlbumBean>>());
    }

    public Observable<List<SingleBean>> getAllLatestSingle(int page, int limit) {
        return apiService.getAllLatestSingle(page, limit)
                .compose(new PageTransforme<List<SingleBean>>())
                .compose(new ScheduleTransformer<List<SingleBean>>());
    }

    public Observable<AlbumDetailBean> getAlbumDetail(long id) {
        return apiService.getAlbumDetail(id)
                .compose(new ResultTransforme<AlbumDetailBean>())
                .compose(new ScheduleTransformer<AlbumDetailBean>());
    }

    public Observable<SingleDetailBean> getSingleDetail(long id) {
        return apiService.getSingleDetail(id)
                .compose(new ResultTransforme<SingleDetailBean>())
                .compose(new ScheduleTransformer<SingleDetailBean>());
    }

    public Observable<List<AlbumBean>> getFilterAlbum(HashMap map) {
        return apiService.getFilterAlbum(map)
                .compose(new PageTransforme<List<AlbumBean>>())
                .compose(new ScheduleTransformer<List<AlbumBean>>());
    }

    public Observable<SearchResultBean> getSearchResult(String keyword) {
        return apiService.getSearchResult(keyword)
                .compose(new ResultTransforme<SearchResultBean>())
                .compose(new ScheduleTransformer<SearchResultBean>());
    }

    public Observable<List<SearchWordBean>> getSearchHotWord() {
        return apiService.getSearchHotWord()
                .compose(new ResultTransforme<List<SearchWordBean>>())
                .compose(new ScheduleTransformer<List<SearchWordBean>>());
    }
}
