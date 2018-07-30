package com.healthmall.sail.cat_doctor.delegate;

import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.bean.BloodOxygenReport;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.utils.VoiceMamanger;
import com.healthmall.sail.cat_doctor.widget.NoPaddingTextView;
import com.healthmall.sail.cat_doctor.widget.ProgressView;
import com.healthmall.sail.cat_doctor.widget.PulseRateView;
import com.healthmall.sail.cat_doctor.widget.TipPopWin;
import com.healthmall.sail.cat_doctor.widget.WaterView;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import java.util.List;

import butterknife.Bind;

/**
 * Created by mai on 2017/11/15.
 */
public class BloodoDelegate extends AppDelegate {

    @Bind(R.id.tv_step1)
    TextView tvStep1;
    @Bind(R.id.tv_step2)
    TextView tvStep2;
    @Bind(R.id.tv_step3)
    TextView tvStep3;
    @Bind(R.id.rl_step1)
    RelativeLayout rlStep1;
    @Bind(R.id.wv_blood_o)
    WaterView wvBloodO;
    @Bind(R.id.tv_blood_o)
    NoPaddingTextView tvBloodO;
    @Bind(R.id.divider)
    View divider;
    @Bind(R.id.prv_pluse_rate)
    public PulseRateView prvPluseRate;
    @Bind(R.id.rl_pluse_rate)
    RelativeLayout rlPluseRate;
    @Bind(R.id.tv_result_tip1)
    TextView tvResultTip1;
    @Bind(R.id.tv_bloodo_result)
    TextView tvBloodoResult;
    @Bind(R.id.tv_bloodo_result_alarm)
    TextView tvBloodoResultAlarm;
    @Bind(R.id.tv_result_tip2)
    TextView tvResultTip2;
    @Bind(R.id.tv_pluse_result)
    TextView tvPluseResult;
    @Bind(R.id.tv_pluse_result_alarm)
    TextView tvPluseResultAlarm;
    @Bind(R.id.rl_step23)
    RelativeLayout rlStep23;
    @Bind(R.id.iv_step)
    ImageView ivStep;

    TipPopWin step2PopWin, step3PopWin;
    public ProgressView pvProgress;
    @Bind(R.id.ll_bloodo_tip)
    LinearLayout llBloodoTip;
    @Bind(R.id.rl_result1)
    RelativeLayout rlResult1;
    @Bind(R.id.rl_result2)
    RelativeLayout rlResult2;
    public int currStep = -1;

    Handler showPopHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            step3PopWin.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        }
    };
    @Bind(R.id.iv_bloodo_tip)
    ImageView ivBloodoTip;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_examine_bloodo1;
    }

    @Override
    public void initWidget() {
        super.initWidget();

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

        Glide.with(mContext).load(R.mipmap.bloodo_tip).into(ivBloodoTip);
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

        wvBloodO.setProgressIndex(0);
        wvBloodO.setProgress(0);
        tvBloodO.setText(0 + "");

        prvPluseRate.clearData();

        if (step2PopWin == null) {
            step2PopWin = new TipPopWin(mContext, R.layout.dialog_tip_no_button);
            pvProgress = step2PopWin.getContentView().findViewById(R.id.pv_progress);
            pvProgress.setOnFinishListener(new ProgressView.OnFinishListener() {
                @Override
                public void onFinish() { //完成了
                    showStep3Real(false);
                }
            });
        }
        step2PopWin.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);

        VoiceMamanger.speak("您正在测量血氧，请保持手指不要抖动");
        pvProgress.start();

    }

    public void showStepIng(BloodOxygenReport bloodOxygenReport) {
        initBloodoIng(bloodOxygenReport);
    }

    public void showStep3(BloodOxygenReport bloodOxygenReport) {

        initBloodo(bloodOxygenReport);
        pvProgress.finish();
    }

    public void showStep3Init(BloodOxygenReport bloodOxygenReport) {

        prvPluseRate.setmData(bloodOxygenReport.getLastPluseDatas());

        initBloodo(bloodOxygenReport);

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
            step3PopWin = new TipPopWin(mContext, R.layout.dialog_tip_result);
            step3PopWin.getContentView().findViewById(R.id.tv_next).setOnClickListener(mOnClickListener);
            step3PopWin.getContentView().findViewById(R.id.tv_reexamine).setOnClickListener(mOnClickListener);
            step3PopWin.getContentView().findViewById(R.id.tv_report).setOnClickListener(mOnClickListener);
            ((TextView) step3PopWin.getContentView().findViewById(R.id.tv_content)).setText(R.string.bloodo_tip);
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

    public void setLastPluseDatas(List<Integer> mDatas) {
        prvPluseRate.setmData(mDatas);
    }

    public void initBloodoIng(BloodOxygenReport bloodOxygenReport) {

        wvBloodO.setProgress(Integer.parseInt(bloodOxygenReport.getBm_blox_spO2()));
        tvBloodO.setText(bloodOxygenReport.getBm_blox_spO2());

        prvPluseRate.addData(Integer.parseInt(bloodOxygenReport.getBm_blox_pr()));
    }

    public void initBloodo(BloodOxygenReport bloodOxygenReport) {
        prvPluseRate.setFinish(true);
        initBloodoIng(bloodOxygenReport);
        int spo2 = Integer.parseInt(bloodOxygenReport.getBm_blox_spO2());
        int pr = Integer.parseInt(bloodOxygenReport.getBm_blox_pr());

        if (spo2 >= 94) { //正常
            tvBloodoResult.setText("正常");
            tvBloodoResultAlarm.setText(R.string.bloodo_tip_nomal);
            tvBloodoResult.setTextColor(ContextCompat.getColor(mContext, R.color.good_green));
        } else if (spo2 >= 90) { //供氧不足
            tvBloodoResult.setText("供氧不足");
            tvBloodoResultAlarm.setText(R.string.bloodo_tip_low1);
            tvBloodoResult.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
        } else if (spo2 > 80) { //低血氧
            tvBloodoResult.setText("低血氧");
            tvBloodoResultAlarm.setText(R.string.bloodo_tip_low2);
            tvBloodoResult.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
        } else { //严重低血氧
            tvBloodoResult.setText("严重低血氧");
            tvBloodoResultAlarm.setText(R.string.bloodo_tip_low3);
            tvBloodoResult.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
        }

        if (pr > 100) {
            tvPluseResult.setText("偏高");
            tvPluseResultAlarm.setText(R.string.pluse_rate_tip_high);
        } else if (pr < 60) {
            tvPluseResult.setText("偏低");
            tvPluseResultAlarm.setText(R.string.pluse_rate_tip_low);
        } else {
            tvPluseResult.setText("正常");
            tvPluseResultAlarm.setText(R.string.pluse_rate_tip_nomal);
        }
    }
}
