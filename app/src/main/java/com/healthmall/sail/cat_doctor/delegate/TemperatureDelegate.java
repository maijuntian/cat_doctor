package com.healthmall.sail.cat_doctor.delegate;

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
import com.healthmall.sail.cat_doctor.bean.TemperatureReport;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.utils.VoiceMamanger;
import com.healthmall.sail.cat_doctor.widget.ProgressView;
import com.healthmall.sail.cat_doctor.widget.TemperatureView;
import com.healthmall.sail.cat_doctor.widget.TipPopWin;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import butterknife.Bind;

/**
 * Created by mai on 2018/1/5.
 */
public class TemperatureDelegate extends AppDelegate {
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
    @Bind(R.id.rl_step23)
    RelativeLayout rlStep23;
    @Bind(R.id.tv_temp_area_nomal)
    TextView tvTempAreaNomal;
    @Bind(R.id.tv_temp_area_high1)
    TextView tvTempAreaHigh1;
    @Bind(R.id.tv_temp_area_high2)
    TextView tvTempAreaHigh2;
    @Bind(R.id.tv_temp_area_high3)
    TextView tvTempAreaHigh3;
    @Bind(R.id.tv_temp_view)
    TemperatureView tvTempView;

    TipPopWin step2PopWin, step3PopWin;
    public ProgressView pvProgress;
    public int currStep = -1;

    Handler showPopHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            step3PopWin.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        }
    };
    @Bind(R.id.iv_temp_tip)
    ImageView ivTempTip;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_examine_temp;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        tvTempView.setOnFinishListener(new TemperatureView.OnFinishListener() {
            @Override
            public void finish(float temp) {
                TemperatureReport.setTextTag(mContext, temp, tvTempAreaNomal, tvTempAreaHigh1, tvTempAreaHigh2, tvTempAreaHigh3);
            }
        });

        Glide.with(mContext).load(R.mipmap.temp_tip).into(ivTempTip);
    }

    public void hidePopWin() {
        if (step2PopWin != null)
            step2PopWin.dismiss();

        if (step3PopWin != null)
            step3PopWin.dismiss();
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

        tvTempAreaNomal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.mmmgray));
        tvTempAreaHigh1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.mmmgray));
        tvTempAreaHigh2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.mmmgray));
        tvTempAreaHigh3.setBackgroundColor(ContextCompat.getColor(mContext, R.color.mmmgray));

        tvTempView.clearTemp();


        if (step2PopWin == null) {
            step2PopWin = new TipPopWin(mContext, R.layout.dialog_tip_no_button);
            pvProgress = step2PopWin.getContentView().findViewById(R.id.pv_progress);
            ((TextView) step2PopWin.getContentView().findViewById(R.id.tv_content)).setText("您正在测量体温，请按下开关不放...");
            pvProgress.setOnFinishListener(new ProgressView.OnFinishListener() {
                @Override
                public void onFinish() { //完成了
                    showStep3Real(false);
                }
            });
        }
        step2PopWin.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);

        VoiceMamanger.speak("您正在测量体温，请按下开关不放");
        pvProgress.start();
    }

    public void showStep3(TemperatureReport temperatureReport) {
        tvTempView.setTemperature(Float.parseFloat(temperatureReport.getBm_bdtemp()));
        pvProgress.finish();
    }

    public void showStep3Real(boolean isDelayShow) {
        currStep = 3;
        rlStep1.setVisibility(View.GONE);
        rlStep23.setVisibility(View.VISIBLE);
        ivStep.setImageResource(R.mipmap.progress_3);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.step_select));

        if (step2PopWin != null)
            step2PopWin.dismiss();

        if (step3PopWin == null) {
            step3PopWin = new TipPopWin(mContext, R.layout.dialog_tip_result);
            step3PopWin.getContentView().findViewById(R.id.tv_next).setOnClickListener(mOnClickListener);
            step3PopWin.getContentView().findViewById(R.id.tv_reexamine).setOnClickListener(mOnClickListener);
            step3PopWin.getContentView().findViewById(R.id.tv_report).setOnClickListener(mOnClickListener);
        }
        if (isDelayShow) {
            showPopHandler.sendEmptyMessageDelayed(0, 500);
            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    step3PopWin.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
                }
            }, 1000);*/

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

    public void showStep3Init(TemperatureReport currTemperatureReport) {
        tvTempView.setTemperature(Float.parseFloat(currTemperatureReport.getBm_bdtemp()));
        showStep3Real(true);
    }
}
