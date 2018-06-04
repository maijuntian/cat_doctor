package com.healthmall.sail.cat_doctor.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseActivity;
import com.healthmall.sail.cat_doctor.base.BaseExamineActivity;
import com.healthmall.sail.cat_doctor.delegate.MainDelegate;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.utils.DialogUtils;
import com.healthmall.sail.cat_doctor.utils.VoiceMamanger;


import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by mai on 2017/11/10.
 */
public class MainActivity extends BaseExamineActivity<MainDelegate> {


    boolean isShowDialog = false;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntent().getBooleanExtra("noVoice", false)) {
            VoiceMamanger.speak("欢迎来到猫博士体测机");

            DialogUtils.showVoiceDialog(this, new Action1<Boolean>() {
                @Override
                public void call(Boolean voice) {
                    MyApplication.get().getCurrUser().setVoice(voice);
                    if (voice && !MyApplication.get().getCurrUser().isUsed()) {
                        DialogUtils.showVoiceTipDialog(MainActivity.this);
                    }
                }
            });
        }
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

    @OnCheckedChanged(R.id.cb_voice)
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            SerialPortCmd.asr();
        } else {
            SerialPortCmd.stopAsr();
        }
    }

    public void startExamineActivity(Bundle bundle) {

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
        }
    }
}
