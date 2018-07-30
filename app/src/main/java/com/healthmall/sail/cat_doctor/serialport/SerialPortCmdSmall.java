package com.healthmall.sail.cat_doctor.serialport;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.bean.FaceTonReport;
import com.healthmall.sail.cat_doctor.bean.Message;
import com.healthmall.sail.cat_doctor.bean.ResponeMsg;
import com.healthmall.sail.cat_doctor.utils.Keys;
import com.mai.xmai_fast_lib.utils.SharedPreferencesHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by maijuntian on 2018/7/12.
 */
public class SerialPortCmdSmall {

    private final static String FACE = "FACE";
    private final static String TON = "TON";
    private final static String FACE_TON = "FACE_TON";
    private final static String STP = "STP";


    public static void face() {
        Message msg = new Message(FACE, SharedPreferencesHelper.getInstance(MyApplication.get().getApplicationContext()).getStringValue(Keys.KEY_DEVICE_ID), MyApplication.get().getCurrUser().getMallId());
        SerialPortEngineSmall.getInstance().sendMsg(new Gson().toJson(msg));
    }

    public static void ton() {
        Message msg = new Message(TON, SharedPreferencesHelper.getInstance(MyApplication.get().getApplicationContext()).getStringValue(Keys.KEY_DEVICE_ID), MyApplication.get().getCurrUser().getMallId());
        SerialPortEngineSmall.getInstance().sendMsg(new Gson().toJson(msg));
    }

    public static void face_ton() {
        Message msg = new Message(FACE_TON, SharedPreferencesHelper.getInstance(MyApplication.get().getApplicationContext()).getStringValue(Keys.KEY_DEVICE_ID), MyApplication.get().getCurrUser().getMallId());
        SerialPortEngineSmall.getInstance().sendMsg(new Gson().toJson(msg));
    }

    public static void stp() {
        Message msg = new Message(STP, "", "");
        SerialPortEngineSmall.getInstance().sendMsg(new Gson().toJson(msg));
    }

    public static void parseFaceTonReport(String msg, FaceTonReport report) {
        try {
            List<ResponeMsg> responeMsgs = new Gson().fromJson(msg, new TypeToken<List<ResponeMsg>>() {
            }.getType());

            if (responeMsgs != null) {
                for (ResponeMsg responeMsg : responeMsgs) {
                    if (responeMsg.getType().equals(FACE)) {
                        report.setFinishFace(true);
                        report.setFaceUrl(responeMsg.getUrl());
                    } else if (responeMsg.getType().equals(TON)) {
                        report.setFinishTon(true);
                        report.setTonUrl(responeMsg.getUrl());
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
