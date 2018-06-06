package com.piano.android.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.piano.android.R;
import com.piano.android.bean.songbook.SingleBean;
import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午10:25
 * @describe:
 */

public class AlbumDetailAdapter extends BaseQuickAdapter<SingleBean, BaseViewHolder> {
    public AlbumDetailAdapter(int layoutResId, @Nullable List<SingleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SingleBean item) {
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_type, item.getHardName())
                .setText(R.id.tv_author, item.getAuthor())
                .setText(R.id.tv_uploader, String.format(mContext.getString(R.string.uploader), item.getUploadUser()));
    }
}
