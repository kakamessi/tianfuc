package com.piano.android.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.piano.android.R;
import com.piano.android.bean.course.CourseBean;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/4/25 上午12:08
 * @describe:
 */

public class MineCourseAdapter extends BaseQuickAdapter<CourseBean, BaseViewHolder> {
    public MineCourseAdapter(int layoutResId, @Nullable List<CourseBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseBean item) {
        helper.setText(R.id.tv_title, item.getName())
                .setText(R.id.tv_content, item.getIntroduce());
    }
}
