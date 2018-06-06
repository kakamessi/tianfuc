package com.piano.android.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chico.common.DensityUtils;
import com.chico.common.ToastUtils;
import com.piano.android.R;
import com.piano.android.base.BasePresenterActivity;
import com.piano.android.bean.MultipleItem;
import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.common.Constant;
import com.piano.android.di.component.ActivityComponent;
import com.piano.android.ui.adapter.AlbumAdapter;
import com.piano.android.ui.mvp.constract.AlbumConstract;
import com.piano.android.ui.mvp.presenter.AlbumPresenter;
import com.piano.android.widget.GridItemDecoration;
import com.piano.android.widget.LoadingLayout;

import java.util.List;

import butterknife.BindView;

/**
 * @author: 推荐列表
 * @date: 2018/4/25 下午10:30
 * @describe: 推荐专辑-最新专辑
 */

public class AlbumActivity extends BasePresenterActivity<AlbumPresenter> implements AlbumConstract.View {

    public static final int ALBUM_RECOMMEND = 1;
    public static final int ALBUM_LATEST = 2;

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
    private AlbumAdapter albumAdapter;
    private int page = 1;
    private int limit = 12;

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
        setToolbar(toolbar, toolbarTitle, category == ALBUM_RECOMMEND ? "推荐专辑" : "最新专辑", true, true);
        configRecyclerView();

        if (category == ALBUM_RECOMMEND) {
            presenter.getAllRecommendAlbum(page, limit);
        } else {
            presenter.getAllLatestAlbum(page, limit);
        }

    }

    private void configRecyclerView() {
        albumAdapter = new AlbumAdapter(R.layout.item_album, null);
        mRecycleView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecycleView.setAdapter(albumAdapter);
        mRecycleView.addItemDecoration(new GridItemDecoration(3, DensityUtils.dp2px(this, 5), true, 0));
        albumAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                if (category == ALBUM_RECOMMEND) {
                    presenter.getAllRecommendAlbum(page, limit);
                } else {
                    presenter.getAllLatestAlbum(page, limit);
                }
            }
        }, mRecycleView);

        albumAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AlbumBean bean = (AlbumBean) adapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putLong(Constant.INTENT_ID, bean.getId());
                startActivity(AlbumActivity.this, AlbumDetailActivity.class, bundle);
            }
        });


        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                if (category == ALBUM_RECOMMEND) {
                    presenter.getAllRecommendAlbum(page, limit);
                } else {
                    presenter.getAllLatestAlbum(page, limit);
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
            albumAdapter.loadMoreComplete();
        }
    }

    @Override
    public void showError(String msg) {
        finishLoad();
        if (page == 1 && albumAdapter.getData().size() == 0) {
            mLoadingLayout.showError();
        }else{
            ToastUtils.showShortToast(this, msg);
        }
    }

    @Override
    public void setAllAlbum(List<AlbumBean> list) {
        finishLoad();
        mLoadingLayout.showMain();
        if (list == null || list.size() == 0) {
            albumAdapter.loadMoreEnd(false);
            if (page == 1) {
                mLoadingLayout.showEmpty();
            }
            return;
        } else {
            albumAdapter.loadMoreEnd(true);
            if (page == 1) {
                albumAdapter.setNewData(list);
            } else {
                albumAdapter.addData(list);
            }
        }

    }
}
