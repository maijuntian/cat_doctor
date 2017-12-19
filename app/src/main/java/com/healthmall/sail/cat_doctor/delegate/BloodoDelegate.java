package com.healthmall.sail.cat_doctor.delegate;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.bean.BloodOxygenReport;
import com.healthmall.sail.cat_doctor.widget.CircleProgressView;
import com.healthmall.sail.cat_doctor.widget.NoPaddingTextView;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

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
    @Bind(R.id.iv_reexamine)
    ImageView ivReexamine;
    @Bind(R.id.iv_next)
    ImageView ivNext;
    @Bind(R.id.tv_step1_alarm)
    TextView tvStep1Alarm;
    @Bind(R.id.ll_step1)
    LinearLayout llStep1;
    @Bind(R.id.ll_step23)
    LinearLayout llStep23;
    @Bind(R.id.cpv_bloodo)
    CircleProgressView cpvBloodo;
    @Bind(R.id.tv_spo2)
    NoPaddingTextView tvSpo2;
    @Bind(R.id.tv_pr)
    NoPaddingTextView tvPr;
    @Bind(R.id.tv_bloodo)
    NoPaddingTextView tvBloodo;
    @Bind(R.id.tv_bloodo_tip)
    TextView tvBloodoTip;
    @Bind(R.id.rl_bar_step3)
    RelativeLayout rlBarStep3;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_examine_bloodo;
    }


    public void showStep1() {

        llStep1.setVisibility(View.VISIBLE);
        llStep23.setVisibility(View.GONE);
        rlBarStep3.setVisibility(View.GONE);
        tvStep1Alarm.setVisibility(View.VISIBLE);

        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.mgray));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.mgray));

        cpvBloodo.setProgressIndex(0);

    }

    public void showStep2(BloodOxygenReport bloodOxygenReport) {

        tvStep1Alarm.setVisibility(View.GONE);
        llStep1.setVisibility(View.GONE);
        llStep23.setVisibility(View.VISIBLE);
        rlBarStep3.setVisibility(View.GONE);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.mgray));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.mgray));

        initBloodoIng(bloodOxygenReport);
    }

    public void showStep3(BloodOxygenReport bloodOxygenReport) {

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

        initBloodo(bloodOxygenReport);
    }

    public void initBloodoIng(BloodOxygenReport bloodOxygenReport) {

        int spo2 = Integer.parseInt(bloodOxygenReport.getBm_blox_spO2());
        cpvBloodo.setProgress(spo2);
        tvBloodo.setText(spo2 + "%");
        tvSpo2.setText(spo2+"");
        tvPr.setText(bloodOxygenReport.getBm_blox_pr());

        tvBloodoTip.setText("正在测量...");
    }

    public void initBloodo(BloodOxygenReport bloodOxygenReport) {

        int spo2 = Integer.parseInt(bloodOxygenReport.getBm_blox_spO2());
        cpvBloodo.setProgress(spo2);
        tvBloodo.setText(spo2 + "%");
        tvSpo2.setText(spo2+"");
        tvPr.setText(bloodOxygenReport.getBm_blox_pr());

        if(spo2 < 94){
            tvBloodoTip.setText("血氧过低");
        } else if(spo2 < 90){
            tvBloodoTip.setText("血氧不正常");
        } else {
            tvBloodoTip.setText("血氧正常");
        }
    }
}
