package com.androidwind.gank.bean.model;

import com.androidwind.gank.bean.entity.GankBean;

import java.util.List;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class SimpleGank extends Base {
    public List<GankBean> results;

    @Override
    public String toString() {
        return "SimpleGank{" +
                "results=" + results +
                ", error=" + error +
                '}';
    }
}
