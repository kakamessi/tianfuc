package com.chico.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author: 陈国权
 * @date: 2018/4/25 上午12:24
 * @describe: 确认对话框
 */
public class ConfirmDialog extends BaseDialog implements View.OnClickListener {

    private static final String MSG = "msg";

    private Dialog dialog;
    private TextView msgText;
    private Button cancelBtn, okBtn;

    private String msg;

    public static ConfirmDialog newInstance(String msg) {
        ConfirmDialog fragment = new ConfirmDialog();
        Bundle bundle = new Bundle();
        bundle.putString(MSG, msg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        msg = getArguments().getString(MSG);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.dialog_confirm;
    }

    @Override
    protected void initDialogFragment(View view) {
        cancelBtn = view.findViewById(R.id.btn_cancel);
        okBtn = view.findViewById(R.id.btn_confirm);
        msgText = view.findViewById(R.id.tv_msg);
        cancelBtn.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        msgText.setText(msg);
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            window.setWindowAnimations(R.style.DialogScaleAnim);
            WindowManager.LayoutParams wl = window.getAttributes();
            window.setAttributes(wl);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dialog = null;
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_confirm) {
            if (mDialogListener != null) {
                mDialogListener.onDialogClick(DialogConstant.DIALOG_CONFIRM, null);
            }
        }
        dialog.dismiss();
    }

}
