package com.piano.android.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.piano.android.R;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/5/5 下午6:04
 * @describe:
 */

public class HistoryWordAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HistoryWordAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item)
                .addOnClickListener(R.id.tv_name)
                .addOnClickListener(R.id.iv_delete);
    }
}
