package com.healthmall.sail.cat_doctor.delegate;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.bean.BloodPressureReport;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.utils.VoiceMamanger;
import com.healthmall.sail.cat_doctor.utils.WaveHelper;
import com.healthmall.sail.cat_doctor.widget.HeartRateView;
import com.healthmall.sail.cat_doctor.widget.NoPaddingTextView;
import com.healthmall.sail.cat_doctor.widget.ProgressView;
import com.healthmall.sail.cat_doctor.widget.TipPopWin;
import com.healthmall.sail.cat_doctor.widget.WaveView;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import butterknife.Bind;

/**
 * Created by mai on 2017/11/15.
 */
public class BloodHeartDelegate extends AppDelegate {

    @Bind(R.id.iv_step)
    ImageView ivStep;
    @Bind(R.id.tv_step1)
    TextView tvStep1;
    @Bind(R.id.tv_step2)
    TextView tvStep2;
    @Bind(R.id.tv_step3)
    TextView tvStep3;
    @Bind(R.id.tv_start)
    TextView tvStart;
    @Bind(R.id.rl_step1)
    RelativeLayout rlStep1;
    @Bind(R.id.wave1)
    WaveView wave1;
    @Bind(R.id.tv_high_pressure)
    NoPaddingTextView tvHighPressure;
    @Bind(R.id.rl_hp)
    RelativeLayout rlHp;
    @Bind(R.id.wave2)
    WaveView wave2;
    @Bind(R.id.tv_low_pressure)
    NoPaddingTextView tvLowPressure;
    @Bind(R.id.divider)
    View divider;
    @Bind(R.id.rl_heart_rate)
    RelativeLayout rlHeartRate;
    @Bind(R.id.tv_heart_rate_high)
    TextView tvHeartRateHigh;
    @Bind(R.id.tv_heart_rate_low)
    TextView tvHeartRateLow;
    @Bind(R.id.tv_result_tip1)
    TextView tvResultTip1;
    @Bind(R.id.tv_result_blood_pressure)
    TextView tvResultBloodPressure;
    @Bind(R.id.tv_result_blood_pressure_alarm)
    TextView tvResultBloodPressureAlarm;
    @Bind(R.id.rl_result1)
    RelativeLayout rlResult1;
    @Bind(R.id.tv_result_tip2)
    TextView tvResultTip2;
    @Bind(R.id.tv_result_heart_rate)
    TextView tvResultHeartRate;
    @Bind(R.id.tv_result_heart_rate_alarm)
    TextView tvResultHeartRateAlarm;
    @Bind(R.id.rl_result2)
    RelativeLayout rlResult2;
    @Bind(R.id.rl_step23)
    RelativeLayout rlStep23;
    @Bind(R.id.tv_heart_rate)
    NoPaddingTextView tvHeartRate;

    TipPopWin step2PopWin, step3PopWin;
    public ProgressView pvProgress;

    public WaveHelper waveHelper1, waveHelper2;
    @Bind(R.id.hrv_hr)
    HeartRateView hrvHr;
    public int currStep = -1;

    Handler showPopHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            step3PopWin.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        }
    };
    @Bind(R.id.iv_bp_hr_tip)
    ImageView ivBpHrTip;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_examine_blood_heart1;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        wave1.setWaveColor(Color.parseColor("#5Cfec600"), Color.parseColor("#5Cf83900"), Color.parseColor("#38fec600"), Color.parseColor("#38f83900"));
        wave2.setWaveColor(Color.parseColor("#5C00c6ff"), Color.parseColor("#5C0072ff"), Color.parseColor("#3800c6ff"), Color.parseColor("#380072ff"));

        waveHelper1 = new WaveHelper(wave1);
        waveHelper2 = new WaveHelper(wave2);

        Glide.with(mContext).load(R.mipmap.bp_tip).into(ivBpHrTip);
    }

    public void hidePopWin() {

        if (step2PopWin != null)
            step2PopWin.dismiss();

        if (step3PopWin != null)
            step3PopWin.dismiss();
        showPopHandler.removeMessages(0);
    }

    public void showStep1() {

        currStep = 1;
        rlStep1.setVisibility(View.VISIBLE);
        rlStep23.setVisibility(View.GONE);
        ivStep.setImageResource(R.mipmap.progress_1);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.step_select));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));

        hidePopWin();
    }

    public void showStep2() {
        currStep = 2;
        rlStep1.setVisibility(View.GONE);
        rlStep23.setVisibility(View.VISIBLE);
        ivStep.setImageResource(R.mipmap.progress_2);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.step_select));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));

        rlResult1.setVisibility(View.GONE);
        rlResult2.setVisibility(View.GONE);

        waveHelper1.start();
        waveHelper2.start();

        hrvHr.setProgressIndex(0);
        hrvHr.setProgress(0);
        tvHighPressure.setText("0");
        tvLowPressure.setText("0");
        tvHeartRate.setText("0");

        if (step2PopWin == null) {
            step2PopWin = new TipPopWin(mContext, R.layout.dialog_tip_no_button2);
            pvProgress = step2PopWin.getContentView().findViewById(R.id.pv_progress);
            ((TextView) step2PopWin.getContentView().findViewById(R.id.tv_content)).setText("您正在测量血压，请保持安静，放松心情...");
            ((TextView) step2PopWin.getContentView().findViewById(R.id.tv_tip)).setText(R.string.bloodpressure_tip);
            pvProgress.setOnFinishListener(new ProgressView.OnFinishListener() {
                @Override
                public void onFinish() { //完成了
                    showStep3Real(false);
                }
            });
        }
        step2PopWin.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);

        pvProgress.start();
        VoiceMamanger.speak("您正在测量血压，请保持安静，放松心情");

    }

    public void showStep3(BloodPressureReport bloodPressureReport) {
        initBloodPressure(bloodPressureReport);
        pvProgress.finish();
    }

    public void showStep3Init(BloodPressureReport bloodPressureReport) {

        waveHelper1.start();
        waveHelper2.start();
        initBloodPressure(bloodPressureReport);
        showStep3Real(true);
    }

    public void showStep3Real(boolean isDelayShow) {
        currStep = 3;
        rlStep1.setVisibility(View.GONE);
        rlStep23.setVisibility(View.VISIBLE);
        ivStep.setImageResource(R.mipmap.progress_3);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.step_select));

        rlResult1.setVisibility(View.VISIBLE);
        rlResult2.setVisibility(View.VISIBLE);

        if (step2PopWin != null)
            step2PopWin.dismiss();

        if (step3PopWin == null) {
            step3PopWin = new TipPopWin(mContext, R.layout.dialog_tip_result2);
            step3PopWin.getContentView().findViewById(R.id.tv_next).setOnClickListener(mOnClickListener);
            step3PopWin.getContentView().findViewById(R.id.tv_reexamine).setOnClickListener(mOnClickListener);
            step3PopWin.getContentView().findViewById(R.id.tv_report).setOnClickListener(mOnClickListener);
        }

        if (isDelayShow) {
            showPopHandler.sendEmptyMessageDelayed(0, 500);
        } else {
            step3PopWin.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        }

        if (MyApplication.get().getCurrUserReport().isFinish()) { //已全部完成
            step3PopWin.getContentView().findViewById(R.id.tv_next).setVisibility(View.INVISIBLE);
        } else {
            step3PopWin.getContentView().findViewById(R.id.tv_next).setVisibility(View.VISIBLE);
        }

        SerialPortCmd.face4();
    }

    public void initBloodPressure(BloodPressureReport bloodPressureReport) {


        int sp = Integer.parseInt(bloodPressureReport.getBm_sph_sp());
        int dp = Integer.parseInt(bloodPressureReport.getBm_sph_dp());

        int hr = Integer.parseInt(bloodPressureReport.getBm_sph_hr());
        tvHighPressure.setText(sp + "");
        tvLowPressure.setText(dp + "");
        tvHeartRate.setText(hr + "");

        if (sp >= 140) {
            waveHelper1.setData(0.61f + (sp - 140) * 0.39f / 60f);
        } else if (sp >= 90) {
            waveHelper1.setData(0.39f + (sp - 90) * 0.22f / 50f);
        } else {
            waveHelper1.setData(sp * 0.39f / 90f);
        }

        if (dp >= 90) {
            waveHelper2.setData(0.61f + (dp - 90) * 0.39f / 60f);
        } else if (dp >= 60) {
            waveHelper2.setData(0.39f + (dp - 60) * 0.22f / 30f);
        } else {
            waveHelper2.setData(dp * 0.39f / 90f);
        }


        if (sp >= 140 || dp >= 90) { //高血压
            tvResultBloodPressure.setText("偏高");
            tvResultBloodPressure.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
            tvResultBloodPressureAlarm.setText(R.string.bp_tip_high);
        } else if (sp <= 90 || dp <= 60) { //低血压
            tvResultBloodPressure.setText("偏低");
            tvResultBloodPressure.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
            tvResultBloodPressureAlarm.setText(R.string.bp_tip_low);
        } else { //血压正常
            tvResultBloodPressure.setText("正常");
            tvResultBloodPressure.setTextColor(ContextCompat.getColor(mContext, R.color.good_green));
            tvResultBloodPressureAlarm.setText(R.string.bp_tip_nomal);
        }

        hrvHr.setProgress(hr);
        if (hr > 100) {
            tvResultHeartRate.setText("过快");
            tvResultHeartRate.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
            tvResultHeartRateAlarm.setText(R.string.hr_tip_high);
        } else if (hr < 50) {
            tvResultHeartRate.setText("过缓");
            tvResultHeartRate.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
            tvResultHeartRateAlarm.setText(R.string.hr_tip_low);
        } else {
            tvResultHeartRate.setText("正常");
            tvResultHeartRate.setTextColor(ContextCompat.getColor(mContext, R.color.good_green));
            tvResultHeartRateAlarm.setText(R.string.hr_tip_high);
        }
    }
}
