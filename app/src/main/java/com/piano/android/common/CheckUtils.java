package com.piano.android.common;

import android.text.TextUtils;

import com.chico.common.ToastUtils;
import com.piano.android.App;

/**
 * @author: 陈国权
 * @date: 2018/4/27 下午10:22
 * @describe: 验证
 */

public class CheckUtils {


    /**
     * 验证登陆信息
     *
     * @param mobile
     * @param password
     * @return
     */
    public static boolean checkLoginInfo(String mobile, String password) {

        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.showShortToast(App.getInstance(), "手机号不能为空");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShortToast(App.getInstance(), "密码不能为空");
            return false;
        }

        return true;

    }

    /**
     * 验证发送短信手机号码
     *
     * @param mobile
     * @return
     */
    public static boolean checkSmsMobile(String mobile) {

        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.showShortToast(App.getInstance(), "手机号不能为空");
            return false;
        }

        return true;
    }

    /**
     * 验证注册信息
     *
     * @param mobile
     * @param smsCode
     * @param password
     * @param passwordAgain
     * @return
     */
    public static boolean checkRegister(String mobile, String smsCode, String password, String passwordAgain) {

        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.showShortToast(App.getInstance(), "手机号不能为空");
            return false;
        }

        if (TextUtils.isEmpty(smsCode)) {
            ToastUtils.showShortToast(App.getInstance(), "验证码不能为空");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShortToast(App.getInstance(), "密码不能为空");
            return false;
        }

        if (TextUtils.isEmpty(passwordAgain)) {
            ToastUtils.showShortToast(App.getInstance(), "确认密码不能为空");
            return false;
        }

        if (!password.equals(passwordAgain)) {
            ToastUtils.showShortToast(App.getInstance(), "两次密码输入不一致");
            return false;
        }
        return true;
    }

    /**
     * 验证昵称是否唯恐
     *
     * @param nickname
     * @return
     */
    public static boolean checkNickname(String nickname) {
        if (TextUtils.isEmpty(nickname)) {
            ToastUtils.showShortToast(App.getInstance(), "昵称不能为空");
            return false;
        }
        return true;
    }

    /**
     * 验证修改密码信息
     *
     * @param password
     * @param smsCode
     * @return
     */
    public static boolean checkUpdatePassword(String password, String smsCode) {
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShortToast(App.getInstance(), "密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(smsCode)) {
            ToastUtils.showShortToast(App.getInstance(), "验证码不能为空");
            return false;
        }
        return true;
    }

    /**
     * 验证反馈信息
     *
     * @param feedbackType
     * @param feedbackContent
     * @return
     */
    public static boolean checkFeedback(String feedbackType, String feedbackContent) {

        if (TextUtils.isEmpty(feedbackType)) {
            ToastUtils.showShortToast(App.getInstance(), "反馈类型不能为空");
            return false;
        }
        if (TextUtils.isEmpty(feedbackContent)) {
            ToastUtils.showShortToast(App.getInstance(), "反馈内容不能为空");
            return false;
        }
        return true;
    }
}
