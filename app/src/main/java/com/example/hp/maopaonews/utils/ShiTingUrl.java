package com.example.hp.maopaonews.utils;

/**
 * Created by hp on 2016/1/12.
 */
public class ShiTingUrl {
    public int startPage=0;

    public int getStratPage() {
        return startPage;
    }
    public void setStratPage(int stratPage) {
        this.startPage = stratPage;
    }
    public String getShiTingUrl() {
        int page = getStratPage();
        String JzStUrl = "http://c.m.163.com/nc/video/list/V9LG4B3A0/y/" + startPage + "-" + (startPage + 20) + ".html";
        return JzStUrl;
    }
}
