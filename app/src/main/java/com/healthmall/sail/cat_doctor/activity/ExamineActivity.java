package com.healthmall.sail.cat_doctor.activity;

import android.os.Bundle;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseActivity;
import com.healthmall.sail.cat_doctor.delegate.ExamineDelegate;
import com.healthmall.sail.cat_doctor.utils.DialogUtils;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewDelegate.checkMenu(getIntent().getIntExtra(EXAMINE_MENU, 0));

        viewDelegate.notifyMenu();
    }

    public void iv_reportClick() {
        startActivity(ReportActivity.class, true);
    }


    @Override
    public void serialPortCallBack(String msg) {
        viewDelegate.serialPortCallBack(msg);
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
        DialogUtils.showLogoutDialog(this, new Action0() {
            @Override
            public void call() {
                MyApplication.get().logout();
                finish();
            }
        });
    }

    public void showNextExamine() {
        viewDelegate.nextExamine();
    }
}


