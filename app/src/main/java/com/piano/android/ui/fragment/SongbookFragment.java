package com.piano.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.piano.android.App;
import com.piano.android.R;
import com.piano.android.base.BaseFragment;
import com.piano.android.common.Constant;
import com.piano.android.ui.activity.HistoryActivity;
import com.piano.android.ui.activity.LoginActivity;
import com.piano.android.ui.activity.SearchActivity;
import com.piano.android.ui.adapter.TabPageAdapter;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: 陈国权
 * @date: 2018/4/21 下午8:22
 * @describe: 曲库
 */

public class SongbookFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.view_page)
    ViewPager viewPage;

    @BindArray(R.array.songbook_tabs)
    String[] mTitles;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private TabPageAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_songbook;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setUserVisibleHint(true);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initFragment() {

        mFragments.add(SongbookRecommendFragment.newInstance());
        mFragments.add(SongbookLatestFragment.newInstance());
        mFragments.add(SongbookSortFragment.newInstance());

        adapter = new TabPageAdapter(getChildFragmentManager(), mFragments, mTitles);
        viewPage.setAdapter(adapter);
        viewPage.setCurrentItem(0);
        viewPage.setOffscreenPageLimit(adapter.getCount());
        tabLayout.setViewPager(viewPage, mTitles);
        viewPage.setOnPageChangeListener(this);
    }


    @OnClick({R.id.tv_search, R.id.btn_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                startActivity(getActivity(), SearchActivity.class);
                break;
            case R.id.btn_history:
                if (App.getInstance().checkLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.INTENT_TYPE, 0);
                    startActivity(getActivity(), HistoryActivity.class, bundle);
                } else {
                    startActivity(getActivity(), LoginActivity.class);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position != 2) {
            SongbookSortFragment sortFragment = (SongbookSortFragment) mFragments.get(2);
            sortFragment.hideFragment();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
