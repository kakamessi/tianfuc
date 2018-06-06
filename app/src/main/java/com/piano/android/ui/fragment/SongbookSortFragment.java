package com.piano.android.ui.fragment;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chico.common.ToastUtils;
import com.piano.android.R;
import com.piano.android.base.BasePresenterFragment;
import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.common.Constant;
import com.piano.android.di.component.FragmentComponent;
import com.piano.android.ui.activity.AlbumDetailActivity;
import com.piano.android.ui.adapter.AlbumAdapter;
import com.piano.android.ui.adapter.FilterAdapter;
import com.piano.android.ui.mvp.constract.SongbookSortConstract;
import com.piano.android.ui.mvp.presenter.SongbookSortPresenter;
import com.piano.android.widget.LoadingLayout;
import com.piano.android.widget.Solve7PopupWindow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * @author: 陈国权
 * @date: 2018/4/25 下午11:00
 * @describe: 曲库分类
 */

public class SongbookSortFragment extends BasePresenterFragment<SongbookSortPresenter> implements SongbookSortConstract.View {

    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;


    @BindView(R.id.ll_difficult)
    LinearLayout llDifficult;
    @BindView(R.id.ll_style)
    LinearLayout llStyle;
    @BindView(R.id.ll_more)
    LinearLayout llMore;

    @BindView(R.id.tv_difficult)
    TextView tvDifficult;
    @BindView(R.id.tv_style)
    TextView tvStyle;
    @BindView(R.id.tv_more)
    TextView tvMore;
    Unbinder unbinder;

    @BindArray(R.array.sort_difficult)
    String[] difficultArray;
    @BindArray(R.array.sort_style)
    String[] styleArray;

    private AlbumAdapter mAdapter;
    private int page = 1;
    private int limit = 12;

    private Solve7PopupWindow difficultPop;
    private Solve7PopupWindow stylePop;
    private Solve7PopupWindow morePop;
    private MoreViewHold moreViewHold;
    private FilterAdapter styleAdapter;
    private FilterAdapter difficultAdapter;

    //0不限1新手2初级3中级4高级5挑战
    private int hard = -1;
    //0不限1流行2古典3轻音乐4动漫5影视
    private int style = -1;
    //0成人1儿童
    private int person = -1;
    //0弹唱1弹琴
    private int scene = -1;
    //0经典教材1考级教材
    private int teaching = -1;

    public static Fragment newInstance() {
        SongbookSortFragment fragment = new SongbookSortFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_songbook_sort;
    }

    @Override
    public void injectFragmentComponent(FragmentComponent component) {
        super.injectFragmentComponent(component);
        component.inject(this);
    }

    public void hideFragment() {
        super.onPause();
        if (difficultPop != null) {
            difficultPop.dismiss();
            setTextDrawableLeft(tvDifficult, R.drawable.arrow_down);
        }

        if (stylePop != null) {
            stylePop.dismiss();
            setTextDrawableLeft(tvStyle, R.drawable.arrow_down);
        }

        if (morePop != null) {
            morePop.dismiss();
            setTextDrawableLeft(tvMore, R.drawable.arrow_down);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        hideFragment();
    }

    @Override
    protected void initFragment() {
        super.initFragment();
        configRecyclerView();
        difficultPopwindow();
        stylePopwindow();
        morePopwindow();
        requestData();
    }

    private void configRecyclerView() {
        mAdapter = new AlbumAdapter(R.layout.item_songbook_recommend_album, null);
        mRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecycleView.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                requestData();

            }
        }, mRecycleView);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AlbumBean bean = (AlbumBean) adapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putLong(Constant.INTENT_ID, bean.getId());
                startActivity(getActivity(), AlbumDetailActivity.class, bundle);
            }
        });


        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                requestData();
            }
        });
    }

    private void requestData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", limit);
        if (hard != -1) {
            map.put("hard", hard);
        }
        if (style != -1) {
            map.put("style", style);
        }
        if (person != -1) {
            map.put("person", person);
        }
        if (scene != -1) {
            map.put("scene", scene);
        }
        if (teaching != -1) {
            map.put("teaching", teaching);
        }

        presenter.getFilterAlbum(map);
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
        if (page == 1) {
            mLoadingLayout.showError();
        } else {
            ToastUtils.showShortToast(getActivity(), msg);
        }
    }

    @Override
    public void setFilterAlbum(List<AlbumBean> list) {
        finishLoad();
        mLoadingLayout.showMain();
        if (list == null || list.size() == 0) {
            mAdapter.loadMoreEnd(false);
            if (page == 1) {
                mAdapter.setNewData(null);
                mLoadingLayout.setEmptyText("暂无数据，请重新选择筛选条件！");
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


    private void initFilter(int index) {
        switch (index) {
            case 1:
                style = -1;
                person = -1;
                scene = -1;
                teaching = -1;
                break;
            case 2:
                hard = -1;
                person = -1;
                scene = -1;
                teaching = -1;
                break;
            case 3:
                hard = -1;
                style = -1;
                break;
        }
    }


    @OnClick({R.id.ll_difficult, R.id.ll_style, R.id.ll_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_difficult:
                initFilter(1);
                stylePop.dismiss();
                setTextDrawableLeft(tvStyle, R.drawable.arrow_down);
                morePop.dismiss();
                setTextDrawableLeft(tvMore, R.drawable.arrow_down);
                if (difficultPop.isShowing()) {
                    difficultPop.dismiss();
                    setTextDrawableLeft(tvDifficult, R.drawable.arrow_down);
                } else {
                    difficultPop.showAsDropDown(llDifficult);
                    updateDifficultPopwindow();
                    setTextDrawableLeft(tvDifficult, R.drawable.arrow_up);
                }
                break;
            case R.id.ll_style:
                initFilter(2);
                difficultPop.dismiss();
                setTextDrawableLeft(tvDifficult, R.drawable.arrow_down);
                morePop.dismiss();
                setTextDrawableLeft(tvMore, R.drawable.arrow_down);
                if (stylePop.isShowing()) {
                    stylePop.dismiss();
                    setTextDrawableLeft(tvStyle, R.drawable.arrow_down);
                } else {
                    stylePop.showAsDropDown(llStyle);
                    updateStylePopwindow();
                    setTextDrawableLeft(tvStyle, R.drawable.arrow_up);
                }
                break;
            case R.id.ll_more:
                initFilter(3);
                difficultPop.dismiss();
                setTextDrawableLeft(tvDifficult, R.drawable.arrow_down);
                stylePop.dismiss();
                setTextDrawableLeft(tvStyle, R.drawable.arrow_down);
                if (morePop.isShowing()) {
                    morePop.dismiss();
                    setTextDrawableLeft(tvMore, R.drawable.arrow_down);
                } else {
                    morePop.showAsDropDown(llMore);
                    updateMorePopwindow();
                    setTextDrawableLeft(tvMore, R.drawable.arrow_up);
                }
                break;
        }
    }

    private void setTextDrawableLeft(TextView textView, int id) {
        Drawable drawable = getResources().getDrawable(id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
        if (id == R.drawable.arrow_up) {
            textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_theme));
        } else {
            textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_title));
        }
    }

    private void difficultPopwindow() {
        final LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popwindow_difficult, null);
        final RecyclerView recyclerView = layout.findViewById(R.id.recycle_view_difficult);
        difficultAdapter = new FilterAdapter(R.layout.item_filter, Arrays.asList(difficultArray));
        difficultAdapter.setCheckIndex(hard);
        recyclerView.setAdapter(difficultAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        difficultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                page = 1;
                hard = position + 1;
                requestData();
                difficultAdapter.setCheckIndex(position);
                difficultPop.dismiss();
                setTextDrawableLeft(tvDifficult, R.drawable.arrow_down);
            }
        });

        difficultPop = new Solve7PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        difficultPop.setFocusable(false);   // 设置SelectPicPopupWindow弹出窗体可点击
        difficultPop.setOutsideTouchable(false);
        difficultPop.update();// 刷新状态
        ColorDrawable dw = new ColorDrawable(0000000000); // 实例化一个ColorDrawable颜色为半透明
        difficultPop.setBackgroundDrawable(dw);// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getY() > recyclerView.getBottom()) {
                    difficultPop.dismiss();
                    setTextDrawableLeft(tvDifficult, R.drawable.arrow_down);
                }
                return false;
            }
        });
    }

    private void updateDifficultPopwindow(){
        if(difficultAdapter!=null){
            difficultAdapter.setCheckIndex(hard);
        }
    }


    private void stylePopwindow() {
        final LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popwindow_style, null);
        final RecyclerView recyclerView = layout.findViewById(R.id.recycle_view_style);
        styleAdapter = new FilterAdapter(R.layout.item_filter, Arrays.asList(styleArray));
        styleAdapter.setCheckIndex(style);
        recyclerView.setAdapter(styleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        styleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                page = 1;
                style = position + 1;
                requestData();
                styleAdapter.setCheckIndex(position);
                stylePop.dismiss();
                setTextDrawableLeft(tvStyle, R.drawable.arrow_down);
            }
        });

        stylePop = new Solve7PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        stylePop.setFocusable(false);   // 设置SelectPicPopupWindow弹出窗体可点击
        stylePop.setOutsideTouchable(false);
        stylePop.update();// 刷新状态
        ColorDrawable dw = new ColorDrawable(0000000000); // 实例化一个ColorDrawable颜色为半透明
        stylePop.setBackgroundDrawable(dw);// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getY() > recyclerView.getBottom()) {
                    stylePop.dismiss();
                    setTextDrawableLeft(tvStyle, R.drawable.arrow_down);
                }
                return false;
            }
        });
    }

    private void updateStylePopwindow(){
        if(styleAdapter!=null){
            styleAdapter.setCheckIndex(style);
        }
    }


    private void morePopwindow() {
        final LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popwindow_more, null);
        moreViewHold = new MoreViewHold(layout);
        morePop = new Solve7PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        morePop.setFocusable(false);   // 设置SelectPicPopupWindow弹出窗体可点击
        morePop.setOutsideTouchable(false);
        morePop.update();// 刷新状态
        ColorDrawable dw = new ColorDrawable(0000000000); // 实例化一个ColorDrawable颜色为半透明
        morePop.setBackgroundDrawable(dw);// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getY() > moreViewHold.bottomLayout.getBottom()) {
                    morePop.dismiss();
                    setTextDrawableLeft(tvMore, R.drawable.arrow_down);
                }
                return false;
            }
        });
    }

    private void updateMorePopwindow() {
        if (moreViewHold != null) {
            moreViewHold.tvPersonNone.setSelected(person == 1);
            moreViewHold.tvAdult.setSelected(person == 2);
            moreViewHold.tvChild.setSelected(person == 3);
            moreViewHold.tvSceneNone.setSelected(scene == 1);
            moreViewHold.tvSing.setSelected(scene == 2);
            moreViewHold.tvPractise.setSelected(scene == 3);
            moreViewHold.tvCourseNone.setSelected(teaching == 1);
            moreViewHold.tvClassics.setSelected(teaching == 2);
            moreViewHold.tvTest.setSelected(teaching == 3);
        }
    }


    class MoreViewHold {

        @BindView(R.id.tv_person_none)
        TextView tvPersonNone;
        @BindView(R.id.tv_adult)
        TextView tvAdult;
        @BindView(R.id.tv_child)
        TextView tvChild;
        @BindView(R.id.tv_scene_none)
        TextView tvSceneNone;
        @BindView(R.id.tv_sing)
        TextView tvSing;
        @BindView(R.id.tv_practise)
        TextView tvPractise;
        @BindView(R.id.tv_course_none)
        TextView tvCourseNone;
        @BindView(R.id.tv_classics)
        TextView tvClassics;
        @BindView(R.id.tv_test)
        TextView tvTest;
        @BindView(R.id.tv_reset)
        TextView tvReset;
        @BindView(R.id.ll_bottom)
        ConstraintLayout bottomLayout;

        public MoreViewHold(View moreview) {
            ButterKnife.bind(this, moreview);
        }

        @OnClick({R.id.tv_person_none, R.id.tv_adult, R.id.tv_child, R.id.tv_scene_none, R.id.tv_sing, R.id.tv_practise, R.id.tv_course_none, R.id.tv_classics, R.id.tv_test, R.id.tv_reset, R.id.tv_confirm})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.tv_person_none:
                    person = 1;
                    moreViewHold.tvPersonNone.setSelected(true);
                    moreViewHold.tvAdult.setSelected(false);
                    moreViewHold.tvChild.setSelected(false);
                    break;
                case R.id.tv_adult:
                    person = 2;
                    moreViewHold.tvPersonNone.setSelected(false);
                    moreViewHold.tvAdult.setSelected(true);
                    moreViewHold.tvChild.setSelected(false);
                    break;
                case R.id.tv_child:
                    person = 3;
                    moreViewHold.tvPersonNone.setSelected(false);
                    moreViewHold.tvAdult.setSelected(false);
                    moreViewHold.tvChild.setSelected(true);
                    break;
                case R.id.tv_scene_none:
                    scene = 1;
                    moreViewHold.tvSceneNone.setSelected(true);
                    moreViewHold.tvSing.setSelected(false);
                    moreViewHold.tvPractise.setSelected(false);
                    break;
                case R.id.tv_sing:
                    scene = 2;
                    moreViewHold.tvSceneNone.setSelected(false);
                    moreViewHold.tvSing.setSelected(true);
                    moreViewHold.tvPractise.setSelected(false);
                    break;
                case R.id.tv_practise:
                    scene = 3;
                    moreViewHold.tvSceneNone.setSelected(false);
                    moreViewHold.tvSing.setSelected(false);
                    moreViewHold.tvPractise.setSelected(true);
                    break;
                case R.id.tv_course_none:
                    teaching = 1;
                    moreViewHold.tvCourseNone.setSelected(true);
                    moreViewHold.tvClassics.setSelected(false);
                    moreViewHold.tvTest.setSelected(false);
                    break;
                case R.id.tv_classics:
                    teaching = 2;
                    moreViewHold.tvCourseNone.setSelected(false);
                    moreViewHold.tvClassics.setSelected(true);
                    moreViewHold.tvTest.setSelected(false);
                    break;
                case R.id.tv_test:
                    teaching = 3;
                    moreViewHold.tvCourseNone.setSelected(false);
                    moreViewHold.tvClassics.setSelected(false);
                    moreViewHold.tvTest.setSelected(true);
                    break;
                case R.id.tv_reset:
                    person = -1;
                    scene = -1;
                    teaching = -1;
                    moreViewHold.tvPersonNone.setSelected(false);
                    moreViewHold.tvAdult.setSelected(false);
                    moreViewHold.tvChild.setSelected(false);
                    moreViewHold.tvSceneNone.setSelected(false);
                    moreViewHold.tvSing.setSelected(false);
                    moreViewHold.tvPractise.setSelected(false);
                    moreViewHold.tvCourseNone.setSelected(false);
                    moreViewHold.tvClassics.setSelected(false);
                    moreViewHold.tvTest.setSelected(false);
                    break;
                case R.id.tv_confirm:
                    page = 1;
                    requestData();
                    morePop.dismiss();
                    setTextDrawableLeft(tvMore, R.drawable.arrow_down);
                    break;
            }
        }
    }
}
