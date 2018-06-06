package com.piano.android.widget.banner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.banner.widget.Banner.BaseIndicatorBanner;
import com.piano.android.BuildConfig;
import com.piano.android.bean.songbook.BannerBean;
import com.piano.android.common.ImageLoad;

/**
 * @author: 陈国权
 * @date: 2018/4/21 下午10:41
 * @describe:
 */

public class SongbookRecommendBanner extends BaseIndicatorBanner<BannerBean, SongbookRecommendBanner> {
    private ColorDrawable colorDrawable;

    public SongbookRecommendBanner(Context context) {
        this(context, null, 0);
    }

    public SongbookRecommendBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SongbookRecommendBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        colorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    @Override
    public void onTitleSlect(TextView textView, int position) {
    }

    @Override
    public View onCreateItemView(int position) {
        ImageView bannerImage = new ImageView(mContext);
        bannerImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 170));
        BannerBean bean = mDatas.get(position);
        if (bean != null) {
            if (!TextUtils.isEmpty(bean.getImg())) {
                ImageLoad.loadImage(mContext, BuildConfig.BASE_IMAGE_URL + bean.getImg(), bannerImage);
            } else {
                bannerImage.setImageDrawable(colorDrawable);
            }
        }
        return bannerImage;
    }
}
