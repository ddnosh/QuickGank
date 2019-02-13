package com.androidwind.gank.gankcatalogue;

import com.androidwind.gank.base.BaseModel;
import com.androidwind.gank.bean.model.SimpleGank;

import la.xiong.androidquick.ui.base.BaseContract;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface GankItemContract {
    interface Model extends BaseModel {

    }

    interface View extends BaseContract.BaseView {
        void showItemData(SimpleGank item);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void initData(String catalogue, int page);
    }
}
