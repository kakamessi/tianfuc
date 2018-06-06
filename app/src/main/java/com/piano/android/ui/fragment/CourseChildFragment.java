package com.piano.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chico.common.ToastUtils;
import com.piano.android.R;
import com.piano.android.base.BaseFragment;
import com.piano.android.base.BasePresenterFragment;
import com.piano.android.bean.course.CourseBean;
import com.piano.android.common.Constant;
import com.piano.android.di.component.FragmentComponent;
import com.piano.android.ui.activity.CourseDetailActivity;
import com.piano.android.ui.adapter.CourseAdapter;
import com.piano.android.ui.mvp.constract.CourseChildConstract;
import com.piano.android.ui.mvp.presenter.CourseChildPresenter;
import com.piano.android.widget.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: 陈国权
 * @date: 2018/4/21 下午8:48
 * @describe:
 */

public class CourseChildFragment extends BasePresenterFragment<CourseChildPresenter> implements CourseChildConstract.View {

    public static final int COURSE_BASE = 1;
    public static final int COURSE_ADV = 2;
    public static final int COURSE_TEST = 3;


    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;

    private CourseAdapter mAdapter;
    private int category;

    public static Fragment newInstance(int category) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.INTENT_CATEGORY, category);
        CourseChildFragment fragment = new CourseChildFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = getArguments().getInt(Constant.INTENT_CATEGORY);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_course_child;
    }

    @Override
    public void injectFragmentComponent(FragmentComponent component) {
        super.injectFragmentComponent(component);
        component.inject(this);
    }

    @Override
    protected void initFragment() {
        super.initFragment();
        configRecyclerView();
        presenter.getCourseList(category);
    }

    private void configRecyclerView() {
        mAdapter = new CourseAdapter(R.layout.item_course, null);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getCourseList(category);
            }
        });
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

    @Override
    public void showError(String msg) {
        mSwipeLayout.setRefreshing(false);
        if (mAdapter == null || mAdapter.getData().size() == 0) {
            mLoadingLayout.showError();
        } else {
            ToastUtils.showShortToast(getActivity(), msg);
        }
    }

    @Override
    public void setCourseList(List<CourseBean> list) {
        mSwipeLayout.setRefreshing(false);
        if (list != null && list.size() > 0) {
            mAdapter.setNewData(list);
            mLoadingLayout.showMain();
        } else {
            mLoadingLayout.showEmpty();
        }
    }
}
