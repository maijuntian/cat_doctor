package com.healthmall.sail.cat_doctor.activity;

import android.os.Bundle;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseActivity;
import com.healthmall.sail.cat_doctor.base.BaseSoftActivity;
import com.healthmall.sail.cat_doctor.delegate.ManagerMainDelegate;
import com.healthmall.sail.cat_doctor.utils.DialogUtils;
import com.mai.xmai_fast_lib.mvvm.presenter.ActivityPresenter;

import java.io.IOException;

import butterknife.OnClick;
import rx.functions.Action0;


public class ManagerMainActivity extends BaseSoftActivity<ManagerMainDelegate> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.iv_logout)
    public void iv_logoutClick() {
        finish();
    }

    @OnClick(R.id.iv_power_off)
    public void iv_power_offClick() {
        DialogUtils.showPowerDialog(this, new Action0() {
            @Override
            public void call() { //关机
                powerOff();
            }
        }, new Action0() {
            @Override
            public void call() { //重启
                reboot();

            }
        });
    }


    public int powerOff() {
        int r;
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot -p"});
            r = process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
            r = -1;
        } catch (InterruptedException e) {
            e.printStackTrace();
            r = -1;
        }
        return r;
    }

    public int reboot() {
        int r;
        try {
            Process process = Runtime.getRuntime().exec("su -c \"reboot\"");
            r = process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
            r = -1;
        } catch (InterruptedException e) {
            e.printStackTrace();
            r = -1;
        }
        log("result: " + r);
        return r;
    }

    public void serialPortCallBack(String msg) {  //串口通信的回调
        if(viewDelegate.hardwareCheckFragment != null)
            viewDelegate.hardwareCheckFragment.serialPortCallBack(msg);
    }
}


