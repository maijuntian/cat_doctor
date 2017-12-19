package com.healthmall.sail.cat_doctor.utils;

import android.os.Environment;

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
}
