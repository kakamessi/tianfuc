package com.piano.android.bean.songbook;

/**
 * @author: 陈国权
 * @date: 2018/4/27 下午11:24
 * @describe:
 */

public class AdvertBean {


    /**
     * id : 1
     * name : 弹窗广告
     * img : 1523257952136.jpg
     * sort : 3
     * status : 1
     * releaseTime : 2018-04-09 15:12:43
     * url : www.tinefu.com
     * createTime : 2018-02-28 15:43:13
     * updateTime : 2018-04-09 15:12:43
     */

    private int id;
    private String name;
    private String img;
    private int sort;
    private int status;
    private String releaseTime;
    private String url;
    private String createTime;
    private String updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
