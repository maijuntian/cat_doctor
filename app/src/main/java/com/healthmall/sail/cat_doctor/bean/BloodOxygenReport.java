package com.healthmall.sail.cat_doctor.bean;

/**
 * Created by mai on 2017/11/20.
 */
public class BloodOxygenReport {


    private int source;

    boolean isFinish;
    String deviceId;                //设备号
    String mallId;              //会员id
    String bm_blox_spO2;         //血氧饱和度
    String bm_blox_pr;            //脉率
    String bm_blox_pi;             //灌注指数

    public BloodOxygenReport(String deviceId, String mallId) {
        this.deviceId = deviceId;
        this.mallId = mallId;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public String getBm_blox_spO2() {
        return bm_blox_spO2;
    }

    public void setBm_blox_spO2(String bm_blox_spO2) {
        this.bm_blox_spO2 = bm_blox_spO2;
    }

    public String getBm_blox_pr() {
        return bm_blox_pr;
    }

    public void setBm_blox_pr(String bm_blox_pr) {
        this.bm_blox_pr = bm_blox_pr;
    }

    public String getBm_blox_pi() {
        return bm_blox_pi;
    }

    public void setBm_blox_pi(String bm_blox_pi) {
        this.bm_blox_pi = bm_blox_pi;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }
}
