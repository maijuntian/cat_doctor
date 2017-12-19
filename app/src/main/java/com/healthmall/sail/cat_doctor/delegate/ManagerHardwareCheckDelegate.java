package com.healthmall.sail.cat_doctor.delegate;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    @Bind(R.id.iv_power)
    ImageView ivPower;
    @Bind(R.id.tv_body)
    TextView tvBody;
    @Bind(R.id.tv_blood_o)
    TextView tvBloodO;
    @Bind(R.id.tv_bp_hr)
    TextView tvBpHr;
    @Bind(R.id.ll_content)
    LinearLayout llContent;

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

    public void showChecking(){
        ivTip.setVisibility(View.GONE);
        llContent.setVisibility(View.VISIBLE);

        tvBloodO.setText("正在扫描");
        tvBody.setText("正在扫描");
        tvBpHr.setText("正在扫描");
        tvBloodO.setTextColor(ContextCompat.getColor(mContext, R.color.scaning));
        tvBody.setTextColor(ContextCompat.getColor(mContext, R.color.scaning));
        tvBpHr.setTextColor(ContextCompat.getColor(mContext, R.color.scaning));
    }

    public void showCalibration() {
        ivTip.setVisibility(View.VISIBLE);
        ivTip.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.hardware_calibration));
        llContent.setVisibility(View.GONE);
    }

    public void showCalibartioning(){
        ivTip.setVisibility(View.GONE);
        llContent.setVisibility(View.VISIBLE);

        tvBloodO.setText("正在扫描");
        tvBody.setText("正在扫描");
        tvBpHr.setText("正在扫描");
        tvBloodO.setTextColor(ContextCompat.getColor(mContext, R.color.scaning));
        tvBody.setTextColor(ContextCompat.getColor(mContext, R.color.scaning));
        tvBpHr.setTextColor(ContextCompat.getColor(mContext, R.color.scaning));

    }

    public void showBloodOResult(boolean result) {

        if (result) {
            tvBloodO.setText("扫描成功");
            tvBloodO.setTextColor(ContextCompat.getColor(mContext, R.color.scan_succ));
        } else {
            tvBloodO.setText("校准出错");
            tvBloodO.setTextColor(ContextCompat.getColor(mContext, R.color.scan_fail));
        }
    }

    public void showBodyResult(boolean result) {
        if (result) {
            tvBody.setText("扫描成功");
            tvBody.setTextColor(ContextCompat.getColor(mContext, R.color.scan_succ));
        } else {
            tvBody.setText("校准出错");
            tvBody.setTextColor(ContextCompat.getColor(mContext, R.color.scan_fail));
        }
    }

    public void showBPHRResult(boolean result) {
        if (result) {
            tvBpHr.setText("扫描成功");
            tvBpHr.setTextColor(ContextCompat.getColor(mContext, R.color.scan_succ));
        } else {
            tvBpHr.setText("校准出错");
            tvBpHr.setTextColor(ContextCompat.getColor(mContext, R.color.scan_fail));
        }
    }
}
