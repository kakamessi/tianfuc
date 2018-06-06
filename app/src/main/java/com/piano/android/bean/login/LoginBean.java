package com.piano.android.bean.login;

/**
 * @author: 陈国权
 * @date: 2018/4/27 下午10:16
 * @describe: 登陆实体
 */

public class LoginBean {

    /**
     * expire : 604800
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNyIsImlhdCI6MTUyNDg0MTQ2MCwiZXhwIjoxNTI1NDQ2MjYwfQ.oQ6QBVATFm-bSD21uFixa6sKByOEd1Ty57XcDb-NuhM51rlM5XLmcXQnSYAgwAa8CjWSeYNEfS657v2OQq8hhQ
     */

    private int expire;
    private String token;

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
