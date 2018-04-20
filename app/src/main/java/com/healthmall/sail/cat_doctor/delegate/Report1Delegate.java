package com.healthmall.sail.cat_doctor.delegate;

import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseDelegate;
import com.healthmall.sail.cat_doctor.bean.BloodPressureReport;
import com.healthmall.sail.cat_doctor.bean.BodyReport;
import com.healthmall.sail.cat_doctor.bean.BodyRespone;
import com.healthmall.sail.cat_doctor.bean.FaceTonReport;
import com.healthmall.sail.cat_doctor.bean.QuestionReport;
import com.healthmall.sail.cat_doctor.bean.Symptom;
import com.healthmall.sail.cat_doctor.bean.User;
import com.healthmall.sail.cat_doctor.bean.UserReport;
import com.healthmall.sail.cat_doctor.utils.Configs;
import com.healthmall.sail.cat_doctor.utils.FloatUtils;
import com.healthmall.sail.cat_doctor.utils.MGlide;
import com.healthmall.sail.cat_doctor.widget.BloodoHorizontalProgressView;
import com.healthmall.sail.cat_doctor.widget.BlueCircleProgressView;
import com.healthmall.sail.cat_doctor.widget.BlueHorizontalProgress;
import com.healthmall.sail.cat_doctor.widget.HorizontalProgress;
import com.mai.xmai_fast_lib.baseadapter.BaseListViewAdapter;
import com.mai.xmai_fast_lib.baseadapter.BaseViewHolder;
import com.mai.xmai_fast_lib.utils.MLog;

import java.util.List;

import butterknife.Bind;
import me.panpf.swsv.SpiderWebScoreView;


/**
 * 加上是否需要体温的判断
 */
public class Report1Delegate extends BaseDelegate {

    @Bind(R.id.rb_result)
    RadioButton rbResult;
    @Bind(R.id.rb_body)
    RadioButton rbBody;
    @Bind(R.id.rb_temp)
    RadioButton rbTemp;
    @Bind(R.id.rb_bo)
    RadioButton rbBo;
    @Bind(R.id.rb_bp)
    RadioButton rbBp;
    @Bind(R.id.rb_face_ton)
    RadioButton rbFaceTon;
    @Bind(R.id.rb_question)
    RadioButton rbQuestion;
    @Bind(R.id.lv_report)
    public ListView lvReport;

    boolean isShowAllResult = false;
    @Bind(R.id.iv_logout)
    ImageView ivLogout;
    @Bind(R.id.rl_top)
    RelativeLayout rlTop;
    @Bind(R.id.tv_exit)
    TextView tvExit;
    @Bind(R.id.tv_return)
    TextView tvReturn;
    @Bind(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @Bind(R.id.rg_index)
    RadioGroup rgIndex;

    BaseListViewAdapter<Object> adapter;

    public View.OnClickListener adapterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_more:
                    isShowAllResult = true;
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_report;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        rbResult.setChecked(true);
        if(!Configs.useTemp)
            rbTemp.setVisibility(View.GONE);
        lvReport.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                MLog.log("scrollState:" + scrollState);
                if (scrollState == 0) {
                    int firstVisibleItem = lvReport.getFirstVisiblePosition();
                    if(!Configs.useTemp){
                        switch (firstVisibleItem) {
                            case 0:
                                rbResult.setChecked(true);
                                break;
                            case 1:
                                rbBody.setChecked(true);
                                break;
                            case 2:
                                rbBo.setChecked(true);
                                break;
                            case 3:
                                rbBp.setChecked(true);
                                break;
                            case 4:
                                rbFaceTon.setChecked(true);
                                break;
                            case 5:
                                rbQuestion.setChecked(true);
                                break;
                        }
                    } else {
                        switch (firstVisibleItem) {
                            case 0:
                                rbResult.setChecked(true);
                                break;
                            case 1:
                                rbBody.setChecked(true);
                                break;
                            case 2:
                                rbTemp.setChecked(true);
                                break;
                            case 3:
                                rbBo.setChecked(true);
                                break;
                            case 4:
                                rbBp.setChecked(true);
                                break;
                            case 5:
                                rbFaceTon.setChecked(true);
                                break;
                            case 6:
                                rbQuestion.setChecked(true);
                                break;
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                MLog.log(firstVisibleItem + "   " + visibleItemCount + "  " + totalItemCount);

            }
        });
    }

    public void initView(final List<Symptom> symptoms) {

        final UserReport report = MyApplication.get().getCurrUserReport();

        adapter = new BaseListViewAdapter<Object>(null) {

            @Override
            public int getCount() {
                if(!Configs.useTemp)
                    return 6;
                return 7;
            }

            @Override
            protected int bindLayoutId(int position) {
                if(!Configs.useTemp){
                    switch (position) {
                        case 0:
                            return R.layout.item_report_result;
                        case 1:
                            if (report.getBodyReport().isFinish()) {
                                return R.layout.item_report_body;
                            } else {
                                return R.layout.item_report_body_un;
                            }
                        case 2:
                            if (report.getBloodOxygenReport().isFinish()) {
                                return R.layout.item_report_bloodo;
                            } else {
                                return R.layout.item_report_bloodo_un;
                            }
                        case 3:
                            if (report.getBloodPressureReport().isFinish()) {
                                return R.layout.item_report_bp_hr;
                            } else {
                                return R.layout.item_report_bp_hr_un;
                            }
                        case 4:
                            if (report.getFaceTonReport().isFinish()) {
                                return R.layout.item_report_face_ton;
                            } else {
                                return R.layout.item_report_face_ton_un;
                            }
                        case 5:
                            if (report.getQuestionReport().isFinish()) {
                                return R.layout.item_report_question;
                            } else {
                                return R.layout.item_report_question_un;
                            }
                    }
                }
                switch (position) {
                    case 0:
                        return R.layout.item_report_result;
                    case 1:
                        if (report.getBodyReport().isFinish()) {
                            return R.layout.item_report_body;
                        } else {
                            return R.layout.item_report_body_un;
                        }
                    case 2:
                        if (report.getTemperatureReport().isFinish()) {
                            return R.layout.item_report_temp;
                        } else {
                            return R.layout.item_report_temp_un;
                        }
                    case 3:
                        if (report.getBloodOxygenReport().isFinish()) {
                            return R.layout.item_report_bloodo;
                        } else {
                            return R.layout.item_report_bloodo_un;
                        }
                    case 4:
                        if (report.getBloodPressureReport().isFinish()) {
                            return R.layout.item_report_bp_hr;
                        } else {
                            return R.layout.item_report_bp_hr_un;
                        }
                    case 5:
                        if (report.getFaceTonReport().isFinish()) {
                            return R.layout.item_report_face_ton;
                        } else {
                            return R.layout.item_report_face_ton_un;
                        }
                    case 6:
                        if (report.getQuestionReport().isFinish()) {
                            return R.layout.item_report_question;
                        } else {
                            return R.layout.item_report_question_un;
                        }
                }
                return 0;
            }

            @Override
            protected void initView(Object data, BaseViewHolder viewHolder) {
                if(!Configs.useTemp){
                    switch (viewHolder.getPosition()) {
                        case 0:
                            initResult(viewHolder);
                            break;
                        case 1:
                            initBody(viewHolder);
                            break;
                        case 2:
                            initBloodo(viewHolder);
                            break;
                        case 3:
                            initBloodPressure(viewHolder);
                            break;
                        case 4:
                            initFaceTon(viewHolder);
                            break;
                        case 5:
                            initQuestion(viewHolder);
                            break;
                    }
                } else {
                    switch (viewHolder.getPosition()) {
                        case 0:
                            initResult(viewHolder);
                            break;
                        case 1:
                            initBody(viewHolder);
                            break;
                        case 2:
                            initTemp(viewHolder);
                            break;
                        case 3:
                            initBloodo(viewHolder);
                            break;
                        case 4:
                            initBloodPressure(viewHolder);
                            break;
                        case 5:
                            initFaceTon(viewHolder);
                            break;
                        case 6:
                            initQuestion(viewHolder);
                            break;
                    }
                }
            }

            private void initResult(BaseViewHolder viewHolder) {

//                GridView gvType = viewHolder.findViewById(R.id.gv_type);
                ListView lvExpalin = viewHolder.findViewById(R.id.lv_explain);
                TextView tvType1 = viewHolder.findViewById(R.id.tv_type1);
                TextView tvType2 = viewHolder.findViewById(R.id.tv_type2);

                tvType1.setText(symptoms.get(0).getName());
                if (symptoms.size() > 1) {
                    tvType2.setText(symptoms.get(1).getName());
                    tvType2.setVisibility(View.VISIBLE);
                } else {
                    tvType2.setVisibility(View.GONE);
                }

               /* gvType.setAdapter(new BaseListViewAdapter<Symptom>(symptoms) {
                    @Override
                    protected int bindLayoutId(int position) {
                        return R.layout.item_item_result_text;
                    }

                    @Override
                    protected void initView(Symptom data, BaseViewHolder viewHolder) {
                        viewHolder.setText(R.id.tv_type, data.getName());
                    }
                });*/
                lvExpalin.setAdapter(new BaseListViewAdapter<Symptom>(symptoms) {
                    @Override
                    protected int bindLayoutId(int position) {
                        if (position % 2 == 0) {
                            return R.layout.item_item_result1;
                        } else {
                            return R.layout.item_item_result2;
                        }
                    }

                    @Override
                    public int getCount() {

                        if (mData.size() > 3 && !isShowAllResult) {
                            return 3;
                        }
                        return super.getCount();
                    }

                    @Override
                    protected void initView(Symptom data, BaseViewHolder viewHolder) {
                        String explain = data.getName() + "，中医证候名。" + data.getDescription();

                        Spannable span = new SpannableString(explain);
                        span.setSpan(new AbsoluteSizeSpan(60), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        span.setSpan(new AbsoluteSizeSpan(20), 1, explain.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        TextView tvExplain = viewHolder.findViewById(R.id.tv_explain);
                        tvExplain.setText(span);
                    }
                });

                if (isShowAllResult || symptoms.size() <= 3) {
                    viewHolder.findViewById(R.id.tv_more).setVisibility(View.GONE);
                } else {
                    viewHolder.findViewById(R.id.tv_more).setOnClickListener(adapterClickListener);
                }
            }

            private void initBody(BaseViewHolder viewHolder) {

                BodyReport bodyReport = report.getBodyReport();
                BodyRespone bodyRespone = bodyReport.getBodyRespone();

                User currUser = MyApplication.get().getCurrUser();

                if (report.getBodyReport().isFinish()) {

                    viewHolder.setText(R.id.tv_body_age, bodyReport.getBm_bf_ba())
                            .setText(R.id.tv_basic_meta, bodyReport.getBm_bf_bm())
                            .setText(R.id.tv_score, bodyReport.getBm_bf_cs())
                            .setText(R.id.tv_height, bodyReport.getBm_height())
                            .setText(R.id.tv_weight, bodyReport.getBm_weight())

                            //身体成分分析
                            .setText(R.id.tv_bf, bodyReport.getBm_bf_bf())
                            .setText(R.id.tv_mw, bodyReport.getBm_bf_mw())
                            .setText(R.id.tv_protein, bodyReport.getBm_bf_protein())
                            .setText(R.id.tv_tm, bodyReport.getBm_bf_tm())
                            .setText(R.id.tv_muscel_rate, bodyReport.getMuscleRate() + "")
                            .setText(R.id.tv_bfr, bodyReport.getBm_bf_bfr())
                            .setBackgroundDrawable(R.id.rl_body_bg1, ContextCompat.getDrawable(mContext, currUser.getMemberSex() == 1 ? R.mipmap.report_body_1 : R.mipmap.report_body_1_f))
                            .setBackgroundDrawable(R.id.rl_body_bg2, ContextCompat.getDrawable(mContext, currUser.getMemberSex() == 1 ? R.mipmap.report_body_2 : R.mipmap.report_body_2_f))
                            .setBackgroundDrawable(R.id.rl_body_bg3, ContextCompat.getDrawable(mContext, currUser.getMemberSex() == 1 ? R.mipmap.report_body_3 : R.mipmap.report_body_3_f))
                            .setBackgroundDrawable(R.id.rl_body_bg4, ContextCompat.getDrawable(mContext, currUser.getMemberSex() == 1 ? R.mipmap.report_body_4 : R.mipmap.report_body_4_f))
                            .setBackgroundDrawable(R.id.rl_body_bg5, ContextCompat.getDrawable(mContext, bodyRespone.getReportTypeImg()))

                            //肥胖水肿度
                            .setText(R.id.tv_vfl, bodyReport.getBm_bf_vfl())
                            .setText(R.id.tv_doo, bodyReport.getBm_bf_doo())
                            .setText(R.id.tv_ed, bodyReport.getBm_bf_ed())
                            .setText(R.id.tv_bmi, bodyReport.getBm_bf_bmi())
                            .setText(R.id.tv_bmr, bodyReport.getBm_bf_bmr())

                            //肌肉评估
                            .setText(R.id.tv_tmm, bodyReport.getBm_bf_tmm())
                            .setText(R.id.tv_lhmm, bodyReport.getBm_bf_lhmm())
                            .setText(R.id.tv_rhmm, bodyReport.getBm_bf_rhmm())
                            .setText(R.id.tv_llmm, bodyReport.getBm_bf_llmm())
                            .setText(R.id.tv_rlmm, bodyReport.getBm_bf_rlmm())

                            //体型判断
                            .setText(R.id.tv_wthr, bodyReport.getBm_bf_wthr())
                            .setText(R.id.tv_body_type, bodyRespone.getWaistToHipRatioResult())
                            .setText(R.id.tv_sw, bodyReport.getBm_bf_sw())
                            .setText(R.id.tv_sdm, bodyReport.getBm_bf_sdm())
                            .setText(R.id.tv_fc, bodyReport.getBm_bf_fc());

                    //身体成分
                    BlueHorizontalProgress hpBf = viewHolder.findViewById(R.id.hp_bf);
                    BlueHorizontalProgress hpMw = viewHolder.findViewById(R.id.hp_mw);
                    BlueHorizontalProgress hpProtein = viewHolder.findViewById(R.id.hp_protein);
                    BlueHorizontalProgress hpTm = viewHolder.findViewById(R.id.hp_tm);

                    hpBf.setMax(60);
                    hpBf.setProgress(Float.parseFloat(bodyReport.getBm_bf_bf()));
                    hpMw.setMax(70);
                    hpMw.setProgress(Float.parseFloat(bodyReport.getBm_bf_mw()));
                    hpProtein.setMax(40);
                    hpProtein.setProgress(Float.parseFloat(bodyReport.getBm_bf_protein()));
                    hpTm.setMax(140);
                    hpTm.setProgress(Float.parseFloat(bodyReport.getBm_bf_tm()));

                    HorizontalProgress hpMuscelRate = viewHolder.findViewById(R.id.hp_muscel_rate);
                    List<Float> mrRange = bodyRespone.getMuscleRate();
                    viewHolder.setText(R.id.tv_muscel_rate_nl, FloatUtils.round(mrRange.get(0)) + "");
                    viewHolder.setText(R.id.tv_muscel_rate_nh, FloatUtils.round(mrRange.get(1)) + "");
                    hpMuscelRate.setRange(mrRange);
                    hpMuscelRate.setMax(80);
                    hpMuscelRate.setProgress(bodyReport.getMuscleRate());

                    HorizontalProgress hpBfr = viewHolder.findViewById(R.id.hp_bfr);
                    List<Float> fatRange = bodyRespone.getFatRate();
                    viewHolder.setText(R.id.tv_bfr_nl, FloatUtils.round(fatRange.get(0)) + "");
                    viewHolder.setText(R.id.tv_bfr_nh, FloatUtils.round(fatRange.get(1)) + "");
                    hpBfr.setRange(fatRange);
                    hpBfr.setMax(50);
                    hpBfr.setProgress(Float.parseFloat(bodyReport.getBm_bf_bfr()));

                    //肥胖水肿度分析
                    BlueCircleProgressView cpVfl = viewHolder.findViewById(R.id.cp_vfl);
                    BlueCircleProgressView cpDoo = viewHolder.findViewById(R.id.cp_doo);
                    BlueCircleProgressView cpEd = viewHolder.findViewById(R.id.cp_ed);
                    cpVfl.setMax(10);
                    cpDoo.setMax(80);
                    cpEd.setMax(5);
                    cpVfl.setProgress(Float.parseFloat(bodyReport.getBm_bf_vfl()));
                    cpDoo.setProgress(Float.parseFloat(bodyReport.getBm_bf_doo()));
                    cpEd.setProgress(Float.parseFloat(bodyReport.getBm_bf_ed()));

                    HorizontalProgress hpBmi = viewHolder.findViewById(R.id.hp_bmi);
                    List<Float> bmiRange = bodyRespone.getBmiRate();
                    viewHolder.setText(R.id.tv_bmi_nl, FloatUtils.round(bmiRange.get(0)) + "");
                    viewHolder.setText(R.id.tv_bmi_nh, FloatUtils.round(bmiRange.get(1)) + "");
                    hpBmi.setRange(bmiRange);
                    hpBmi.setMax(50);
                    hpBmi.setProgress(Float.parseFloat(bodyReport.getBm_bf_bmi()));

                    HorizontalProgress hpWr = viewHolder.findViewById(R.id.hp_wr);
                    List<Float> wrRange = bodyRespone.getWaterRate().subList(1, 3);
                    viewHolder.setText(R.id.tv_wr_nl, FloatUtils.round(wrRange.get(0)) + "");
                    viewHolder.setText(R.id.tv_wr_nh, FloatUtils.round(wrRange.get(1)) + "");
                    hpWr.setRange(wrRange);
                    hpWr.setMax(66);
                    hpWr.setProgress(Float.parseFloat(bodyReport.getBm_bf_bmr()));

                    //肌肉评估
                    ImageView ivUpBal = viewHolder.findViewById(R.id.iv_up_bal);
                    ImageView ivUpBalUn = viewHolder.findViewById(R.id.iv_up_unbal);
                    ImageView ivUpStr = viewHolder.findViewById(R.id.iv_up_str);
                    ImageView ivUpStrUn = viewHolder.findViewById(R.id.iv_up_unstr);
                    if (bodyRespone.getUpperLimbBalance() != null && bodyRespone.getUpperLimbBalance().equals("均衡")) { //上肢均衡
                        ivUpBal.setVisibility(View.VISIBLE);
                        ivUpBalUn.setVisibility(View.GONE);
                    } else {
                        ivUpBal.setVisibility(View.GONE);
                        ivUpBalUn.setVisibility(View.VISIBLE);
                    }

                    if (bodyRespone.getUpperLimbDeveloped() != null && bodyRespone.getUpperLimbDeveloped().equals("发达")) { //上肢发达
                        ivUpStr.setVisibility(View.VISIBLE);
                        ivUpStrUn.setVisibility(View.GONE);
                    } else {
                        ivUpStr.setVisibility(View.GONE);
                        ivUpStrUn.setVisibility(View.VISIBLE);
                    }

                    ImageView ivDownBal = viewHolder.findViewById(R.id.iv_down_bal);
                    ImageView ivDownBalUn = viewHolder.findViewById(R.id.iv_down_unbal);
                    ImageView ivDownStr = viewHolder.findViewById(R.id.iv_down_str);
                    ImageView ivDownStrUn = viewHolder.findViewById(R.id.iv_down_unstr);
                    if (bodyRespone.getLowerLimbBalance() != null && bodyRespone.getLowerLimbBalance().equals("均衡")) { //下肢均衡
                        ivDownBal.setVisibility(View.VISIBLE);
                        ivDownBalUn.setVisibility(View.GONE);
                    } else {
                        ivDownBal.setVisibility(View.GONE);
                        ivDownBalUn.setVisibility(View.VISIBLE);
                    }

                    if (bodyRespone.getLowerLimbDeveloped() != null && bodyRespone.getLowerLimbDeveloped().equals("发达")) { //下肢发达
                        ivDownStr.setVisibility(View.VISIBLE);
                        ivDownStrUn.setVisibility(View.GONE);
                    } else {
                        ivDownStr.setVisibility(View.GONE);
                        ivDownStrUn.setVisibility(View.VISIBLE);
                    }

                    SpiderWebScoreView spiderWebMucel = viewHolder.findViewById(R.id.spiderWeb_mucel);

                    float tmm = Float.parseFloat(bodyReport.getBm_bf_tmm());
                    float lhmm = Float.parseFloat(bodyReport.getBm_bf_lhmm());
                    float rhmm = Float.parseFloat(bodyReport.getBm_bf_rhmm());
                    float llmm = Float.parseFloat(bodyReport.getBm_bf_llmm());
                    float rlmm = Float.parseFloat(bodyReport.getBm_bf_rlmm());

                    float[] values = new float[]{tmm, rhmm, rlmm, llmm, lhmm};
                    spiderWebMucel.setScores(FloatUtils.getMax(values), values);

                } else {
                    viewHolder.setOnClickListener(mOnClickListener, R.id.iv_examine_body)
                            .setBackgroundDrawable(R.id.ll_body_bg_un, ContextCompat.getDrawable(mContext, currUser.getMemberSex() == 1 ? R.mipmap.report_body_bg_un : R.mipmap.report_body_bg_un_f));
                }
            }

            private void initTemp(BaseViewHolder viewHolder) {

                User currUser = MyApplication.get().getCurrUser();
                if (report.getTemperatureReport().isFinish()) {
                    viewHolder.setBackgroundDrawable(R.id.rl_temp_bg, ContextCompat.getDrawable(mContext, currUser.getMemberSex() == 1 ? R.mipmap.report_temp_bg : R.mipmap.report_temp_bg_f))
                            .setText(R.id.tv_temp, report.getTemperatureReport().getBm_bdtemp() + "°C");
                } else {
                    viewHolder.setOnClickListener(mOnClickListener, R.id.iv_examine_temp)
                            .setBackgroundDrawable(R.id.ll_temp_bg_un, ContextCompat.getDrawable(mContext, currUser.getMemberSex() == 1 ? R.mipmap.report_temp_bg_un : R.mipmap.report_temp_bg_un_f));
                }
            }

            private void initBloodo(BaseViewHolder viewHolder) {
                User currUser = MyApplication.get().getCurrUser();
                if (report.getBloodOxygenReport().isFinish()) {
                    viewHolder.setBackgroundDrawable(R.id.ll_bloodo_bg, ContextCompat.getDrawable(mContext, currUser.getMemberSex() == 1 ? R.mipmap.report_bloodo_bg : R.mipmap.report_bloodo_bg_f));

                    int bo = Integer.parseInt(report.getBloodOxygenReport().getBm_blox_spO2());
                    int pr = Integer.parseInt(report.getBloodOxygenReport().getBm_blox_pr());

                    TextView tvBo = viewHolder.findViewById(R.id.tv_bo);
                    TextView tvPluse = viewHolder.findViewById(R.id.tv_pluse);
                    tvBo.setText(bo + "");
                    tvBo.setTextColor(report.getBloodOxygenReport().getBoTextColor(mContext));
                    tvPluse.setText(pr + "");
                    tvPluse.setTextColor(report.getBloodOxygenReport().getPluseTextColor(mContext));
                    BloodoHorizontalProgressView hpBo = viewHolder.findViewById(R.id.hp_bo);
                    BloodoHorizontalProgressView hpPluse = viewHolder.findViewById(R.id.hp_pluse);
                    hpBo.setProgress(bo);
                    hpPluse.setMax(138);
                    hpPluse.setPlus(1);
                    hpPluse.setRang(60, 100);
                    hpPluse.setProgress(pr);
                } else {
                    viewHolder.setOnClickListener(mOnClickListener, R.id.iv_examine_bloodo)
                            .setBackgroundDrawable(R.id.ll_bloodo_bg_un, ContextCompat.getDrawable(mContext, currUser.getMemberSex() == 1 ? R.mipmap.report_bo_bg_un : R.mipmap.report_bo_bg_un_f));
                }
            }

            private void initBloodPressure(BaseViewHolder viewHolder) {
                BloodPressureReport bloodPressureReport = report.getBloodPressureReport();
                User currUser = MyApplication.get().getCurrUser();

                if (bloodPressureReport.isFinish()) {
                    viewHolder.setBackgroundDrawable(R.id.ll_bp_hr_bg, ContextCompat.getDrawable(mContext, currUser.getMemberSex() == 1 ? R.mipmap.report_bp_hr_bg : R.mipmap.report_bp_hr_bg_f));

                    viewHolder.setImageDrawable(R.id.iv_shuzhangya, mContext.getResources().getDrawable(bloodPressureReport.getDpBg()))
                            .setImageDrawable(R.id.iv_shousuoya, mContext.getResources().getDrawable(bloodPressureReport.getSpBg()))
                            .setImageDrawable(R.id.iv_hr, mContext.getResources().getDrawable(bloodPressureReport.getHrBg()));

                    TextView tvShuzhangya = viewHolder.findViewById(R.id.tv_shuzhangya);
                    TextView tvShousuoya = viewHolder.findViewById(R.id.tv_shousuoya);
                    TextView tvHr = viewHolder.findViewById(R.id.tv_hr);
                    tvShuzhangya.setText(bloodPressureReport.getBm_sph_dp());
                    tvShousuoya.setText(bloodPressureReport.getBm_sph_sp());
                    tvHr.setText(bloodPressureReport.getBm_sph_hr());
                    tvShuzhangya.setCompoundDrawablesWithIntrinsicBounds(0, 0, bloodPressureReport.getDpIcon(), 0);
                    tvShousuoya.setCompoundDrawablesWithIntrinsicBounds(0, 0, bloodPressureReport.getSpIcon(), 0);
                    tvHr.setCompoundDrawablesWithIntrinsicBounds(0, 0, bloodPressureReport.getHrIcon(), 0);


                    TextView tvBpTip = viewHolder.findViewById(R.id.tv_bp_tip);
                    TextView tvHrTip = viewHolder.findViewById(R.id.tv_hr_tip);
                    tvBpTip.setText(bloodPressureReport.getBpText());
                    tvBpTip.setTextColor(bloodPressureReport.getBpTextColor(mContext));
                    tvHrTip.setText(bloodPressureReport.getHrText());
                    tvHrTip.setTextColor(bloodPressureReport.getHrTextColor(mContext));

                    viewHolder.setText(R.id.tv_bp_alarm, bloodPressureReport.getBpAlarm(mContext))
                            .setText(R.id.tv_hr_alarm, bloodPressureReport.getHrAlarm(mContext));
                } else {
                    viewHolder.setOnClickListener(mOnClickListener, R.id.iv_examine_bp_hr)
                            .setBackgroundDrawable(R.id.ll_bp_hr_bg_un, ContextCompat.getDrawable(mContext, currUser.getMemberSex() == 1 ? R.mipmap.report_bp_bg_un : R.mipmap.report_bp_bg_un_f));

                }

            }

            private void initFaceTon(BaseViewHolder viewHolder) {
                FaceTonReport faceTonReport = report.getFaceTonReport();
                if (faceTonReport.isFinish()) {
                    ImageView ivFace = viewHolder.findViewById(R.id.iv_face);
                    ImageView ivTon = viewHolder.findViewById(R.id.iv_ton);
                    MGlide.loadFace2(mContext, ivFace);
                    MGlide.loadTon2(mContext, ivTon);
                } else {
                    viewHolder.setOnClickListener(mOnClickListener, R.id.iv_examine_face_ton);
                }
            }

            private void initQuestion(BaseViewHolder viewHolder) {
                QuestionReport questionReport = report.getQuestionReport();
                viewHolder.setText(R.id.tv_result, questionReport.getQuestionResultNameReal())
                        .setBackgroundDrawable(R.id.rl_result_bg, ContextCompat.getDrawable(mContext, questionReport.getQuestionResultBg()));
            }
        };

        lvReport.setAdapter(adapter);
    }

}


