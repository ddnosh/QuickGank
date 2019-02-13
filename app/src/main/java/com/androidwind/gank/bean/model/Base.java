package com.androidwind.gank.bean.model;

import java.io.Serializable;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class Base implements Serializable {
    public boolean error;

    @Override
    public String toString() {
        return "Base{" +
                "error=" + error +
                '}';
    }
}
