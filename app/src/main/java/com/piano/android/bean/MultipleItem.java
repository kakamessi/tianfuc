package com.piano.android.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author 陈国权
 * @date 2018/3/15 0015
 */

public class MultipleItem<T> implements MultiItemEntity {

    public static final int SPAN_SIZE_SINGE = 1;
    public static final int SPAN_SIZE_GRID = 2;

    //曲库
    public static final int SONGBOOK_BANNER = 0;
    public static final int SONGBOOK_ALBUM_TITLE = 1;
    public static final int SONGBOOK_ALBUM = 2;
    public static final int SONGBOOK_SINGLE_TITLE = 3;
    public static final int SONGBOOK_SINGLE = 4;
    public static final int SONGBOOK_DIVIDER = 5;


    private int itemType;
    private int spanSize;
    private T data;

    public MultipleItem() {
    }

    public MultipleItem(int itemType, T data) {
        this(itemType, SPAN_SIZE_GRID, data);
    }

    public MultipleItem(int itemType, int spanSize, T data) {
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
