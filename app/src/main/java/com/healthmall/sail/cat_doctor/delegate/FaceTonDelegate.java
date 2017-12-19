package com.healthmall.sail.cat_doctor.delegate;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.bean.FaceTonReport;
import com.healthmall.sail.cat_doctor.utils.MGlide;
import com.healthmall.sail.cat_doctor.widget.FaceTonSaveProgressView;
import com.healthmall.sail.cat_doctor.widget.NoPaddingTextView;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;
import com.mai.xmai_fast_lib.utils.MLog;
import com.wonderkiln.camerakit.CameraView;

import butterknife.Bind;

/**
 * Created by mai on 2017/11/15.
 */
public class FaceTonDelegate extends AppDelegate {
    @Bind(R.id.cv_camera)
    public CameraView cvCamera;
    @Bind(R.id.iv_remake)
    ImageView ivRemake;
    @Bind(R.id.iv_next)
    ImageView ivNext;
    @Bind(R.id.tv_scan_bg)
    ImageView tvScanBg;
    @Bind(R.id.iv_example)
    ImageView ivExample;
    @Bind(R.id.iv_go)
    ImageView ivGo;
    @Bind(R.id.tv_tip1)
    TextView tvTip1;
    @Bind(R.id.tv_tip2)
    TextView tvTip2;
    @Bind(R.id.iv_face_area)
    ImageView ivFaceArea;
    @Bind(R.id.iv_ton_area)
    ImageView ivTonArea;
    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.rl_camera)
    RelativeLayout rlCamera;
    @Bind(R.id.iv_status)
    ImageView ivStatus;
    @Bind(R.id.fts_progress)
    FaceTonSaveProgressView ftsProgress;
    @Bind(R.id.tv_progress)
    NoPaddingTextView tvProgress;
    @Bind(R.id.ll_save)
    LinearLayout llSave;
    @Bind(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @Bind(R.id.tv_camera_tip)
    TextView tvCameraTip;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_examine_face_ton;
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
        tvTip1.setVisibility(View.VISIBLE);
        tvTip2.setVisibility(View.VISIBLE);
        ivFaceArea.setVisibility(View.VISIBLE);
        rlCamera.setVisibility(View.VISIBLE);
        ivImage.setVisibility(View.GONE);
        rlBottom.setVisibility(View.GONE);
        ivStatus.setVisibility(View.GONE);
        llSave.setVisibility(View.GONE);
        ivTonArea.setVisibility(View.GONE);
        tvCameraTip.setVisibility(View.VISIBLE);
        ivExample.setImageResource(R.mipmap.face_ton_face_exam);
        tvCameraTip.setText(R.string.sys_is_analying_face);

    }

    public void showTonCamera() {
        tvTip1.setVisibility(View.VISIBLE);
        tvTip2.setVisibility(View.VISIBLE);
        ivFaceArea.setVisibility(View.GONE);
        rlCamera.setVisibility(View.VISIBLE);
        ivImage.setVisibility(View.GONE);
        rlBottom.setVisibility(View.GONE);
        ivStatus.setVisibility(View.GONE);
        llSave.setVisibility(View.GONE);
        ivTonArea.setVisibility(View.VISIBLE);
        tvCameraTip.setVisibility(View.VISIBLE);
        ivExample.setImageResource(R.mipmap.face_ton_ton_exam);
        tvCameraTip.setText(R.string.sys_is_analying_ton);
    }

    public void showSaving(FaceTonReport currFaceTonReport, Bitmap bmp) {
        tvTip1.setVisibility(View.GONE);
        tvTip2.setVisibility(View.GONE);
        rlCamera.setVisibility(View.GONE);
        ivImage.setVisibility(View.VISIBLE);
        ivStatus.setVisibility(View.VISIBLE);
        llSave.setVisibility(View.VISIBLE);
        rlBottom.setVisibility(View.GONE);
        MGlide.loadCircle(bmp, ivImage);
        ivStatus.setImageResource(R.mipmap.face_ton_geting);
        ftsProgress.setProgress(0);

        tvCameraTip.setVisibility(View.VISIBLE);
        if (currFaceTonReport.isFinishFace()) {
            tvCameraTip.setText(R.string.sys_is_saving_ton);
        } else {

            tvCameraTip.setText(R.string.sys_is_saving_face);
        }
    }

    public void showSavingProgress(float progress) {
        ftsProgress.setProgress(progress);

        tvProgress.setText(((int) progress) + "%");
    }

    public void showSaveFaceSucc() {
        ivStatus.setImageResource(R.mipmap.face_ton_save_succ);
        rlBottom.setVisibility(View.VISIBLE);
        ivNext.setVisibility(View.VISIBLE);
        ivNext.setImageResource(R.mipmap.examine_take_p_ton);
        ivRemake.setImageResource(R.mipmap.examine_retake_pic_face);

        tvCameraTip.setVisibility(View.GONE);
    }

    public void showSaveTonSucc() {
        tvCameraTip.setVisibility(View.GONE);
        ivStatus.setImageResource(R.mipmap.face_ton_save_succ);
        rlBottom.setVisibility(View.VISIBLE);
        ivRemake.setImageResource(R.mipmap.examine_retake_ton);

        if (!MyApplication.get().getCurrUserReport().isFinish()) {
            ivNext.setImageResource(R.mipmap.examine_start_next);
            ivNext.setVisibility(View.VISIBLE);
        } else {
            ivNext.setVisibility(View.GONE);
        }
    }

}
