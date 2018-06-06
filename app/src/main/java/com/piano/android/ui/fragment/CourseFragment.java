package com.piano.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.piano.android.App;
import com.piano.android.R;
import com.piano.android.base.BaseFragment;
import com.piano.android.common.Constant;
import com.piano.android.ui.activity.HistoryActivity;
import com.piano.android.ui.activity.LoginActivity;
import com.piano.android.ui.activity.MineCourseActivity;
import com.piano.android.ui.activity.SearchActivity;
import com.piano.android.ui.adapter.TabPageAdapter;
import com.piano.android.ui.mvp.constract.CourseChildConstract;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: 陈国权
 * @date: 2018/4/21 下午8:22
 * @describe: 教程
 */
public class CourseFragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.view_page)
    ViewPager viewPage;

    @BindArray(R.array.course_tabs)
    String[] mTitles;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private TabPageAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_course;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setUserVisibleHint(true);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initFragment() {

        mFragments.add(CourseChildFragment.newInstance(CourseChildFragment.COURSE_BASE));
        mFragments.add(CourseChildFragment.newInstance(CourseChildFragment.COURSE_ADV));
        mFragments.add(CourseChildFragment.newInstance(CourseChildFragment.COURSE_TEST));

        adapter = new TabPageAdapter(getChildFragmentManager(), mFragments, mTitles);
        viewPage.setAdapter(adapter);
        viewPage.setCurrentItem(0);
        viewPage.setOffscreenPageLimit(adapter.getCount());
        tabLayout.setViewPager(viewPage, mTitles);
    }

    @OnClick({R.id.tv_search, R.id.btn_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                startActivity(getActivity(), SearchActivity.class);
                break;
            case R.id.btn_history:
                if (App.getInstance().checkLogin()) {
                    startActivity(getActivity(), MineCourseActivity.class);
                } else {
                    startActivity(getActivity(), LoginActivity.class);
                }
                break;
            default:
                break;
        }
    }

}
