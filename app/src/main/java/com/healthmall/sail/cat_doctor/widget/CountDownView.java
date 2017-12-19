package com.healthmall.sail.cat_doctor.widget;

import android.os.CountDownTimer;
import android.widget.TextView;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by mai on 2017/12/15.
 */
public class CountDownView extends CountDownTimer {

    TextView tvView;

    Action0 finishListener;

    public CountDownView(long millisInFuture, long countDownInterval, TextView tvView, Action0 finishListener) {
        super(millisInFuture, countDownInterval);
        this.tvView = tvView;

        if (tvView != null)
            tvView.setText(millisInFuture / 1000 + "s");

        this.finishListener = finishListener;
    }

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownView(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);

    }

    @Override
    public void onTick(long millisUntilFinished) {

        if (tvView != null) {
            tvView.setText(millisUntilFinished / 1000 + "s");
        }
    }

    @Override
    public void onFinish() {
        finishListener.call();
    }
}
