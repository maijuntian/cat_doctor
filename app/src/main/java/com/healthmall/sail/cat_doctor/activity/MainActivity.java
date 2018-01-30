package com.healthmall.sail.cat_doctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseActivity;
import com.healthmall.sail.cat_doctor.delegate.MainDelegate;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.utils.DialogUtils;
import com.healthmall.sail.cat_doctor.utils.VoiceMamanger;


import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import rx.functions.Action0;

/**
 * Created by mai on 2017/11/10.
 */
public class MainActivity extends BaseActivity<MainDelegate> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntent().getBooleanExtra("noVoice", false)) {
            VoiceMamanger.speak("欢迎来到猫博士体测机");
        }

        SerialPortCmd.asr();


    }

    @OnClick(R.id.iv_body)
    public void iv_bodyClick() {
        Bundle bundle = new Bundle();
        bundle.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_BODY_EXAMINE);
        startExamineActivity(bundle);
    }


    @OnClick(R.id.iv_temp)
    public void iv_tempClick() {
        Bundle bundle = new Bundle();
        bundle.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_TEMPERATURE);
        startExamineActivity(bundle);
    }

    @OnClick(R.id.iv_blood_o)
    public void iv_blood_oClick() {
        Bundle bundle = new Bundle();
        bundle.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_BLOODO_EXAMINE);
        startExamineActivity(bundle);
    }

    @OnClick(R.id.iv_bp_hp)
    public void iv_bp_hpClick() {
        Bundle bundle = new Bundle();
        bundle.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_BLOOD_HEART_EXAMINE);
        startExamineActivity(bundle);
    }

    @OnClick(R.id.iv_face_ton)
    public void iv_face_tonClick() {
        Bundle bundle = new Bundle();
        bundle.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_FACE_TON_EXAMINE);
        startExamineActivity(bundle);
    }

    @OnClick(R.id.iv_question)
    public void iv_questionClick() {
        Bundle bundle = new Bundle();
        bundle.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_QUETION_EXAMINE);
//        startActivity(ExamineActivity.class, bundle, true);
        startExamineActivity(bundle);
    }

    @OnClick(R.id.iv_logout)
    public void iv_logoutClick() {
        DialogUtils.showLogoutDialog(this, new Action0() {
            @Override
            public void call() {
                MyApplication.get().logout();
                finish();
            }
        });
    }

    @OnCheckedChanged(R.id.cb_voice)
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            SerialPortCmd.asr();
        } else {
            SerialPortCmd.stopAsr();
        }
    }

    public void startExamineActivity(Bundle bundle) {

        bundle.putBoolean("isVoice", viewDelegate.cbVoice.isChecked());
        startActivity(ExamineActivity.class, bundle, true);

       /* Intent intent = new Intent(this, ExamineActivity.class);

        Pair first = new Pair<>(viewDelegate.ivHeadIcon, ViewCompat.getTransitionName(viewDelegate.ivHeadIcon));

        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, first);

        ActivityCompat.startActivity(this,
                intent, transitionActivityOptions.toBundle());*/
    }

    @Override
    public void serialPortCallBack(String msg) {
        super.serialPortCallBack(msg);

        switch (msg) {
            case "AT+ASRRTCF"://人体成分
                iv_bodyClick();
                break;
            case "AT+ASRTW": //体温
                iv_tempClick();
                break;
            case "AT+ASRXYG": //血氧
                iv_blood_oClick();
                break;
            case "AT+ASRXY": //血压
                iv_bp_hpClick();
                break;
            case "AT+ASRMZSZ": //面诊舌诊
                iv_face_tonClick();
                break;
            case "AT+ASRZYTXBS": //中医体质辨识
                iv_questionClick();
                break;
        }
    }
}
