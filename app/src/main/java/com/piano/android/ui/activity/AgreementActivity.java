package com.piano.android.ui.activity;

/**
 * @author: 陈国权
 * @date: 2018/5/9 下午11:23
 * @describe: 用户协议
 */

public class AgreementActivity extends HtmlActivity {

    @Override
    public void getIntentData() {

    }

    @Override
    public void initActivity() {
        super.initActivity();
        mWebView.loadUrl("file:///android_asset/agreement.html");
    }
}
