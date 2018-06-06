package com.piano.android.bean.songbook;

import com.piano.android.bean.course.CourseBean;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/5/5 下午5:08
 * @describe:
 */

public class SearchResultBean {


    private List<AlbumBean> songsetList;
    private List<SingleBean> songList;
    private List<CourseBean> teachList;

    public List<AlbumBean> getSongsetList() {
        return songsetList;
    }

    public void setSongsetList(List<AlbumBean> songsetList) {
        this.songsetList = songsetList;
    }

    public List<SingleBean> getSongList() {
        return songList;
    }

    public void setSongList(List<SingleBean> songList) {
        this.songList = songList;
    }

    public List<CourseBean> getTeachList() {
        return teachList;
    }

    public void setTeachList(List<CourseBean> teachList) {
        this.teachList = teachList;
    }
}
