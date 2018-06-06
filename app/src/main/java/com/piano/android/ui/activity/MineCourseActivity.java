package com.piano.android.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chico.common.ToastUtils;
import com.chico.common.VideoUtils;
import com.piano.android.R;
import com.piano.android.base.BasePresenterActivity;
import com.piano.android.bean.course.CourseBean;
import com.piano.android.common.Constant;
import com.piano.android.di.component.ActivityComponent;
import com.piano.android.ui.adapter.MineCourseAdapter;
import com.piano.android.ui.mvp.constract.MineCourseConstract;
import com.piano.android.ui.mvp.presenter.MineCoursePresenter;
import com.piano.android.widget.LoadingLayout;

import java.util.List;

import butterknife.BindView;

/**
 * @author: 陈国权
 * @date: 2018/4/25 上午12:04
 * @describe: 我浏览的课程
 */

public class MineCourseActivity extends BasePresenterActivity<MineCoursePresenter> implements MineCourseConstract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;

    private MineCourseAdapter mAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_mine_course;
    }

    @Override
    public void injectActivityComponent(ActivityComponent component) {
        super.injectActivityComponent(component);
        component.inject(this);
    }

    @Override
    public void initActivity() {
        super.initActivity();
        setToolbar(toolbar, toolbarTitle, "我浏览的课程", true, true);
        configRecyclerView();
        presenter.getMineCourseList();
    }

    private void configRecyclerView() {
        mAdapter = new MineCourseAdapter(R.layout.item_mine_course, null);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getMineCourseList();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String url = "http://video.ihuoqiu.com/MP4/huoqiuwang_201805011608522044.mp4?_upt=eb43dc461525789336";
//                VideoUtils.openSysVideo(MineCourseActivity.this, url);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.INTENT_URL, url);
                startActivity(MineCourseActivity.this, VideoActivity.class, bundle);
            }
        });
    }

    @Override
    public void showError(String msg) {
        mSwipeLayout.setRefreshing(false);
        if (mAdapter.getData().size() == 0) {
            loadingLayout.showError();
        } else {
            ToastUtils.showShortToast(this, msg);
        }
    }

    @Override
    public void setMineCourseList(List<CourseBean> list) {
        mSwipeLayout.setRefreshing(false);
        if (list == null || list.size() == 0) {
            loadingLayout.showEmpty();
        } else {
            loadingLayout.showMain();
            mAdapter.setNewData(list);
        }
    }
}
