package com.piano.android.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.piano.android.R;
import com.piano.android.bean.course.CourseVideoBean;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/5/3 下午8:27
 * @describe:
 */

public class CourseDetailAdapter extends BaseQuickAdapter<CourseVideoBean, BaseViewHolder> {

    public CourseDetailAdapter(int layoutResId, @Nullable List<CourseVideoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseVideoBean item) {
        helper.setText(R.id.tv_number, String.valueOf(helper.getPosition() + 1))
                .setText(R.id.tv_title, item.getName())
                .setText(R.id.tv_sub_title, item.getIntroduce());
    }
}
