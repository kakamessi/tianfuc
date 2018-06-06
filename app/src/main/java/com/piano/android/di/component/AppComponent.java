package com.piano.android.di.component;


import com.piano.android.di.module.ApiServiceModule;
import com.piano.android.di.module.AppModule;
import com.piano.android.http.ApiService;

import javax.inject.Singleton;
import dagger.Component;

/**
 *
 * @author 陈国权
 * @date 2018/3/2 0002
 */

@Component(modules = {AppModule.class, ApiServiceModule.class})
@Singleton
public interface AppComponent {
    ApiService getService();
}
