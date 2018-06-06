package com.piano.android.ui.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.piano.android.R;
import com.piano.android.bean.songbook.HistoryBean;
import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/5/6 下午8:55
 * @describe:
 */

public class HistoryAdapter extends BaseQuickAdapter<HistoryBean, BaseViewHolder> {
    public HistoryAdapter(int layoutResId, @Nullable List<HistoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryBean item) {
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_type, item.getHard())
                .setText(R.id.tv_author, item.getAuthor())
                .setText(R.id.tv_uploader, String.format(mContext.getString(R.string.uploader), item.getUploadUser()));
    }
}
