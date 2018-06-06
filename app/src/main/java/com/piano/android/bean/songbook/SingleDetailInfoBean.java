package com.piano.android.bean.songbook;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午11:10
 * @describe: 曲谱详情-详情
 */

public class SingleDetailInfoBean {


    /**
     * id : 3
     * name : 广东爱情故事
     * author : 王毅
     * uploadUser : 王毅
     * releaseTime : 2018-02-26 12:35:12
     * hard : 2
     * hardName : 初级
     * type : 1
     * typeName : 流行
     * opern : 1519619709684.xml
     * opernName : artifacts.xml
     * createTime : 2018-02-26 12:35:12
     * updateTime : 2018-05-07 10:20:56
     * sort : null
     * img : 1525659652134.jpg
     * introduce : 广东爱情故事
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
    private String updateTime;
    private Object sort;
    private String img;
    private String introduce;

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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
