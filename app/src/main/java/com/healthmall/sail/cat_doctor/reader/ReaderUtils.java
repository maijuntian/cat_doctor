package com.healthmall.sail.cat_doctor.reader;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.text.TextUtils;

import com.acs.smartcard.Reader;
import com.baidu.tts.tools.DeviceId;
import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.http.CatDoctorApi;
import com.healthmall.sail.cat_doctor.utils.Keys;
import com.mai.xmai_fast_lib.basehttp.MParams;
import com.mai.xmai_fast_lib.utils.MLog;
import com.mai.xmai_fast_lib.utils.SharedPreferencesHelper;

import java.util.Arrays;

import rx.functions.Action1;


/**
 * Created by mai on 2018/3/14.
 */
public class ReaderUtils {

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    Reader mReader;
    UsbManager mManager;

    Reader.OnStateChangeListener listener = new Reader.OnStateChangeListener() {

        @Override
        public void onStateChange(int slotNum, int prevState, int currState) {


            if (currState == Reader.CARD_PRESENT) {
                try {
                    byte[] response = new byte[30];
                    mReader.power(0, 2);
                    mReader.setProtocol(0, 3);
                    mReader.transmit(0,
                            new byte[]{(byte) 0xFF, (byte) 0xCA, 0x00, 0x00, 0x00}, 5, response,
                            response.length);

                    String result = toHexString(response).substring(0, 10);
                    MLog.log("手环id-->" + result);

                    String deviceId = SharedPreferencesHelper.getInstance(MyApplication.get().getApplicationContext()).getStringValue(Keys.KEY_DEVICE_ID);
                    if (!TextUtils.isEmpty(deviceId) && result.endsWith("90")) {
                        CatDoctorApi.getInstance().scanCode(new MParams().add("wristbandCode", result.substring(0, 8))
                                .add("deviceId", deviceId), MyApplication.get().getApplicationContext())
                                .subscribe(new Action1<Object>() {
                                    @Override
                                    public void call(Object o) {

                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        throwable.printStackTrace();
                                    }
                                });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    };

    PendingIntent mPermissionIntent;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            MLog.log("action--->" + action);

            if (ACTION_USB_PERMISSION.equals(action)) {

                synchronized (this) {

                    UsbDevice device = intent
                            .getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(
                            UsbManager.EXTRA_PERMISSION_GRANTED, false)) {

                        if (device != null) {
                            mReader.open(device);
                        }
                    }
                }

            } else if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {

                synchronized (this) {

                    UsbDevice device = intent
                            .getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    MLog.log("" + (device != null));
                    MLog.log("" + (mReader.isSupported(device)));
                    if (device != null && mReader.isSupported(device)) {
                        MLog.log("开始请求权限");

                        mManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
                        mReader = new Reader(mManager);
                        mManager.requestPermission(device,
                                mPermissionIntent);
                        mReader.setOnStateChangeListener(listener);
                    }
                }
            }
        }
    };


    public void openDevice(Context ctx) {


        mPermissionIntent = PendingIntent.getBroadcast(ctx, 0, new Intent(
                ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        ctx.registerReceiver(mReceiver, filter);

        // Get USB manager
        mManager = (UsbManager) ctx.getSystemService(Context.USB_SERVICE);
        mReader = new Reader(mManager);


        for (UsbDevice device : mManager.getDeviceList().values()) {
            MLog.log("device--->" + device.getDeviceName() + "   " + mReader.isSupported(device));
            if (mReader.isSupported(device)) {
                mManager.requestPermission(device,
                        mPermissionIntent);
            }
        }


        mReader.setOnStateChangeListener(listener);

    }

    private String toHexString(byte[] buffer) {

        String bufferString = "";

        for (int i = 0; i < buffer.length; i++) {

            String hexChar = Integer.toHexString(buffer[i] & 0xFF);
            if (hexChar.length() == 1) {
                hexChar = "0" + hexChar;
            }

            bufferString += hexChar.toUpperCase();
        }

        return bufferString;
    }
}
