package com.healthmall.sail.cat_doctor.bean;

/**
 * Created by mai on 2018/1/5.
 */
public class TemperatureReport {
    boolean isFinish;
    String deviceId;                //设备号
    String mallId;              //会员id

    public TemperatureReport(String deviceId, String mallId) {
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
}
