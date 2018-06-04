package com.healthmall.sail.cat_doctor.base;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.activity.IndexActivity;
import com.mai.xmai_fast_lib.utils.MLog;
import com.mai.xmai_fast_lib.utils.XAppManager;

/**
 * Created by maijuntian on 2018/4/20.
 */
public class BaseExamineActivity <T extends BaseDelegate> extends BaseActivity<T>{

    private Handler handler = new Handler();
    private long time=1000*60*5;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                MLog.log("按下");
                handler.removeCallbacks(runnable);
                break;
            case MotionEvent.ACTION_UP:
                MLog.log("松手");
                startAD();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            MyApplication.get().logout();
            XAppManager.getInstance().finishALLActivityExcept(IndexActivity.class);
        }
    };
    public void startAD() {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, time);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startAD();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }


}
