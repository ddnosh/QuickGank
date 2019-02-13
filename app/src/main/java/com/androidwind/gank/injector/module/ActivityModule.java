package com.androidwind.gank.injector.module;

import android.app.Activity;
import com.androidwind.gank.injector.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {this.mActivity = activity;}

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
