package com.chico.dialog;

import android.view.View;
import android.widget.TextView;


/**
 * @author: 陈国权
 * @date: 2018/4/24 下午11:12
 * @describe: 性别对话框
 */

public class GenderDialog extends BaseDialog implements View.OnClickListener {

    public static final int GENDER_MALE = 0;
    public static final int GENDER_FEMALE = 1;

    private TextView tvBoy;
    private TextView tvGirl;
    private TextView tvCancel;

    public static GenderDialog newInstance() {
        GenderDialog dialog = new GenderDialog();
        return dialog;
    }


    @Override
    protected int getContentViewId() {
        return R.layout.dialog_gender;
    }

    @Override
    protected void initDialogFragment(View view) {
        tvBoy = view.findViewById(R.id.tv_boy);
        tvGirl = view.findViewById(R.id.tv_girl);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvBoy.setOnClickListener(this);
        tvGirl.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_boy) {
            if (mDialogListener != null) {
                mDialogListener.onDialogClick(DialogConstant.DIALOG_GENDER, GENDER_MALE);
            }
        } else if (i == R.id.tv_girl) {
            if (mDialogListener != null) {
                mDialogListener.onDialogClick(DialogConstant.DIALOG_GENDER, GENDER_FEMALE);
            }
        }
        dialog.dismiss();
    }
}
