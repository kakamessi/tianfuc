package com.piano.android.ui.activity;


import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chico.common.ToastUtils;
import com.piano.android.App;
import com.piano.android.R;
import com.piano.android.base.BasePresenterActivity;
import com.piano.android.bean.event.LoginSuccessEvent;
import com.piano.android.bean.login.LoginBean;
import com.piano.android.common.CheckUtils;
import com.piano.android.common.Constant;
import com.piano.android.di.component.ActivityComponent;
import com.piano.android.ui.mvp.constract.LoginConstract;
import com.piano.android.ui.mvp.presenter.LoginPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: 陈国权
 * @date: 2018/4/21 下午10:02
 * @describe:
 */

public class LoginActivity extends BasePresenterActivity<LoginPresenter> implements LoginConstract.View {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void injectActivityComponent(ActivityComponent component) {
        super.injectActivityComponent(component);
        component.inject(this);
    }

    @Override
    public void initActivity() {
        super.initActivity();
        setToolbar(toolbar, toolbarTitle, getString(R.string.login), true, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_register:
                startActivity(this, RegisterActivity.class, Constant.REQUEST_CODE_OK);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.tv_forget_password, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_password:
                startActivity(this, ForgetPasswordActivity.class, Constant.REQUEST_CODE_OK);
                break;
            case R.id.tv_login:
                String mobile = etMobile.getText().toString();
                String password = etPassword.getText().toString();
                if (CheckUtils.checkLoginInfo(mobile, password)) {
                    showLoading();
                    presenter.login(mobile, password);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void loginSuccess(LoginBean bean) {
        dismissLoading();
        if (bean != null) {
            App.getInstance().setToken(bean.getToken());
            EventBus.getDefault().post(new LoginSuccessEvent());
            finish();
        }
    }


    @Override
    public void showError(String msg) {
        dismissLoading();
        ToastUtils.showShortToast(this, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_OK) {

                if (data == null) {
                    return;
                }

                String mobile = data.getExtras().getString(Constant.INTENT_MOBILE);
                String password = data.getExtras().getString(Constant.INTENT_PASSWORD);

                etMobile.setText(mobile);
                etPassword.setText(password);
            }
        }
    }
}
