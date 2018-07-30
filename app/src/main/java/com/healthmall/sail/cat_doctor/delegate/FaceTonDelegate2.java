package com.healthmall.sail.cat_doctor.delegate;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.bean.BodyReport;
import com.healthmall.sail.cat_doctor.bean.FaceTonReport;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.utils.MGlide;
import com.healthmall.sail.cat_doctor.utils.VoiceMamanger;
import com.healthmall.sail.cat_doctor.widget.TipPopWin;
import com.mai.xmai_fast_lib.baseadapter.BaseViewHolder;
import com.mai.xmai_fast_lib.baseadapter.BaseViewPagerAdapter;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mai on 2017/11/15.
 */
public class FaceTonDelegate2 extends AppDelegate {

    @Bind(R.id.iv_step)
    ImageView ivStep;
    @Bind(R.id.tv_step1)
    TextView tvStep1;
    @Bind(R.id.tv_step2)
    TextView tvStep2;
    @Bind(R.id.tv_step3)
    TextView tvStep3;
    @Bind(R.id.vp_result)
    public ViewPager vpResult;
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.rl_ton_result)
    RelativeLayout rlTonResult;
    @Bind(R.id.tv_start)
    TextView tvStart;
    @Bind(R.id.rl_step1)
    RelativeLayout rlStep1;
    @Bind(R.id.iv_tip1)
    ImageView ivTip1;
    @Bind(R.id.iv_tip2)
    ImageView ivTip2;
    @Bind(R.id.rl_step2)
    RelativeLayout rlStep2;
    private PopupWindow popWin;

    Handler showPopHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showPopWin();
        }
    };
    public int currStep;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_examine_face_ton2;
    }

    @Override
    public void initWidget() {
        super.initWidget();
    }

    public void hidePopWin() {
        if (popWin != null) {
            showPopHandler.removeMessages(0);
            popWin.dismiss();
        }
    }

    public void showPopWin() {
        if (popWin == null) {
            popWin = new TipPopWin(mContext, R.layout.dialog_tip_result);
            popWin.getContentView().findViewById(R.id.tv_next).setOnClickListener(mOnClickListener);
            popWin.getContentView().findViewById(R.id.tv_reexamine).setOnClickListener(mOnClickListener);
            popWin.getContentView().findViewById(R.id.tv_report).setOnClickListener(mOnClickListener);
            popWin.getContentView().findViewById(R.id.tv_reexamine).setBackgroundResource(R.drawable.button_retake_pic_selector);
        }
        popWin.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

    public void showStep1() {
        currStep = 1;
        ivStep.setImageResource(R.mipmap.progress_1);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.step_select));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));

        rlStep1.setVisibility(View.VISIBLE);
        rlStep2.setVisibility(View.GONE);
        rlTonResult.setVisibility(View.GONE);

        Glide.with(mContext).load(R.mipmap.face_ton_tip).into(ivTip1);

        hidePopWin();

    }

    public void showStep2() {

        currStep = 2;
        BodyReport bodyReport = MyApplication.get().getCurrUserReport().getBodyReport();
        if (!TextUtils.isEmpty(bodyReport.getBm_height()) && bodyReport.getBm_height().length() >= 3) {
            SerialPortCmd.fsjhFaceTon(bodyReport.getBm_height().substring(0, 3));
        }

        ivStep.setImageResource(R.mipmap.progress_2);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.step_select));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));

        rlStep1.setVisibility(View.GONE);
        rlStep2.setVisibility(View.VISIBLE);
        rlTonResult.setVisibility(View.GONE);

        Glide.with(mContext).load(R.mipmap.look_head).into(ivTip2);
        hidePopWin();

        VoiceMamanger.speak("请目视镜头，按照示例图进行拍摄");
        SerialPortCmd.fillin();
    }


    public void showSaveTonSucc(boolean isDelayShow, final FaceTonReport report) {


        currStep = 3;
        ivStep.setImageResource(R.mipmap.face_ton_progress4);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.step_select));

        rlStep1.setVisibility(View.GONE);
        rlStep2.setVisibility(View.GONE);
        rlTonResult.setVisibility(View.VISIBLE);

        vpResult.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    ivLeft.setEnabled(false);
                    ivRight.setEnabled(true);

                    ivLeft.setImageResource(R.mipmap.examine_index_left_n);
                    ivRight.setImageResource(R.mipmap.examine_index_right_s);
                    if (popWin != null) {
                        ((TextView) popWin.getContentView().findViewById(R.id.tv_content)).setText(R.string.face_alarm);

                    }
                } else {
                    ivLeft.setEnabled(true);
                    ivRight.setEnabled(false);
                    ivLeft.setImageResource(R.mipmap.examine_index_left_s);
                    ivRight.setImageResource(R.mipmap.examine_index_right_n);
                    if (popWin != null)
                        ((TextView) popWin.getContentView().findViewById(R.id.tv_content)).setText(R.string.ton_alarm);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpResult.setAdapter(new BaseViewPagerAdapter<Object>(null) {
            @Override
            protected int bindLayoutId(int position) {
                return R.layout.item_face_ton;
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            protected void initView(Object data, BaseViewHolder viewHolder) {

                ImageView ivResult = viewHolder.findViewById(R.id.iv_face_ton_result);

                if (viewHolder.getPosition() == 0) {
                    MGlide.load(mContext, report.getFaceUrl(), ivResult);
                    viewHolder.setText(R.id.tv_face_ton_tip, "面诊照片")
                            .setText(R.id.tv_explain_title, "中医面向解说图")
                            .setImageDrawable(R.id.iv_explain_img, ContextCompat.getDrawable(mContext, R.mipmap.face_ton_face_example));
                } else {
                    MGlide.load(mContext, report.getTonUrl(), ivResult);
                    viewHolder.setText(R.id.tv_face_ton_tip, "舌诊照片")
                            .setText(R.id.tv_explain_title, "舌像构造图")
                            .setImageDrawable(R.id.iv_explain_img, ContextCompat.getDrawable(mContext, R.mipmap.face_ton_ton_example));
                }

            }
        });

        ivLeft.setImageResource(R.mipmap.examine_index_left_n);
        ivRight.setImageResource(R.mipmap.examine_index_right_s);
        ivLeft.setEnabled(false);
        ivRight.setEnabled(true);
        vpResult.setCurrentItem(0);

        if (isDelayShow) {
            showPopHandler.sendEmptyMessageDelayed(0, 500);
        } else {
            showPopWin();
            ((TextView) popWin.getContentView().findViewById(R.id.tv_content)).setText(R.string.ton_alarm);
            popWin.getContentView().findViewById(R.id.tv_report).setVisibility(View.VISIBLE);
        }
        SerialPortCmd.face4();
        SerialPortCmd.stpFillin();
    }


    @OnClick(R.id.iv_left)
    public void iv_leftClick() {
        if (vpResult != null) {
            vpResult.setCurrentItem(0);
        }
    }

    @OnClick(R.id.iv_right)
    public void iv_rightClick() {
        if (vpResult != null) {
            vpResult.setCurrentItem(1);
        }
    }
}
