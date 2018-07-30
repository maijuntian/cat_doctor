package com.healthmall.sail.cat_doctor.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseActivity;
import com.healthmall.sail.cat_doctor.base.MyThrowable;
import com.healthmall.sail.cat_doctor.delegate.IndexDelegate;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.utils.VoiceMamanger;

import java.util.concurrent.TimeUnit;

import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class IndexActivity extends BaseActivity<IndexDelegate> {

    Subscription sbTimer;

    boolean isPause = false;

    private final long delay = 60000;

    @OnClick(R.id.iv_video)
    public void iv_videoClick() {
        startActivity(VideoActivity.class, false);
    }

    @OnClick(R.id.iv_manager)
    public void iv_managerClick() {
        startActivity(ManagerLoginActivity.class, false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        startActivity(VideoActivity.class, false);

    }

    @Override
    public void loginSucc() {

        SerialPortCmd.scanSucc();

        if (!isPause) {
            if (MyApplication.get().getCurrUser().isAuthentication() || !TextUtils.isEmpty(MyApplication.get().getCurrUser().getBirthday())) {
                startActivity(TipActivity.class, false);
            } else {
                startActivity(InfoActivity.class, false);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SerialPortCmd.face0();
        sbTimer = Observable.timer(delay, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                startActivity(VideoActivity.class, false);
            }
        }, new MyThrowable());
        isPause = false;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (sbTimer != null)
            sbTimer.unsubscribe();
        isPause = true;
    }
}


