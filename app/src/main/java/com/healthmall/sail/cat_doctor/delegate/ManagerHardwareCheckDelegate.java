package com.healthmall.sail.cat_doctor.delegate;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.R;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import butterknife.Bind;

/**
 * Created by mai on 2017/11/21.
 */
public class ManagerHardwareCheckDelegate extends AppDelegate {
    @Bind(R.id.rb_check)
    public RadioButton rbCheck;
    @Bind(R.id.rb_calibration)
    public RadioButton rbCalibration;
    @Bind(R.id.rg_set)
    RadioGroup rgSet;
    @Bind(R.id.iv_tip)
    ImageView ivTip;
    @Bind(R.id.tv_height_tip)
    TextView tvHeightTip;
    @Bind(R.id.rl_height)
    RelativeLayout rlHeight;
    @Bind(R.id.tv_weight)
    TextView tvWeight;
    @Bind(R.id.tv_weight_tip)
    TextView tvWeightTip;
    @Bind(R.id.rl_weight)
    RelativeLayout rlWeight;
    @Bind(R.id.tv_bloodo_tip)
    TextView tvBloodoTip;
    @Bind(R.id.rl_bloodo)
    RelativeLayout rlBloodo;
    @Bind(R.id.tv_bp_hr_tip)
    TextView tvBpHrTip;
    @Bind(R.id.rl_bp_hr)
    RelativeLayout rlBpHr;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.tv_title)
    TextView tvTitle;


    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_hardware_check;
    }


    @Override
    public void initWidget() {
        super.initWidget();

        rgSet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_check) {
                    showCheck();
                } else {
                    showCalibration();
                }
            }
        });
    }

    public void showCheck() {
        ivTip.setVisibility(View.VISIBLE);
        ivTip.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.hardware_check));
        llContent.setVisibility(View.GONE);

    }

    public void showChecking() {

        tvTitle.setText("检测中，人不可站在机器上...");
        ivTip.setVisibility(View.GONE);
        llContent.setVisibility(View.VISIBLE);

        rlBloodo.setVisibility(View.VISIBLE);
        rlBpHr.setVisibility(View.VISIBLE);

        rlBpHr.setBackgroundResource(R.mipmap.checking);
        rlBloodo.setBackgroundResource(R.mipmap.checking);
        rlHeight.setBackgroundResource(R.mipmap.checking);
        rlWeight.setBackgroundResource(R.mipmap.checking);

        tvBloodoTip.setTextColor(ContextCompat.getColor(mContext, R.color.checking));
        tvBpHrTip.setTextColor(ContextCompat.getColor(mContext, R.color.checking));
        tvHeightTip.setTextColor(ContextCompat.getColor(mContext, R.color.checking));
        tvWeightTip.setTextColor(ContextCompat.getColor(mContext, R.color.checking));
        tvBloodoTip.setText("正在检测...");
        tvBpHrTip.setText("正在检测...");
        tvHeightTip.setText("正在检测...");
        tvWeightTip.setText("正在检测...");
    }

    public void showCalibration() {
        ivTip.setVisibility(View.VISIBLE);
        ivTip.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.hardware_calibration));
        llContent.setVisibility(View.GONE);
    }

    public void showCalibartioning() {

        tvTitle.setText("校准中，人不可站在机器上...");
        ivTip.setVisibility(View.GONE);
        llContent.setVisibility(View.VISIBLE);

        rlBloodo.setVisibility(View.GONE);

        rlHeight.setBackgroundResource(R.mipmap.checking);
        rlWeight.setBackgroundResource(R.mipmap.checking);
        rlBpHr.setBackgroundResource(R.mipmap.checking);

        tvHeightTip.setTextColor(ContextCompat.getColor(mContext, R.color.checking));
        tvWeightTip.setTextColor(ContextCompat.getColor(mContext, R.color.checking));
        tvBpHrTip.setTextColor(ContextCompat.getColor(mContext, R.color.checking));
        tvHeightTip.setText("正在校准...");
        tvWeightTip.setText("正在校准...");
        tvBpHrTip.setText("正在校准...");
    }

    public void showBloodOResult(boolean result, boolean isCheck) {

        if (result) {
            rlBloodo.setBackgroundResource(R.mipmap.check_succ);
            if(isCheck)
                tvBloodoTip.setText("检测成功");
            else
                tvBloodoTip.setText("校准成功");
            tvBloodoTip.setTextColor(ContextCompat.getColor(mContext, R.color.good_green));
        } else {
            rlBloodo.setBackgroundResource(R.mipmap.check_fail);
            if(isCheck)
                tvBloodoTip.setText("检测失败");
            else
                tvBloodoTip.setText("校准失败");
            tvBloodoTip.setTextColor(ContextCompat.getColor(mContext, R.color.scan_fail));
        }
    }

    public void showHeightResult(boolean result, boolean isCheck) {


        if (result) {
            rlHeight.setBackgroundResource(R.mipmap.check_succ);
            if(isCheck)
                tvHeightTip.setText("检测成功");
            else
                tvHeightTip.setText("校准成功");
            tvHeightTip.setTextColor(ContextCompat.getColor(mContext, R.color.good_green));
        } else {
            rlHeight.setBackgroundResource(R.mipmap.check_fail);
            if(isCheck)
                tvHeightTip.setText("检测失败");
            else
                tvHeightTip.setText("校准失败");
            tvHeightTip.setTextColor(ContextCompat.getColor(mContext, R.color.scan_fail));
        }
    }

    public void showWeightResult(boolean result, boolean isCheck) {
        if (result) {
            rlWeight.setBackgroundResource(R.mipmap.check_succ);
            if(isCheck)
                tvWeightTip.setText("检测成功");
            else
                tvWeightTip.setText("校准成功");
            tvWeightTip.setTextColor(ContextCompat.getColor(mContext, R.color.good_green));
        } else {
            rlWeight.setBackgroundResource(R.mipmap.check_fail);
            if(isCheck)
                tvWeightTip.setText("检测失败");
            else
                tvWeightTip.setText("校准失败");
            tvWeightTip.setTextColor(ContextCompat.getColor(mContext, R.color.scan_fail));
        }
    }

    public void showBPHRResult(boolean result, boolean isCheck) {
        if (result) {
            rlBpHr.setBackgroundResource(R.mipmap.check_succ);
            if(isCheck)
                tvBpHrTip.setText("检测成功");
            else
                tvBpHrTip.setText("校准成功");
            tvBpHrTip.setTextColor(ContextCompat.getColor(mContext, R.color.good_green));
        } else {
            rlBpHr.setBackgroundResource(R.mipmap.check_fail);
            if(isCheck)
                tvBpHrTip.setText("检测失败");
            else
                tvBpHrTip.setText("校准失败");
            tvBpHrTip.setTextColor(ContextCompat.getColor(mContext, R.color.scan_fail));
        }
    }
}
