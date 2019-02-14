package com.androidwind.gank.injector.component;

import com.androidwind.gank.gankcatalogue.GankItemFragment;
import com.androidwind.gank.ganklist.GankListFragment;
import com.androidwind.gank.ganksearch.GankSearchFragment;
import com.androidwind.gank.injector.FragmentScope;
import com.androidwind.gank.injector.module.FragmentModule;

import dagger.Component;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(GankListFragment gankListFragment);
    void inject(GankItemFragment gankItemFragment);
    void inject(GankSearchFragment gankSearchFragment);
}
