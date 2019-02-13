package com.androidwind.gank.bean.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class BaseGankBean implements Serializable {
    public String _id;
    public boolean used;
    public String type;
    public String url;
    public String who;
    public String desc;
    public Date createdAt;
    public Date publishedAt;
    public String source;
}
