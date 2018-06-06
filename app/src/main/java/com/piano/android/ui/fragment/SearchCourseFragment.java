package com.piano.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chico.common.DensityUtils;
import com.piano.android.R;
import com.piano.android.base.BaseFragment;
import com.piano.android.bean.course.CourseBean;
import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.common.Constant;
import com.piano.android.ui.activity.AlbumDetailActivity;
import com.piano.android.ui.activity.CourseDetailActivity;
import com.piano.android.ui.adapter.AlbumAdapter;
import com.piano.android.ui.adapter.CourseAdapter;
import com.piano.android.widget.GridItemDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * @author: 陈国权
 * @date: 2018/5/5 下午5:24
 * @describe:
 */

public class SearchCourseFragment extends BaseFragment {

    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;

    private CourseAdapter mAdapter;

    /**
     * 更新数据
     *
     * @param list
     */
    public void updateData(List<CourseBean> list) {
        if (mAdapter == null) {
            mAdapter = new CourseAdapter(R.layout.item_course, null);
            mRecycleView.setAdapter(mAdapter);
            mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    CourseBean bean = (CourseBean) adapter.getData().get(position);
                    Bundle bundle = new Bundle();
                    bundle.putLong(Constant.INTENT_ID, bean.getId());
                    bundle.putString(Constant.INTENT_TITLE, bean.getName());
                    startActivity(getActivity(), CourseDetailActivity.class, bundle);
                }
            });
        }

        mAdapter.setNewData(list);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_search_result;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setUserVisibleHint(true);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initFragment() {
    }

}
