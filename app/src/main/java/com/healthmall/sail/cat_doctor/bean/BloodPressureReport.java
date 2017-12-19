package com.healthmall.sail.cat_doctor.bean;

/**
 * Created by mai on 2017/11/20.
 */
public class BloodPressureReport {

    boolean isFinish;
    String deviceId;            //设备号
    String mallId;              //会员id
    String bm_sph_sp;           //收缩压（高压）
    String bm_sph_dp;            //舒张压（低压）
    String bm_sph_hr;            //心率

    private int source;

    public BloodPressureReport(String deviceId, String mallId) {
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

    public String getBm_sph_sp() {
        return bm_sph_sp;
    }

    public void setBm_sph_sp(String bm_sph_sp) {
        this.bm_sph_sp = bm_sph_sp;
    }

    public String getBm_sph_dp() {
        return bm_sph_dp;
    }

    public void setBm_sph_dp(String bm_sph_dp) {
        this.bm_sph_dp = bm_sph_dp;
    }

    public String getBm_sph_hr() {
        return bm_sph_hr;
    }

    public void setBm_sph_hr(String bm_sph_hr) {
        this.bm_sph_hr = bm_sph_hr;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }
}
