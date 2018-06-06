package com.piano.android.bean.songbook;

/**
 * @author: 陈国权
 * @date: 2018/5/5 下午5:18
 * @describe:
 */

public class SearchWordBean {


    /**
     * id : 17
     * name : 周杰伦
     * sort : null
     * createTime : 2018-04-29 17:28:25
     */

    private int id;
    private String name;
    private Object sort;
    private String createTime;

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

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
