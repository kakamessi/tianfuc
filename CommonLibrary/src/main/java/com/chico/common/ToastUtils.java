package com.chico.common;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 * @author 陈国权
 * @date 2018/3/7 0007
 * @describe 提示
 */

public class ToastUtils {
    public static void showShortToast(Context mContext, String msg) {
        showToast(mContext, msg, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(Context mContext, int strRes) {
        showShortToast(mContext, mContext.getResources().getString(strRes));
    }

    public static void showLongToast(Context mContext, String msg) {
        showToast(mContext, msg, Toast.LENGTH_LONG);
    }

    public static void showLongToast(Context mContext, int strRes) {
        showLongToast(mContext, mContext.getResources().getString(strRes));
    }

    public static void showToast(Context context, String msg, int duration) {
        showToast(context, msg, duration, Gravity.CENTER);
    }

    public static void showToast(Context context, String msg, int duration, int gravity) {
        Toast toast = Toast.makeText(context, msg, duration);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    public static void showCustomToast(Context context, int layoutId) {
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
