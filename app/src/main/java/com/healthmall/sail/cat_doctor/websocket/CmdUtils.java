package com.healthmall.sail.cat_doctor.websocket;

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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
