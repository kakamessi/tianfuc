package com.piano.android.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chico.common.DeviceUtils;
import com.piano.android.R;
import com.piano.android.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: 陈国权
 * @date: 2018/4/24 上午12:07
 * @describe:
 */

public class AboutActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;

    @Override
    public int getContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    public void initActivity() {
        setToolbar(toolbar, toolbarTitle, getString(R.string.about), true, true);
        tvVersion.setText(String.format(getString(R.string.current_version), DeviceUtils.getVersionName(this)));
    }


    @OnClick({R.id.tv_version, R.id.tv_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_version:
                break;
            case R.id.tv_agreement:
                startActivity(this,AgreementActivity.class);
                break;
            default:
                break;
        }
    }
}
