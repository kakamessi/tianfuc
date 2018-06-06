package com.piano.android.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.jaeger.library.StatusBarUtil;
import com.piano.android.BuildConfig;
import com.piano.android.R;
import com.piano.android.base.BaseActivity;
import com.piano.android.base.BasePresenterActivity;
import com.piano.android.bean.songbook.AdvertBean;
import com.piano.android.common.dialog.AdvertDialogFragment;
import com.piano.android.di.component.ActivityComponent;
import com.piano.android.ui.fragment.CourseFragment;
import com.piano.android.ui.fragment.MineFragment;
import com.piano.android.ui.fragment.SongbookFragment;
import com.piano.android.ui.mvp.constract.MainConstract;
import com.piano.android.ui.mvp.presenter.MainPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * @author 陈国权
 */
public class MainActivity extends BasePresenterActivity<MainPresenter> implements MainConstract.View, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_SONGBOOK = 0;
    private static final int FRAGMENT_COURSE = 1;
    private static final int FRAGMENT_MINE = 2;

    private SongbookFragment songbookFragment;
    private CourseFragment courseFragment;
    private MineFragment mineFragment;

    @BindView(R.id.bottom_view)
    BottomNavigationViewEx bottomView;


    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_theme), 1);
    }

    @Override
    public void injectActivityComponent(ActivityComponent component) {
        super.injectActivityComponent(component);
        component.inject(this);
    }

    @Override
    public void initActivity() {
        super.initActivity();
        bottomView.enableAnimation(false);
        bottomView.enableShiftingMode(false);
        bottomView.enableItemShiftingMode(false);
        bottomView.setItemIconTintList(null);
        bottomView.setOnNavigationItemSelectedListener(this);
        showFragment(FRAGMENT_SONGBOOK);
        //申请必须权限
        checkPermission(new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE});
        presenter.getAdvert();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_songbook:
                showFragment(FRAGMENT_SONGBOOK);
                break;
            case R.id.navigation_course:
                showFragment(FRAGMENT_COURSE);
                break;
            case R.id.navigation_mine:
                showFragment(FRAGMENT_MINE);
                break;
            default:
                break;
        }
        return true;
    }


    private void showFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        switch (index) {
            case FRAGMENT_SONGBOOK:
                if (songbookFragment == null) {
                    songbookFragment = new SongbookFragment();
                    ft.add(R.id.fragment_content, songbookFragment, SongbookFragment.class.getName());
                } else {
                    ft.show(songbookFragment);
                }
                break;

            case FRAGMENT_COURSE:
                if (courseFragment == null) {
                    courseFragment = new CourseFragment();
                    ft.add(R.id.fragment_content, courseFragment, CourseFragment.class.getName());
                } else {
                    ft.show(courseFragment);
                }
                break;
            case FRAGMENT_MINE:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    ft.add(R.id.fragment_content, mineFragment, MineFragment.class.getName());
                } else {
                    ft.show(mineFragment);
                }
                break;
            default:
                break;
        }
        ft.commit();
    }

    /**
     * 隐藏fragment
     *
     * @param ft
     */
    private void hideFragment(FragmentTransaction ft) {
        if (songbookFragment != null) {
            ft.hide(songbookFragment);
        }
        if (courseFragment != null) {
            ft.hide(courseFragment);
        }

        if (mineFragment != null) {
            ft.hide(mineFragment);
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != songbookFragment && fragment != courseFragment && fragment != mineFragment)
                ft.remove(fragment);
        }
        ft.commitAllowingStateLoss();
        if (bottomView != null)
            bottomView.setCurrentItem(0);
    }


    @Override
    public void showError(String msg) {

    }

    @Override
    public void setAdvert(List<AdvertBean> list) {
        if (list != null && list.size() > 0) {
            AdvertBean bean = list.get(0);
            if(bean!=null&& !TextUtils.isEmpty(bean.getImg())){
                AdvertDialogFragment
                        .newInstance(BuildConfig.BASE_IMAGE_URL+bean.getImg())
                        .show(getSupportFragmentManager(),null);
            }
        }
    }
}
