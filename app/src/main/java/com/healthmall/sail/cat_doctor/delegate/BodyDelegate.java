package com.healthmall.sail.cat_doctor.delegate;

import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.bean.BodyReport;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import butterknife.Bind;

/**
 * Created by mai on 2017/11/15.
 */
public class BodyDelegate extends AppDelegate {


    @Bind(R.id.iv_step)
    ImageView ivStep;
    @Bind(R.id.tv_step1)
    TextView tvStep1;
    @Bind(R.id.tv_step2)
    TextView tvStep2;
    @Bind(R.id.tv_step3)
    TextView tvStep3;
    @Bind(R.id.tv_step4)
    TextView tvStep4;
    @Bind(R.id.tv_step5)
    TextView tvStep5;
    @Bind(R.id.tv_step6)
    TextView tvStep6;
    @Bind(R.id.tv_start)
    TextView tvStart;
    @Bind(R.id.rl_step1)
    RelativeLayout rlStep1;
    @Bind(R.id.vs_hw)
    ViewStub vsHw;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_examine_body1;
    }


    public void showStep1() {
    }

    public void showStep2() {


    }

    public void showExamBody3() {

    }

    public void showExamBody2() {

    }

    public void showExaming(String text) {

    }

    public void showStep3() {
    }


    public void initBodyData(BodyReport bodyReport) {
    }


}
