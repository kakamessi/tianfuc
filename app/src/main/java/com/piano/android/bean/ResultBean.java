package com.piano.android.bean;

/**
 * @author: 陈国权
 * @date: 2018/4/21 下午9:36
 * @describe: 统一返回实体
 */

public class ResultBean<T> {
    public int code;
    public String msg;
    public T result;
}
