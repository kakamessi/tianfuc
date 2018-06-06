package com.piano.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chico.common.ToastUtils;
import com.chico.dialog.ConfirmDialog;
import com.chico.dialog.DialogListener;
import com.piano.android.R;
import com.piano.android.base.BasePresenterFragment;
import com.piano.android.bean.event.UpdateEvent;
import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.common.Constant;
import com.piano.android.di.component.FragmentComponent;
import com.piano.android.ui.activity.SingleDetailActivity;
import com.piano.android.ui.adapter.SingleAdapter;
import com.piano.android.ui.mvp.constract.MineFavoriteSingleConstract;
import com.piano.android.ui.mvp.presenter.MineFavoriteSinglePresenter;
import com.piano.android.widget.LoadingLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: 陈国权
 * @date: 2018/4/24 上午12:05
 * @describe: 我的收藏曲谱
 */

public class MineFavoriteSingleFragment extends BasePresenterFragment<MineFavoriteSinglePresenter> implements MineFavoriteSingleConstract.View {

    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.ll_edit)
    LinearLayout llEdit;


    private SingleAdapter mAdapter;
    private int currentSelected;

    public static Fragment newInstance() {
        MineFavoriteSingleFragment fragment = new MineFavoriteSingleFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mine_favorite;
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
        presenter.getMineFavoriteSingleList();
    }


    private void configRecyclerView() {
        mAdapter = new SingleAdapter(R.layout.item_single, null);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(mAdapter);
        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getMineFavoriteSingleList();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SingleBean bean = (SingleBean) adapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putLong(Constant.INTENT_ID, bean.getId());
                bundle.putString(Constant.INTENT_TITLE, bean.getName());
                startActivity(getActivity(), SingleDetailActivity.class, bundle);
            }
        });
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                final SingleBean bean = (SingleBean) adapter.getData().get(position);
                ConfirmDialog confirmDialog = ConfirmDialog.newInstance("确认删除？");
                confirmDialog.show(getActivity().getSupportFragmentManager(), null);
                confirmDialog.setDialogListener(new DialogListener() {
                    @Override
                    public void onDialogClick(int type, Object object) {
                        currentSelected = position;
                        presenter.cancelFavorite(bean.getId());
                    }
                });
                return false;
            }
        });
    }

    @OnClick({R.id.tv_all, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                break;
            case R.id.tv_delete:
                break;
            default:
                break;
        }
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
    public void setMineFavoriteSingleList(List<SingleBean> list) {
        mSwipeLayout.setRefreshing(false);
        if (list == null || list.size() == 0) {
            mLoadingLayout.setEmptyText("您还没收藏过内容，请去收藏吧");
            mLoadingLayout.showEmpty();

        } else {
            mLoadingLayout.showMain();
            mAdapter.setNewData(list);
        }
    }

    @Override
    public void cancelFavoriteSuccess() {
        mAdapter.remove(currentSelected);
        if (mAdapter.getData().size() == 0) {
            mLoadingLayout.setEmptyText("您还没收藏过内容，请去收藏吧");
            mLoadingLayout.showEmpty();
        }
    }

    @Override
    public void cancelFavoriteFail() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onUpdateEvent(UpdateEvent event) {
        if (event == null || mAdapter == null) {
            return;
        }
        for (int i = 0; i < mAdapter.getData().size(); i++) {
            SingleBean bean = mAdapter.getData().get(i);
            if (bean.getId() == event.id) {
                mAdapter.remove(i);
            }
        }

        if (mAdapter.getData().size() == 0) {
            mLoadingLayout.setEmptyText("您还没收藏过内容，请去收藏吧");
            mLoadingLayout.showEmpty();
        }
    }
}
