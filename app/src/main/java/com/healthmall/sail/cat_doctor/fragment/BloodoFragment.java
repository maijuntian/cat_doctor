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
import com.healthmall.sail.cat_doctor.bean.BodyReport;
import com.healthmall.sail.cat_doctor.delegate.BloodoDelegate;
import com.healthmall.sail.cat_doctor.http.CatDoctorApi;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;

import java.util.Random;
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currBloodOxygenReport = MyApplication.get().getCurrUserReport().getBloodOxygenReport();

        if (currBloodOxygenReport.isFinish()) {
            viewDelegate.showStep3Init(currBloodOxygenReport);
        } else {
            startExamine();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            if (currBloodOxygenReport.isFinish()) {
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
        return 60000;
    }

    /* int times;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (times == 20) {
                serialPortCallBack("OK+BLOODOX+SPO2=" + (76 + times) + "+PR=" + (new Random().nextInt(180)) + "+PI=68+ST=2");
            } else {
                serialPortIng("OK+BLOODOX+SPO2=" + (76 + times) + "+PR=" + (new Random().nextInt(180)) + "+PI=68+ST=2");
                handler.sendEmptyMessageDelayed(0, 1000);
            }
            times++;
        }
    };*/

    @OnClick(R.id.tv_start)
    public void tv_startClick() {
        startErrorDelay();
        viewDelegate.showStep2();
        SerialPortCmd.bloodOX();

       /* times = 0;
        handler.sendEmptyMessageDelayed(0, 10000);*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopAll();

    }

    private void stopAll() {
        stopErrorDelay();
        SerialPortCmd.stopBloodOX();
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

        if (msg.startsWith(SerialPortCmd.OK_BLOODOX)) {
            stopErrorDelay();
            SerialPortCmd.parseBloodOX(msg, currBloodOxygenReport);
            currBloodOxygenReport.setFinish(true);
            currBloodOxygenReport.setLastPluseDatas(viewDelegate.prvPluseRate.getmData());
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
            viewDelegate.showStepIng(currBloodOxygenReport);
        }
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
                ((ExamineActivity) getActivity()).showReport();
                break;
        }
    }
}
