package com.piano.android.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chico.common.DensityUtils;
import com.chico.common.ToastUtils;
import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.piano.android.R;
import com.piano.android.base.BasePresenterFragment;
import com.piano.android.bean.MultipleItem;
import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.bean.songbook.BannerBean;
import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.common.Constant;
import com.piano.android.di.component.FragmentComponent;
import com.piano.android.ui.activity.AlbumActivity;
import com.piano.android.ui.activity.AlbumDetailActivity;
import com.piano.android.ui.activity.SingleActivity;
import com.piano.android.ui.activity.SingleDetailActivity;
import com.piano.android.ui.adapter.SongbookRecommendAdapter;
import com.piano.android.ui.mvp.constract.SongBookRecommendConstract;
import com.piano.android.ui.mvp.presenter.SongbookRecommendPresenter;
import com.piano.android.widget.LoadingLayout;
import com.piano.android.widget.banner.SongbookRecommendBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;


/**
 * @author: 陈国权
 * @date: 2018/4/21 下午8:41
 * @describe:
 */

public class SongbookRecommendFragment extends BasePresenterFragment<SongbookRecommendPresenter> implements SongBookRecommendConstract.View {


    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;

    private SongbookRecommendAdapter mAdapter;
    private List<MultipleItem> mDates = new ArrayList<>();
    private List<BannerBean> bannerBeanList;
    private List<AlbumBean> albumBeanList;
    private List<SingleBean> singleBeanList;

    public static Fragment newInstance() {
        SongbookRecommendFragment fragment = new SongbookRecommendFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_songbook_recommend;
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
        presenter.getBannerList();
        presenter.getSongbookRecommendAlbum();
        presenter.getSongbookRecommendSingle();
    }

    /**
     * 配置RecyclerView
     */
    private void configRecyclerView() {
        mRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter = new SongbookRecommendAdapter(mDates);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                MultipleItem item = mAdapter.getData().get(position);
                if (item.getItemType() == MultipleItem.SONGBOOK_ALBUM) {
                    return 1;
                } else {
                    return 3;
                }
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MultipleItem item = (MultipleItem) adapter.getData().get(position);
                Bundle bundle;
                switch (item.getItemType()) {
                    case MultipleItem.SONGBOOK_ALBUM_TITLE:
                        bundle = new Bundle();
                        bundle.putInt(Constant.INTENT_CATEGORY, AlbumActivity.ALBUM_RECOMMEND);
                        startActivity(getActivity(), AlbumActivity.class, bundle);
                        break;
                    case MultipleItem.SONGBOOK_ALBUM:
                        AlbumBean albumBean = (AlbumBean) item.getData();
                        bundle = new Bundle();
                        bundle.putLong(Constant.INTENT_ID, albumBean.getId());
                        startActivity(getActivity(), AlbumDetailActivity.class, bundle);
                        break;
                    case MultipleItem.SONGBOOK_SINGLE_TITLE:
                        bundle = new Bundle();
                        bundle.putInt(Constant.INTENT_CATEGORY, SingleActivity.SINGLE_RECOMMEND);
                        startActivity(getActivity(), SingleActivity.class, bundle);
                        break;
                    case MultipleItem.SONGBOOK_SINGLE:
                        SingleBean singleBean = (SingleBean) item.getData();
                        bundle = new Bundle();
                        bundle.putLong(Constant.INTENT_ID, singleBean.getId());
                        startActivity(getActivity(), SingleDetailActivity.class, bundle);
                        break;
                    default:
                        break;
                }
            }
        });

        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getBannerList();
                presenter.getSongbookRecommendAlbum();
                presenter.getSongbookRecommendSingle();
            }
        });


    }

    @Override
    public void showError(String msg) {
        mSwipeLayout.setRefreshing(false);
        if (mAdapter.getData().size() == 0) {
            mLoadingLayout.showError();
        } else {
            ToastUtils.showShortToast(getActivity(), msg);
        }
    }

    @Override
    public void setBannerList(List<BannerBean> list) {
        this.bannerBeanList = list;
        showPageDate();
    }

    @Override
    public void setSongbookRecommendAlbum(List<AlbumBean> list) {
        this.albumBeanList = list;
        showPageDate();
    }

    @Override
    public void setSongbookRecommendSingle(List<SingleBean> list) {
        this.singleBeanList = list;
        showPageDate();
    }


    private void showPageDate() {
        mSwipeLayout.setRefreshing(false);

        mDates.clear();
        if (bannerBeanList != null && bannerBeanList.size() > 0) {
            mDates.add(new MultipleItem(MultipleItem.SONGBOOK_BANNER, bannerBeanList));
        }

        if (albumBeanList != null && albumBeanList.size() > 0) {
            mDates.add(new MultipleItem(MultipleItem.SONGBOOK_ALBUM_TITLE, "推荐专辑"));
            for (AlbumBean albumBean : albumBeanList) {
                mDates.add(new MultipleItem(MultipleItem.SONGBOOK_ALBUM, albumBean));
            }
        }

        mDates.add(new MultipleItem(MultipleItem.SONGBOOK_DIVIDER,null));

        if (singleBeanList != null && singleBeanList.size() > 0) {
            mDates.add(new MultipleItem(MultipleItem.SONGBOOK_SINGLE_TITLE, "推荐单曲"));
            for (SingleBean singleBean : singleBeanList) {
                mDates.add(new MultipleItem(MultipleItem.SONGBOOK_SINGLE, singleBean));
            }
        }

        mAdapter.setNewData(mDates);
    }
}
