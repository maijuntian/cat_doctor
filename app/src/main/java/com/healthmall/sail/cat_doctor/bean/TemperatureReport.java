package com.healthmall.sail.cat_doctor.bean;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.R;

/**
 * Created by mai on 2018/1/5.
 */
public class TemperatureReport {
    boolean isFinish;
    String deviceId;                //设备号
    String mallId;              //会员id
    private String bm_bdtemp;//体温
    private int source;

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

    public String getBm_bdtemp() {
        return bm_bdtemp;
    }

    public void setBm_bdtemp(String bm_bdtemp) {
        this.bm_bdtemp = bm_bdtemp;
    }

    public static int getTextColor(Context ctx, float temp) {
        if (temp > 37.3f) { //偏高
            return ContextCompat.getColor(ctx, R.color.temp_high1);
        } else if (temp > 38f) { //发烧
            return ContextCompat.getColor(ctx, R.color.temp_high2);
        } else if (temp > 39.1f) { //高烧
            return ContextCompat.getColor(ctx, R.color.temp_high3);
        } else { //正常
            return ContextCompat.getColor(ctx, R.color.temp_nomal);
        }
    }

    public static int getTextTagColor(Context ctx, float temp) {
        if (temp > 37.3f) { //偏高
            return ContextCompat.getColor(ctx, R.color.temp_high1_txt);
        } else if (temp > 38f) { //发烧
            return ContextCompat.getColor(ctx, R.color.temp_high2_txt);
        } else if (temp > 39.1f) { //高烧
            return ContextCompat.getColor(ctx, R.color.temp_high3_txt);
        } else { //正常
            return ContextCompat.getColor(ctx, R.color.temp_nomal_txt);
        }
    }

    public static int getTextTagBgColor(Context ctx, float temp) {
        if (temp > 37.3f) { //偏高
            return ContextCompat.getColor(ctx, R.color.temp_high1_bg);
        } else if (temp > 38f) { //发烧
            return ContextCompat.getColor(ctx, R.color.temp_high2_bg);
        } else if (temp > 39.1f) { //高烧
            return ContextCompat.getColor(ctx, R.color.temp_high3_bg);
        } else { //正常
            return ContextCompat.getColor(ctx, R.color.temp_nomal_bg);
        }
    }

    public static void setTextTag(Context ctx, float temp, TextView tvNomal, TextView tvHigh1, TextView tvHigh2, TextView tvHigh3) {
        if (temp > 37.3f) { //偏高
            tvHigh1.setTextColor(getTextTagColor(ctx, temp));
            tvHigh1.setBackgroundColor(getTextTagBgColor(ctx, temp));
        } else if (temp > 38f) { //发烧
            tvHigh2.setTextColor(getTextTagColor(ctx, temp));
            tvHigh2.setBackgroundColor(getTextTagBgColor(ctx, temp));
        } else if (temp > 39.1f) { //高烧
            tvHigh3.setTextColor(getTextTagColor(ctx, temp));
            tvHigh3.setBackgroundColor(getTextTagBgColor(ctx, temp));
        } else { //正常
            tvNomal.setTextColor(getTextTagColor(ctx, temp));
            tvNomal.setBackgroundColor(getTextTagBgColor(ctx, temp));
        }
    }

}
