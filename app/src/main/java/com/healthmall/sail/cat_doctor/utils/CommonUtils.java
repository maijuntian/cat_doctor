package com.healthmall.sail.cat_doctor.utils;

import android.os.Environment;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.healthmall.sail.cat_doctor.Constant;
import com.mai.xmai_fast_lib.utils.MLog;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mai on 2017/12/11.
 */
public class CommonUtils {

    public static void moveWeightRuler(ImageView iv, float weight) { //相对60kg移动多少像素
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv.getLayoutParams();
        MLog.log("marginTop--->" + params.topMargin);
        params.topMargin = (int) ((-707f) + (weight - 60) * 8.7f);
        iv.setLayoutParams(params);
    }

    public static void moveHeightRuler(ImageView iv, float height) {//相对130移动多少像素
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv.getLayoutParams();
        MLog.log("marginTop--->" + params.topMargin);
        params.topMargin = (int) ((-273) + (height - 130) * 8.8f);
        iv.setLayoutParams(params);
    }

    public static void moveBmiRuler(ImageView iv, float bmi) {//相对20移动多少像素
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv.getLayoutParams();
        MLog.log("marginTop--->" + params.topMargin);
        params.topMargin = (int) ((bmi - 20f) * 17.5f);
        iv.setLayoutParams(params);
    }

    public static JSONArray toArray(String text) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(text);

        return jsonArray;
    }

    public static String getFaceFilePath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Constant.SDCARD_CACHE_IMG_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
            File cropFile = new File(file, "face.png");
            return cropFile.getAbsolutePath();
        } else {
            return null;
        }
    }

    public static File getFaceFile() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Constant.SDCARD_CACHE_IMG_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
            File cropFile = new File(file, "face.png");

            return cropFile;
        } else {
            return null;
        }
    }

    public static String getTonFilePath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Constant.SDCARD_CACHE_IMG_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
            File cropFile = new File(file, "ton.png");
            return cropFile.getAbsolutePath();
        } else {
            return null;
        }
    }

    public static File getTonFile() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Constant.SDCARD_CACHE_IMG_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
            return new File(file, "ton.png");
        } else {
            return null;
        }
    }
}
