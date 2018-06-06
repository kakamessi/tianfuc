package com.piano.android.bean.songbook;

import java.io.Serializable;

/**
 * @author: 陈国权
 * @date: 2018/4/25 下午10:44
 * @describe: 专辑
 */

public class AlbumBean implements Serializable{

    /**
     * id : 2
     * name : 周杰伦
     * introduce : null
     * uploadUser : 王毅
     * releaseTime : 2018-02-26 14:39:44
     * img : 1523254737046.jpg
     * hard : 4
     * hardName : 高级
     * style : null
     * styleName :
     * person : null
     * personName :
     * scene : null
     * sceneName :
     * teaching : null
     * teachingName :
     * createTime : null
     * updateTime : null
     * sort : 1
     */

    private long id;
    private String name;
    private String introduce;
    private String uploadUser;
    private String releaseTime;
    private String img;
    private int hard;
    private String hardName;
    private String style;
    private String styleName;
    private String person;
    private String personName;
    private String scene;
    private String sceneName;
    private String teaching;
    private String teachingName;
    private String createTime;
    private String updateTime;
    private int sort;
    private String subhead;
    private String author;

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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public String getTeaching() {
        return teaching;
    }

    public void setTeaching(String teaching) {
        this.teaching = teaching;
    }

    public String getTeachingName() {
        return teachingName;
    }

    public void setTeachingName(String teachingName) {
        this.teachingName = teachingName;
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

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
