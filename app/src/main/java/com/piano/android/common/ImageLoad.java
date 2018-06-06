package com.piano.android.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.piano.android.R;
import com.piano.android.common.glide.BlurTransformation;
import com.piano.android.common.glide.CornersTransform;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author 陈国权
 * @date 2018/3/5 0005
 * @describe 图片加载器
 */

public class ImageLoad {

    /**
     * 加载图片
     *
     * @param context 上下文
     * @param id      图片id
     * @param view    图片控件
     */
    public static void loadImage(Context context, int id, ImageView view) {
        GlideApp
                .with(context)
                .load(id)
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    /**
     * 加载图片
     *
     * @param context 上下文
     * @param url     图片链接
     * @param view    图片显示控件
     */
    public static void loadImage(Context context, String url, ImageView view) {
        loadImage(context, url, view, -1, -1);
    }


    /**
     * 加载图片
     *
     * @param context     上下文
     * @param url         图片链接
     * @param view        图片显示控件
     * @param placeholder 默认占位图片
     * @param error       加载失败占位图片
     */
    public static void loadImage(Context context, String url, ImageView view, int placeholder, int error) {
        GlideApp
                .with(context)
                .load(url)
                .transition(withCrossFade())
                .placeholder(placeholder)
                .error(error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    /**
     * 加载指定大小的图片
     *
     * @param context 上下文
     * @param url     图片链接
     * @param width   图片显示控件
     * @param height  默认占位图片
     * @param view    加载失败占位图片
     */
    public static void loadImage(Context context, String url, int width, int height, ImageView view) {
        loadImage(context, url, width, height, view, -1, -1);
    }

    /**
     * 加载指定大小的图片
     *
     * @param context     上下文
     * @param url         图片链接
     * @param width       图片显示控件
     * @param height      默认占位图片
     * @param view        加载失败占位图片
     * @param placeholder 默认占位图片
     * @param errorholder 加载失败占位图片
     */
    public static void loadImage(Context context, String url, int width, int height, ImageView view, int placeholder, int errorholder) {
        GlideApp
                .with(context)
                .load(url)
                .transition(withCrossFade())
                .placeholder(placeholder)
                .error(errorholder)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }


    /**
     * 加载圆角图片
     *
     * @param context
     * @param url
     * @param view
     */
    public static void loadImageRounder(Context context, String url, ImageView view) {
        loadImageRounder(context, url, view, 10);
    }


    /**
     * 加载指定圆角图片
     *
     * @param context 上下文
     * @param url     图片链接
     * @param view    图片控件
     * @param radius  圆角角度
     */
    public static void loadImageRounder(Context context, String url, ImageView view, int radius) {
        GlideApp.with(context).load(url)
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transforms(new CenterCrop(), new RoundedCorners(radius))
                .placeholder(R.drawable.default_cover)
                .error(R.drawable.default_cover)
                .into(view);
    }


    public static void loadImageRounderNoCrop(Context context, String url, ImageView view) {
        GlideApp.with(context).load(url)
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transforms(new RoundedCorners(10))
                .into(view);
    }


    /**
     * 加载圆形图片
     *
     * @param context 上下文
     * @param url     图片链接
     * @param view    图片控件
     */
    public static void loadImageCircle(Context context, String url, ImageView view) {
        GlideApp.with(context).load(url)
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transforms(new CenterCrop(), new CircleCrop())
                .into(view);
    }


    /**
     * 加载圆形图片
     *
     * @param context 上下文
     * @param id      本地资源
     * @param view    图片控件
     */
    public static void loadImageCircle(Context context, Object id, ImageView view) {
        GlideApp.with(context).load(id)
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transforms(new CenterCrop(), new CircleCrop())
                .into(view);
    }

    /**
     * 加载圆形图片
     *
     * @param context 上下文
     * @param url     图片链接
     * @param view    图片控件
     */
    public static void loadImageCircle(Context context, String url, ImageView view, int placeholder) {
        GlideApp.with(context).load(url)
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transforms(new CenterCrop(), new CircleCrop())
                .placeholder(placeholder)
                .error(placeholder)
                .into(view);
    }

    /**
     * 加载Bitmap图片
     *
     * @param context 上下文
     * @param bitmap  bitmap图片
     * @param view    图片控件
     */
    public static void loadImageBitmap(Context context, Bitmap bitmap, ImageView view) {
        GlideApp.with(context).asBitmap().load(bitmap).into(view);
    }

    /**
     * 加载Gif图片
     *
     * @param context 上下文
     * @param url     图片链接
     * @param view    图片控件
     */
    public static void loadImagGif(Context context, String url, ImageView view) {
        GlideApp.with(context).asGif().load(url).into(view);
    }

    public static void loadImagBlur(Context context, String url, ImageView view) {
        GlideApp
                .with(context)
                .load(url)
                .transition(withCrossFade())
                .override(100, 100)
                .transform(new BlurTransformation(context, 25))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }


    public static void loadImageBlurRound(Context context, String url, ImageView view) {
        GlideApp
                .with(context)
                .load(url)
                .transition(withCrossFade())
                .override(100, 100)
                .transforms(new CornersTransform(context, 10), new BlurTransformation(context, 25))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }
}
