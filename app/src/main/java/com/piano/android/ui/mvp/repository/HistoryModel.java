package com.piano.android.ui.mvp.repository;

import com.piano.android.bean.songbook.HistoryBean;
import com.piano.android.http.ResultTransforme;
import com.piano.android.http.ScheduleTransformer;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;

/**
 * @author: 陈国权
 * @date: 2018/5/5 下午10:35
 * @describe: 历史
 */

public class HistoryModel extends BaseModel {


    @Inject
    public HistoryModel() {
    }

    public Observable<List<HistoryBean>> getHistoryList(int type) {
        return apiService.getHistoryList(type)
                .compose(new ResultTransforme<List<HistoryBean>>())
                .compose(new ScheduleTransformer<List<HistoryBean>>());
    }


    public Observable<String> addHistory(HashMap map) {
        return apiService.addHistory(map)
                .compose(new ResultTransforme<String>())
                .compose(new ScheduleTransformer<String>());
    }
}
