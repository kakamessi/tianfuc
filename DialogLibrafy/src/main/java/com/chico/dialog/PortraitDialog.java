package com.chico.dialog;

import android.view.View;
import android.widget.TextView;

/**
 * @author: 陈国权
 * @date: 2018/4/24 下午11:39
 * @describe: 图片来源选择
 */

public class PortraitDialog extends BaseDialog implements View.OnClickListener {

    private TextView tvCamera;
    private TextView tvAlbum;
    private TextView tvCancel;

    public static PortraitDialog newInstance() {
        PortraitDialog dialog = new PortraitDialog();
        return dialog;
    }


    @Override
    protected int getContentViewId() {
        return R.layout.dialog_portrait;
    }

    @Override
    protected void initDialogFragment(View view) {

        tvCamera = view.findViewById(R.id.tv_camera);
        tvAlbum = view.findViewById(R.id.tv_album);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvCamera.setOnClickListener(this);
        tvAlbum.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_camera) {
            if (mDialogListener != null) {
                mDialogListener.onDialogClick(DialogConstant.DIALOG_CAMERA, null);
            }
        } else if (i == R.id.tv_album) {
            if (mDialogListener != null) {
                mDialogListener.onDialogClick(DialogConstant.DIALOG_ALBUM, null);
            }
        }
        dialog.dismiss();
    }
}
