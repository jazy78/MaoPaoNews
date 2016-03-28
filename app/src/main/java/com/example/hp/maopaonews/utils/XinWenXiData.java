package com.example.hp.maopaonews.utils;

import java.io.Serializable;

/**
 * Created by hp on 2016/1/7.
 */
public class XinWenXiData implements Serializable {

    private int bujuType;

    private int lanMuType;
    private String url;
    private int replaycount;
    private String title;

    private String xinwentext;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getXinwentext() {
        return xinwentext;
    }

    public void setXinwentext(String xinwentext) {
        this.xinwentext = xinwentext;
    }

    public int getBujuType() {
        return bujuType;
    }

    public void setBujuType(int bujuType) {
        this.bujuType = bujuType;
    }

    public int getLanMuType() {
        return lanMuType;
    }

    public void setLanMuType(int lanMuType) {
        this.lanMuType = lanMuType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getReplaycount() {
        return replaycount;
    }

    public void setReplaycount(int replaycount) {
        this.replaycount = replaycount;
    }
}

