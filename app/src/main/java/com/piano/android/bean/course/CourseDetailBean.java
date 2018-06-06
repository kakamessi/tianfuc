package com.piano.android.bean.course;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午5:57
 * @describe: 教程详情
 */

public class CourseDetailBean {


    /**
     * vedioList : [{"id":79,"teachId":7,"name":"车尔尼","url":"45","createTime":"2018-04-28 14:17:18","introduce":"null"}]
     * detail : {"id":7,"name":"车尔尼","subhead":"车尔尼","introduce":"简介","img":"1523256658477.jpg","type":0,"uploadUser":"佚名","createTime":"2018-02-28 18:00:43","updateTime":"2018-04-28 14:17:18","typeName":"基础"}
     * songList : [{"id":5,"name":"站在最高点","author":"王毅","uploadUser":"王毅","releaseTime":"2018-02-26 12:38:31","hard":3,"hardName":"中级","type":1,"typeName":"流行","opern":"1519619909089.xml","opernName":"artifacts.xml","createTime":"2018-02-26 12:38:31","updateTime":null,"sort":null},{"id":6,"name":"夜的钢琴曲","author":"王毅","uploadUser":"王毅","releaseTime":"2018-02-26 12:42:02","hard":2,"hardName":"初级","type":0,"typeName":"考级","opern":"1519620119672.xml","opernName":"artifacts.xml","createTime":"2018-02-26 12:42:02","updateTime":"2018-04-28 13:54:35","sort":null}]
     * collect : false
     */

    private CourseDetailInfoBean detail;
    private boolean collect;
    private List<CourseVideoBean> vedioList;
    private List<CourseSongBean> songList;

    public CourseDetailInfoBean getDetail() {
        return detail;
    }

    public void setDetail(CourseDetailInfoBean detail) {
        this.detail = detail;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public List<CourseVideoBean> getVedioList() {
        return vedioList;
    }

    public void setVedioList(List<CourseVideoBean> vedioList) {
        this.vedioList = vedioList;
    }

    public List<CourseSongBean> getSongList() {
        return songList;
    }

    public void setSongList(List<CourseSongBean> songList) {
        this.songList = songList;
    }
}
