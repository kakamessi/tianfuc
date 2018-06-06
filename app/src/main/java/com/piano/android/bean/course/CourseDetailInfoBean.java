package com.piano.android.bean.course;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午8:29
 * @describe: 教程详情-详情
 */

public class CourseDetailInfoBean {

    /**
     * id : 7
     * name : 车尔尼
     * subhead : 车尔尼
     * introduce : 简介
     * img : 1523256658477.jpg
     * type : 0
     * uploadUser : 佚名
     * createTime : 2018-02-28 18:00:43
     * updateTime : 2018-04-28 14:17:18
     * typeName : 基础
     */

    private int id;
    private String name;
    private String subhead;
    private String introduce;
    private String img;
    private int type;
    private String uploadUser;
    private String createTime;
    private String updateTime;
    private String typeName;

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

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
