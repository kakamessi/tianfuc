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
import com.piano.android.bean.course.CourseBean;
import com.piano.android.common.ImageLoad;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/4/22 下午2:30
 * @describe:
 */

public class CourseAdapter extends BaseQuickAdapter<CourseBean, BaseViewHolder> {

    public CourseAdapter(int layoutResId, @Nullable List<CourseBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseBean item) {
        if (item != null) {
            int width = ScreenUtils.getScreenWidth(mContext) - DensityUtils.dp2px(mContext, 20);
            ImageView cover = helper.getView(R.id.iv_cover);
            cover.setLayoutParams(new ConstraintLayout.LayoutParams(width, DensityUtils.dp2px(mContext, 150)));
            ImageLoad.loadImageRounderNoCrop(mContext, BuildConfig.BASE_IMAGE_URL + item.getImg(), cover);

            helper.setText(R.id.tv_course_number, item.getName())
                    .setText(R.id.tv_course_author,item.getAuthor())
                    .setText(R.id.tv_course_describe, item.getSubhead())
                    .setText(R.id.tv_uploader, String.format(mContext.getString(R.string.uploader), item.getUploadUser()));
        }
    }
}
