package com.healthmall.sail.cat_doctor.delegate;

import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.bean.BodyReport;
import com.healthmall.sail.cat_doctor.widget.NoPaddingTextView;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mai on 2017/11/15.
 */
public class BodyDelegate extends AppDelegate {
    @Bind(R.id.vs_step2)
    ViewStub vsStep2;
    @Bind(R.id.vs_step3)
    ViewStub vsStep3;

    RelativeLayout rlStep2, rlStep3;
    @Bind(R.id.tv_step1_alarm)
    TextView tvStep1Alarm;
    @Bind(R.id.tv_step1)
    TextView tvStep1;
    @Bind(R.id.tv_step2)
    TextView tvStep2;
    @Bind(R.id.tv_step3)
    TextView tvStep3;
    @Bind(R.id.ll_step1)
    LinearLayout llStep1;

    ImageView ivBodyPerson;

    Step3ViewHolder step3ViewHolder;

    TextView tvAlarmStep2;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_examine_body1;
    }


    public void showStep1() {

        if (rlStep2 != null) {
            rlStep2.setVisibility(View.GONE);
        }

        if (rlStep3 != null) {
            rlStep3.setVisibility(View.GONE);
        }
        llStep1.setVisibility(View.VISIBLE);

        tvStep1Alarm.setVisibility(View.VISIBLE);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.mgray));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.mgray));
    }

    public void showStep2() {

        tvStep1Alarm.setVisibility(View.GONE);
        llStep1.setVisibility(View.GONE);
        if (rlStep2 == null) {

            View step2RootView = vsStep2.inflate();
            rlStep2 = step2RootView.findViewById(R.id.rl_step2);

            ivBodyPerson = step2RootView.findViewById(R.id.iv_body_person);
            tvAlarmStep2 = step2RootView.findViewById(R.id.tv_alarm_step2);
            tvAlarmStep2.setText(R.string.examine_body_tip21);

            final AnimationDrawable ad = (AnimationDrawable) ivBodyPerson.getDrawable();
            if (ad != null) {
                if (!ad.isRunning()) {
                    ad.start();
                }
            }
        } else {
            rlStep2.setVisibility(View.VISIBLE);
        }

        if (rlStep3 != null) {
            rlStep3.setVisibility(View.GONE);
        }
        ivBodyPerson.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.body_height_step));

        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.mgray));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.mgray));

    }

    public void showExamBody3() {

        tvAlarmStep2.setText(R.string.examine_body_tip23);
        ivBodyPerson.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.body_fat_step));
        final AnimationDrawable ad = (AnimationDrawable) ivBodyPerson.getDrawable();
        if (ad != null) {
            if (!ad.isRunning()) {
                ad.start();
            }
        }
    }

    public void showExamBody2() {

        tvAlarmStep2.setText(R.string.examine_body_tip22);
        ivBodyPerson.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.body_temp_step));
        final AnimationDrawable ad = (AnimationDrawable) ivBodyPerson.getDrawable();
        if (ad != null) {
            if (!ad.isRunning()) {
                ad.start();
            }
        }
    }

    public void showExaming(String text) {

        tvAlarmStep2.setText(text);
    }

    public void showStep3() {
        llStep1.setVisibility(View.GONE);
        tvStep1Alarm.setVisibility(View.GONE);

        if (rlStep2 != null) {
            rlStep2.setVisibility(View.GONE);
        }

        if (rlStep3 == null) {
            View step3View = vsStep3.inflate();
            rlStep3 = step3View.findViewById(R.id.rl_step3);
            step3ViewHolder = new Step3ViewHolder();
            ButterKnife.bind(step3ViewHolder, step3View);

            step3ViewHolder.ivNext.setOnClickListener(mOnClickListener);
            step3ViewHolder.ivReexamine.setOnClickListener(mOnClickListener);
        } else {
            rlStep3.setVisibility(View.VISIBLE);
        }

        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.mgray));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.mgray));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.black));

        if (!MyApplication.get().getCurrUserReport().isFinish()) {
            step3ViewHolder.ivNext.setVisibility(View.VISIBLE);
        } else {
            step3ViewHolder.ivNext.setVisibility(View.INVISIBLE);
        }
    }


    public void initBodyData(BodyReport bodyReport) {
        step3ViewHolder.tvScore.setText(bodyReport.getBm_bf_cs());
        step3ViewHolder.tvAge.setText(bodyReport.getBm_bf_ba());
        step3ViewHolder.tvTemp.setText(bodyReport.getBm_bdtemp() + "°");
        step3ViewHolder.tvMeta.setText(bodyReport.getBm_bf_bm());
        step3ViewHolder.tvWeight.setText(bodyReport.getBm_weight());
        step3ViewHolder.tvHeight.setText(bodyReport.getBm_height());
        step3ViewHolder.tvWeight2.setText(bodyReport.getBm_weight());
        step3ViewHolder.tvWeightNoFat.setText(bodyReport.getBm_bf_rfbw());
        step3ViewHolder.tvTonMuaRate.setText(bodyReport.getBm_bf_sm());
        step3ViewHolder.tvTonMine.setText(bodyReport.getBm_bf_bonysalts());
        step3ViewHolder.tvBodyWater.setText(bodyReport.getBm_bf_bmr());
        step3ViewHolder.tvCellWater.setText(bodyReport.getBm_bf_ef());
        step3ViewHolder.tvBmi.setText(bodyReport.getBm_bf_bmi());
        step3ViewHolder.tvFatPercent.setText(bodyReport.getBm_bf_bfr());
        step3ViewHolder.tvFatLevel.setText(bodyReport.getBm_bf_vfl());
        step3ViewHolder.tvArmLeftMua.setText(bodyReport.getBm_bf_lhmm());
        step3ViewHolder.tvArmRightMua.setText(bodyReport.getBm_bf_rhmm());
        step3ViewHolder.tvBodyMua.setText(bodyReport.getBm_bf_tmm());
        step3ViewHolder.tvLegLeftMua.setText(bodyReport.getBm_bf_llmm());
        step3ViewHolder.tvLegRightMua.setText(bodyReport.getBm_bf_rlmm());
    }

    public static class Step3ViewHolder {

        @Bind(R.id.tv_score)
        public TextView tvScore;
        @Bind(R.id.tv_age)
        public NoPaddingTextView tvAge;
        @Bind(R.id.tv_temp)
        public NoPaddingTextView tvTemp;
        @Bind(R.id.tv_meta)
        public NoPaddingTextView tvMeta;
        @Bind(R.id.tv_weight)
        public NoPaddingTextView tvWeight;
        @Bind(R.id.tv_height)
        public NoPaddingTextView tvHeight;
        @Bind(R.id.tv_weight2)
        public TextView tvWeight2;
        @Bind(R.id.tv_weight_no_fat)
        public TextView tvWeightNoFat;
        @Bind(R.id.tv_ton_mua_rate)
        public TextView tvTonMuaRate;
        @Bind(R.id.tv_ton_mine)
        public TextView tvTonMine;
        @Bind(R.id.tv_body_water)
        public TextView tvBodyWater;
        @Bind(R.id.tv_cell_water)
        public TextView tvCellWater;
        @Bind(R.id.tv_bmi)
        public TextView tvBmi;
        @Bind(R.id.tv_fat_percent)
        public TextView tvFatPercent;
        @Bind(R.id.tv_fat_level)
        public TextView tvFatLevel;
        @Bind(R.id.tv_arm_left_mua)
        public TextView tvArmLeftMua;
        @Bind(R.id.tv_arm_right_mua)
        public TextView tvArmRightMua;
        @Bind(R.id.tv_body_mua)
        public TextView tvBodyMua;
        @Bind(R.id.tv_leg_left_mua)
        public TextView tvLegLeftMua;
        @Bind(R.id.tv_leg_right_mua)
        public TextView tvLegRightMua;
        @Bind(R.id.tv_up_balance)
        public TextView tvUpBalance;
        @Bind(R.id.tv_down_balance)
        public TextView tvDownBalance;
        @Bind(R.id.tv_up_advance)
        public TextView tvUpAdvance;
        @Bind(R.id.tv_down_advance)
        public TextView tvDownAdvance;
        @Bind(R.id.rl_step3)
        public RelativeLayout rlStep3;
        @Bind(R.id.iv_next)
        public ImageView ivNext;
        @Bind(R.id.iv_reexamine)
        public ImageView ivReexamine;
    }
}
