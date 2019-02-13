package com.androidwind.gank;

import android.app.Activity;

import com.androidwind.gank.crash.CrashHandler;
import com.androidwind.gank.injector.component.ApplicationComponent;
import com.androidwind.gank.injector.component.DaggerApplicationComponent;
import com.androidwind.gank.injector.module.ApplicationModule;
import com.androidwind.gank.ui.AQActivityLifecycleCallbacks;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import la.xiong.androidquick.network.retrofit.RetrofitManager;
import la.xiong.androidquick.tool.SpUtil;
import la.xiong.androidquick.tool.ToastUtil;
import spa.lyh.cn.statusbarlightmode.ImmersionConfiguration;
import spa.lyh.cn.statusbarlightmode.ImmersionMode;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class MyApplication extends android.support.multidex.MultiDexApplication {

    private static MyApplication sINSTANCE;

    private AQActivityLifecycleCallbacks lifecycleCallback;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        //get application
        if (sINSTANCE == null) {
            sINSTANCE = this;
        }
        //lifecyclecallback
        lifecycleCallback = new AQActivityLifecycleCallbacks();
        registerActivityLifecycleCallbacks(lifecycleCallback);
        //init ToastUtil
        ToastUtil.register(this);
        //init SpUtil
        SpUtil.init(this);
        //init retrofit url
        RetrofitManager.initBaseUrl("http://gank.io/api/");
        //init immersion
        ImmersionConfiguration configuration = new ImmersionConfiguration.Builder(this)
                .enableImmersionMode(ImmersionConfiguration.ENABLE)
                .setColor(R.color.base_bg)//default color
                .build();
        ImmersionMode.getInstance().init(configuration);
        //init stetho
        Stetho.initializeWithDefaults(this);
        //init crashhandler
        CrashHandler.getInstance().init(this);
    }

    public static synchronized MyApplication getInstance() {
        return sINSTANCE;
    }

    public static Activity getCurrentVisibleActivity() {
        return sINSTANCE.lifecycleCallback.getCurrentVisibleActivity();
    }

    public static boolean isInForeground() {
        return null != sINSTANCE && null != sINSTANCE.lifecycleCallback && sINSTANCE.lifecycleCallback.isInForeground();
    }

    //dagger2:get ApplicationComponent
    public static ApplicationComponent getApplicationComponent() {
        return DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(sINSTANCE)).build();
    }

}
