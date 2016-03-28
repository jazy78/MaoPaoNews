package com.example.hp.maopaonews.utils;

import java.util.Calendar;

/**
 * Created by hp on 2016/1/7.
 */
public class DateTime {
    public static  String getDate(){

        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DATE);
        return year + "年" + month + "月" + day + "日" ;
    }
    public static  String getDate_Time(){
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DATE);
        int hour=calendar.get(Calendar.HOUR);
        int minute=calendar.get(Calendar.MINUTE);
        int second=calendar.get(Calendar.SECOND);

        return year + "年" + month + "月" + day + "日"
                +hour + "时" + minute + "分" + second + "秒";

    }



}
