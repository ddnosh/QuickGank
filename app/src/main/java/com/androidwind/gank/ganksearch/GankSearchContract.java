package com.androidwind.gank.ganksearch;

import com.androidwind.gank.base.BaseModel;
import com.androidwind.gank.bean.model.SearchGank;

import com.androidwind.androidquick.ui.mvp.BaseContract;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface GankSearchContract {
    interface Model extends BaseModel {

    }

    interface View extends BaseContract.BaseView {
        void showSearchList(SearchGank girl);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void initData(String search, int page);
    }
}
