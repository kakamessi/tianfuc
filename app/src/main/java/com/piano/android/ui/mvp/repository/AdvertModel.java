package com.piano.android.ui.mvp.repository;

import com.piano.android.bean.songbook.AdvertBean;
import com.piano.android.bean.songbook.BannerBean;
import com.piano.android.http.ResultTransforme;
import com.piano.android.http.ScheduleTransformer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author: 陈国权
 * @date: 2018/4/27 下午11:29
 * @describe: 广告
 */

public class AdvertModel extends BaseModel{

    @Inject
    public AdvertModel() {
    }

    public Observable<List<AdvertBean>> getAdvertList(){
        return apiService.getAdvertList()
                .compose(new ResultTransforme<List<AdvertBean>>())
                .compose(new ScheduleTransformer<List<AdvertBean>>());
    }
}
