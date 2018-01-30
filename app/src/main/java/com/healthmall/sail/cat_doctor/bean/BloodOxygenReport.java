package com.healthmall.sail.cat_doctor.bean;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.healthmall.sail.cat_doctor.R;

import java.util.List;

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

    List<Integer> lastPluseDatas;

    public void setLastPluseDatas(List<Integer> lastPluseDatas) {
        this.lastPluseDatas = lastPluseDatas;
    }

    public List<Integer> getLastPluseDatas() {
        return lastPluseDatas;
    }

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

    public String getBoResult() {
        if (Integer.parseInt(bm_blox_spO2) >= 94) {
            return "正常";
        } else {
            return "偏低";
        }
    }

    public int getBoTextColor(Context ctx) {
        if (Integer.parseInt(bm_blox_spO2) >= 94) {
            return ContextCompat.getColor(ctx, R.color.nomal);
        } else {
            return ContextCompat.getColor(ctx, R.color.low);
        }
    }

    public int getPluseTextColor(Context ctx) {
        if (Integer.parseInt(bm_blox_pr) > 100) {
            return ContextCompat.getColor(ctx, R.color.high);
        } else if (Integer.parseInt(bm_blox_pr) < 60) {
            return ContextCompat.getColor(ctx, R.color.low);
        } else {
            return ContextCompat.getColor(ctx, R.color.nomal);
        }
    }
}
