package com.piano.android.ui.mvp.repository;

import com.piano.android.bean.songbook.BannerBean;
import com.piano.android.http.ResultTransforme;
import com.piano.android.http.ScheduleTransformer;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;

/**
 * @author: 陈国权
 * @date: 2018/4/27 下午11:26
 * @describe: 轮播
 */

public class BannerModel extends BaseModel {

    @Inject
    public BannerModel() {
    }

    public Observable<List<BannerBean>> getBannerList() {
        return apiService.getBannerList()
                .compose(new ResultTransforme<List<BannerBean>>())
                .compose(new ScheduleTransformer<List<BannerBean>>());
    }
}
