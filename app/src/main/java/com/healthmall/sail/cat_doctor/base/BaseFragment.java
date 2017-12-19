package com.healthmall.sail.cat_doctor.base;


import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.utils.DialogUtils;
import com.healthmall.sail.cat_doctor.widget.CountDownView;
import com.healthmall.sail.cat_doctor.widget.LoadingDialog;
import com.mai.xmai_fast_lib.mvvm.presenter.FragmentPresenter;
import com.mai.xmai_fast_lib.mvvm.view.IDelegate;

import rx.functions.Action0;

/**
 * Created by mai on 2017/11/15.
 */
public class BaseFragment<T extends IDelegate> extends FragmentPresenter<T> {

    private LoadingDialog loadingDialog;

    CountDownView countDownView;

//    Subscription sbError;

    long ERROR_DELAY = 120000; //异常倒计时时间

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


    /**
     * 弹出异常倒计时
     */
    protected void startErrorDelay() {

        if (countDownView != null)
            countDownView.cancel();

        countDownView = new CountDownView(getDelayTime(), 1000, null, new Action0() {
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

    protected long getDelayTime() {
        return ERROR_DELAY;
    }

    /**
     * 停止异常倒计时
     */
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
