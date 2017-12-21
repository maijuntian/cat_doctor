package com.healthmall.sail.cat_doctor.fragment;

import android.os.Bundle;
import android.view.View;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.activity.ExamineActivity;
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


    Subscription sbTimer;
    final long delay = 5000;

    BloodPressureReport currBloodPressureReport;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currBloodPressureReport = MyApplication.get().getCurrUserReport().getBloodPressureReport();

        if (currBloodPressureReport.isFinish()) {
            viewDelegate.showStep3(currBloodPressureReport);
        } else {
            startExamine();
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden && !currBloodPressureReport.isFinish()) {
            startExamine();
        } else {
            stopAll();
        }
    }

    private void startExamine() {

        viewDelegate.showStep1();
        sbTimer = rx.Observable.timer(delay, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {

                startErrorDelay();
                viewDelegate.showStep2();
                SerialPortCmd.bloodOPRS();
            }
        });
    }

    @Override
    protected long getDelayTime() {
        return 180000L; //延迟三分钟
    }

    @Override
    public void onDestroy() {

        stopAll();

        if (sbTimer != null)
            sbTimer.unsubscribe();


        super.onDestroy();
    }

    private void stopAll() {

        stopErrorDelay();
        SerialPortCmd.stopBloodOPRS();
    }

    @Override
    public void serialPortCallBack(String msg) {

        if (msg.startsWith(SerialPortCmd.OK_BLOODPRS)) {

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

    @OnClick(R.id.iv_next)
    public void iv_nextClick() {
        ((ExamineActivity) getActivity()).showNextExamine();
    }

    @OnClick(R.id.iv_reexamine)
    public void iv_reexamineClick() {
        reExamine();
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
}
