package com.androidwind.gank.gankdaily;

import com.androidwind.gank.base.BaseModel;
import com.androidwind.gank.bean.entity.GankBean;

import java.util.List;

import com.androidwind.androidquick.ui.mvp.BaseContract;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface GankDailyContract {
    interface Model extends BaseModel {

    }

    interface View extends BaseContract.BaseView {
        void showDailyList(List<GankBean> gankBeanList);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getDaily(int year, int month, int day);
    }
}
