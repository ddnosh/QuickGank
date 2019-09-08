package com.androidwind.gank.tool;

import java.util.Calendar;
import java.util.Date;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class DateUtil {

    public static StringBuilder toDateString1(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new StringBuilder(year + "-" + month + "-" + day);
    }

    public static String toDateString2(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "年" + month + "月" + day + "日";
    }
}
