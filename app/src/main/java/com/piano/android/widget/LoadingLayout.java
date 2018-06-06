package com.piano.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.piano.android.R;

/**
 * @author: 陈国权
 * @date: 2018/4/3 0003 下午 15:51
 * @description:
 */

public class LoadingLayout extends FrameLayout {
    private Context mContext;

    private View mMainPage;
    private View errorPage;
    private View emptyPage;

    private ImageView errorImg;
    private TextView errorText;

    private ImageView emptyImg;
    private TextView emptyText;

    private int mErrorResId;
    private int mEmptyResId;
    private String mErrorStr;
    private String mEmptyStr;
    private String mLoadingStr;

    private AnimationDrawable animationDrawable;

    public LoadingLayout(Context context) {
        this(context, null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.LoadingLayout, 0, 0);
            mErrorResId = a.getResourceId(R.styleable.LoadingLayout_loading_layout_error_img, R.drawable.icon_error);
            mErrorStr = a.getString(R.styleable.LoadingLayout_loading_layout_error_text);
            mEmptyResId = a.getResourceId(R.styleable.LoadingLayout_loading_layout_empty_img, R.drawable.icon_noresult);
            mEmptyStr = a.getString(R.styleable.LoadingLayout_loading_layout_empty_text);
            mLoadingStr = a.getString(R.styleable.LoadingLayout_loading_layout_loading_text);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();
        if (childCount == 0) {
            throw new IllegalStateException("LoadingLayout can host only one direct child");
        }

        if (childCount > 1) {
            throw new IllegalStateException("LoadingLayout can host only one direct child");
        }

        mMainPage = getChildAt(0);
        mMainPage.setVisibility(VISIBLE);

        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        errorPage = inflater.inflate(R.layout.view_layout_error, null);
        emptyPage = inflater.inflate(R.layout.view_layout_empty, null);


        errorImg = errorPage.findViewById(R.id.error_img);
        errorText = errorPage.findViewById(R.id.error_text);

        emptyImg = emptyPage.findViewById(R.id.empty_img);
        emptyText = emptyPage.findViewById(R.id.empty_text);

        if (mErrorResId != 0) {
            errorImg.setImageResource(mErrorResId);
        }

        if (mErrorStr != null) {
            errorText.setText(mErrorStr);
        }

        if (mEmptyResId != 0) {
            emptyImg.setImageResource(mEmptyResId);
        }

        if (mEmptyStr != null) {
            emptyText.setText(mEmptyStr);
        }

        this.addView(errorPage);
        this.addView(emptyPage);

        errorPage.setVisibility(GONE);
        emptyPage.setVisibility(GONE);
    }

    /**
     * 设置错误布局图片资源
     *
     * @param errorResId
     */
    public void setErrorImg(int errorResId) {
        if (errorResId != 0) {
            errorImg.setImageResource(errorResId);
            this.mErrorResId = errorResId;
        }
    }

    /**
     * 设置空布局图片资源
     *
     * @param emptyResId
     */
    public void setEmptyImg(int emptyResId) {
        if (emptyResId != 0) {
            emptyImg.setImageResource(emptyResId);
            this.mEmptyResId = emptyResId;
        }
    }

    /**
     * 设置错误布局文字
     *
     * @param errorStr
     */
    public void setErrorText(String errorStr) {
        if (!TextUtils.isEmpty(errorStr)) {
            errorText.setText(errorStr);
            this.mErrorStr = errorStr;
        }
    }

    /**
     * 设置错误布局文字
     *
     * @param resId
     */
    public void setErrorText(int resId) {
        String string = mContext.getResources().getString(resId);
        if (!TextUtils.isEmpty(string)) {
            errorText.setText(string);
            this.mErrorStr = string;
        }
    }

    /**
     * 设置空布局文字
     *
     * @param emptyStr
     */
    public void setEmptyText(String emptyStr) {
        if (!TextUtils.isEmpty(emptyStr)) {
            emptyText.setText(emptyStr);
            this.mEmptyStr = emptyStr;
        }
    }

    /**
     * 设置空布局文字
     *
     * @param resId
     */
    public void setEmptyText(int resId) {
        String string = mContext.getResources().getString(resId);
        if (!TextUtils.isEmpty(string)) {
            emptyText.setText(string);
            this.mEmptyStr = string;
        }
    }


    /**
     * 显示错误布局
     */
    public void showError() {
        stopLoadingAnim();
        errorPage.setVisibility(VISIBLE);
        emptyPage.setVisibility(GONE);
        mMainPage.setVisibility(GONE);
    }

    /**
     * 显示空布局
     */
    public void showEmpty() {
        stopLoadingAnim();
        errorPage.setVisibility(GONE);
        emptyPage.setVisibility(VISIBLE);
        mMainPage.setVisibility(GONE);
    }

    /**
     * 显示内容布局
     */
    public void showMain() {
        stopLoadingAnim();
        errorPage.setVisibility(GONE);
        emptyPage.setVisibility(GONE);
        mMainPage.setVisibility(VISIBLE);

    }

    private void startLoadingAnim() {
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }

    private void stopLoadingAnim() {
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }

}
