package com.healthmall.sail.cat_doctor.bean;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.healthmall.sail.cat_doctor.R;

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

    public int getSpIcon() {
        if (Integer.parseInt(bm_sph_sp) > 140) { //高压
            return R.mipmap.report_bloodo_up;
        } else if (Integer.parseInt(bm_sph_sp) < 90) { //低压
            return R.mipmap.report_bloodo_down;
        } else { //正常
            return 0;
        }
    }

    public int getDpIcon() {
        if (Integer.parseInt(bm_sph_dp) > 90) { //高压
            return R.mipmap.report_bloodo_up;
        } else if (Integer.parseInt(bm_sph_dp) < 60) { //低压
            return R.mipmap.report_bloodo_down;
        } else { //正常
            return 0;
        }
    }

    public int getHrIcon() {
        if (Integer.parseInt(bm_sph_hr) > 100) { //高压
            return R.mipmap.report_bloodo_up;
        } else if (Integer.parseInt(bm_sph_hr) < 60) { //低压
            return R.mipmap.report_bloodo_down;
        } else { //正常
            return 0;
        }
    }

    public int getSpBg() {
        if (Integer.parseInt(bm_sph_sp) > 140) { //高压
            return R.mipmap.report_bp_hr_high;
        } else if (Integer.parseInt(bm_sph_sp) < 90) { //低压
            return R.mipmap.report_bp_hr_low;
        } else { //正常
            return R.mipmap.report_bp_hr_normal;
        }
    }

    public int getDpBg() {
        if (Integer.parseInt(bm_sph_dp) > 90) { //高压
            return R.mipmap.report_bp_hr_high;
        } else if (Integer.parseInt(bm_sph_dp) < 60) { //低压
            return R.mipmap.report_bp_hr_low;
        } else { //正常
            return R.mipmap.report_bp_hr_normal;
        }
    }

    public int getHrBg() {
        if (Integer.parseInt(bm_sph_hr) > 100) { //高压
            return R.mipmap.report_bp_hr_high;
        } else if (Integer.parseInt(bm_sph_hr) < 60) { //低压
            return R.mipmap.report_bp_hr_low;
        } else { //正常
            return R.mipmap.report_bp_hr_normal;
        }
    }

    public String getBpText() {
        if (Integer.parseInt(bm_sph_dp) > 90 || Integer.parseInt(bm_sph_sp) > 140) { //高压
            return "高压";
        } else if (Integer.parseInt(bm_sph_hr) < 60 || Integer.parseInt(bm_sph_sp) < 90) { //低压
            return "低压";
        } else { //正常
            return "正常";
        }
    }

    public String getBpAlarm(Context ctx) {
        if (Integer.parseInt(bm_sph_dp) > 90 || Integer.parseInt(bm_sph_sp) > 140) { //高压
            return ctx.getString(R.string.bp_tip_high);
        } else if (Integer.parseInt(bm_sph_hr) < 60 || Integer.parseInt(bm_sph_sp) < 90) { //低压
            return ctx.getString(R.string.bp_tip_low);
        } else { //正常
            return ctx.getString(R.string.bp_tip_nomal);
        }
    }

    public String getHrText() {
        if (Integer.parseInt(bm_sph_hr) > 100) {
            return "过快";
        } else if (Integer.parseInt(bm_sph_hr) < 60) {
            return "过慢";
        } else { //正常
            return "正常";
        }
    }

    public String getHrAlarm(Context ctx) {
        if (Integer.parseInt(bm_sph_hr) > 100) {
            return ctx.getString(R.string.hr_tip_high);
        } else if (Integer.parseInt(bm_sph_hr) < 60) {
            return ctx.getString(R.string.hr_tip_low);
        } else { //正常
            return ctx.getString(R.string.hr_tip_nomal);
        }
    }

    public int getHrTextColor(Context ctx) {
        if (Integer.parseInt(bm_sph_hr) > 100) {
            return ContextCompat.getColor(ctx, R.color.high);
        } else if (Integer.parseInt(bm_sph_hr) < 60) {
            return ContextCompat.getColor(ctx, R.color.low);
        } else { //正常
            return ContextCompat.getColor(ctx, R.color.nomal);
        }
    }

    public int getBpTextColor(Context ctx) {
        if (Integer.parseInt(bm_sph_dp) > 90 || Integer.parseInt(bm_sph_sp) > 140) { //高压
            return ContextCompat.getColor(ctx, R.color.high);
        } else if (Integer.parseInt(bm_sph_hr) < 60 || Integer.parseInt(bm_sph_sp) < 90) { //低压
            return ContextCompat.getColor(ctx, R.color.low);
        } else { //正常
            return ContextCompat.getColor(ctx, R.color.nomal);
        }
    }
}
