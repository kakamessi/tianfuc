package com.piano.android.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.piano.android.bean.songbook.SearchWordBean;
import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/5/5 下午6:02
 * @describe:
 */

public class HotWordAdapter extends BaseQuickAdapter<SearchWordBean,BaseViewHolder>{

    public HotWordAdapter(int layoutResId, @Nullable List<SearchWordBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchWordBean item) {

    }
}
