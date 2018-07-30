package com.healthmall.sail.cat_doctor.bean;

/**
 * Created by maijuntian on 2018/7/14.
 */
public class ResponeMsg {

    String type;
    String url;

    public ResponeMsg(String type, String url) {
        this.type = type;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
