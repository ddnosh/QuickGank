package com.androidwind.gank.bean.entity;

import java.util.List;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class GankBean extends BaseGankBean {

    public String playUrl;
    public List<String> images;

    @Override
    public String toString() {
        return "GankBean{" +
                "playUrl='" + playUrl + '\'' +
                ", images=" + images +
                ", _id='" + _id + '\'' +
                ", used=" + used +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", who='" + who + '\'' +
                ", desc='" + desc + '\'' +
                ", createdAt=" + createdAt +
                ", publishedAt=" + publishedAt +
                ", source='" + source + '\'' +
                '}';
    }
}
