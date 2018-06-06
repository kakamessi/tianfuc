package com.piano.android.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.piano.android.R;


/**
 * @author 陈国权
 * @date 2015/9/10
 * @description 加载圆形进度
 */
public class LoadingDialog {
    private Dialog dialog;
    private ImageView mLoadingImage;
    private AnimationDrawable animationDrawable;

    public LoadingDialog(Context mContext) {
        View mDialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null);
        mLoadingImage = mDialogView.findViewById(R.id.iv_loading);
        mLoadingImage.setBackgroundResource(R.drawable.anim_loading);
        animationDrawable = (AnimationDrawable) mLoadingImage.getBackground();

        if (dialog != null) {
            dialog.cancel();
        }

        dialog = new Dialog(mContext, R.style.LoadingDialogTheme);
        dialog.setContentView(mDialogView);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
    }

    /**
     * 显示对话框
     */
    public void show() {
        if (dialog == null) {
            return;
        }
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }
        dialog.show();
    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        if (dialog == null) {
            return;
        }
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
        dialog.dismiss();
    }
}
