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
import com.healthmall.sail.cat_doctor.bean.BodyReport;
import com.healthmall.sail.cat_doctor.bean.BodyRespone;
import com.healthmall.sail.cat_doctor.delegate.BodyDelegate;
import com.healthmall.sail.cat_doctor.http.CatDoctorApi;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.utils.DialogUtils;
import com.mai.xmai_fast_lib.utils.MLog;

import java.util.concurrent.TimeUnit;

import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by mai on 2017/11/15.
 */
public class BodyFragment extends BaseFragment<BodyDelegate> {

    BodyReport currBodyReport;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currBodyReport = MyApplication.get().getCurrUserReport().getBodyReport();

        if (currBodyReport.isFinish()) {
            viewDelegate.showStep6Real(true);
        } else {
            startExamine();
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            if (currBodyReport.isFinish()) {
                viewDelegate.showStep6Real(false);
            } else {
                startExamine();
            }
        } else {
            viewDelegate.hidePopWin();
            stopAll();
        }
    }

    @OnClick(R.id.tv_start)
    public void tv_startClick() {
        startHeightWeightExamine();

    }

    private void startExamine() {
        viewDelegate.showStep1();
    }

   /*  int times = 0;

   Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (times < 3) {
                times++;
                serialPortIng("OK+WEIGHT=" + (661 + (times * 10)));
                serialPortIng("OK+HEIGHT=" + (1645 + (times * 10)));
                handler.sendEmptyMessageDelayed(0, 1000);
            } else {
                serialPortCallBack("OK+WEIGHT=" + 661);
                serialPortCallBack("OK+HEIGHT=" + 1745);
            }
        }
    };*/

    private void startHeightWeightExamine() {
        startErrorDelay();
        viewDelegate.showStep2();
        SerialPortCmd.height();
        SerialPortCmd.weight();

//        handler.sendEmptyMessageDelayed(0, 1000);
    }


    private void startFatExamine() {
        viewDelegate.showStep5();
        startErrorDelay();
        SerialPortCmd.bodyFat(MyApplication.get().getCurrUser().getMemberSex(), MyApplication.get().getCurrUser().getMemberAge(), ((int) Float.parseFloat(currBodyReport.getBm_weight()) * 10) + "", ((int) Float.parseFloat(currBodyReport.getBm_height()) * 10) + "");

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                serialPortCallBack("OK+BODYFAT+BF=123+MW=431+TM=302+EF=113+IF=188+RFBW=456+SDM=426+BONYSALTS=25+SM=257+PROTEIN=129+BMI=191+BM=1379+BFR=212+BMR=521+SW=624+VFL=10+BA=18+CS=80+ED=3+DOO=0+MC=32773+WC=43+FC=5+TFR=221+RHFR=49+LHFR=110+RLFR=130+LFFR=141+TMM=100+RHMM=31+LHMM=27+RLMM=115+LLMM=119+NC=333+WWC=416+HIP=899+BUST=903+RAC=257+LAC=269+RTC=539+LTC=536");
            }
        }, 2000);*/
    }

    @Override
    public void onDestroy() {
        stopAll();
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
        startExamine();
    }

    private void stopAll() {

        SerialPortCmd.stopHeight();
        SerialPortCmd.stopWeight();
        SerialPortCmd.stopFat();

        stopErrorDelay();
    }

    @Override
    public void serialPortIng(String msg) {
        super.serialPortIng(msg);
        if (msg.startsWith(SerialPortCmd.OK_HEIGHT)) { //身高
            SerialPortCmd.parseHeight(msg, currBodyReport);
            viewDelegate.showStep2ing(currBodyReport);
        } else if (msg.startsWith(SerialPortCmd.OK_WEIGHT)) { //体重
            SerialPortCmd.parseWeight(msg, currBodyReport);
            viewDelegate.showStep2ing(currBodyReport);
        }
    }

    public void showStep3() {

        if (currBodyReport.isFinishWeight() && currBodyReport.isFinishHeight()) {
            stopErrorDelay();
            viewDelegate.showStep3(currBodyReport);
        }
    }

    public void serialPortCallBack(String msg) {  //串口通信的回调

        switch (msg) { //语音
            case "AT+ASRKSCL": //开始测量
                if (viewDelegate.currStep == 1) {
                    startHeightWeightExamine();
                } else if (viewDelegate.currStep == 4) {
                    startFatExamine();
                }
                return;
            case "AT+ASRCXCL": //重新测量
                if (viewDelegate.currStep == 3) {
                    reExamine();
                }
                return;
            case "AT+ASRCLTZ": //测量体脂
                if (viewDelegate.currStep == 3) {
                    viewDelegate.showStep4();
                }
                return;
            case "AT+ASRCXCLTZ": //重新测量体脂
                if (viewDelegate.currStep == 6) {
                    viewDelegate.showStep4();
                }
                return;
            case "AT+ASRCXCLRTCF": //重新测量人体成分
                if (viewDelegate.currStep == 6) {
                    reExamine();
                }
                return;
            case "AT+ASRCLXYX": //测量下一项
                if (viewDelegate.currStep == 6) {
                    ((ExamineActivity) getActivity()).showNextExamine();
                }
                return;
            case "AT+ASRSCBG": //生成报告
                if (viewDelegate.currStep == 6) {
                    ((ExamineActivity) getActivity()).showReport();
                }
                return;
        }

        if (msg.startsWith(SerialPortCmd.OK_HEIGHT)) { //身高
            SerialPortCmd.parseHeight(msg, currBodyReport);
            currBodyReport.setFinishHeight(true);
            showStep3();
        } else if (msg.startsWith(SerialPortCmd.OK_WEIGHT)) { //体重
            SerialPortCmd.parseWeight(msg, currBodyReport);
            currBodyReport.setFinishWeight(true);
            showStep3();
        } else if (msg.startsWith(SerialPortCmd.OK_BODYFAT)) { //体脂

            stopErrorDelay();
            SerialPortCmd.parseBodyFat(msg, currBodyReport);
            SerialPortCmd.stopFat();

            CatDoctorApi.getInstance().bodyReport(currBodyReport, getActivity()).subscribe(new Action1<BodyRespone>() {
                @Override
                public void call(BodyRespone bodyRespone) {

                    currBodyReport.setFinishFat(true);
                    ((ExamineActivity) getActivity()).notifyMenu();

                    currBodyReport.setBodyRespone(bodyRespone);
                    viewDelegate.showStep6();
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

        switch (v.getId()) {
            case R.id.tv_reexamine:
            case R.id.tv_reexamine_hw://重新测量
                reExamine();
                break;
            case R.id.tv_start_fat: //开始测量体脂
                startFatExamine();
                break;
            case R.id.tv_reexamine_fat://测量体脂
            case R.id.tv_examine_fat:
                viewDelegate.showStep4();
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
