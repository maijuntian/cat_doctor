package com.healthmall.sail.cat_doctor.fragment;

import android.os.Bundle;
import android.view.View;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.activity.ExamineActivity;
import com.healthmall.sail.cat_doctor.base.BaseFragment;
import com.healthmall.sail.cat_doctor.bean.BodyReport;
import com.healthmall.sail.cat_doctor.delegate.BodyDelegate;
import com.healthmall.sail.cat_doctor.http.CatDoctorApi;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.mai.xmai_fast_lib.utils.MLog;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by mai on 2017/11/15.
 */
public class BodyFragment extends BaseFragment<BodyDelegate> {

    BodyReport currBodyReport;

    Subscription sbTimer;

    final long delay = 10000;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currBodyReport = MyApplication.get().getCurrUserReport().getBodyReport();

        if (currBodyReport.isFinish()) {
            viewDelegate.showStep3();
            viewDelegate.initBodyData(currBodyReport);
        } else {
            startHeightWeightExamine();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden && !currBodyReport.isFinish()) {
            startHeightWeightExamine();
        } else if (hidden) {
            stopAll();
        }
    }


    private void startHeightWeightExamine() {
        viewDelegate.showStep1();

        if (sbTimer != null)
            sbTimer.unsubscribe();

        sbTimer = rx.Observable.timer(delay, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {

                startErrorDelay();
                viewDelegate.showStep2();
                SerialPortCmd.height();
                SerialPortCmd.weight();

            }
        });

    }

    @Override
    public void onDestroy() {
        stopAll();
        if (sbTimer != null)
            sbTimer.unsubscribe();
        super.onDestroy();

    }

    @Override
    public void error() {
        stopAll();
    }

    @Override
    public void reExamine() {
        super.reExamine();

        MyApplication.get().getCurrUserReport().setBodyReport(new BodyReport(currBodyReport.getDeviceId(), currBodyReport.getMallId()));
        currBodyReport = MyApplication.get().getCurrUserReport().getBodyReport();
        ((ExamineActivity) getActivity()).notifyMenu();
        startHeightWeightExamine();
    }

    private void stopAll() {

        SerialPortCmd.stopHeight();
        SerialPortCmd.stopWeight();
        SerialPortCmd.stopTemp();
        SerialPortCmd.stopFat();

        stopErrorDelay();
    }

    private void startTempExamine() {

        viewDelegate.showStep2();
        MLog.log("拿到体重和身高--->");
        if (currBodyReport.isFinishHeight() && currBodyReport.isFinishWeight()) {

            startErrorDelay();

            viewDelegate.showExamBody2();
            SerialPortCmd.bodyTemp();
        }
    }


    private void startFatExamine() {

        startErrorDelay();

        viewDelegate.showExamBody3();
        SerialPortCmd.bodyFat(MyApplication.get().getCurrUser().getMemberSex(), MyApplication.get().getCurrUser().getMemberAge(), ((int) Float.parseFloat(currBodyReport.getBm_weight()) * 10) + "", ((int) Float.parseFloat(currBodyReport.getBm_height()) * 10) + "");
    }


    public void serialPortCallBack(String msg) {  //串口通信的回调

        if (msg.startsWith(SerialPortCmd.OK_HEIGHT)) { //身高
            SerialPortCmd.parseHeight(msg, currBodyReport);
            currBodyReport.setFinishHeight(true);
            startTempExamine();
        } else if (msg.startsWith(SerialPortCmd.OK_WEIGHT)) { //体重
            SerialPortCmd.parseWeight(msg, currBodyReport);
            currBodyReport.setFinishWeight(true);
            startFatExamine();
        } /*else if (msg.startsWith(SerialPortCmd.OK_BODYTEMP)) { //体温
            SerialPortCmd.parseTemp(msg, currBodyReport);
            currBodyReport.setFinishTemp(true);
        } */else if (msg.startsWith(SerialPortCmd.OK_BODYFAT)) { //体脂

            stopErrorDelay(); //停止错误判定

            SerialPortCmd.parseBodyFat(msg, currBodyReport);
            currBodyReport.setFinishFat(true);
            SerialPortCmd.stopFat();
            //测量结束
            viewDelegate.showStep3();
            viewDelegate.initBodyData(currBodyReport);
            ((ExamineActivity) getActivity()).notifyMenu();

            CatDoctorApi.getInstance().bodyReport(currBodyReport, getActivity()).subscribe(new Action1<Object>() {
                @Override
                public void call(Object o) {
                    MLog.log("上传成功");
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
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.iv_next) {
            ((ExamineActivity) getActivity()).showNextExamine();
        } else if (v.getId() == R.id.iv_reexamine) { //重新测量
            reExamine();
        }
    }
}
