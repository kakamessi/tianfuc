package com.piano.android.common.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chico.common.DensityUtils;
import com.chico.common.ScreenUtils;
import com.chico.dialog.BaseDialog;
import com.piano.android.R;
import com.piano.android.common.Constant;
import com.piano.android.common.ImageLoad;

/**
 * @author: 陈国权
 * @date: 2018/5/8 下午11:31
 * @describe:
 */

public class AdvertDialogFragment extends BaseDialog {

    private ImageView ivAdvert;

    private Dialog dialog;
    private String url;


    public static AdvertDialogFragment newInstance(String url) {
        AdvertDialogFragment fragment = new AdvertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.INTENT_URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString(Constant.INTENT_URL);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.dialog_advert;
    }

    @Override
    protected void initDialogFragment(View view) {
        ivAdvert = view.findViewById(R.id.iv_advert);

        int width = ScreenUtils.getScreenWidth(getActivity()) - DensityUtils.dp2px(getActivity(), 100);
        ivAdvert.setLayoutParams(new RelativeLayout.LayoutParams(width, width * 650 / 520));
        ImageLoad.loadImage(getActivity(), url, ivAdvert);

        view.findViewById(R.id.iv_clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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
            window.setWindowAnimations(com.chico.dialog.R.style.DialogScaleAnim);
            WindowManager.LayoutParams wl = window.getAttributes();
            window.setAttributes(wl);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dialog = null;
    }
}
