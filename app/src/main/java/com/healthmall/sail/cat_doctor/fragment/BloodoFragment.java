package com.healthmall.sail.cat_doctor.fragment;

import android.os.Bundle;
import android.view.View;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.activity.ExamineActivity;
import com.healthmall.sail.cat_doctor.base.BaseFragment;
import com.healthmall.sail.cat_doctor.bean.BloodOxygenReport;
import com.healthmall.sail.cat_doctor.bean.BodyReport;
import com.healthmall.sail.cat_doctor.delegate.BloodoDelegate;
import com.healthmall.sail.cat_doctor.http.CatDoctorApi;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;

import java.util.concurrent.TimeUnit;

import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by mai on 2017/11/15.
 */
public class BloodoFragment extends BaseFragment<BloodoDelegate> {


    BloodOxygenReport currBloodOxygenReport;


    Subscription sbTimer;

    final long delay = 5000;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currBloodOxygenReport = MyApplication.get().getCurrUserReport().getBloodOxygenReport();

        if (currBloodOxygenReport.isFinish()) {
            viewDelegate.showStep3(currBloodOxygenReport);
        } else {
            startExamine();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden && !currBloodOxygenReport.isFinish()) {
            startExamine();
        } else {
            stopAll();
        }
    }

    private void startExamine() {

        startErrorDelay();
        viewDelegate.showStep1();

        SerialPortCmd.bloodOX();

       /* if (sbTimer != null)
            sbTimer.unsubscribe();

        sbTimer = rx.Observable.timer(delay, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {

                SerialPortCmd.bloodOX();
            }
        });*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopAll();

        if (sbTimer != null)
            sbTimer.unsubscribe();
    }

    private void stopAll() {

        stopErrorDelay();
        SerialPortCmd.stopBloodOX();
    }


    @Override
    public void serialPortCallBack(String msg) {

        if (msg.startsWith(SerialPortCmd.OK_BLOODOX)) {
            stopErrorDelay();
            SerialPortCmd.parseBloodOX(msg, currBloodOxygenReport);
            currBloodOxygenReport.setFinish(true);
            viewDelegate.showStep3(currBloodOxygenReport);
            ((ExamineActivity) getActivity()).notifyMenu();

            CatDoctorApi.getInstance().bloodOxygenReport(currBloodOxygenReport, getActivity()).subscribe(new Action1<Object>() {
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
    public void serialPortIng(String msg) {
        super.serialPortIng(msg);

        if (msg.startsWith(SerialPortCmd.OK_BLOODOX)) {
            SerialPortCmd.parseBloodOX(msg, currBloodOxygenReport);
            viewDelegate.showStep2(currBloodOxygenReport);
        }
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

        MyApplication.get().getCurrUserReport().setBloodOxygenReport(new BloodOxygenReport(currBloodOxygenReport.getDeviceId(), currBloodOxygenReport.getMallId()));
        currBloodOxygenReport = MyApplication.get().getCurrUserReport().getBloodOxygenReport();
        ((ExamineActivity) getActivity()).notifyMenu();

        startExamine();
    }
}
