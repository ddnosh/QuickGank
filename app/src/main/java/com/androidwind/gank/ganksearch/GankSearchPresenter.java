package com.androidwind.gank.ganksearch;

import android.content.Context;

import com.androidwind.gank.MyApplication;
import com.androidwind.gank.api.GankApis;
import com.androidwind.gank.base.BasePresenter;
import com.androidwind.gank.bean.model.SearchGank;
import com.androidwind.gank.tool.RxUtils;

import javax.inject.Inject;

import la.xiong.androidquick.network.retrofit.RetrofitManager;
import la.xiong.androidquick.network.retrofit.exeception.ApiException;
import la.xiong.androidquick.tool.LogUtil;
import la.xiong.androidquick.ui.base.BaseObserver;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class GankSearchPresenter extends BasePresenter<GankSearchContract.View> implements GankSearchContract.Presenter {

    public static final String TAG = "GankSearchPresenter";

    private RetrofitManager mRetrofitManager;
    private Context mContext;

    @Inject
    public GankSearchPresenter(Context mContext, RetrofitManager mRetrofitManager) {
        this.mContext = mContext;
        this.mRetrofitManager = mRetrofitManager;
    }

    @Override
    public void initData(String search, int page) {
        GankApis gankDaysApis = mRetrofitManager.createApi(MyApplication.getInstance().getApplicationContext(), GankApis.class);
        gankDaysApis.getGankSearch(search, page)
                .compose(RxUtils.applySchedulers())
                .compose(getView().bindToLife())
                .subscribe(new BaseObserver<SearchGank>() {
                    @Override
                    public void onError(ApiException exception) {
                        LogUtil.e(TAG, "error:" + exception.getMessage());
                    }

                    @Override
                    public void onSuccess(SearchGank searchGank) {
                        LogUtil.i(TAG, searchGank.toString());
                        getView().showSearchList(searchGank);
                    }
                });
    }

}
