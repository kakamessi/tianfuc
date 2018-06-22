package com.piano.android.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chico.common.DateUtils;
import com.chico.common.DensityUtils;
import com.chico.common.ScreenUtils;
import com.jaeger.library.StatusBarUtil;
import com.piano.android.App;
import com.piano.android.BuildConfig;
import com.piano.android.R;
import com.piano.android.base.BasePresenterActivity;
import com.piano.android.bean.event.UpdateEvent;
import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.bean.songbook.SingleDetailBean;
import com.piano.android.bean.songbook.SingleDetailInfoBean;
import com.piano.android.common.Constant;
import com.piano.android.common.ImageLoad;
import com.piano.android.di.component.ActivityComponent;
import com.piano.android.ui.adapter.AlbumAdapter;
import com.piano.android.ui.mvp.constract.SingleDetailConstract;
import com.piano.android.ui.mvp.presenter.SingleDetailPresenter;
import com.piano.android.widget.GridItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: 陈国权
 * @date: 2018/4/26 下午9:41
 * @describe: 曲谱详情
 */

public class SingleDetailActivity extends BasePresenterActivity<SingleDetailPresenter> implements SingleDetailConstract.View {
    @BindView(R.id.iv_background)
    ImageView ivBackground;
    @BindView(R.id.view_statue)
    View statueView;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_detail_cover)
    ImageView ivDetailCover;
    @BindView(R.id.tv_detail_type)
    TextView tvDetailType;
    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @BindView(R.id.tv_detail_author)
    TextView tvDetailAuthor;
    @BindView(R.id.tv_detail_style)
    TextView tvDetailStyle;
    @BindView(R.id.tv_detail_play)
    TextView tvDetailPlay;
    @BindView(R.id.tv_detail_uploader)
    TextView tvDetailUploader;
    @BindView(R.id.tv_detail_time)
    TextView tvDetailTime;
    @BindView(R.id.tv_detail_introduce)
    TextView tvDetailIntroduce;
    @BindView(R.id.tv_staff)
    TextView tvStaff;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;

    private AlbumAdapter mAdapter;

    private long id;
    private boolean isFavorite;
    private MenuItem menuItem;

    private String songName;

    @Override
    public int getContentViewId() {
        return R.layout.activity_single_detail;
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
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
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
        setToolbar(toolbar, toolbarTitle, "曲谱详情", true, false);
        presenter.getSingleDetail(id);
        if (App.mInstance.checkLogin()) {
            presenter.addHistory(id);
        }
    }

    @Override
    public void showError(String msg) {
    }


    @Override
    public void setSingleDetail(SingleDetailBean bean) {
        if (bean == null) {
            return;
        }

        songName = bean.getDetail().getOpern();

        this.isFavorite = bean.isCollect();
        if (App.getInstance().checkLogin()) {
            setMenuIcon(isFavorite);
        }

        SingleDetailInfoBean infoBean = bean.getDetail();
        ImageLoad.loadImageRounder(this, BuildConfig.BASE_IMAGE_URL + infoBean.getImg(), ivDetailCover);
        ImageLoad.loadImagBlur(this, BuildConfig.BASE_IMAGE_URL + infoBean.getImg(), ivBackground);

        tvDetailTitle.setText(infoBean.getName());
        tvDetailAuthor.setText(infoBean.getAuthor());
        tvDetailIntroduce.setText(infoBean.getIntroduce());
        tvDetailUploader.setText(String.format(getString(R.string.uploader), infoBean.getUploadUser()));
        tvDetailStyle.setText(infoBean.getTypeName());
        tvDetailTime.setText(DateUtils.formatStringToString(infoBean.getCreateTime(), "yyyy-MM-dd hh:mm:ss", "yyyy-MM-dd"));

        tvDetailType.setText(getResources().getStringArray(R.array.sort_difficult)[infoBean.getHard()]);
        if (infoBean.getHard() <= 2) {
            tvDetailType.setBackgroundResource(R.drawable.icon_level_junior);
        } else if (infoBean.getHard() == 3) {
            tvDetailType.setBackgroundResource(R.drawable.icon_level_advanced);
        } else {
            tvDetailType.setBackgroundResource(R.drawable.icon_level_challenge);
        }

        List<AlbumBean> albumBeanList = bean.getSongsetList();
        if (albumBeanList != null && albumBeanList.size() > 0) {
            if (mAdapter == null) {
                mAdapter = new AlbumAdapter(R.layout.item_album, albumBeanList);
                mRecycleView.setAdapter(mAdapter);
                mRecycleView.setLayoutManager(new GridLayoutManager(this, 3));
                mRecycleView.addItemDecoration(new GridItemDecoration(3, DensityUtils.dp2px(this, 5), true, 0));
                mRecycleView.setNestedScrollingEnabled(false);
                mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        AlbumBean bean = (AlbumBean) adapter.getData().get(position);
                        Bundle bundle = new Bundle();
                        bundle.putLong(Constant.INTENT_ID, bean.getId());
                        startActivity(SingleDetailActivity.this, AlbumDetailActivity.class, bundle);
                    }
                });
            } else {
                mAdapter.setNewData(albumBeanList);
            }
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


//    @OnClick(R.id.tv_more_album)
//    public void onViewClicked() {
//        Bundle bundle = new Bundle();
//        bundle.putInt(Constant.INTENT_CATEGORY, AlbumActivity.ALBUM_LATEST);
//        startActivity(this, AlbumActivity.class, bundle);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_staff, R.id.bg_staff,R.id.tv_more_album})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more_album:
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.INTENT_CATEGORY, AlbumActivity.ALBUM_LATEST);
                startActivity(this, AlbumActivity.class, bundle);
                break;

            case R.id.btn_staff:
                Bundle name = new Bundle();
                name.putString(Constant.INTENT_FILE_NAME,songName);
                startActivity(this, MusicActivity.class, name);
                break;

            case R.id.bg_staff:
                Bundle name1 = new Bundle();
                name1.putString(Constant.INTENT_FILE_NAME,songName);
                startActivity(this, MusicActivity.class, name1);
                break;


        }
    }



}




