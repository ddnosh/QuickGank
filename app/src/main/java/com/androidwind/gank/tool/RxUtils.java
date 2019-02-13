package com.androidwind.gank.tool;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class RxUtils {

    static final ObservableTransformer schedulersTransformer = upstream -> (upstream).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());


    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) schedulersTransformer;
    }
}
