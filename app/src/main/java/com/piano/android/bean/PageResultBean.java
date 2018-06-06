package com.piano.android.bean;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午5:18
 * @describe: 分页返回结构
 */

public class PageResultBean<T> {
    public String msg;
    public int code;
    public PageBean<T> page;
}
