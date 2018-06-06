package com.piano.android.bean.course;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午8:27
 * @describe: 教程详情-视频
 */

public class CourseVideoBean {
    /**
     * id : 79
     * teachId : 7
     * name : 车尔尼
     * url : 45
     * createTime : 2018-04-28 14:17:18
     * introduce : null
     */

    private int id;
    private int teachId;
    private String name;
    private String url;
    private String createTime;
    private String introduce;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeachId() {
        return teachId;
    }

    public void setTeachId(int teachId) {
        this.teachId = teachId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
