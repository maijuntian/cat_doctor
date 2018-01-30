package com.healthmall.sail.cat_doctor.delegate;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.utils.MGlide;
import com.healthmall.sail.cat_doctor.utils.VoiceMamanger;
import com.healthmall.sail.cat_doctor.widget.FaceTonSaveProgressView;
import com.healthmall.sail.cat_doctor.widget.FaceTonScaningImageView;
import com.healthmall.sail.cat_doctor.widget.TipPopWin;
import com.mai.xmai_fast_lib.baseadapter.BaseViewHolder;
import com.mai.xmai_fast_lib.baseadapter.BaseViewPagerAdapter;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;
import com.mai.xmai_fast_lib.utils.MLog;
import com.wonderkiln.camerakit.CameraView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mai on 2017/11/15.
 */
public class FaceTonDelegate extends AppDelegate {

    @Bind(R.id.cv_camera)
    public CameraView cvCamera;
    @Bind(R.id.iv_scan_point)
    ImageView ivScanPoint;
    @Bind(R.id.iv_scan_line)
    FaceTonScaningImageView ivScanLine;
    @Bind(R.id.tv_take_pic)
    TextView tvTakePic;
    @Bind(R.id.rl_camera)
    RelativeLayout rlCamera;
    @Bind(R.id.iv_tip)
    ImageView ivTip;
    @Bind(R.id.ll_tip)
    LinearLayout llTip;
    @Bind(R.id.iv_face_ton_result)
    ImageView ivFaceTonResult;
    @Bind(R.id.tv_face_ton_tip)
    TextView tvFaceTonTip;
    @Bind(R.id.rl_face_ton_result)
    RelativeLayout rlFaceTonResult;
    @Bind(R.id.ll_explain)
    LinearLayout llExplain;
    @Bind(R.id.tv_tip)
    TextView tvTip;
    @Bind(R.id.tv_explain_title)
    TextView tvExplainTitle;
    @Bind(R.id.iv_explain_img)
    ImageView ivExplainImg;
    @Bind(R.id.vp_result)
    public ViewPager vpResult;
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.rl_ton_result)
    RelativeLayout rlTonResult;
    @Bind(R.id.iv_picture)
    ImageView ivPicture;

    TipPopWin popWin;
    @Bind(R.id.ftsp_save_progress)
    FaceTonSaveProgressView ftspSaveProgress;
    @Bind(R.id.tv_step1)
    TextView tvStep1;
    @Bind(R.id.tv_step2)
    TextView tvStep2;
    @Bind(R.id.tv_step3)
    TextView tvStep3;
    @Bind(R.id.tv_step4)
    TextView tvStep4;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_step)
    ImageView ivStep;
    public int currStep = -1;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_examine_face_ton1;
    }

    public void hidePopWin() {
        if (popWin != null)
            popWin.dismiss();
    }

    public void showPopWin() {
        if (popWin == null) {
            popWin = new TipPopWin(mContext, R.layout.dialog_tip_result);
            popWin.getContentView().findViewById(R.id.tv_next).setOnClickListener(mOnClickListener);
            popWin.getContentView().findViewById(R.id.tv_reexamine).setOnClickListener(mOnClickListener);
            popWin.getContentView().findViewById(R.id.tv_report).setOnClickListener(mOnClickListener);
            ((TextView) popWin.getContentView().findViewById(R.id.tv_reexamine)).setText("重新拍摄");
        }
        popWin.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }


    @Override
    public void onResume() {
        MLog.log("开始摄像头");
        try {
            cvCamera.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        MLog.log("关闭摄像头");
        try {
            cvCamera.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    public void showFaceCamera() {
        currStep = 1;
        ivStep.setImageResource(R.mipmap.face_ton_progress1);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.step_select));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));
        tvStep4.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));

        llTip.setVisibility(View.VISIBLE);
        rlCamera.setVisibility(View.VISIBLE);
        rlFaceTonResult.setVisibility(View.GONE);
        llExplain.setVisibility(View.GONE);
        rlTonResult.setVisibility(View.GONE);
        ivPicture.setVisibility(View.GONE);
        ftspSaveProgress.setVisibility(View.GONE);

        ivScanPoint.setImageResource(R.mipmap.face_ton_scan_point);
        ivTip.setImageResource(R.mipmap.face_ton_face_tip);
        tvTip.setText(R.string.face_tip);
        tvTitle.setText(R.string.face_title);
        ivScanLine.startScaning();

        hidePopWin();

        VoiceMamanger.speak("请目视镜头，将完整的面部放于扫描区");
        SerialPortCmd.fillin();
    }

    public void showTonCamera() {

        currStep = 3;
        ivStep.setImageResource(R.mipmap.face_ton_progress3);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.step_select));
        tvStep4.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));

        llTip.setVisibility(View.VISIBLE);
        rlCamera.setVisibility(View.VISIBLE);
        rlFaceTonResult.setVisibility(View.GONE);
        llExplain.setVisibility(View.GONE);
        rlTonResult.setVisibility(View.GONE);
        ivPicture.setVisibility(View.GONE);
        ftspSaveProgress.setVisibility(View.GONE);

        ivScanPoint.setImageResource(R.mipmap.face_ton_scan_point_ton);
        ivTip.setImageResource(R.mipmap.face_ton_ton_tip);
        tvTip.setText(R.string.ton_tip);
        tvTitle.setText(R.string.ton_title);
        ivScanLine.startScaning();

        hidePopWin();

        VoiceMamanger.speak("请目视镜头，将舌头按照示例图放于扫描区");
        SerialPortCmd.fillin();
    }

    public void showSaving(Bitmap bmp) {
        ivScanLine.stopScaning();

        ivPicture.setVisibility(View.VISIBLE);
        ivPicture.setImageBitmap(bmp);
    }

    public void showSavingProgress(float progress) {
        ivScanLine.stopScaning();

        ftspSaveProgress.setVisibility(View.VISIBLE);
        ftspSaveProgress.setProgress(progress);
    }

    public void showSaveFaceSucc() {

        currStep = 2;
        ivStep.setImageResource(R.mipmap.face_ton_progress3);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.step_select));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));
        tvStep4.setTextColor(ContextCompat.getColor(mContext, R.color.step_unfinish));

        ivScanLine.stopScaning();
        llTip.setVisibility(View.GONE);
        rlCamera.setVisibility(View.GONE);
        rlFaceTonResult.setVisibility(View.VISIBLE);
        llExplain.setVisibility(View.VISIBLE);
        rlTonResult.setVisibility(View.GONE);

        MGlide.loadFace(mContext, ivFaceTonResult);
        ivExplainImg.setImageResource(R.mipmap.face_ton_face_example);

        showPopWin();
        ((TextView) popWin.getContentView().findViewById(R.id.tv_content)).setText(R.string.face_alarm);
        ((TextView) popWin.getContentView().findViewById(R.id.tv_next)).setText("拍摄舌像");
        popWin.getContentView().findViewById(R.id.tv_report).setVisibility(View.INVISIBLE);
        SerialPortCmd.stpFillin();
    }

    public void showSaveTonSucc(boolean isDelayShow) {

        currStep = 4;
        ivStep.setImageResource(R.mipmap.face_ton_progress4);
        tvStep1.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep2.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep3.setTextColor(ContextCompat.getColor(mContext, R.color.step_finish));
        tvStep4.setTextColor(ContextCompat.getColor(mContext, R.color.step_select));

        ivScanLine.stopScaning();
        llTip.setVisibility(View.GONE);
        rlCamera.setVisibility(View.GONE);
        rlFaceTonResult.setVisibility(View.GONE);
        llExplain.setVisibility(View.GONE);
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
                    MGlide.loadFace(mContext, ivResult);
                    viewHolder.setText(R.id.tv_face_ton_tip, "面诊照片")
                            .setText(R.id.tv_explain_title, "中医面向解说图")
                            .setImageDrawable(R.id.iv_explain_img, ContextCompat.getDrawable(mContext, R.mipmap.face_ton_face_example));
                } else {
                    MGlide.loadTon(mContext, ivResult);
                    viewHolder.setText(R.id.tv_face_ton_tip, "舌诊照片")
                            .setText(R.id.tv_explain_title, "舌像构造图")
                            .setImageDrawable(R.id.iv_explain_img, ContextCompat.getDrawable(mContext, R.mipmap.face_ton_ton_example));
                }

            }
        });

        vpResult.setCurrentItem(1);

        if (isDelayShow) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showPopWin();
                    ((TextView) popWin.getContentView().findViewById(R.id.tv_content)).setText(R.string.ton_alarm);
                    ((TextView) popWin.getContentView().findViewById(R.id.tv_next)).setText("测量下一项");
                    popWin.getContentView().findViewById(R.id.tv_report).setVisibility(View.VISIBLE);

                }
            }, 1000);
        } else {
            showPopWin();
            ((TextView) popWin.getContentView().findViewById(R.id.tv_content)).setText(R.string.ton_alarm);
            ((TextView) popWin.getContentView().findViewById(R.id.tv_next)).setText("测量下一项");
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
