package com.piano.android.ui.activity;

import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.piano.android.R;
import com.piano.android.base.BaseActivity;
import com.piano.android.common.Constant;

import butterknife.BindView;

/**
 * @author: 陈国权
 * @date: 2018/5/7 下午10:28
 * @describe:
 */

public class HtmlActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;
    @BindView(R.id.ll_webview)
    LinearLayout llWebview;

    protected WebView mWebView;
    private String url;

    @Override
    public int getContentViewId() {
        return R.layout.activity_html;
    }


    @Override
    public void getIntentData() {
        super.getIntentData();
        url = getIntent().getExtras().getString(Constant.INTENT_URL);
    }

    @Override
    public void initActivity() {

        setToolbar(toolbar, toolbarTitle, "", true, true);

        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        llWebview.addView(mWebView);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        if (TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }
    }

    class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

    }

    class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            toolbarTitle.setText(title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                pbProgress.setVisibility(View.GONE);
            } else {
                if (View.INVISIBLE == pbProgress.getVisibility()) {
                    pbProgress.setVisibility(View.VISIBLE);
                }
                pbProgress.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
            llWebview.removeAllViews();
        }
    }


}
