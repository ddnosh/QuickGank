package com.androidwind.gank.ganklist;

import android.content.Context;

import com.androidwind.androidquick.module.retrofit.RetrofitManager;
import com.androidwind.androidquick.module.retrofit.exeception.ApiException;
import com.androidwind.gank.api.GankApis;
import com.androidwind.gank.base.BasePresenter;
import com.androidwind.gank.bean.model.SimpleGank;
import com.androidwind.gank.tool.RxUtil;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import com.androidwind.androidquick.module.rxjava.BaseObserver;
import com.androidwind.androidquick.util.LogUtil;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class GankListPresenter extends BasePresenter<GankListContract.View> implements GankListContract.Presenter {

    public static final String TAG = "GankListPresenter";

    private RetrofitManager mRetrofitManager;
    private Context mContext;

    @Inject
    public GankListPresenter(Context mContext, RetrofitManager mRetrofitManager) {
        this.mContext = mContext;
        this.mRetrofitManager = mRetrofitManager;
    }

    @Override
    public void initData(int page) {
        GankApis gankDaysApis = mRetrofitManager.createApi(GankApis.class);

        Observable.zip(gankDaysApis.getGankList("福利", page),
                gankDaysApis.getGankList("休息视频", page),
                new BiFunction<SimpleGank, SimpleGank, SimpleGank>() {
                    @Override
                    public SimpleGank apply(SimpleGank girl, SimpleGank rest) {
                        return composeWithGirlAndRest(girl, rest);
                    }
                })
                .compose(RxUtil.applySchedulers())
                .compose(getView().bindToLife())
                .subscribe(new BaseObserver<SimpleGank>() {
                    @Override
                    public void onError(ApiException exception) {
                        LogUtil.e(TAG, "error:" + exception.getMessage());
                    }

                    @Override
                    public void onSuccess(SimpleGank girl) {
                        LogUtil.i(TAG, girl.toString());
                        getView().showGirlList(girl);
                    }
                });
    }

    private SimpleGank composeWithGirlAndRest(SimpleGank girl, SimpleGank rest) {
        int size = Math.min(girl.results.size(), rest.results.size());
        for (int i = 0; i < size; i++) {
            girl.results.get(i).desc = rest.results.get(i).desc;
            girl.results.get(i).who = rest.results.get(i).who;
            girl.results.get(i).playUrl = rest.results.get(i).url;
        }
        return girl;
    }
}
