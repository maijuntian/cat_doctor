package com.healthmall.sail.cat_doctor.activity;

import android.os.Bundle;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseActivity;
import com.healthmall.sail.cat_doctor.delegate.MainDelegate;
import com.healthmall.sail.cat_doctor.utils.DialogUtils;


import butterknife.OnClick;
import rx.functions.Action0;

/**
 * Created by mai on 2017/11/10.
 */
public class MainActivity extends BaseActivity<MainDelegate> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.iv_body)
    public void iv_bodyClick() {
        Bundle bundle = new Bundle();
        bundle.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_BODY_EXAMINE);
        startActivity(ExamineActivity.class, bundle, true);
    }

    @OnClick(R.id.iv_blood_o)
    public void iv_blood_oClick() {
        Bundle bundle = new Bundle();
        bundle.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_BLOODO_EXAMINE);
        startActivity(ExamineActivity.class, bundle, true);
    }

    @OnClick(R.id.iv_bp_hp)
    public void iv_bp_hpClick() {
        Bundle bundle = new Bundle();
        bundle.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_BLOOD_HEART_EXAMINE);
        startActivity(ExamineActivity.class, bundle, true);
    }

    @OnClick(R.id.iv_face_ton)
    public void iv_face_tonClick() {
        Bundle bundle = new Bundle();
        bundle.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_FACE_TON_EXAMINE);
        startActivity(ExamineActivity.class, bundle, true);
    }

    @OnClick(R.id.iv_question)
    public void iv_questionClick() {
        Bundle bundle = new Bundle();
        bundle.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_QUETION_EXAMINE);
        startActivity(ExamineActivity.class, bundle, true);
    }

    @OnClick(R.id.iv_logout)
    public void iv_logoutClick(){
        DialogUtils.showLogoutDialog(this, new Action0() {
            @Override
            public void call() {
                MyApplication.get().logout();
                finish();
            }
        });
    }
}
