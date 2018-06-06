package com.piano.android.bean.songbook;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午9:39
 * @describe: 专辑详情
 */

public class AlbumDetailBean {


    /**
     * detail : {"id":2,"name":"周杰伦","introduce":"周杰伦（Jay Chou），1979年1月18日出生于台湾省新北市，中国台湾流行乐男歌手、音乐人、演员、导演、编剧、监制、商人。","uploadUser":"王毅","releaseTime":"2018-02-26 14:39:44","img":"1523254737046.jpg","hard":4,"hardName":"高级","style":1,"styleName":"流行","person":0,"personName":"成人","scene":0,"sceneName":"弹唱","teaching":1,"teachingName":"考级教材","createTime":"2018-02-26 14:39:44","updateTime":"2018-04-28 14:05:24","sort":null,"subhead":"周杰伦","author":"周杰伦"}
     * songList : [{"id":7,"name":"梦中的婚礼","author":"王毅","uploadUser":"王毅","releaseTime":"2018-02-26 12:47:18","hard":2,"hardName":"初级","type":2,"typeName":"古典","opern":"1523254225667.xml","opernName":"1 _1_.xml","createTime":"2018-02-26 12:47:18","updateTime":"2018-04-28 13:54:57","sort":null},{"id":6,"name":"夜的钢琴曲","author":"王毅","uploadUser":"王毅","releaseTime":"2018-02-26 12:42:02","hard":2,"hardName":"初级","type":0,"typeName":"考级","opern":"1519620119672.xml","opernName":"artifacts.xml","createTime":"2018-02-26 12:42:02","updateTime":"2018-04-28 13:54:35","sort":null},{"id":5,"name":"站在最高点","author":"王毅","uploadUser":"王毅","releaseTime":"2018-02-26 12:38:31","hard":3,"hardName":"中级","type":1,"typeName":"流行","opern":"1519619909089.xml","opernName":"artifacts.xml","createTime":"2018-02-26 12:38:31","updateTime":null,"sort":null},{"id":4,"name":"菊次郎的夏天","author":"王毅","uploadUser":"王毅","releaseTime":"2018-02-26 12:37:57","hard":4,"hardName":"高级","type":1,"typeName":"流行","opern":"1519619874300.xml","opernName":"artifacts.xml","createTime":"2018-02-26 12:37:57","updateTime":"2018-04-09 14:12:33","sort":null},{"id":3,"name":"广东爱情故事","author":"王毅","uploadUser":"王毅","releaseTime":"2018-02-26 12:35:12","hard":2,"hardName":"初级","type":1,"typeName":"流行","opern":"1519619709684.xml","opernName":"artifacts.xml","createTime":"2018-02-26 12:35:12","updateTime":null,"sort":null},{"id":2,"name":"时间煮雨简版","author":"王毅","uploadUser":"王毅","releaseTime":"2018-02-26 12:34:33","hard":5,"hardName":"挑战","type":1,"typeName":"流行","opern":"1519619671383.xml","opernName":"artifacts.xml","createTime":"2018-02-26 12:34:33","updateTime":"2018-04-09 14:12:46","sort":null},{"id":1,"name":"贝多芬的忧伤","author":"王毅","uploadUser":"王毅","releaseTime":"2018-02-24 12:56:31","hard":4,"hardName":"高级","type":1,"typeName":"流行","opern":"1519453463379.xml","opernName":"artifacts.xml","createTime":"2018-02-24 12:56:31","updateTime":"2018-02-24 14:24:25","sort":null}]
     * collect : false
     */

    private AlbumDetailInfoBean detail;
    private boolean collect;
    private List<SingleBean> songList;

    public AlbumDetailInfoBean getDetail() {
        return detail;
    }

    public void setDetail(AlbumDetailInfoBean detail) {
        this.detail = detail;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public List<SingleBean> getSongList() {
        return songList;
    }

    public void setSongList(List<SingleBean> songList) {
        this.songList = songList;
    }
}
