package com.piano.android.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chico.common.ScreenUtils;
import com.jaeger.library.StatusBarUtil;
import com.piano.android.App;
import com.piano.android.BuildConfig;
import com.piano.android.R;
import com.piano.android.base.BasePresenterActivity;
import com.piano.android.bean.event.UpdateEvent;
import com.piano.android.bean.songbook.AlbumDetailBean;
import com.piano.android.bean.songbook.AlbumDetailInfoBean;
import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.common.Constant;
import com.piano.android.common.ImageLoad;
import com.piano.android.di.component.ActivityComponent;
import com.piano.android.ui.adapter.AlbumDetailAdapter;
import com.piano.android.ui.mvp.constract.AlbumDetailConstract;
import com.piano.android.ui.mvp.presenter.AlbumDetailPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import butterknife.BindView;

/**
 * @author: 陈国权
 * @date: 2018/4/26 下午9:41
 * @describe: 专辑详情
 */

public class AlbumDetailActivity extends BasePresenterActivity<AlbumDetailPresenter> implements AlbumDetailConstract.View {
    @BindView(R.id.view_statue)
    View statueView;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;

    @BindView(R.id.iv_background)
    ImageView ivBackground;
    @BindView(R.id.iv_detail_cover)
    ImageView ivDetailCover;
    @BindView(R.id.tv_detail_type)
    TextView tvDetailType;
    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @BindView(R.id.tv_detail_author)
    TextView tvDetailAuthor;
    @BindView(R.id.tv_detail_content)
    TextView tvDetailContent;
    @BindView(R.id.tv_detail_uploader)
    TextView tvDetailUploader;
    @BindView(R.id.tv_detail_number)
    TextView tvDetailNumber;


    private AlbumDetailAdapter mAdapter;

    private long id;
    private boolean isFavorite;
    private MenuItem menuItem;

    @Override
    public int getContentViewId() {
        return R.layout.activity_album_detail;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
    }

    /**
     * 设置menu图标
     *
     * @param flag
     */
    private void setMenuIcon(boolean flag) {
        if (menuItem == null) {
            return;
        }

        menuItem.setIcon(flag ? R.drawable.icon_collected : R.drawable.icon_collection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        menuItem = menu.getItem(0);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_favorite:
                if (App.mInstance.checkLogin()) {
                    if (isFavorite) {
                        presenter.cancelFavorite(id);
                    } else {
                        presenter.addFavorite(id);
                    }
                } else {
                    startActivity(this, LoginActivity.class);
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        id = getIntent().getExtras().getLong(Constant.INTENT_ID);
    }

    @Override
    public void injectActivityComponent(ActivityComponent component) {
        super.injectActivityComponent(component);
        component.inject(this);
    }

    @Override
    public void initActivity() {
        super.initActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            statueView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ScreenUtils.getStatusHeight(this)));
        } else {
            statueView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, 0));
        }
        setToolbar(toolbar, toolbarTitle, "专辑", true, false);
        configRecyclerView();
        presenter.getAlbumDetail(id);
    }

    private void configRecyclerView() {
        mAdapter = new AlbumDetailAdapter(R.layout.item_songbook_recommend_single, null);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SingleBean singleBean = (SingleBean) adapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putLong(Constant.INTENT_ID, singleBean.getId());
                startActivity(AlbumDetailActivity.this, SingleDetailActivity.class, bundle);
            }
        });
    }

    @Override
    public void showError(String msg) {
    }


    @Override
    public void setAlbumDetail(AlbumDetailBean bean) {
        if (bean == null) {
            return;
        }
        this.isFavorite = bean.isCollect();
        if (App.getInstance().checkLogin()) {
            setMenuIcon(isFavorite);
        }

        AlbumDetailInfoBean infoBean = bean.getDetail();
        if (infoBean != null) {
            ImageLoad.loadImageRounder(this, BuildConfig.BASE_IMAGE_URL + infoBean.getImg(), ivDetailCover);
            ImageLoad.loadImagBlur(this, BuildConfig.BASE_IMAGE_URL + infoBean.getImg(), ivBackground);

            tvDetailTitle.setText(infoBean.getName());
            tvDetailAuthor.setText(infoBean.getAuthor());
            tvDetailContent.setText(infoBean.getIntroduce());
            tvDetailUploader.setText(String.format(getString(R.string.uploader), infoBean.getUploadUser()));

            tvDetailType.setText(getResources().getStringArray(R.array.sort_difficult)[infoBean.getHard()]);
            if (infoBean.getHard() <= 2) {
                tvDetailType.setBackgroundResource(R.drawable.icon_level_junior);
            } else if (infoBean.getHard() == 3) {
                tvDetailType.setBackgroundResource(R.drawable.icon_level_advanced);
            } else {
                tvDetailType.setBackgroundResource(R.drawable.icon_level_challenge);
            }
        }

        List<SingleBean> songBeanList = bean.getSongList();
        if (songBeanList != null && songBeanList.size() > 0) {
            mAdapter.setNewData(songBeanList);
        }
    }

    @Override
    public void addFavoriteSuccess() {
        setMenuIcon(true);
        this.isFavorite = true;
    }

    @Override
    public void addFavoriteFail() {

    }

    @Override
    public void cancelFavoriteSuccess() {
        setMenuIcon(false);
        this.isFavorite = false;
        EventBus.getDefault().post(new UpdateEvent(id));
    }

    @Override
    public void cancelFavoriteFail() {

    }

}
