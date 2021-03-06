package com.piano.android.bean.songbook;

/**
 * @author: 陈国权
 * @date: 2018/5/6 下午8:51
 * @describe:
 */

public class HistoryBean {


    /**
     * opern : 1519453463379.xml
     * createTime : 2018-05-06 20:46:36
     * author : 王毅
     * name : 贝多芬的忧伤
     * hard : 高级
     * type : 0
     */



    private long id;
    private String name;
    private String author;
    private String uploadUser;
    private String releaseTime;
    private String hard;
    private String hardName;
    private int type;
    private String typeName;
    private String opern;
    private String opernName;
    private String createTime;
    private String updateTime;
    private int sort;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getHard() {
        return hard;
    }

    public void setHard(String hard) {
        this.hard = hard;
    }

    public String getHardName() {
        return hardName;
    }

    public void setHardName(String hardName) {
        this.hardName = hardName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getOpern() {
        return opern;
    }

    public void setOpern(String opern) {
        this.opern = opern;
    }

    public String getOpernName() {
        return opernName;
    }

    public void setOpernName(String opernName) {
        this.opernName = opernName;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
