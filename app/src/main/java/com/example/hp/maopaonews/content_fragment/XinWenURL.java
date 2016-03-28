package com.example.hp.maopaonews.content_fragment;

/**
 * Created by hp on 2016/1/6.
 */
public class XinWenURL {
    public  int startPage=0;
    String redian="http://c.3g.163.com/nc/article/list/T1429173762551/0-20.html";
    public int getStratPage() {
        return startPage;
    }
    public void setStratPage(int stratPage) {
        this.startPage = stratPage;
    }

    public String getRedian() {
        return redian;
    }


    public String getToutiao() {
        int page = getStratPage();
        String toutiao = "http://c.m.163.com/nc/article/headline/T1348647853363/" + page + "-" + (page + 20) + ".html";//头条
        return toutiao;
    }

    public String getYule() {
        int page = getStratPage();
        String yule = "http://c.m.163.com/nc/article/list/T1348648517839/" + startPage + "-" + (startPage + 20) + ".html";//娱乐
        return yule;
    }

    public String getTiyu() {
        int page = getStratPage();
        String tiyu = "http://c.m.163.com/nc/article/list/T1348649079062/" + startPage+ "-" + (startPage + 20) + ".html";//体育
        return tiyu;
    }

    public String getChaijing() {
        int page = getStratPage();
        String chaijing = "http://c.m.163.com/nc/article/list/T1348648756099/" + startPage + "-" + (startPage + 20) + ".html";//财经
        return chaijing;
    }

    public String getKeji() {
        int page = getStratPage();
        String keji = "http://c.m.163.com/nc/article/list/T1348649580692/" + startPage + "-" + (startPage + 20) + ".html";//科技
        return keji;
    }

    public String getQiche() {
        String qiche = "http://c.m.163.com/nc/article/list/T1348654060988/" + startPage + "-" + (startPage + 20) + ".html";//汽车
        return qiche;
    }

    public String getShishang() {
        String shishang = "http://c.m.163.com/nc/article/list/T1348650593803/" + startPage + "-" + (startPage + 20) + ".html";//时尚
        return shishang;
    }

    public String getJunshi() {
        String junshi = "http://c.m.163.com/nc/article/list/T1348648141035/" + startPage + "-" + (startPage + 20) + ".html";//军事
        return junshi;
    }

    public String getLishi() {
        String lishi = "http://c.m.163.com/nc/article/list/T1368497029546/" + startPage + "-" + (startPage + 20) + ".html";//历史
        return lishi;
    }

    public String getCaipiao() {
        String caipiao = "http://c.m.163.com/nc/article/list/T1356600029035/" + startPage + "-" + (startPage + 20) + ".html";//彩票
        return caipiao;
    }

    public String getYouxi() {
        String youxi = "http://c.m.163.com/nc/article/list/T1348654151579/" + startPage + "-" + (startPage + 20) + ".html";//游戏
        return youxi;
    }
}
