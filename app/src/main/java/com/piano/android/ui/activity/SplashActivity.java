package com.piano.android.ui.activity;

import com.jaeger.library.StatusBarUtil;
import com.piano.android.R;
import com.piano.android.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @author: 陈国权
 * @date: 2018/4/21 下午7:50
 * @describe:
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
    }


    @Override
    public int getContentViewId() {
        return -1;
    }

    @Override
    public void initActivity() {

        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        startActivity(SplashActivity.this, MainActivity.class);
                        finish();
                    }
                });

    }
}
