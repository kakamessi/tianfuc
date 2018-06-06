package com.chico.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author: 陈国权
 * @date: 2018/4/24 下午11:04
 * @describe:
 */

public abstract class BaseDialog extends DialogFragment implements DialogInterface.OnKeyListener, DialogInterface.OnCancelListener {


    protected Dialog dialog;

    protected DialogListener mDialogListener;

    private DismissListener mDismissListener;

    protected abstract int getContentViewId();

    protected abstract void initDialogFragment(View view);

    protected boolean showBottomAnim() {
        return true;
    }

    protected boolean setCancelable() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(getContentViewId(), container);
        initDialogFragment(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog = getDialog();
        if (dialog != null) {
            dialog.setOnKeyListener(this);
            dialog.setOnCancelListener(this);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(setCancelable());
            if (showBottomAnim()) {
                Window window = dialog.getWindow();
                window.setWindowAnimations(R.style.DialogBottomStyle);
                WindowManager.LayoutParams wl = window.getAttributes();
                wl.x = 0;
                wl.y = getActivity().getWindowManager().getDefaultDisplay().getHeight();
                wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
                wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                dialog.onWindowAttributesChanged(wl);
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dialog = null;
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && dialog != null) {
            dialog.dismiss();
            if (mDismissListener != null) {
                mDismissListener.onDismiss();
            }
        }
        return false;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (mDismissListener != null) {
            mDismissListener.onDismiss();
        }
    }

    public void setDismissListener(DismissListener listener) {
        mDismissListener = listener;
    }

    public void setDialogListener(DialogListener listener) {
        this.mDialogListener = listener;
    }


}
