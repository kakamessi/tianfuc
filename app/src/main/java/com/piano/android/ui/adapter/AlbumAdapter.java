package com.piano.android.ui.adapter;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chico.common.DensityUtils;
import com.chico.common.ScreenUtils;
import com.piano.android.BuildConfig;
import com.piano.android.R;
import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.common.ImageLoad;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/4/25 下午10:43
 * @describe: 专辑
 */

public class AlbumAdapter extends BaseQuickAdapter<AlbumBean, BaseViewHolder> {
    public AlbumAdapter(int layoutResId, @Nullable List<AlbumBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlbumBean item) {
        int width = ScreenUtils.getScreenWidth(mContext) - DensityUtils.dp2px(mContext, 20);
        ImageView cover = helper.getView(R.id.iv_cover);
        cover.setLayoutParams(new ConstraintLayout.LayoutParams(width / 3, width / 3));
        ImageLoad.loadImageRounder(mContext, BuildConfig.BASE_IMAGE_URL + item.getImg(), cover);

        helper.setText(R.id.tv_title, item.getName())
                .setText(R.id.tv_content, item.getSubhead())
                .setText(R.id.tv_type, mContext.getResources().getStringArray(R.array.sort_difficult)[item.getHard()]);

        if (item.getHard() <= 2) {
            helper.setBackgroundRes(R.id.tv_type, R.drawable.icon_level_junior);
        } else if (item.getHard() == 3) {
            helper.setBackgroundRes(R.id.tv_type, R.drawable.icon_level_advanced);
        } else {
            helper.setBackgroundRes(R.id.tv_type, R.drawable.icon_level_challenge);
        }
    }
}
