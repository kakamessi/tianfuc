package com.piano.android.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.piano.android.App;
import com.piano.android.BuildConfig;
import com.piano.android.R;
import com.piano.android.base.BasePresenterActivity;
import com.piano.android.bean.course.CourseDetailBean;
import com.piano.android.bean.course.CourseDetailInfoBean;
import com.piano.android.bean.course.CourseVideoBean;
import com.piano.android.bean.event.UpdateEvent;
import com.piano.android.common.Constant;
import com.piano.android.common.ImageLoad;
import com.piano.android.di.component.ActivityComponent;
import com.piano.android.ui.adapter.CourseDetailAdapter;
import com.piano.android.ui.mvp.constract.CourseDetailConstract;
import com.piano.android.ui.mvp.presenter.CourseDetailPresenter;
import org.greenrobot.eventbus.EventBus;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: 陈国权
 * @date: 2018/4/26 下午9:41
 * @describe: 教程详情
 */

public class CourseDetailActivity extends BasePresenterActivity<CourseDetailPresenter> implements CourseDetailConstract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;


    private CourseDetailAdapter mAdapter;
    private View headerView;
    private HeaderViewHolder headerViewHolder;

    private String title;
    private long id;
    private boolean isFavorite;
    private MenuItem menuItem;

    @Override
    public int getContentViewId() {
        return R.layout.activity_course_detail;
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
        title = getIntent().getExtras().getString(Constant.INTENT_TITLE);

    }

    @Override
    public void injectActivityComponent(ActivityComponent component) {
        super.injectActivityComponent(component);
        component.inject(this);
    }

    @Override
    public void initActivity() {
        super.initActivity();
        setToolbar(toolbar, toolbarTitle, title, true, true);
        configRecyclerView();
        presenter.getCourseDetail(id);
        if (App.mInstance.checkLogin()) {
            presenter.addHistory(id, -1);
        }
    }

    private void configRecyclerView() {
        headerView = LayoutInflater.from(this).inflate(R.layout.activity_course_detail_header, null);
        headerViewHolder = new HeaderViewHolder(headerView);
        mAdapter = new CourseDetailAdapter(R.layout.item_course_detail, null);
        mAdapter.addHeaderView(headerView);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getCourseDetail(id);
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CourseVideoBean bean = (CourseVideoBean) adapter.getData().get(position);
                if (App.mInstance.checkLogin()) {
                    presenter.addHistory(-1, bean.getId());
                }
                String url = "http://video.ihuoqiu.com/MP4/huoqiuwang_201805011608522044.mp4?_upt=eb43dc461525789336";
//                VideoUtils.openSysVideo(CourseDetailActivity.this, url);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.INTENT_URL,url);
                startActivity(CourseDetailActivity.this,VideoActivity.class,bundle);
            }
        });
    }

    @Override
    public void showError(String msg) {
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void setCourseDetail(CourseDetailBean bean) {
        mSwipeLayout.setRefreshing(false);
        if (bean == null) {
            return;
        }

        this.isFavorite = bean.isCollect();
        if (App.getInstance().checkLogin()) {
            setMenuIcon(isFavorite);
        }

        CourseDetailInfoBean infoBean = bean.getDetail();
        if (infoBean != null) {
            ImageLoad.loadImageBlurRound(this, BuildConfig.BASE_IMAGE_URL + infoBean.getImg(), headerViewHolder.ivBackground);
            ImageLoad.loadImageRounder(this, BuildConfig.BASE_IMAGE_URL + infoBean.getImg(), headerViewHolder.ivDetailCover);
            headerViewHolder.tvDetailTitle.setText(infoBean.getName());
            headerViewHolder.tvDetailContent.setText(infoBean.getSubhead());
            headerViewHolder.tvDetailUploader.setText(String.format(getString(R.string.uploader), infoBean.getUploadUser()));
        }

        List<CourseVideoBean> videoBeanList = bean.getVedioList();
        if (videoBeanList != null && videoBeanList.size() > 0) {
            mAdapter.setNewData(videoBeanList);
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


    class HeaderViewHolder {
        @BindView(R.id.iv_background)
        ImageView ivBackground;
        @BindView(R.id.iv_detail_cover)
        ImageView ivDetailCover;
        @BindView(R.id.tv_detail_title)
        TextView tvDetailTitle;
        @BindView(R.id.tv_detail_content)
        TextView tvDetailContent;
        @BindView(R.id.tv_detail_uploader)
        TextView tvDetailUploader;

        public HeaderViewHolder(View headerRootView) {
            ButterKnife.bind(this, headerRootView);
        }
    }
}
