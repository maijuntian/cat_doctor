package com.healthmall.sail.cat_doctor.utils;

import com.mai.xmai_fast_lib.utils.MLog;

/**
 * Created by mai on 2018/1/12.
 */
public class FloatUtils {
    public static String getString(float value) {
        String temp = round(value) + "";
        MLog.log("temp-->" + temp);
        if (temp.endsWith(".0")) {
            temp = temp.replace(".0", "");
        }

        if (temp.endsWith(".00")) {
            temp = temp.replace(".00", "");
        }
        return temp;
    }

    public static float round(float value) {
        return (float) (Math.round(value * 10)) / 10;
    }


    public static float getMax(float[] values) {
        float max = 0f;
        for (float value : values) {
            if (value > max)
                max = value;
        }
        return max;
    }
}
