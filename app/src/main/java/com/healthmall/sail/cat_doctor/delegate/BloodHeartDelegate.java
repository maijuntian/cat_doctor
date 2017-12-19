package com.healthmall.sail.cat_doctor.delegate;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.bean.BloodPressureReport;
import com.healthmall.sail.cat_doctor.widget.ArcProgressView;
import com.healthmall.sail.cat_doctor.widget.NoPaddingTextView;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import butterknife.Bind;

/**
 * Created by mai on 2017/11/15.
 */
public class BloodHeartDelegate extends AppDelegate {
    @Bind(R.id.iv_reexamine)
    ImageView ivReexamine;
    @Bind(R.id.iv_next)
    ImageView ivNext;
    @Bind(R.id.tv_step1_alarm)
    TextView tvStep1Alarm;
    @Bind(R.id.ll_step1)
    LinearLayout llStep1;
    @Bind(R.id.tv_sp)
    NoPaddingTextView tvSp;
    @Bind(R.id.tv_dp)
    NoPaddingTextView tvDp;
    @Bind(R.id.tv_hr)
    NoPaddingTextView tvHr;
    @Bind(R.id.tv_pressure)
    NoPaddingTextView tvPressure;
    @Bind(R.id.tv_pressure_tip)
    TextView tvPressureTip;
    @Bind(R.id.apv_pressure)
    ArcProgressView apvPressure;
    @Bind(R.id.tv_hr2)
    NoPaddingTextView tvHr2;
    @Bind(R.id.tv_hr_tip)
    TextView tvHrTip;
    @Bind(R.id.apv_hr2)
    ArcProgressView apvHr2;
    @Bind(R.id.ll_step23)
    LinearLayout llStep23;
    @Bind(R.id.tv_step1)
    TextView tvStep1;
    @Bind(R.id.tv_step2)
    TextView tvStep2;
    @Bind(R.id.tv_step3)
    TextView tvStep3;
    @Bind(R.id.rl_bar_step3)
    RelativeLayout rlBarStep3;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_examine_blood_heart;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        apvHr2.setMaxProgress(150);
    }

    public void showStep1() {

        llStep1.setVisibility(View.VISIBLE);
        llStep23.setVisibility(View.GONE);
        tvStep1Alarm.setVisibility(View.VISIBLE);
        rlBarStep3.setVisibility(View.GONE);

        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.mgray));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.mgray));

        apvPressure.setProgressIndex(0);
        apvHr2.setProgressIndex(0);
    }

    public void showStep2() {

        tvStep1Alarm.setVisibility(View.GONE);
        llStep1.setVisibility(View.GONE);
        llStep23.setVisibility(View.VISIBLE);
        rlBarStep3.setVisibility(View.GONE);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.mgray));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.mgray));

        tvHrTip.setText("测量中...");
        tvPressureTip.setText("测量中...");

        tvPressure.setText("0/0");
        tvSp.setText(0 + "");
        tvDp.setText(0 + "");
        tvHr.setText(0 + "");

        tvHr2.setText(0 + "");
        apvHr2.setProgress(0);
        apvPressure.setProgress(0);
//        initBloodIng(bloodPressureReport);
    }

    public void showStep3(BloodPressureReport bloodPressureReport) {

        llStep1.setVisibility(View.GONE);
        tvStep1Alarm.setVisibility(View.GONE);
        llStep23.setVisibility(View.VISIBLE);
        rlBarStep3.setVisibility(View.VISIBLE);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.mgray));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.mgray));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.black));

        if (!MyApplication.get().getCurrUserReport().isFinish()) {
            ivNext.setVisibility(View.VISIBLE);
        } else {
            ivNext.setVisibility(View.INVISIBLE);
        }

        initBloodPressure(bloodPressureReport);
    }

    public void initBloodIng(BloodPressureReport bloodPressureReport) {

        tvSp.setText(bloodPressureReport.getBm_sph_sp());
        tvDp.setText(bloodPressureReport.getBm_sph_dp());
        tvHr.setText(bloodPressureReport.getBm_sph_hr());

        tvHr2.setText(bloodPressureReport.getBm_sph_hr());
        apvHr2.setProgress(Integer.parseInt(bloodPressureReport.getBm_sph_hr()));
    }

    public void initBloodPressure(BloodPressureReport bloodPressureReport) {
        int sp = Integer.parseInt(bloodPressureReport.getBm_sph_sp());
        int dp = Integer.parseInt(bloodPressureReport.getBm_sph_dp());

        int hr = Integer.parseInt(bloodPressureReport.getBm_sph_hr());

        tvSp.setText(bloodPressureReport.getBm_sph_sp());
        tvDp.setText(bloodPressureReport.getBm_sph_dp());
        tvHr.setText(bloodPressureReport.getBm_sph_hr());

        tvHr2.setText(bloodPressureReport.getBm_sph_hr());
        apvHr2.setProgress(hr);

        tvPressure.setText(bloodPressureReport.getBm_sph_sp() + "/" + bloodPressureReport.getBm_sph_dp());


        if (sp >= 140 || dp >= 90) { //高血压

            apvPressure.setProgress(80);
            tvPressureTip.setText("血压偏高");
            tvPressureTip.setTextColor(ContextCompat.getColor(mContext, R.color.bh_low_high));
        } else if (sp <= 90 || dp <= 60) { //低血压

            apvPressure.setProgress(20);
            tvPressureTip.setText("血压偏低");
            tvPressureTip.setTextColor(ContextCompat.getColor(mContext, R.color.bh_mid));
        } else { //血压正常
            apvPressure.setProgress(50);
            tvPressureTip.setText("血压正常");
            tvPressureTip.setTextColor(ContextCompat.getColor(mContext, R.color.bh_normal));
        }

        if (hr > 100) {
            tvHrTip.setText("心率偏高");
            tvPressureTip.setTextColor(ContextCompat.getColor(mContext, R.color.bh_low_high));
        } else if (hr < 50) {
            tvHrTip.setText("心率偏低");
            tvPressureTip.setTextColor(ContextCompat.getColor(mContext, R.color.bh_mid));
        } else {
            tvHrTip.setText("心率正常");
            tvPressureTip.setTextColor(ContextCompat.getColor(mContext, R.color.bh_normal));
        }

    }
}
