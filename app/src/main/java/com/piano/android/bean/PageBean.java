package com.piano.android.bean;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午5:35
 * @describe:
 */

public class PageBean<T> {

    public int totalCount;
    public int pageSize;
    public int totalPage;
    public int currPage;
    public T list;
}
