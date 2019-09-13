package com.androidwind.gank.base;

import android.os.Bundle;

import com.androidwind.androidquick.ui.mvp.BasePresenter;
import com.androidwind.gank.MyApplication;
import com.androidwind.gank.injector.component.DaggerFragmentComponent;
import com.androidwind.gank.injector.component.FragmentComponent;
import com.androidwind.gank.injector.module.FragmentModule;

import javax.inject.Inject;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class BaseTFragment<T extends BasePresenter> extends BaseFFragment {
    @Inject
    public T mPresenter;
    protected String TAG = "BaseTFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dagger2
        initInjector();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    protected void initInjector() {

    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

}
