package com.piano.android.bean.songbook;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午9:41
 * @describe:
 */

public class SingleDetailBean {


    /**
     * detail : {"id":5,"name":"站在最高点","author":"王毅","uploadUser":"王毅","releaseTime":"2018-02-26 12:38:31","hard":3,"hardName":"中级","type":1,"typeName":"流行","opern":"1519619909089.xml","opernName":"artifacts.xml","createTime":"2018-02-26 12:38:31","updateTime":null,"sort":null}
     * collect : false
     */

    private List<AlbumBean> songsetList;
    private SingleDetailInfoBean detail;
    private boolean collect;

    public List<AlbumBean> getSongsetList() {
        return songsetList;
    }

    public void setSongsetList(List<AlbumBean> songsetList) {
        this.songsetList = songsetList;
    }

    public SingleDetailInfoBean getDetail() {
        return detail;
    }

    public void setDetail(SingleDetailInfoBean detail) {
        this.detail = detail;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }
}
