package com.piano.android.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chico.common.ToastUtils;
import com.piano.android.R;
import com.piano.android.base.BasePresenterActivity;
import com.piano.android.common.CheckUtils;
import com.piano.android.common.Constant;
import com.piano.android.di.component.ActivityComponent;
import com.piano.android.ui.mvp.constract.RegisterConstract;
import com.piano.android.ui.mvp.presenter.RegisterPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: 陈国权
 * @date: 2018/4/23 下午11:24
 * @describe:
 */

public class RegisterActivity extends BasePresenterActivity<RegisterPresenter> implements RegisterConstract.View {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_verify)
    EditText etVerify;
    @BindView(R.id.tv_send_verify)
    TextView tvSendVerify;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_again)
    EditText etPasswordAgain;

    private String mobile, password;

    @Override
    public int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    public void initActivity() {
        super.initActivity();
        setToolbar(toolbar, toolbarTitle, getString(R.string.register), true, true);
    }

    @Override
    public void injectActivityComponent(ActivityComponent component) {
        super.injectActivityComponent(component);
        component.inject(this);
    }

    @OnClick({R.id.tv_send_verify, R.id.tv_login,R.id.tv_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_verify:
                mobile = etMobile.getText().toString();
                if (CheckUtils.checkSmsMobile(mobile)) {
                    tvSendVerify.setEnabled(false);
                    presenter.getSMSCode(mobile);
                }
                break;
            case R.id.tv_login:
                mobile = etMobile.getText().toString();
                String smsCode = etVerify.getText().toString();
                password = etPassword.getText().toString();
                String passwordAgain = etPasswordAgain.getText().toString();
                if (CheckUtils.checkRegister(mobile, smsCode, password, passwordAgain)) {
                    presenter.register(mobile, password, smsCode);
                }
                break;
            case R.id.tv_agreement:
                startActivity(this,AgreementActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showShortToast(this, msg);
    }

    @Override
    public void getSMSCodeSuccess() {
        ToastUtils.showShortToast(this, "短信发送成功,请注意查收");
        presenter.timer(tvSendVerify);
    }

    @Override
    public void getSMSCodeFail(String msg) {
        tvSendVerify.setEnabled(true);
        ToastUtils.showShortToast(this, msg);
    }

    @Override
    public void registerSuccess() {
        Intent intent = new Intent();
        intent.putExtra(Constant.INTENT_MOBILE, mobile);
        intent.putExtra(Constant.INTENT_PASSWORD, password);
        setResult(RESULT_OK, intent);
        finish();
    }
}
