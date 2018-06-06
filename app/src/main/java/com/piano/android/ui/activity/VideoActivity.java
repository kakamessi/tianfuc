package com.piano.android.ui.activity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.MediaController;
import com.chico.common.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.piano.android.R;
import com.piano.android.base.BaseActivity;
import com.piano.android.common.Constant;
import com.piano.android.widget.CustomVideoView;
import butterknife.BindView;


/**
 * @author: 陈国权
 * @date: 2018/5/24 0024 上午 11:53
 * @description:
 */

public class VideoActivity extends BaseActivity {
    @BindView(R.id.vv_video)
    CustomVideoView mVideoView;

    private Uri mUri;
    private int mPositionWhenPaused = -1;
    private MediaController mMediaController;
    private String url;


    @Override
    public int getContentViewId() {
        return R.layout.activity_video;
    }


    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        url = getIntent().getExtras().getString(Constant.INTENT_URL);
    }


    @Override
    public void initActivity() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (TextUtils.isEmpty(url)) {
            finish();
        }
        mUri = Uri.parse(url);

        mMediaController = new MediaController(this);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        // Play Video
        if (mVideoView != null && mUri != null) {
            mVideoView.setVideoURI(mUri);
            mVideoView.start();
        } else {
            ToastUtils.showShortToast(this, "发生错误");
        }
        super.onStart();
    }

    @Override
    public void onPause() {
        mPositionWhenPaused = mVideoView.getCurrentPosition();
        mVideoView.stopPlayback();
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mPositionWhenPaused >= 0) {
            mVideoView.seekTo(mPositionWhenPaused);
            mPositionWhenPaused = -1;
        }
        super.onResume();
    }

    public boolean onError(MediaPlayer player, int arg1, int arg2) {
        return false;
    }

    public void onCompletion(MediaPlayer mp) {
        this.finish();
    }

}
