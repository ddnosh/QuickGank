package com.androidwind.gank.bean.model;

import com.androidwind.gank.bean.entity.GankBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class FullGank extends Base {

    public List<String> category;
    public Result results;

    public class Result {
        @SerializedName("Android")
        public List<GankBean> androidList;
        @SerializedName("休息视频")
        public List<GankBean> restList;
        @SerializedName("iOS")
        public List<GankBean> iOSList;
        @SerializedName("福利")
        public List<GankBean> girlList;
        @SerializedName("拓展资源")
        public List<GankBean> outerList;
        @SerializedName("瞎推荐")
        public List<GankBean> recommendList;
        @SerializedName("App")
        public List<GankBean> appList;
        @SerializedName("前端")
        public List<GankBean> frontList;
    }

    @Override
    public String toString() {
        return "FullGank{" +
                "category=" + category +
                ", results=" + results +
                ", error=" + error +
                '}';
    }
}
