package com.piano.android.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.piano.android.R;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/5/5 下午2:49
 * @describe:
 */

public class FilterAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int index;

    public void setCheckIndex(int index) {
        this.index = index;
        notifyDataSetChanged();
    }

    public FilterAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item);

        if (index == helper.getPosition()) {
            helper.setVisible(R.id.iv_checked, true);
            helper.setTextColor(R.id.tv_name, ContextCompat.getColor(mContext, R.color.color_theme));
        } else {
            helper.setVisible(R.id.iv_checked, false);
            helper.setTextColor(R.id.tv_name, ContextCompat.getColor(mContext, R.color.color_title));
        }


    }


}
