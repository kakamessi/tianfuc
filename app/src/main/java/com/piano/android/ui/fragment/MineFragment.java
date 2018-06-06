package com.piano.android.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.piano.android.App;
import com.piano.android.BuildConfig;
import com.piano.android.R;
import com.piano.android.base.BasePresenterFragment;
import com.piano.android.bean.event.LoginSuccessEvent;
import com.piano.android.bean.user.UserBean;
import com.piano.android.common.ImageLoad;
import com.piano.android.di.component.FragmentComponent;
import com.piano.android.ui.activity.AboutActivity;
import com.piano.android.ui.activity.FeedbackActivity;
import com.piano.android.ui.activity.LoginActivity;
import com.piano.android.ui.activity.MineCourseActivity;
import com.piano.android.ui.activity.MineFavoriteActivity;
import com.piano.android.ui.activity.UserActivity;
import com.piano.android.ui.mvp.constract.MineConstract;
import com.piano.android.ui.mvp.presenter.MinePresenter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: 陈国权
 * @date: 2018/4/21 下午8:22
 * @describe: 我的
 */

public class MineFragment extends BasePresenterFragment<MinePresenter> implements MineConstract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_portrait)
    ImageView ivPortrait;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.ll_login)
    RelativeLayout llLogin;
    @BindView(R.id.iv_default_portrait)
    ImageView ivDefaultPortrait;
    @BindView(R.id.ll_unlogin)
    RelativeLayout llUnlogin;
    @BindView(R.id.tv_logout)
    TextView tvLogout;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setUserVisibleHint(true);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void injectFragmentComponent(FragmentComponent component) {
        super.injectFragmentComponent(component);
        component.inject(this);
    }

    @Override
    protected void initFragment() {
        super.initFragment();
        setToolbar(toolbar, toolbarTitle, getString(R.string.main_mine));
        ImageLoad.loadImageCircle(getActivity(), R.drawable.icon_default_portrait, ivDefaultPortrait);
    }

    @Override
    public void onResume() {
        super.onResume();
        setLoginStatue();
    }

    @OnClick({R.id.ll_login, R.id.tv_login, R.id.tv_mine_favorite, R.id.tv_mine_course, R.id.tv_feedback, R.id.tv_about, R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_login:
                startActivity(getActivity(), UserActivity.class);
                break;
            case R.id.tv_login:
                startActivity(getActivity(), LoginActivity.class);
                break;
            case R.id.tv_mine_favorite:
                if (App.getInstance().checkLogin()) {
                    startActivity(getActivity(), MineFavoriteActivity.class);
                } else {
                    startActivity(getActivity(), LoginActivity.class);
                }
                break;
            case R.id.tv_mine_course:
                if (App.getInstance().checkLogin()) {
                    startActivity(getActivity(), MineCourseActivity.class);
                } else {
                    startActivity(getActivity(), LoginActivity.class);
                }
                break;
            case R.id.tv_feedback:
                startActivity(getActivity(), FeedbackActivity.class);
                break;
            case R.id.tv_about:
                startActivity(getActivity(), AboutActivity.class);
                break;
            case R.id.tv_logout:
                App.mInstance.setUserInfo(null);
                App.mInstance.setToken("");
                setLoginStatue();
                break;
            default:
                break;
        }
    }

    private void setLoginStatue() {
        if (App.getInstance().checkLogin()) {
            llLogin.setVisibility(View.VISIBLE);
            llUnlogin.setVisibility(View.GONE);
            tvLogout.setVisibility(View.VISIBLE);
            presenter.getUserInfo();
        } else {
            llLogin.setVisibility(View.GONE);
            llUnlogin.setVisibility(View.VISIBLE);
            tvLogout.setVisibility(View.GONE);
            ImageLoad.loadImageCircle(getActivity(), R.drawable.icon_default_portrait, ivDefaultPortrait);
        }
    }

    private void setTextViewDrawable(int id) {
        Drawable drawable = getResources().getDrawable(id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvSex.setCompoundDrawables(drawable, null, null, null);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void setUserInfo(UserBean bean) {
        if (bean != null) {
            tvName.setText(bean.getUsername());
            if ("1".equals(bean.getSex())) {
                tvSex.setText("女");
                setTextViewDrawable(R.drawable.icon_girl);
            } else {
                tvSex.setText("男");
                setTextViewDrawable(R.drawable.icon_boy);
            }

            if (TextUtils.isEmpty(bean.getImg())) {
                ImageLoad.loadImageCircle(getActivity(), R.drawable.icon_default_portrait, ivPortrait);
            } else {
                ImageLoad.loadImageCircle(getActivity(), bean.getImg(), ivPortrait);
            }
        }
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
    public void loginSuccess(LoginSuccessEvent event) {
        setLoginStatue();
    }

}
