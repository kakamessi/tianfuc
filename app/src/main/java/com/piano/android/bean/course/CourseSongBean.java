package com.piano.android.bean.course;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午8:28
 * @describe: 教程详情-曲谱
 */

public class CourseSongBean {
    /**
     * id : 5
     * name : 站在最高点
     * author : 王毅
     * uploadUser : 王毅
     * releaseTime : 2018-02-26 12:38:31
     * hard : 3
     * hardName : 中级
     * type : 1
     * typeName : 流行
     * opern : 1519619909089.xml
     * opernName : artifacts.xml
     * createTime : 2018-02-26 12:38:31
     * updateTime : null
     * sort : null
     */

    private int id;
    private String name;
    private String author;
    private String uploadUser;
    private String releaseTime;
    private int hard;
    private String hardName;
    private int type;
    private String typeName;
    private String opern;
    private String opernName;
    private String createTime;
    private Object updateTime;
    private Object sort;

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

    public int getHard() {
        return hard;
    }

    public void setHard(int hard) {
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

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }
}
