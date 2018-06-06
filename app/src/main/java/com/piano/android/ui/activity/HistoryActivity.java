package com.piano.android.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chico.common.ToastUtils;
import com.piano.android.R;
import com.piano.android.base.BasePresenterActivity;
import com.piano.android.bean.songbook.HistoryBean;
import com.piano.android.common.Constant;
import com.piano.android.di.component.ActivityComponent;
import com.piano.android.ui.adapter.HistoryAdapter;
import com.piano.android.ui.mvp.constract.HistoryConstract;
import com.piano.android.ui.mvp.presenter.HistoryPresenter;
import com.piano.android.widget.LoadingLayout;

import java.util.List;

import butterknife.BindView;

/**
 * @author: 陈国权
 * @date: 2018/4/25 下午11:22
 * @describe:
 */

public class HistoryActivity extends BasePresenterActivity<HistoryPresenter> implements HistoryConstract.View {
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

    private HistoryAdapter mAdapter;
    private int type;

    @Override
    public int getContentViewId() {
        return R.layout.activity_history;
    }

    @Override
    public void injectActivityComponent(ActivityComponent component) {
        super.injectActivityComponent(component);
        component.inject(this);
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        type = getIntent().getExtras().getInt(Constant.INTENT_TYPE);
    }

    @Override
    public void initActivity() {
        super.initActivity();
        setToolbar(toolbar, toolbarTitle, "浏览历史", true, true);
        configRecyclerView();
        presenter.getHistoryList(type);
    }

    private void configRecyclerView() {
        mAdapter = new HistoryAdapter(R.layout.item_single, null);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HistoryBean bean = (HistoryBean) adapter.getData().get(position);
                if(bean!=null&&bean.getId()!=0){
                    Bundle bundle = new Bundle();
                    bundle.putLong(Constant.INTENT_ID, bean.getId());
                    startActivity(HistoryActivity.this, SingleDetailActivity.class, bundle);
                }
            }
        });


        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getHistoryList(type);
            }
        });
    }

    @Override
    public void showError(String msg) {
        mSwipeLayout.setRefreshing(false);
        if (mAdapter == null || mAdapter.getData().size() == 0) {
            mLoadingLayout.showError();
        }else{
            ToastUtils.showShortToast(this, msg);
        }
    }

    @Override
    public void setHistoryList(List<HistoryBean> list) {
        mSwipeLayout.setRefreshing(false);
        if (list != null && list.size() > 0) {
            mAdapter.setNewData(list);
            mLoadingLayout.showMain();
        } else {
            mLoadingLayout.showEmpty();
        }
    }
}
