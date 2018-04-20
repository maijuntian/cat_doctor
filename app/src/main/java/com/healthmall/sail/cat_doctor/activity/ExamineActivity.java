package com.healthmall.sail.cat_doctor.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.CompoundButton;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseActivity;
import com.healthmall.sail.cat_doctor.delegate.ExamineDelegate;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.utils.DialogUtils;

import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import rx.functions.Action0;


public class ExamineActivity extends BaseActivity<ExamineDelegate> {

    public static final int SHOW_BODY_EXAMINE = 0;
    public static final int SHOW_TEMPERATURE = 1;
    public static final int SHOW_BLOODO_EXAMINE = 2;
    public static final int SHOW_BLOOD_HEART_EXAMINE = 3;
    public static final int SHOW_FACE_TON_EXAMINE = 4;
    public static final int SHOW_QUETION_EXAMINE = 5;

    public static final String EXAMINE_MENU = "EXAMINE_MENU";

    boolean isShowDialog = false;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewDelegate.checkMenu(getIntent().getIntExtra(EXAMINE_MENU, 0));

        viewDelegate.notifyMenu();

        viewDelegate.cbVoice.setChecked(MyApplication.get().getCurrUser().isVoice());
    }

    public void iv_reportClick() {
        startActivity(ReportActivity.class, true);
    }


    @Override
    public void serialPortCallBack(String msg) {

        switch (msg) {
            case "AT+ASRRTCF"://人体成分
                viewDelegate.checkMenu(SHOW_BODY_EXAMINE);
                break;
            case "AT+ASRTW": //体温
                viewDelegate.checkMenu(SHOW_TEMPERATURE);
                break;
            case "AT+ASRXYG": //血氧
                viewDelegate.checkMenu(SHOW_BLOODO_EXAMINE);
                break;
            case "AT+ASRXY": //血压
                viewDelegate.checkMenu(SHOW_BLOOD_HEART_EXAMINE);
                break;
            case "AT+ASRMZSZ": //面诊舌诊
                viewDelegate.checkMenu(SHOW_FACE_TON_EXAMINE);
                break;
            case "AT+ASRZYTXBS": //中医体质辨识
                viewDelegate.checkMenu(SHOW_QUETION_EXAMINE);
                break;
            case "AT+ASRQX":// 取消
                if (isShowDialog) {
                    dialog.dismiss();
                }
                break;
            case "AT+ASRJS": //结束
                if (isShowDialog) {
                    MyApplication.get().logout();
                    finish();
                }
            default:
                viewDelegate.serialPortCallBack(msg);
                break;
        }

    }

    @Override
    public void serialPortIng(String msg) {
        viewDelegate.serialPortIng(msg);
    }

    public void notifyMenu() {
        viewDelegate.notifyMenu();
    }

    @OnClick(R.id.iv_logout)
    public void iv_logoutClick() {
        isShowDialog = true;
        if (dialog == null) {
            dialog = DialogUtils.showExitDialog(this, new Action0() {
                @Override
                public void call() {
                    MyApplication.get().logout();
                    finish();
                }
            }, new Action0() {
                @Override
                public void call() {
                    isShowDialog = false;
                }
            });
        } else {
            dialog.show();
        }
    }

    public void showNextExamine() {
        viewDelegate.nextExamine();
    }

    @OnCheckedChanged(R.id.cb_voice)
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            SerialPortCmd.asr();
        } else {
            SerialPortCmd.stopAsr();
        }
    }

    public void showReport() {

        if (!MyApplication.get().getCurrUserReport().getBodyReport().isFinish() &&
                !MyApplication.get().getCurrUserReport().getBloodOxygenReport().isFinish() &&
                !MyApplication.get().getCurrUserReport().getBloodPressureReport().isFinish() &&
                !MyApplication.get().getCurrUserReport().getQuestionReport().isFinish()) {
            DialogUtils.showReportDialog(this, new Action0() {
                @Override
                public void call() {
                    viewDelegate.checkMenu(ExamineActivity.SHOW_QUETION_EXAMINE);
                }
            });
        } else {
            startActivity(Report1Activity.class, true);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}


