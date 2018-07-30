package com.healthmall.sail.cat_doctor.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.activity.ExamineActivity;
import com.healthmall.sail.cat_doctor.activity.ReportActivity;
import com.healthmall.sail.cat_doctor.base.BaseFragment;
import com.healthmall.sail.cat_doctor.bean.BloodOxygenReport;
import com.healthmall.sail.cat_doctor.bean.TemperatureReport;
import com.healthmall.sail.cat_doctor.delegate.FaceTonDelegate;
import com.healthmall.sail.cat_doctor.delegate.TemperatureDelegate;
import com.healthmall.sail.cat_doctor.http.CatDoctorApi;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;

import java.util.Random;

import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by mai on 2018/1/5.
 */
public class TemperatureFragment extends BaseFragment<TemperatureDelegate> {
    TemperatureReport currTemperatureReport;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currTemperatureReport = MyApplication.get().getCurrUserReport().getTemperatureReport();

        if (currTemperatureReport.isFinish()) {
            viewDelegate.showStep3Init(currTemperatureReport);
        } else {
            startExamine();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            if (currTemperatureReport.isFinish()) {
                viewDelegate.showStep3Real(false);
            } else {
                startExamine();
            }
        } else {
            viewDelegate.hidePopWin();
            stopAll();
        }
    }

    private void startExamine() {
        viewDelegate.showStep1();
    }


    @OnClick(R.id.tv_start)
    public void tv_startClick() {
        startErrorDelay();
        viewDelegate.showStep2();
        SerialPortCmd.bodyTemp();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopAll();

    }

    private void stopAll() {
        stopErrorDelay();
        SerialPortCmd.stopTemp();
    }


    @Override
    public void serialPortCallBack(String msg) {

        switch (msg) { //语音
            case "AT+ASRKSCL": //开始测量
                if (viewDelegate.currStep == 1) {
                    tv_startClick();
                }
                return;
            case "AT+ASRCXCL": //重新测量
                if (viewDelegate.currStep == 3) {
                    reExamine();
                }
                return;
            case "AT+ASRCLXYX": //测量下一项
                if (viewDelegate.currStep == 3) {
                    ((ExamineActivity) getActivity()).showNextExamine();
                }
                return;
            case "AT+ASRSCBG": //生成报告
                if (viewDelegate.currStep == 3) {
                    ((ExamineActivity) getActivity()).showReport();
                }
                return;
        }

        if (msg.startsWith(SerialPortCmd.OK_BODYTEMP)) { //体温
            finishSpeek();
            stopErrorDelay();
            SerialPortCmd.parseTemp(msg, currTemperatureReport);
            currTemperatureReport.setFinish(true);
            viewDelegate.showStep3(currTemperatureReport);
            ((ExamineActivity) getActivity()).notifyMenu();

            CatDoctorApi.getInstance().temperatureReport(currTemperatureReport, getActivity()).subscribe(new Action1<Object>() {
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

    }

    @Override
    public void error() {
        stopAll();
    }

    @Override
    public void reExamine() {
        super.reExamine();

        MyApplication.get().getCurrUserReport().setTemperatureReport(new TemperatureReport(currTemperatureReport.getDeviceId(), currTemperatureReport.getMallId()));
        currTemperatureReport = MyApplication.get().getCurrUserReport().getTemperatureReport();
        ((ExamineActivity) getActivity()).notifyMenu();

        startExamine();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_reexamine:
                reExamine();
                break;
            case R.id.tv_next:
                ((ExamineActivity) getActivity()).showNextExamine();
                break;
            case R.id.tv_report:
                ((ExamineActivity)getActivity()).showReport();
                break;
        }
    }
}
