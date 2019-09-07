package com.androidwind.gank.gankcatalogue;

import android.content.Context;

import com.androidwind.androidquick.module.retrofit.RetrofitManager;
import com.androidwind.androidquick.module.retrofit.exeception.ApiException;
import com.androidwind.gank.api.GankApis;
import com.androidwind.gank.base.BasePresenter;
import com.androidwind.gank.bean.model.SimpleGank;
import com.androidwind.gank.tool.RxUtils;

import javax.inject.Inject;

import com.androidwind.androidquick.module.rxjava.BaseObserver;
import com.androidwind.androidquick.util.LogUtil;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class GankItemPresenter extends BasePresenter<GankItemContract.View> implements GankItemContract.Presenter {

    public static final String TAG = "GankListPresenter";

    private RetrofitManager mRetrofitManager;
    private Context mContext;

    @Inject
    public GankItemPresenter(Context mContext, RetrofitManager mRetrofitManager) {
        this.mContext = mContext;
        this.mRetrofitManager = mRetrofitManager;
    }

    @Override
    public void initData(String catalogue, int page) {
        GankApis gankDaysApis = mRetrofitManager.createApi(GankApis.class);
        gankDaysApis.getGankList(catalogue, page)
                .compose(RxUtils.applySchedulers())
                .compose(getView().bindToLife())
                .subscribe(new BaseObserver<SimpleGank>() {
                    @Override
                    public void onError(ApiException exception) {
                        LogUtil.e(TAG, "error:" + exception.getMessage());
                    }

                    @Override
                    public void onSuccess(SimpleGank simpleGank) {
                        LogUtil.i(TAG, simpleGank.toString());
                        getView().showItemData(simpleGank);
                    }
                });
    }

}
