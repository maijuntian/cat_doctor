package com.healthmall.sail.cat_doctor.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseActivity;
import com.healthmall.sail.cat_doctor.bean.QuestionReport;
import com.healthmall.sail.cat_doctor.bean.Symptom;
import com.healthmall.sail.cat_doctor.delegate.Report1Delegate;
import com.healthmall.sail.cat_doctor.http.CatDoctorApi;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.utils.Configs;
import com.healthmall.sail.cat_doctor.utils.DialogUtils;
import com.mai.xmai_fast_lib.basehttp.MParams;

import java.util.List;

import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;


public class Report1Activity extends BaseActivity<Report1Delegate> {

    boolean isShowDialog = false;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MyApplication.get().getCurrUserReport().isFinish()) {
            SerialPortCmd.face2();
        } else {
            SerialPortCmd.face3();
        }

        getResult();
    }

    private void getResult() {
        CatDoctorApi.getInstance().getSymptom(this).subscribe(new Action1<List<Symptom>>() {
            @Override
            public void call(List<Symptom> symptoms) {

                if (symptoms.size() > 2) {
                    symptoms = symptoms.subList(0, 2);
                }

                viewDelegate.initView(symptoms);

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    @OnClick({R.id.iv_logout, R.id.tv_exit})
    public void tv_exitClick() {
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

    @OnClick(R.id.tv_return)
    public void tv_returnClick() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("noVoice", true);
        startActivity(MainActivity.class, bundle, true);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.iv_examine_body:
                bundle.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_BODY_EXAMINE);
                break;
            case R.id.iv_examine_temp:
                bundle.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_TEMPERATURE);
                break;
            case R.id.iv_examine_bloodo:
                bundle.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_BLOODO_EXAMINE);
                break;
            case R.id.iv_examine_bp_hr:
                bundle.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_BLOOD_HEART_EXAMINE);
                break;
            case R.id.iv_examine_face_ton:
                bundle.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_FACE_TON_EXAMINE);
                break;
        }
        startActivity(ExamineActivity.class, bundle, true);
    }

    @OnClick({R.id.rb_result, R.id.rb_body, R.id.rb_temp, R.id.rb_bo, R.id.rb_bp, R.id.rb_face_ton, R.id.rb_question})
    public void rbClick(View v) {
        if (!Configs.useTemp) {
            switch (v.getId()) {
                case R.id.rb_result:
                    viewDelegate.lvReport.setSelection(0);
                    break;
                case R.id.rb_body:
                    viewDelegate.lvReport.setSelection(1);
                    break;
                case R.id.rb_bo:
                    viewDelegate.lvReport.setSelection(2);
                    break;
                case R.id.rb_bp:
                    viewDelegate.lvReport.setSelection(3);
                    break;
                case R.id.rb_face_ton:
                    viewDelegate.lvReport.setSelection(4);
                    break;
                case R.id.rb_question:
                    viewDelegate.lvReport.setSelection(5);
                    break;
            }
        } else {
            switch (v.getId()) {
                case R.id.rb_result:
                    viewDelegate.lvReport.setSelection(0);
                    break;
                case R.id.rb_body:
                    viewDelegate.lvReport.setSelection(1);
                    break;
                case R.id.rb_temp:
                    viewDelegate.lvReport.setSelection(2);
                    break;
                case R.id.rb_bo:
                    viewDelegate.lvReport.setSelection(3);
                    break;
                case R.id.rb_bp:
                    viewDelegate.lvReport.setSelection(4);
                    break;
                case R.id.rb_face_ton:
                    viewDelegate.lvReport.setSelection(5);
                    break;
                case R.id.rb_question:
                    viewDelegate.lvReport.setSelection(6);
                    break;
            }
        }
    }

    @Override
    public void serialPortCallBack(String msg) {
        super.serialPortCallBack(msg);

        switch (msg) {
            case "AT+ASRFHSY": //返回首页
                if (!isShowDialog) {
                    tv_returnClick();
                }
                break;
            case "AT+ASRJSTC": //结束体测
                if (!isShowDialog) {
                    tv_exitClick();
                }
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
                break;
        }
    }
}


