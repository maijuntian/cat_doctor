package com.healthmall.sail.cat_doctor.test;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.widget.ScaningImageView;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import butterknife.Bind;

/**
 * Created by mai on 2017/12/8.
 */
public class TestDelegate extends AppDelegate {


    @Bind(R.id.tv_start_fat)
    TextView tvStartFat;
    @Bind(R.id.rl_tip)
    RelativeLayout rlTip;
    @Bind(R.id.iv_scaning)
    ScaningImageView ivScaning;
    @Bind(R.id.rl_examining)
    RelativeLayout rlExamining;
    @Bind(R.id.vp_content)
    ViewPager vpContent;
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.rl_result)
    RelativeLayout rlResult;
    @Bind(R.id.rl_step_fat)
    RelativeLayout rlStepFat;

    @Override
    public int getRootLayoutId() {
        return R.layout.viewstub_examine_body_fat;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ivScaning.startScaning();
            }
        }, 3000);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
