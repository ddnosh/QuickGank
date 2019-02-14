package com.androidwind.gank.bean.model;

import com.androidwind.gank.bean.entity.SearchBean;

import java.util.List;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class SearchGank extends Base {
    public List<SearchBean> results;

    @Override
    public String toString() {
        return "SearchGank{" +
                "results=" + results +
                ", error=" + error +
                '}';
    }
}
