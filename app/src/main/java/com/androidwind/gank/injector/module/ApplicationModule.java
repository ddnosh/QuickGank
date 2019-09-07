package com.androidwind.gank.injector.module;

import android.content.Context;

import com.androidwind.androidquick.module.retrofit.RetrofitManager;
import com.androidwind.gank.MyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
@Module
public class ApplicationModule {
    private final Context context;

    public ApplicationModule(MyApplication context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return context;
    }

    @Provides
    @Singleton
    RetrofitManager provideRetrofitManager() {
        RetrofitManager retrofitManager = new RetrofitManager();
        retrofitManager.initBaseUrl("http://gank.io/api/");
        return retrofitManager;
    }
}
