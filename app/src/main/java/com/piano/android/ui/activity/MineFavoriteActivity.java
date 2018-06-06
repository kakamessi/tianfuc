package com.piano.android.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.flyco.tablayout.SlidingTabLayout;
import com.piano.android.R;
import com.piano.android.base.BaseActivity;
import com.piano.android.ui.adapter.TabPageAdapter;
import com.piano.android.ui.fragment.MineFavoriteAlbumFragment;
import com.piano.android.ui.fragment.MineFavoriteCourseFragment;
import com.piano.android.ui.fragment.MineFavoriteSingleFragment;
import java.util.ArrayList;
import butterknife.BindArray;
import butterknife.BindView;

/**
 * @author: 陈国权
 * @date: 2018/4/24 上午12:01
 * @describe:
 */

public class MineFavoriteActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.view_page)
    ViewPager viewPage;

    @BindArray(R.array.mine_favorite_tabs)
    String[] mTitles;


    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private TabPageAdapter adapter;
    private int currentPage;

    @Override
    public int getContentViewId() {
        return R.layout.activity_mine_favorite;
    }

    @Override
    public void initActivity() {
        setToolbar(toolbar, toolbarTitle, "我的收藏", true, true);

        mFragments.add(MineFavoriteSingleFragment.newInstance());
        mFragments.add(MineFavoriteAlbumFragment.newInstance());
        mFragments.add(MineFavoriteCourseFragment.newInstance());

        adapter = new TabPageAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPage.setAdapter(adapter);
        viewPage.setCurrentItem(0);
        viewPage.setOffscreenPageLimit(adapter.getCount());
        tabLayout.setViewPager(viewPage, mTitles);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
//                if (mFragments != null) {
//                    ((MineFavoriteFragment) mFragments.get(currentPage)).onEdit(isEdit);
//                    isEdit = isEdit ? false : true;
////                    SpannableString spanString = new SpannableString(isEdit ? "编辑" : "取消");
////                    spanString.setSpan(new ForegroundColorSpan(Color.RED), 0, spanString.length(), 0);
//                    if (isEdit) {
//                        item.setTitle(Html.fromHtml("<font color='#3160d3'>编辑</font>"));
//                    } else {
//                        item.setTitle(Html.fromHtml("<font color='#ee4d39'>取消</font>"));
//                    }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
