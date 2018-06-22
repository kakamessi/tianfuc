package com.piano.android;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.chico.common.CacheUtils;
import com.chico.common.PreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.piano.android.bean.user.UserBean;
import com.piano.android.common.Constant;
import com.piano.android.di.component.AppComponent;
import com.piano.android.di.component.DaggerAppComponent;
import com.piano.android.di.module.ApiServiceModule;
import com.piano.android.di.module.AppModule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 陈国权
 * @date: 2018/4/21 下午7:48
 * @describe:
 */

public class App extends Application {

    public static App mInstance;

    private AppComponent appComponent;

    public static synchronized App getInstance() {
        return mInstance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MultiDex.install(this);
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiServiceModule(new ApiServiceModule())
                .build();

       CacheUtils.getFilePath(this,Constant.CACHE_FILE_DIR);
    }


    /**
     * 检查是否登陆
     *
     * @return
     */
    public boolean checkLogin() {
        String token = PreferencesUtils.getString(this, Constant.PRE_TOKEN);
        if (TextUtils.isEmpty(token)) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 保存token
     *
     * @param token
     */
    public void setToken(String token) {
        PreferencesUtils.putString(this, Constant.PRE_TOKEN, token);
    }

    /**
     * 获取token
     *
     * @return
     */
    public String getToke() {
        return PreferencesUtils.getString(this, Constant.PRE_TOKEN);
    }

    /**
     * 保存用户信息
     *
     * @param bean
     */
    public void setUserInfo(UserBean bean) {
        if (bean != null) {
            String json = new Gson().toJson(bean, UserBean.class);
            PreferencesUtils.putString(this, Constant.PRE_USER, json);
        } else {
            PreferencesUtils.putString(this, Constant.PRE_USER, "");
        }
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserBean getUserInfo() {
        String json = PreferencesUtils.getString(this, Constant.PRE_USER);
        return new Gson().fromJson(json, UserBean.class);
    }

    public void setSearchWord(String word) {
        if (TextUtils.isEmpty(word)) {
            return;
        }

        List<String> list = getSearchWord();
        for (String keyword : list) {
            if (keyword.equals(word)) {
                return;
            }
        }
        list.add(word);

        String json = new Gson().toJson(list);
        PreferencesUtils.putString(this, Constant.PRE_KEYWORD, json);
    }

    public List<String> getSearchWord() {
        String json = PreferencesUtils.getString(this, Constant.PRE_KEYWORD);
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        } else {
            return new Gson().fromJson(json, new TypeToken<List<String>>() {
            }.getType());
        }
    }

    public void removeSearchWord(int position) {
        List<String> list = getSearchWord();
        if (list != null && list.size() > 0 && list.size() > position) {
            list.remove(position);
        }
        String json = new Gson().toJson(list);
        PreferencesUtils.putString(this, Constant.PRE_KEYWORD, json);
    }
}
