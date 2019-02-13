package com.androidwind.gank.injector.component;

import android.app.Activity;
import android.content.Context;

import com.androidwind.gank.gankdaily.GankDailyActivity;
import com.androidwind.gank.injector.ActivityScope;
import com.androidwind.gank.injector.module.ActivityModule;

import dagger.Component;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {

    Activity getActivity();
    Context getContext();

    void inject(GankDailyActivity gankDailyActivity);
}
