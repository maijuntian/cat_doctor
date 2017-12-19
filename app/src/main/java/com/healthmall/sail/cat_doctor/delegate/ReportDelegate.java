package com.healthmall.sail.cat_doctor.delegate;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseDelegate;
import com.healthmall.sail.cat_doctor.bean.BloodOxygenReport;
import com.healthmall.sail.cat_doctor.bean.BloodPressureReport;
import com.healthmall.sail.cat_doctor.bean.BodyReport;
import com.healthmall.sail.cat_doctor.bean.FaceTonReport;
import com.healthmall.sail.cat_doctor.bean.QuestionReport;
import com.healthmall.sail.cat_doctor.bean.UserReport;
import com.healthmall.sail.cat_doctor.utils.CommonUtils;
import com.healthmall.sail.cat_doctor.utils.MGlide;

import butterknife.Bind;


public class ReportDelegate extends BaseDelegate {

    @Bind(R.id.tv_height)
    TextView tvHeight;
    @Bind(R.id.tv_weight)
    TextView tvWeight;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.tv_bmi)
    TextView tvBmi;
    @Bind(R.id.tv_examine_now_tip1)
    TextView tvExamineNowTip1;
    @Bind(R.id.iv_examine1)
    ImageView ivExamine1;
    @Bind(R.id.tv_bo_saturability)
    TextView tvBoSaturability;
    @Bind(R.id.tv_examine_now_tip2)
    TextView tvExamineNowTip2;
    @Bind(R.id.iv_examine2)
    ImageView ivExamine2;
    @Bind(R.id.tv_bh_dp)
    TextView tvBhDp;
    @Bind(R.id.tv_bh_sp)
    TextView tvBhSp;
    @Bind(R.id.tv_bh_hr)
    TextView tvBhHr;
    @Bind(R.id.tv_examine_now_tip3)
    TextView tvExamineNowTip3;
    @Bind(R.id.iv_examine3)
    ImageView ivExamine3;
    @Bind(R.id.tv_examine_now_tip4)
    TextView tvExamineNowTip4;
    @Bind(R.id.iv_examine4)
    ImageView ivExamine4;
    @Bind(R.id.iv_return)
    ImageView ivReturn;
    @Bind(R.id.ll_body_result)
    LinearLayout llBodyResult;
    @Bind(R.id.tv_examine_tip1)
    TextView tvExamineTip1;
    @Bind(R.id.ll_bloodo_result)
    LinearLayout llBloodoResult;
    @Bind(R.id.tv_examine_tip2)
    TextView tvExamineTip2;
    @Bind(R.id.ll_bp_hr_result)
    LinearLayout llBpHrResult;
    @Bind(R.id.tv_examine_tip3)
    TextView tvExamineTip3;
    @Bind(R.id.tv_examine_tip4)
    TextView tvExamineTip4;
    @Bind(R.id.ll_tip)
    LinearLayout llTip;
    @Bind(R.id.tv_result)
    TextView tvResult;
    @Bind(R.id.tv_examine_now)
    TextView tvExamineNow;
    @Bind(R.id.iv_face)
    ImageView ivFace;
    @Bind(R.id.iv_ton)
    ImageView ivTon;
    @Bind(R.id.ll_face_ton_result)
    LinearLayout llFaceTonResult;
    @Bind(R.id.tv_pr)
    TextView tvPr;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_health_report;
    }


    @Override
    public void initWidget() {
        super.initWidget();

        UserReport userReport = MyApplication.get().getCurrUserReport();

        initBody(userReport.getBodyReport());
        initBloodO(userReport.getBloodOxygenReport());
        initBloodHeart(userReport.getBloodPressureReport());
        initQuestion(userReport.getQuestionReport());

        initFaceTon(userReport.getFaceTonReport());
    }

    private void initBody(BodyReport bodyReport) {
        if (bodyReport.isFinish()) {
            tvExamineNowTip1.setVisibility(View.GONE);
            tvExamineTip1.setVisibility(View.GONE);
            ivExamine1.setVisibility(View.GONE);
            llBodyResult.setVisibility(View.VISIBLE);

            tvHeight.setText(bodyReport.getBm_height());
            tvWeight.setText(bodyReport.getBm_weight());
            tvAge.setText(bodyReport.getBm_bf_ba());
            tvBmi.setText(bodyReport.getBm_bf_bmi());

        } else {
            tvExamineNowTip1.setVisibility(View.VISIBLE);
            tvExamineTip1.setVisibility(View.VISIBLE);
            ivExamine1.setVisibility(View.VISIBLE);
            llBodyResult.setVisibility(View.GONE);

        }
    }

    private void initBloodO(BloodOxygenReport bloodOxygenReport) {
        if (bloodOxygenReport.isFinish()) {
            tvExamineNowTip2.setVisibility(View.GONE);
            tvExamineTip2.setVisibility(View.GONE);
            ivExamine2.setVisibility(View.GONE);
            llBloodoResult.setVisibility(View.VISIBLE);
            tvBoSaturability.setText(bloodOxygenReport.getBm_blox_spO2());
            tvPr.setText(bloodOxygenReport.getBm_blox_pr());
        } else {

            tvExamineNowTip2.setVisibility(View.VISIBLE);
            tvExamineTip2.setVisibility(View.VISIBLE);
            ivExamine2.setVisibility(View.VISIBLE);
            llBloodoResult.setVisibility(View.GONE);
        }
    }

    private void initBloodHeart(BloodPressureReport bloodPressureReport) {
        if (bloodPressureReport.isFinish()) {
            tvExamineNowTip3.setVisibility(View.GONE);
            tvExamineTip3.setVisibility(View.GONE);
            ivExamine3.setVisibility(View.GONE);
            llBpHrResult.setVisibility(View.VISIBLE);

            tvBhDp.setText(bloodPressureReport.getBm_sph_dp());
            tvBhSp.setText(bloodPressureReport.getBm_sph_sp());
            tvBhHr.setText(bloodPressureReport.getBm_sph_hr());
        } else {

            tvExamineNowTip3.setVisibility(View.VISIBLE);
            tvExamineTip3.setVisibility(View.VISIBLE);
            ivExamine3.setVisibility(View.VISIBLE);
            llBpHrResult.setVisibility(View.GONE);

        }
    }

    private void initQuestion(QuestionReport questionReport) {
        if (questionReport.isFinish()) {
            llTip.setVisibility(View.GONE);
            tvResult.setVisibility(View.VISIBLE);
            tvResult.setText("您的体质是" + questionReport.getQuestionResultName());
        } else {
            llTip.setVisibility(View.VISIBLE);
            tvResult.setVisibility(View.GONE);
        }
    }

    private void initFaceTon(FaceTonReport faceTonReport) {
        if (faceTonReport.isFinish()) {
            llFaceTonResult.setVisibility(View.VISIBLE);
            tvExamineNowTip4.setVisibility(View.GONE);
            tvExamineTip4.setVisibility(View.GONE);
            ivExamine4.setVisibility(View.GONE);

            MGlide.loadFile(CommonUtils.getFaceFilePath(), ivFace);
            MGlide.loadFile(CommonUtils.getTonFilePath(), ivTon);
        } else {
            llFaceTonResult.setVisibility(View.GONE);
            tvExamineNowTip4.setVisibility(View.VISIBLE);
            tvExamineTip4.setVisibility(View.VISIBLE);
            ivExamine4.setVisibility(View.VISIBLE);
        }
    }
}


