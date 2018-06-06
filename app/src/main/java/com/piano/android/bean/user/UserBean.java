package com.piano.android.bean.user;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午6:19
 * @describe:
 */

public class UserBean {


    /**
     * userId : 17
     * img : null
     * username : 18516276648
     * mobile : 18516276648
     * password : null
     * sex : null
     * createTime : 2018-04-27 23:03:14
     */

    private int userId;
    private String img;
    private String username;
    private String mobile;
    private String password;
    private String sex;
    private String createTime;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
