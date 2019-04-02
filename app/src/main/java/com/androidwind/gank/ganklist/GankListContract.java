package com.androidwind.gank.ganklist;

import com.androidwind.gank.base.BaseModel;
import com.androidwind.gank.bean.model.SimpleGank;

import la.xiong.androidquick.ui.mvp.BaseContract;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface GankListContract {
    interface Model extends BaseModel {

    }

    interface View extends BaseContract.BaseView {
        void showGirlList(SimpleGank girl);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void initData(int page);
    }
}
