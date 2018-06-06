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
import com.piano.android.R;
import com.piano.android.base.BasePresenterActivity;
import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.common.Constant;
import com.piano.android.di.component.ActivityComponent;
import com.piano.android.ui.adapter.SingleAdapter;
import com.piano.android.ui.mvp.constract.SingleConstract;
import com.piano.android.ui.mvp.presenter.SinglePresenter;
import com.piano.android.widget.LoadingLayout;

import java.util.List;

import butterknife.BindView;

/**
 * @author: 陈国权
 * @date: 2018/4/29 下午4:42
 * @describe: 推荐单曲-最新单曲
 */

public class SingleActivity extends BasePresenterActivity<SinglePresenter> implements SingleConstract.View {

    public static final int SINGLE_RECOMMEND = 1;
    public static final int SINGLE_LATEST = 2;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;

    private int category;
    private SingleAdapter mAdapter;
    private int page = 1;
    private int limit = 10;

    @Override
    public int getContentViewId() {
        return R.layout.activity_album;
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        category = getIntent().getExtras().getInt(Constant.INTENT_CATEGORY);
    }

    @Override
    public void injectActivityComponent(ActivityComponent component) {
        super.injectActivityComponent(component);
        component.inject(this);
    }

    @Override
    public void initActivity() {
        super.initActivity();
        setToolbar(toolbar, toolbarTitle, category == 1 ? "推荐单曲" : "最新单曲", true, true);
        configRecyclerView();

        if (category == SINGLE_RECOMMEND) {
            presenter.getAllRecommendSingle(page, limit);
        } else {
            presenter.getAllLatestSingle(page, limit);
        }
    }

    private void configRecyclerView() {
        mAdapter = new SingleAdapter(R.layout.item_single, null);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mAdapter);


        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                if (category == SINGLE_RECOMMEND) {
                    presenter.getAllRecommendSingle(page, limit);
                } else {
                    presenter.getAllLatestSingle(page, limit);
                }
            }
        }, mRecycleView);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SingleBean singleBean = (SingleBean) adapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putLong(Constant.INTENT_ID, singleBean.getId());
                startActivity(SingleActivity.this, SingleDetailActivity.class, bundle);
            }
        });

        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                if (category == SINGLE_RECOMMEND) {
                    presenter.getAllRecommendSingle(page, limit);
                } else {
                    presenter.getAllLatestSingle(page, limit);
                }
            }
        });
    }

    /**
     * 结束加载
     */
    private void finishLoad() {
        if (page == 1) {
            mSwipeLayout.setRefreshing(false);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void showError(String msg) {
        finishLoad();
        if (mAdapter.getData().size() == 0 && page == 1) {
            mLoadingLayout.showError();
        } else {
            ToastUtils.showShortToast(this, msg);
        }
    }

    @Override
    public void setAllSingle(List<SingleBean> list) {
        finishLoad();
        mLoadingLayout.showMain();
        if (list == null || list.size() == 0) {
            mAdapter.loadMoreEnd(false);
            if (page == 1) {
                mLoadingLayout.showEmpty();
            }
            return;
        } else {
            mAdapter.loadMoreEnd(true);
            if (page == 1) {
                mAdapter.setNewData(list);
            } else {
                mAdapter.addData(list);
            }
        }
    }
}
