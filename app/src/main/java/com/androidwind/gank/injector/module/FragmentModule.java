package com.androidwind.gank.injector.module;

import android.app.Activity;
import android.support.v4.app.Fragment;
import com.androidwind.gank.injector.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
