package com.healthmall.sail.cat_doctor.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.healthmall.sail.cat_doctor.widget.LoadingDialog;
import com.mai.xmai_fast_lib.mvvm.presenter.ActivityPresenter;
import com.mai.xmai_fast_lib.mvvm.view.IDelegate;

/**
 * Created by mai on 2017/11/10.
 */
public class BaseActivity<T extends BaseDelegate> extends BaseSoftActivity<T> {

    private LoadingDialog loadingDialog;

    protected void showLoadingDialog() {
        if(loadingDialog == null)
            loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if(loadingDialog != null)
            loadingDialog.dismiss();
    }



    public BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                viewDelegate.notifySysTime();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(Intent.ACTION_TIME_TICK); //每分钟变化的action
        mFilter.addAction(Intent.ACTION_TIME_CHANGED); //设置了系统时间的action
        registerReceiver(timeReceiver, mFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(timeReceiver);
    }

}
