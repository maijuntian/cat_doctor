package com.healthmall.sail.cat_doctor.delegate;

import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.bean.BodyReport;
import com.healthmall.sail.cat_doctor.bean.BodyRespone;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.utils.CommonUtils;
import com.healthmall.sail.cat_doctor.utils.FloatUtils;
import com.healthmall.sail.cat_doctor.utils.VoiceMamanger;
import com.healthmall.sail.cat_doctor.widget.LeftBarProgress;
import com.healthmall.sail.cat_doctor.widget.LeftBarProgress2;
import com.healthmall.sail.cat_doctor.widget.NoPaddingTextView;
import com.healthmall.sail.cat_doctor.widget.ProgressView;
import com.healthmall.sail.cat_doctor.widget.RightBarProgress;
import com.healthmall.sail.cat_doctor.widget.RightBarProgress2;
import com.healthmall.sail.cat_doctor.widget.ScaningImageView;
import com.healthmall.sail.cat_doctor.widget.TipPopWin;
import com.mai.xmai_fast_lib.baseadapter.BaseViewHolder;
import com.mai.xmai_fast_lib.baseadapter.BaseViewPagerAdapter;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;
import com.mai.xmai_fast_lib.utils.MLog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mai on 2017/11/15.
 */
public class BodyDelegate extends AppDelegate {

    @Bind(R.id.iv_step)
    ImageView ivStep;
    @Bind(R.id.iv_hw_tip)
    ImageView ivHWTip;
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
    @Bind(R.id.vs_fat)
    ViewStub vsFat;

    RelativeLayout rlStepHw, rlStepFat;

    TipPopWin step2PopWin, step3PopWin, step5PopWin, step6PopWin;
    public ProgressView pvProgress, pvProgress5;

    ViewHolderHW viewHolderHW = new ViewHolderHW();
    ViewHolderFat viewHolderFat = new ViewHolderFat();

    public int currStep = -1;

    Handler showPopHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showstep6PopWin();
        }
    };

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_examine_body1;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        Glide.with(mContext).load(R.mipmap.height_tip).into(ivHWTip);
    }

    public void hidePopWin() {
        if (step2PopWin != null)
            step2PopWin.dismiss();

        if (step3PopWin != null)
            step3PopWin.dismiss();

        if (step5PopWin != null)
            step5PopWin.dismiss();

        if (step6PopWin != null)
            step6PopWin.dismiss();

        if (viewHolderFat.ivScaning != null)
            viewHolderFat.ivScaning.stopScaning();

        showPopHandler.removeMessages(0);
    }

    public void showStep1() {
        currStep = 1;

        if (rlStepHw != null) {
            rlStepHw.setVisibility(View.GONE);
        }
        if (rlStepFat != null) {
            rlStepFat.setVisibility(View.GONE);
        }
        rlStep1.setVisibility(View.VISIBLE);

        ivStep.setImageResource(R.mipmap.body_progress1);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.step_select));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));
        tvStep4.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));
        tvStep5.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));
        tvStep6.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));
        hidePopWin();

    }

    public void showStep2() {
        currStep = 2;

        if (rlStepHw == null) {
            View vsHwView = vsHw.inflate();
            rlStepHw = vsHwView.findViewById(R.id.rl_step_hw);
            ButterKnife.bind(viewHolderHW, vsHwView);
            viewHolderHW.setBg();
        } else {
            rlStepHw.setVisibility(View.VISIBLE);
        }
        if (rlStepFat != null) {
            rlStepFat.setVisibility(View.GONE);
        }

        rlStep1.setVisibility(View.GONE);

        ivStep.setImageResource(R.mipmap.body_progress2);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.step_select));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));
        tvStep4.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));
        tvStep5.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));
        tvStep6.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));

        if (step2PopWin == null) {
            step2PopWin = new TipPopWin(mContext, R.layout.dialog_tip_no_button);
            pvProgress = step2PopWin.getContentView().findViewById(R.id.pv_progress);
            ((TextView) step2PopWin.getContentView().findViewById(R.id.tv_content)).setText("您正在测量身高、体重，请站直站稳，目视前方...");
            pvProgress.setOnFinishListener(new ProgressView.OnFinishListener() {
                @Override
                public void onFinish() { //完成了
                    showStep3Real();
                }
            });
        }

        step2PopWin.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);

        pvProgress.start();
        VoiceMamanger.speak("您正在测量身高、体重，请站直站稳，目视前方");
    }

    public void showStep2ing(BodyReport bodyReport) {
        if (bodyReport.getBm_height() != null && bodyReport.getBm_weight() != null)
            viewHolderHW.setBodyReport(bodyReport);
    }

    public void showStep3(BodyReport bodyReport) {
        viewHolderHW.setBodyReport(bodyReport);
        pvProgress.finish();
    }

    public void showStep3Real() {
        currStep = 3;
        rlStep1.setVisibility(View.GONE);
        ivStep.setImageResource(R.mipmap.body_progress3);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.step_select));
        tvStep4.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));
        tvStep5.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));
        tvStep6.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));

        if (step2PopWin != null)
            step2PopWin.dismiss();

        if (step3PopWin == null) {
            step3PopWin = new TipPopWin(mContext, R.layout.dialog_tip_body_hw);
            step3PopWin.getContentView().findViewById(R.id.tv_examine_fat).setOnClickListener(mOnClickListener);
            step3PopWin.getContentView().findViewById(R.id.tv_reexamine).setOnClickListener(mOnClickListener);
        }
        step3PopWin.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);

    }

    public void showStep4() {
        currStep = 4;
        if (rlStepFat == null) {
            View vsFatView = vsFat.inflate();
            rlStepFat = vsFatView.findViewById(R.id.rl_step_fat);
            ButterKnife.bind(viewHolderFat, vsFatView);
            viewHolderFat.setBg();
            viewHolderFat.tvStartFat.setOnClickListener(mOnClickListener);
        } else {
            rlStepFat.setVisibility(View.VISIBLE);
        }
        if (rlStepHw != null) {
            rlStepHw.setVisibility(View.GONE);
        }
        ivStep.setImageResource(R.mipmap.body_progress4);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep4.setTextColor(ContextCompat.getColor(mContext, R.color.step_select));
        tvStep5.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));
        tvStep6.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));

        hidePopWin();

        viewHolderFat.showTip();
    }


    public void showStep5() {
        currStep = 5;
        ivStep.setImageResource(R.mipmap.body_progress5);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep4.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep5.setTextColor(ContextCompat.getColor(mContext, R.color.step_select));
        tvStep6.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));

        viewHolderFat.showExamining();
        if (step5PopWin == null) {
            step5PopWin = new TipPopWin(mContext, R.layout.dialog_tip_no_button2);
            pvProgress5 = step5PopWin.getContentView().findViewById(R.id.pv_progress);
            pvProgress5.setOnFinishListener(new ProgressView.OnFinishListener() {
                @Override
                public void onFinish() { //完成了
                    showStep6Real(false);
                }
            });
        }
        step5PopWin.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        pvProgress5.start();
        VoiceMamanger.speak("您正在测量体脂，请双手握住体脂测量器的把手保持不动");
    }

    public void showStep6() {
        MLog.log("---停止了---");
        pvProgress5.finish();
    }

    public void showStep6Real(boolean isDelayShow) {
        currStep = 6;
        rlStep1.setVisibility(View.GONE);
        if (rlStepFat == null) {
            View vsFatView = vsFat.inflate();
            rlStepFat = vsFatView.findViewById(R.id.rl_step_fat);
            ButterKnife.bind(viewHolderFat, vsFatView);
            viewHolderFat.setBg();
            viewHolderFat.tvStartFat.setOnClickListener(mOnClickListener);
        }


        hidePopWin();
        ivStep.setImageResource(R.mipmap.body_progress6);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep4.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep5.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep6.setTextColor(ContextCompat.getColor(mContext, R.color.step_select));

        viewHolderFat.showResult(isDelayShow);

    }

    private void showstep6PopWin() {
        if (step6PopWin == null) {
            step6PopWin = new TipPopWin(mContext, R.layout.dialog_tip_result_fat);
            step6PopWin.getContentView().findViewById(R.id.tv_next).setOnClickListener(mOnClickListener);
            step6PopWin.getContentView().findViewById(R.id.tv_reexamine_fat).setOnClickListener(mOnClickListener);
            step6PopWin.getContentView().findViewById(R.id.tv_reexamine_hw).setOnClickListener(mOnClickListener);
            step6PopWin.getContentView().findViewById(R.id.tv_report).setOnClickListener(mOnClickListener);
        }
        if (MyApplication.get().getCurrUserReport().isFinish()) { //已全部完成
            step6PopWin.getContentView().findViewById(R.id.tv_next).setVisibility(View.INVISIBLE);
        } else {
            step6PopWin.getContentView().findViewById(R.id.tv_next).setVisibility(View.VISIBLE);
        }
        step6PopWin.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

    public class ViewHolderHW {

        @Bind(R.id.iv_weight)
        public ImageView ivWeight;
        @Bind(R.id.iv_height)
        public ImageView ivHeight;
        @Bind(R.id.iv_bim)
        public ImageView ivBim;
        @Bind(R.id.tv_weight)
        public NoPaddingTextView tvWeight;
        @Bind(R.id.tv_height)
        public NoPaddingTextView tvHeight;
        @Bind(R.id.tv_bmi)
        public NoPaddingTextView tvBmi;
        @Bind(R.id.iv_bg)
        public ImageView ivBg;

        public void setBodyReport(BodyReport bodyReport) {
            float weight = Float.parseFloat(bodyReport.getBm_weight());
            float height = Float.parseFloat(bodyReport.getBm_height());
            float bmi = weight / ((height / 100f) * (height / 100f));
            bodyReport.setBm_bf_bmi(FloatUtils.round(bmi) + "");

            CommonUtils.moveWeightRuler(ivWeight, weight);
            CommonUtils.moveHeightRuler(ivHeight, height);
            CommonUtils.moveBmiRuler(ivBim, bmi);

            tvWeight.setText(weight + "");
            tvHeight.setText(height + "");
            tvBmi.setText(bodyReport.getBm_bf_bmi());
        }

        public void setBg() {
            if (MyApplication.get().getCurrUser().getMemberSex() == 1) {
                ivBg.setImageResource(R.mipmap.body_man1);
            } else {
                ivBg.setImageResource(R.mipmap.body_woman1);
            }
        }
    }

    public class ViewHolderFat {

        @Bind(R.id.tv_start_fat)
        public TextView tvStartFat;
        @Bind(R.id.rl_tip)
        public RelativeLayout rlTip;
        @Bind(R.id.rl_examining)
        public RelativeLayout rlExamining;
        @Bind(R.id.iv_scaning)
        public ScaningImageView ivScaning;
        @Bind(R.id.vp_content)
        public ViewPager vpContent;
        @Bind(R.id.iv_left)
        public ImageView ivLeft;
        @Bind(R.id.iv_right)
        public ImageView ivRight;
        @Bind(R.id.rl_result)
        public RelativeLayout rlResult;
        @Bind(R.id.rl_step_fat)
        public RelativeLayout rlStepFat;
        @Bind(R.id.iv_bg)
        public ImageView ivBg;
        @Bind(R.id.iv_fat_tip)
        public ImageView ivFatTip;

        public void setBg() {
            if (MyApplication.get().getCurrUser().getMemberSex() == 1) {
                ivBg.setImageResource(R.mipmap.body_man1);
            } else {
                ivBg.setImageResource(R.mipmap.body_woman1);
            }

            Glide.with(mContext).load(R.mipmap.body_fat_tip).into(ivFatTip);
        }

        public void showResult(boolean isDelayShow) {
            rlTip.setVisibility(View.GONE);
            rlExamining.setVisibility(View.GONE);
            rlResult.setVisibility(View.VISIBLE);
            ivScaning.stopScaning();
            vpContent.setOffscreenPageLimit(5);
            vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 4) {
                        ivLeft.setImageResource(R.mipmap.examine_index_left_s);
                        ivRight.setImageResource(R.mipmap.examine_index_right_n);
                    } else {
                        if (position == 0) {
                            ivLeft.setImageResource(R.mipmap.examine_index_left_n);
                        } else {
                            ivLeft.setImageResource(R.mipmap.examine_index_left_s);
                        }
                        ivRight.setImageResource(R.mipmap.examine_index_right_s);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            vpContent.setAdapter(new BaseViewPagerAdapter<Object>(null) {
                @Override
                public int getCount() {
                    return 5;
                }

                @Override
                protected int bindLayoutId(int position) {
                    switch (position) {
                        case 0:
                            return R.layout.item_body_info1;
                        case 1:
                            return R.layout.item_body_info2;
                        case 2:
                            return R.layout.item_body_info3;
                        case 3:
                            return R.layout.item_body_info4;
                        case 4:
                            return R.layout.item_body_info5;
                    }
                    return 0;
                }

                @Override
                protected void initView(Object data, BaseViewHolder viewHolder) {
                    switch (viewHolder.getViewResId()) {
                        case R.layout.item_body_info1:
                            initInfo1(viewHolder);
                            break;
                        case R.layout.item_body_info2:
                            initInfo2(viewHolder);
                            break;
                        case R.layout.item_body_info3:
                            initInfo3(viewHolder);
                            break;
                        case R.layout.item_body_info4:
                            initInfo4(viewHolder);
                            break;
                        case R.layout.item_body_info5:
                            initInfo5(viewHolder);
                            break;
                    }
                }

                private void initInfo1(BaseViewHolder viewHolder) {
                    BodyReport bodyReport = MyApplication.get().getCurrUserReport().getBodyReport();

                    viewHolder.setText(R.id.tv_body_age, bodyReport.getBm_bf_ba())
                            .setText(R.id.tv_bm, bodyReport.getBm_bf_bm())
                            .setText(R.id.tv_score, bodyReport.getBm_bf_cs())
                            .setImageDrawable(R.id.iv_bg, ContextCompat.getDrawable(mContext, MyApplication.get().getCurrUser().getMemberSex() == 1 ? R.mipmap.body_info1_man : R.mipmap.body_info1_woman))
                            .setText(R.id.tv_height, bodyReport.getBm_height())
                            .setText(R.id.tv_weight, bodyReport.getBm_weight())
                            .setImageDrawable(R.id.iv_score, getResources().getDrawable(bodyReport.getScoreImage()));

                }

                private void initInfo2(BaseViewHolder viewHolder) {
                    BodyReport bodyReport = MyApplication.get().getCurrUserReport().getBodyReport();
                    BodyRespone bodyRespone = bodyReport.getBodyRespone();

                    viewHolder.setText(R.id.tv_bf, bodyReport.getBm_bf_bf())
                            .setText(R.id.tv_mw, bodyReport.getBm_bf_bonysalts())
                            .setImageDrawable(R.id.iv_bg, ContextCompat.getDrawable(mContext, MyApplication.get().getCurrUser().getMemberSex() == 1 ? R.mipmap.bofy_info2_man : R.mipmap.bofy_info2_woman))
                            .setText(R.id.tv_protein, bodyReport.getBm_bf_protein())
                            .setText(R.id.tv_tm, bodyReport.getBm_bf_tm());

                    LeftBarProgress lbpMucal = viewHolder.findViewById(R.id.lbp_mucal);
                    RightBarProgress rbpFat = viewHolder.findViewById(R.id.rbp_fat);

                    rbpFat.setRange(bodyRespone.getFatRate());
                    rbpFat.setProgress(Float.parseFloat(bodyReport.getBm_bf_bfr()));
                    lbpMucal.setRange(bodyRespone.getMuscleRate());
                    lbpMucal.setProgress(bodyReport.getMuscleRate());
                }

                private void initInfo3(BaseViewHolder viewHolder) {
                    BodyReport bodyReport = MyApplication.get().getCurrUserReport().getBodyReport();
                    BodyRespone bodyRespone = bodyReport.getBodyRespone();

                    viewHolder.setText(R.id.tv_hand_left, bodyReport.getBm_bf_lhmm())
                            .setText(R.id.tv_hand_right, bodyReport.getBm_bf_rhmm())
                            .setText(R.id.tv_leg_left, bodyReport.getBm_bf_llmm())
                            .setText(R.id.tv_leg_right, bodyReport.getBm_bf_rlmm())
                            .setText(R.id.tv_body, bodyReport.getBm_bf_tmm())
                            .setText(R.id.tv_up_balance, bodyRespone.getUpperLimbBalance())
                            .setText(R.id.tv_down_balance, bodyRespone.getLowerLimbBalance())
                            .setText(R.id.tv_up_advance, bodyRespone.getUpperLimbDeveloped())
                            .setText(R.id.tv_down_advance, bodyRespone.getLowerLimbDeveloped())
                            .setImageDrawable(R.id.iv_bg, ContextCompat.getDrawable(mContext, MyApplication.get().getCurrUser().getMemberSex() == 1 ? R.mipmap.body_info3_man : R.mipmap.body_info3_woman));

                }

                private void initInfo4(BaseViewHolder viewHolder) {
                    BodyReport bodyReport = MyApplication.get().getCurrUserReport().getBodyReport();
                    BodyRespone bodyRespone = bodyReport.getBodyRespone();

                    viewHolder.setText(R.id.tv_doo, bodyReport.getBm_bf_doo())
                            .setText(R.id.tv_vfl, bodyReport.getBm_bf_vfl())
                            .setText(R.id.tv_ed, bodyReport.getBm_bf_ed())
                            .setImageDrawable(R.id.iv_bg, ContextCompat.getDrawable(mContext, MyApplication.get().getCurrUser().getMemberSex() == 1 ? R.mipmap.body_info4_man : R.mipmap.body_info4_women));


                    LeftBarProgress2 lbpWater = viewHolder.findViewById(R.id.lbp_water);
                    RightBarProgress2 rbpBIM = viewHolder.findViewById(R.id.rbp_bim);

                    rbpBIM.setRange(bodyRespone.getBmiRate());
                    rbpBIM.setProgress(Float.parseFloat(bodyReport.getBm_bf_bmi()));
                    lbpWater.setRange(bodyRespone.getWaterRate());
                    lbpWater.setProgress(Float.parseFloat(bodyReport.getBm_bf_bmr()));
                }

                private void initInfo5(BaseViewHolder viewHolder) {
                    BodyReport bodyReport = MyApplication.get().getCurrUserReport().getBodyReport();
                    BodyRespone bodyRespone = bodyReport.getBodyRespone();

                    viewHolder.setImageDrawable(R.id.iv_bg, ContextCompat.getDrawable(mContext, bodyRespone.getBodyTypeImg()))
                            .setText(R.id.tv_wthr, bodyReport.getBm_bf_wthr())
                            .setText(R.id.tv_sdm, bodyReport.getBm_bf_sdm())
                            .setText(R.id.tv_sw, bodyReport.getBm_bf_sw())
                            .setText(R.id.tv_fc, bodyReport.getBm_bf_fc())
                            .setText(R.id.tv_body_type, bodyRespone.getWaistToHipRatioResult());

                }
            });
            if (isDelayShow) {
                showPopHandler.sendEmptyMessageDelayed(0, 500);
               /* new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showstep6PopWin();
                    }
                }, 1000);*/
            } else {
                showstep6PopWin();
            }
            SerialPortCmd.face4();
            ivLeft.setImageResource(R.mipmap.examine_index_left_n);
            ivRight.setImageResource(R.mipmap.examine_index_right_s);
        }

        public void showTip() {
            rlTip.setVisibility(View.VISIBLE);
            rlExamining.setVisibility(View.GONE);
            rlResult.setVisibility(View.GONE);
            ivScaning.stopScaning();
        }

        public void showExamining() {
            rlTip.setVisibility(View.GONE);
            rlExamining.setVisibility(View.VISIBLE);
            rlResult.setVisibility(View.GONE);
            ivScaning.startScaning();
        }

        @OnClick(R.id.iv_left)
        public void iv_leftClick() {
            if (vpContent.getCurrentItem() != 0) {
                vpContent.setCurrentItem(vpContent.getCurrentItem() - 1);
            }
        }

        @OnClick(R.id.iv_right)
        public void iv_rightClick() {
            if (vpContent.getCurrentItem() != 4) {
                vpContent.setCurrentItem(vpContent.getCurrentItem() + 1);
            }
        }
    }

}
