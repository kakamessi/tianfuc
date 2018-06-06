package com.piano.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chico.common.DensityUtils;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.piano.android.R;
import com.piano.android.bean.songbook.SearchWordBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Android TagView Widget
 */
public class TagView extends FlexboxLayout {


    public static final float DEFAULT_TAG_MARGIN = 5;
    public static final float DEFAULT_TAG_TEXT_PADDING_LEFT = 10;
    public static final float DEFAULT_TAG_TEXT_PADDING_TOP = 10;
    public static final float DEFAULT_TAG_TEXT_PADDING_RIGHT = 10;
    public static final float DEFAULT_TAG_TEXT_PADDING_BOTTOM = 10;

    public static final float IMAGE_SIZE = 26;

    public static final float DEFAULT_TAG_TEXT_SIZE = 14f;
    public static int DEFAULT_TAG_TEXT_COLOR = Color.parseColor("#101010");
    public static int DEFAULT_TAG_BACKGROUND = R.drawable.btn_checked_default;

    private int margin; //间距

    private int textPaddingLeft;   //文字左侧padding
    private int textPaddingRight;  //文字右侧padding
    private int textPaddingTop;    //文字上侧padding
    private int texPaddingBottom;  //文字下册padding

    private List<SearchWordBean> mTags = new ArrayList<>();
    private LayoutInflater mInflater;

    public TagView(Context context) {
        super(context, null);
        init(context, null, 0, 0);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public TagView(Context ctx, AttributeSet attrs, int defStyle) {
        super(ctx, attrs, defStyle);
        init(ctx, attrs, defStyle, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle, int defStyleRes) {

        setFlexDirection(FlexDirection.ROW);
        setFlexWrap(FlexWrap.WRAP);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.TagView, defStyle, defStyleRes);
        this.margin = (int) typeArray.getDimension(R.styleable.TagView_margin, DensityUtils.dp2px(this.getContext(), DEFAULT_TAG_MARGIN));
        this.textPaddingLeft = (int) typeArray.getDimension(R.styleable.TagView_textPaddingLeft, DensityUtils.dp2px(this.getContext(), DEFAULT_TAG_TEXT_PADDING_LEFT));
        this.textPaddingRight = (int) typeArray.getDimension(R.styleable.TagView_textPaddingRight, DensityUtils.dp2px(this.getContext(), DEFAULT_TAG_TEXT_PADDING_RIGHT));
        this.textPaddingTop = (int) typeArray.getDimension(R.styleable.TagView_textPaddingTop, DensityUtils.dp2px(this.getContext(), DEFAULT_TAG_TEXT_PADDING_TOP));
        this.texPaddingBottom = (int) typeArray.getDimension(R.styleable.TagView_textPaddingBottom, DensityUtils.dp2px(this.getContext(), DEFAULT_TAG_TEXT_PADDING_BOTTOM));

        typeArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    private void drawTags() {
        if (getVisibility() != View.VISIBLE) return;
        removeAllViews();
        int listIndex = 1;
        for (SearchWordBean item : mTags) {
            final int position = listIndex - 1;
            final SearchWordBean tag = item;


            View tagLayout = mInflater.inflate(R.layout.item_tag, null);
            tagLayout.setId(listIndex);
            tagLayout.setBackgroundResource(DEFAULT_TAG_BACKGROUND);
            tagLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onTagItemClick(mTags.get(position));
                    }
                }
            });

            TextView tagView = tagLayout.findViewById(R.id.tv_tag_name);
            tagView.setTextColor(DEFAULT_TAG_TEXT_COLOR);
            tagView.setText(tag.getName());
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tagView.getLayoutParams();
            params.setMargins(textPaddingLeft, textPaddingTop, textPaddingRight, texPaddingBottom);
            tagView.setLayoutParams(params);
            tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TAG_TEXT_SIZE);
            LayoutParams tagParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tagParams.setMargins(margin, margin, margin, margin);
            addView(tagLayout, tagParams);
            listIndex++;
        }
    }

    public void setTags(List<SearchWordBean> tagList) {
        if (tagList == null || tagList.size() <= 0) return;
        mTags.addAll(tagList);
        drawTags();
    }

    private TagClickListener listener;

    public void setTagClickListener(TagClickListener listener) {
        this.listener = listener;
    }

    public interface TagClickListener {
        void onTagItemClick(SearchWordBean bean);
    }

}
