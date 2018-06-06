package com.piano.android.ui.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chico.common.DensityUtils;
import com.chico.common.ScreenUtils;
import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.piano.android.BuildConfig;
import com.piano.android.R;
import com.piano.android.bean.MultipleItem;
import com.piano.android.bean.songbook.AlbumBean;
import com.piano.android.bean.songbook.BannerBean;
import com.piano.android.bean.songbook.SingleBean;
import com.piano.android.common.Constant;
import com.piano.android.common.ImageLoad;
import com.piano.android.ui.activity.AlbumDetailActivity;
import com.piano.android.ui.activity.CourseDetailActivity;
import com.piano.android.ui.activity.HtmlActivity;
import com.piano.android.ui.activity.SingleDetailActivity;
import com.piano.android.widget.banner.SongbookRecommendBanner;

import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/4/21 下午9:52
 * @describe:
 */

public class SongbookRecommendAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    public SongbookRecommendAdapter(List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.SONGBOOK_BANNER, R.layout.item_songbook_recommend_banner);
        addItemType(MultipleItem.SONGBOOK_ALBUM_TITLE, R.layout.item_songbook_recommend_title);
        addItemType(MultipleItem.SONGBOOK_ALBUM, R.layout.item_songbook_recommend_album);
        addItemType(MultipleItem.SONGBOOK_SINGLE_TITLE, R.layout.item_songbook_recommend_title);
        addItemType(MultipleItem.SONGBOOK_SINGLE, R.layout.item_songbook_recommend_single);
        addItemType(MultipleItem.SONGBOOK_DIVIDER, R.layout.item_songbook_divider);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (item.getItemType()) {
            case MultipleItem.SONGBOOK_BANNER:
                setBannerView(helper, item);
                break;
            case MultipleItem.SONGBOOK_ALBUM_TITLE:
                helper.setText(R.id.tv_name, (String) item.getData());
                break;
            case MultipleItem.SONGBOOK_ALBUM:
                setAlbumView(helper, item);
                break;
            case MultipleItem.SONGBOOK_SINGLE_TITLE:
                helper.setText(R.id.tv_name, (String) item.getData());
                break;
            case MultipleItem.SONGBOOK_SINGLE:
                setSingleView(helper, item);
                break;
            default:
                break;

        }
    }


    /**
     * 设置轮播数据
     *
     * @param helper
     * @param item
     */
    private void setBannerView(BaseViewHolder helper, MultipleItem item) {
        SongbookRecommendBanner banner = helper.getView(R.id.bv_banner);
        final List<BannerBean> list = (List<BannerBean>) item.getData();
        if (list != null) {
            banner.setSource(list).startScroll();
            banner.setOnItemClickL(new BaseBanner.OnItemClickL() {
                @Override
                public void onItemClick(int position) {
                    BannerBean bean = list.get(position);
                    if ("song".equals(bean.getUrlType())) {
                        Intent intent = new Intent(mContext, SingleDetailActivity.class);
                        intent.putExtra(Constant.INTENT_ID, Long.parseLong(bean.getUrl()));
                        mContext.startActivity(intent);
                    } else if ("songset".equals(bean.getUrlType())) {
                        Intent intent = new Intent(mContext, AlbumDetailActivity.class);
                        intent.putExtra(Constant.INTENT_ID, Long.parseLong(bean.getUrl()));
                        mContext.startActivity(intent);
                    } else if ("teach".equals(bean.getUrlType())) {
                        Intent intent = new Intent(mContext, CourseDetailActivity.class);
                        intent.putExtra(Constant.INTENT_ID, Long.parseLong(bean.getUrl()));
                        mContext.startActivity(intent);
                    } else if ("url".equals(bean.getUrlType())) {
                        Intent intent = new Intent(mContext, HtmlActivity.class);
                        intent.putExtra(Constant.INTENT_URL, bean.getUrl());
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    private void setAlbumView(BaseViewHolder helper, MultipleItem item) {
        AlbumBean bean = (AlbumBean) item.getData();
        if (bean != null) {
            int width = ScreenUtils.getScreenWidth(mContext) - DensityUtils.dp2px(mContext, 20);
            ImageView cover = helper.getView(R.id.iv_cover);
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(width / 3, width / 3);
            cover.setLayoutParams(params);
            ImageLoad.loadImageRounder(mContext, BuildConfig.BASE_IMAGE_URL + bean.getImg(), cover);

            helper.setText(R.id.tv_title, bean.getName())
                    .setText(R.id.tv_content, bean.getSubhead())
                    .setText(R.id.tv_type, mContext.getResources().getStringArray(R.array.sort_difficult)[bean.getHard()]);

            if (bean.getHard() <= 2) {
                helper.setBackgroundRes(R.id.tv_type, R.drawable.icon_level_junior);
            } else if (bean.getHard() == 3) {
                helper.setBackgroundRes(R.id.tv_type, R.drawable.icon_level_advanced);
            } else {
                helper.setBackgroundRes(R.id.tv_type, R.drawable.icon_level_challenge);
            }

        }

    }

    /**
     * 设置单曲
     *
     * @param helper
     * @param item
     */
    private void setSingleView(BaseViewHolder helper, MultipleItem item) {
        SingleBean bean = (SingleBean) item.getData();
        if (bean != null) {
            helper.setText(R.id.tv_name, bean.getName())
                    .setText(R.id.tv_type, bean.getHardName())
                    .setText(R.id.tv_author, bean.getAuthor())
                    .setText(R.id.tv_uploader, String.format(mContext.getString(R.string.uploader), bean.getUploadUser()));
        }
    }
}
