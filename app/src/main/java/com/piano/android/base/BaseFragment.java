package com.piano.android.base;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piano.android.App;
import com.piano.android.R;
import com.piano.android.di.component.ActivityComponent;
import com.piano.android.di.component.DaggerFragmentComponent;
import com.piano.android.di.component.FragmentComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 陈国权
 * @date 2018/3/2 0002
 */

public abstract class BaseFragment extends Fragment {

    // Fragment的View加载完毕的标记
    private boolean isViewCreated;

    // Fragment对用户可见的标记
    private boolean isUIVisible;

    private Unbinder unbinder;


    /**
     * 获取布局LayoutId
     *
     * @return id
     */
    protected abstract int getContentViewId();

    /**
     * Fragment操作
     */
    protected abstract void initFragment();


    protected void getIntentData() {
    }

    /**
     * dagger2注入
     *
     * @param component
     */
    public void injectFragmentComponent(FragmentComponent component) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getContentViewId(), null);
        unbinder = ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    /**
     * 懒加载操作
     */
    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            FragmentComponent component = DaggerFragmentComponent.builder()
                    .appComponent(App.getInstance().getAppComponent())
                    .build();
            injectFragmentComponent(component);
            initFragment();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    protected void setToolbar(Toolbar toolbar, TextView titleView, String title) {
        if (toolbar == null) {
            return;
        }
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
                | ActionBar.DISPLAY_SHOW_HOME);
        toolbar.setNavigationIcon(null);

        if (titleView == null) {
            return;
        }

        if (TextUtils.isEmpty(title)) {
            return;
        }
        titleView.setText(title);

    }


    /**
     * 跳转
     *
     * @param activity
     * @param cls
     */
    protected void startActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        startActivity(intent);
    }

    /**
     *  带参数跳转
     * @param activity
     * @param cls
     * @param bundle
     */
    protected void startActivity(Activity activity, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
