package com.androidwind.gank.gankdaily;

import android.content.Context;

import com.androidwind.androidquick.module.retrofit.RetrofitManager;
import com.androidwind.androidquick.module.retrofit.exeception.ApiException;
import com.androidwind.gank.MyApplication;
import com.androidwind.gank.api.GankApis;
import com.androidwind.gank.base.BasePresenter;
import com.androidwind.gank.bean.entity.GankBean;
import com.androidwind.gank.bean.model.FullGank;
import com.androidwind.gank.constant.Constants;
import com.androidwind.gank.tool.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.androidwind.androidquick.module.rxjava.BaseObserver;
import com.androidwind.androidquick.util.LogUtil;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class GankDailyPresenter extends BasePresenter<GankDailyContract.View> implements GankDailyContract.Presenter {

    public static final String TAG = "GankDailyPresenter";

    private RetrofitManager mRetrofitManager;
    private Context mContext;

    @Inject
    public GankDailyPresenter(Context mContext, RetrofitManager mRetrofitManager) {
        this.mContext = mContext;
        this.mRetrofitManager = mRetrofitManager;
    }

    @Override
    public void getDaily(int year, int month, int day) {
        GankApis gankDaysApis = mRetrofitManager.createApi(GankApis.class);
        gankDaysApis.getGankDaily(year, month, day)
                .compose(RxUtils.applySchedulers())
                .compose(getView().bindToLife())
                .subscribe(new BaseObserver<FullGank>() {
                    @Override
                    public void onError(ApiException exception) {
                        LogUtil.e(TAG, "error:" + exception.getMessage());
                    }

                    @Override
                    public void onSuccess(FullGank gank) {
                        LogUtil.i(TAG, gank.toString());
                        getView().showDailyList(composeGankList(gank.results));
                    }
                });
    }

    private List<GankBean> composeGankList(FullGank.Result results) {
        List<GankBean> mGankList = new ArrayList<>();
        if (results.androidList != null) {
            mGankList.add(insertGankBean("Android"));
            mGankList.addAll(results.androidList);
        }
        if (results.iOSList != null) {
            mGankList.add(insertGankBean("iOS"));
            mGankList.addAll(results.iOSList);
        }
        if (results.frontList != null) {
            mGankList.add(insertGankBean("前端"));
            mGankList.addAll(results.frontList);
        }
        if (results.appList != null) {
            mGankList.add(insertGankBean("App"));
            mGankList.addAll(results.appList);
        }
        if (results.outerList != null) {
            mGankList.add(insertGankBean("拓展资源"));
            mGankList.addAll(results.outerList);
        }
        if (results.recommendList != null) {
            mGankList.add(insertGankBean("瞎推荐"));
            mGankList.addAll(results.recommendList);
        }
        if (results.restList != null) {
            mGankList.add(insertGankBean("休息视频"));
            mGankList.addAll(results.restList);
        }
        return mGankList;
    }

    private GankBean insertGankBean(String title) {
        GankBean gankBean = new GankBean();
        gankBean.source = Constants.GANK_TYPE;
        gankBean.type = title;
        return gankBean;
    }
}
