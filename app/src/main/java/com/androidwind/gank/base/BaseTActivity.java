package com.androidwind.gank.base;

import android.os.Bundle;

import com.androidwind.gank.MyApplication;
import com.androidwind.gank.injector.component.ActivityComponent;
import com.androidwind.gank.injector.component.DaggerActivityComponent;
import com.androidwind.gank.injector.module.ActivityModule;

import javax.inject.Inject;

import la.xiong.androidquick.tool.LogUtil;

/**
 * @Description: 第二种类型的BaseActivity
 * @Detail: with: mvp, dagger2, rxjava, systembartint, permission
 * @Author: ddnosh
 * @Website http://blog.csdn.net/ddnosh
 */
public abstract class BaseTActivity<T extends BasePresenter> extends BaseFActivity {

    protected static String TAG = "BaseTActivity";

    @Inject
    public T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtil.i(TAG, "onCreate:" + getClass().getSimpleName());
        //dagger2
        initInjector();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
        LogUtil.i(TAG, "onDestroy:" + getClass().getSimpleName());
    }

    protected void initInjector() {

    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .applicationComponent(MyApplication.getInstance().getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

}
