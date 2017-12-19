package com.healthmall.sail.cat_doctor.base;

import android.os.CountDownTimer;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.activity.ExamineActivity;
import com.healthmall.sail.cat_doctor.utils.DialogUtils;
import com.healthmall.sail.cat_doctor.widget.CountDownView;
import com.healthmall.sail.cat_doctor.widget.LoadingDialog;
import com.mai.xmai_fast_lib.mvvm.presenter.FragmentPresenter;
import com.mai.xmai_fast_lib.mvvm.view.IDelegate;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by mai on 2017/11/15.
 */
public class BaseFragment<T extends IDelegate> extends FragmentPresenter<T> {

    private LoadingDialog loadingDialog;

    CountDownView countDownView;

//    Subscription sbError;

    final long ERROR_DELAY = 120000;

    protected void showLoadingDialog() {
        if (loadingDialog == null)
            loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null)
            loadingDialog.dismiss();
    }

    public void serialPortCallBack(String msg) {  //串口通信的回调
    }

    public void serialPortIng(String msg) {
    }


    protected void startErrorDelay() {

        if(countDownView != null)
            countDownView.cancel();

        countDownView = new CountDownView(ERROR_DELAY, 1000, null, new Action0() {
            @Override
            public void call() {
                error();
                DialogUtils.showErrorDialog(getActivity(), new Action0() {
                    @Override
                    public void call() {
                        logout();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        reExamine();
                    }
                });
            }
        });
        countDownView.start();
        /*
        if (sbError != null)
            sbError.unsubscribe();
        sbError = Observable.timer(ERROR_DELAY, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {

            }
        }, new MyThrowable());*/
    }

    protected void stopErrorDelay() {
        if (countDownView != null) {
            countDownView.cancel();
            countDownView = null;
        }
    }

    public void error() {

    }

    public void logout() {
        MyApplication.get().logout();
        getActivity().finish();
    }

    public void reExamine() {
    }
}
