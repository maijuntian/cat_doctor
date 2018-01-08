package com.healthmall.sail.cat_doctor;

import android.app.Activity;
import android.text.TextUtils;

import com.healthmall.sail.cat_doctor.base.BaseActivity;
import com.healthmall.sail.cat_doctor.base.BaseSoftActivity;
import com.healthmall.sail.cat_doctor.bean.User;
import com.healthmall.sail.cat_doctor.bean.UserReport;
import com.healthmall.sail.cat_doctor.http.CatDoctorApi;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.serialport.SerialPortEngine;
import com.healthmall.sail.cat_doctor.utils.Keys;
import com.healthmall.sail.cat_doctor.utils.VoiceMamanger;
import com.healthmall.sail.cat_doctor.websocket.CatWebSocketClient;
import com.mai.xmai_fast_lib.base.BaseApplication;
import com.mai.xmai_fast_lib.basehttp.MParams;
import com.mai.xmai_fast_lib.utils.MLog;
import com.mai.xmai_fast_lib.utils.SharedPreferencesHelper;
import com.mai.xmai_fast_lib.utils.XAppManager;

import java.io.File;
import java.io.IOException;

import android_serialport_api.SerialPort;
import rx.functions.Action1;

/**
 * Created by mai on 2017/11/17.
 */
public class MyApplication extends BaseApplication {

    private static MyApplication instance;

    public static MyApplication get() {
        return instance;
    }

    User currUser;  //当前用户
    UserReport currUserReport; //当前的报告

    private SerialPort mSerialPort = null;

    @Override
    public String getBuglyAppid() {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initWebSocket();

        SerialPortCmd.reset();

        VoiceMamanger.init(this);
    }


    private void initWebSocket() {
        String deviceId = SharedPreferencesHelper.getInstance(getApplicationContext()).getStringValue(Keys.KEY_DEVICE_ID);

        if (!TextUtils.isEmpty(deviceId)) {
            CatWebSocketClient.getInstance().connect();
        }
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }

    public User getCurrUser() {
        return currUser;
    }

    public UserReport getCurrUserReport() {
        return currUserReport;
    }

    public void setCurrUserReport(UserReport currUserReport) {
        this.currUserReport = currUserReport;
    }

    public void loginSucc(User user) {

        if (currUser != null) {
            MLog.log("已经有用户登录，忽略掉-0-");
            return;
        }

        currUser = user;
        currUserReport = new UserReport();
        currUserReport.getQuestionReport().setQuestionAnswerId(user.getQuestionAnswerId());
        currUserReport.getQuestionReport().setQuestionResultName(user.getQuestionResultName());

        XAppManager.getInstance().doInAllActivity(new XAppManager.DoAllActivityListener() {
            @Override
            public void doAll(Activity act) {
                ((BaseSoftActivity) act).loginSucc();
            }
        });
    }

    public void serialPortCallBack(final String msg) {
        XAppManager.getInstance().doInAllActivity(new XAppManager.DoAllActivityListener() {
            @Override
            public void doAll(Activity act) {
                ((BaseSoftActivity) act).serialPortCallBack(msg);
            }
        });
    }

    public void serialPortIng(final String msg) {
        XAppManager.getInstance().doInAllActivity(new XAppManager.DoAllActivityListener() {
            @Override
            public void doAll(Activity act) {
                ((BaseSoftActivity) act).serialPortIng(msg);
            }
        });
    }

    /**
     * 退出登录
     */
    public void logout() {
        String deviceId = SharedPreferencesHelper.getInstance(getApplicationContext()).getStringValue(Keys.KEY_DEVICE_ID);

        CatDoctorApi.getInstance().quit(new MParams().add("deviceId", deviceId), getApplicationContext()).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        currUser = null;
        currUserReport = null;

    }
}
