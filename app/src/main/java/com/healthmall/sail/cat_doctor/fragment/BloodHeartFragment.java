package com.healthmall.sail.cat_doctor.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.activity.ExamineActivity;
import com.healthmall.sail.cat_doctor.activity.ReportActivity;
import com.healthmall.sail.cat_doctor.base.BaseFragment;
import com.healthmall.sail.cat_doctor.base.MyThrowable;
import com.healthmall.sail.cat_doctor.bean.BloodOxygenReport;
import com.healthmall.sail.cat_doctor.bean.BloodPressureReport;
import com.healthmall.sail.cat_doctor.delegate.BloodHeartDelegate;
import com.healthmall.sail.cat_doctor.http.CatDoctorApi;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.mai.xmai_fast_lib.utils.MLog;

import java.util.concurrent.TimeUnit;

import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by mai on 2017/11/15.
 */
public class BloodHeartFragment extends BaseFragment<BloodHeartDelegate> {


    BloodPressureReport currBloodPressureReport;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currBloodPressureReport = MyApplication.get().getCurrUserReport().getBloodPressureReport();

        if (currBloodPressureReport.isFinish()) {
            viewDelegate.showStep3Init(currBloodPressureReport);
        } else {
            startExamine();
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            if(currBloodPressureReport.isFinish()){
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

    @Override
    protected long getDelayTime() {
        return 180000L; //延迟三分钟
    }

    @OnClick(R.id.tv_start)
    public void tv_startClick() {
        startErrorDelay();
        viewDelegate.showStep2();
        SerialPortCmd.bloodOPRS();

    }

    @Override
    public void onDestroy() {

        stopAll();

        super.onDestroy();
    }

    private void stopAll() {

        stopErrorDelay();
        SerialPortCmd.stopBloodOPRS();
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

        if (msg.startsWith(SerialPortCmd.OK_BLOODPRS)) {

            finishSpeek();
            stopErrorDelay();

            SerialPortCmd.parseBloodOPRS(msg, currBloodPressureReport);
            currBloodPressureReport.setFinish(true);
            viewDelegate.showStep3(currBloodPressureReport);
            ((ExamineActivity) getActivity()).notifyMenu();

            SerialPortCmd.stopBloodOPRS();

            CatDoctorApi.getInstance().bloodPressureReport(currBloodPressureReport, getActivity()).subscribe(new Action1<Object>() {
                @Override
                public void call(Object o) {
                    MLog.log("上传完成");
                }
            }, new MyThrowable());
        }
    }

    @Override
    public void serialPortIng(String msg) {
        super.serialPortIng(msg);

      /*  if(msg.startsWith(SerialPortCmd.OK_BLOODPRS)){
            SerialPortCmd.parseBloodOPRS(msg, currBloodPressureReport);
            viewDelegate.showStep2(currBloodPressureReport);
        }*/
    }


    @Override
    public void error() {
        stopAll();
    }

    @Override
    public void reExamine() {
        super.reExamine();

        MyApplication.get().getCurrUserReport().setBloodPressureReport(new BloodPressureReport(currBloodPressureReport.getDeviceId(), currBloodPressureReport.getMallId()));
        currBloodPressureReport = MyApplication.get().getCurrUserReport().getBloodPressureReport();
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
