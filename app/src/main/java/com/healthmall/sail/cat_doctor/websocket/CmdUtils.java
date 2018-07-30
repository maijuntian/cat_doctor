package com.healthmall.sail.cat_doctor.websocket;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.utils.VersionUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mai on 2017/11/17.
 */
public class CmdUtils {

    public static String getRegisterMsg(String deviceId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message", "register");
            jsonObject.put("deviceID", deviceId);
            jsonObject.put("versionCode", VersionUtils.getVersionName(MyApplication.get().getApplicationContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    public static String getUnLockOk(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message", "unlock ok");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
