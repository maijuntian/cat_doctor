package com.healthmall.sail.cat_doctor.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.activity.ExamineActivity;
import com.healthmall.sail.cat_doctor.activity.ReportActivity;
import com.healthmall.sail.cat_doctor.base.BaseFragment;
import com.healthmall.sail.cat_doctor.bean.FaceTonReport;
import com.healthmall.sail.cat_doctor.delegate.FaceTonDelegate;
import com.healthmall.sail.cat_doctor.http.CatDoctorApi;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.utils.BitmapUtils;
import com.mai.xmai_fast_lib.basehttp.MParams;
import com.mai.xmai_fast_lib.basehttp.UploadListener;
import com.wonderkiln.camerakit.CameraKitEventCallback;
import com.wonderkiln.camerakit.CameraKitImage;

import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by mai on 2017/11/15.
 */
public class FaceTonFragment extends BaseFragment<FaceTonDelegate> {

    FaceTonReport currFaceTonReport;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currFaceTonReport = MyApplication.get().getCurrUserReport().getFaceTonReport();

        if (currFaceTonReport.isFinish()) {
            viewDelegate.showSaveTonSucc(true);
        } else {
            viewDelegate.showFaceCamera();
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (hidden) {
            viewDelegate.hidePopWin();
            viewDelegate.onPause();
            SerialPortCmd.stpFillin();
        } else {
            if (currFaceTonReport.isFinish()) {
                viewDelegate.showSaveTonSucc(false);
            } else {
                viewDelegate.showFaceCamera();
            }
            viewDelegate.onResume();
        }
    }

    @OnClick(R.id.tv_take_pic)
    public void iv_goClick() {

        viewDelegate.cvCamera.captureImage(new CameraKitEventCallback<CameraKitImage>() {
            @Override
            public void callback(final CameraKitImage cameraKitImage) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        final Bitmap bmp = cameraKitImage.getBitmap();
                        viewDelegate.showSaving(bmp);

                        int testType = 1;
                        if (currFaceTonReport.isFinishFace()) {
                            testType = 2;
                        }
                        CatDoctorApi.getInstance().faceTonUpload(bmp, testType, new UploadListener() {
                            @Override
                            public void onRequestProgress(final long bytesWritten, final long contentLength) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        viewDelegate.showSavingProgress(bytesWritten * 100f / contentLength);
                                    }
                                });
                            }
                        }, getActivity()).subscribe(new Action1<Object>() {
                            @Override
                            public void call(Object o) {
                                if (currFaceTonReport.isFinishFace()) {
                                    currFaceTonReport.setFinishTon(true);
                                    viewDelegate.showSaveTonSucc(false);
                                    ((ExamineActivity) getActivity()).notifyMenu();
                                } else {
                                    currFaceTonReport.setFinishFace(true);
                                    viewDelegate.showSaveFaceSucc();
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                                if (currFaceTonReport.isFinishFace()) {
                                    viewDelegate.showTonCamera();
                                } else {
                                    viewDelegate.showFaceCamera();
                                }
                            }
                        });
                    }
                });

            }
        });

    }

    //    @OnClick(R.id.iv_next)
    public void iv_nextClick() { //下一步
        if (currFaceTonReport.isFinishTon()) {
            ((ExamineActivity) getActivity()).showNextExamine();
        } else {
            viewDelegate.showTonCamera();
        }
    }

    //    @OnClick(R.id.iv_remake)
    public void iv_remakeClick() { //重新测量
        if (currFaceTonReport.isFinishTon()) {  //判断是重新测量面诊还是舌诊

            if (viewDelegate.vpResult.getCurrentItem() == 0) { //重新测量舌诊
                currFaceTonReport.setFinishFace(false);
                currFaceTonReport.setFinishTon(false);
                viewDelegate.showFaceCamera();
            } else {
                currFaceTonReport.setFinishTon(false);
                viewDelegate.showTonCamera();
            }
        } else {
            currFaceTonReport.setFinishFace(false);
            viewDelegate.showFaceCamera();
        }

        ((ExamineActivity) getActivity()).notifyMenu();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_next: //下一步
                iv_nextClick();
                break;
            case R.id.tv_report:
                ((ExamineActivity)getActivity()).showReport();
                break;
            case R.id.tv_reexamine:
                iv_remakeClick();
                break;
        }
    }

    @Override
    public void serialPortCallBack(String msg) {
        super.serialPortCallBack(msg);

        switch (msg) { //语音
            case "AT+ASRPS": //拍摄
                if (viewDelegate.currStep == 1 || viewDelegate.currStep == 3) {
                    iv_goClick();
                }
                return;
            case "AT+ASRCXPS": //重新拍摄
                if (viewDelegate.currStep == 2 || viewDelegate.currStep == 4) {
                    iv_remakeClick();
                }
                return;
            case "AT+ASRCLXYX": //测量下一项
                if (viewDelegate.currStep == 4) {
                    ((ExamineActivity) getActivity()).showNextExamine();
                }
                return;
            case "AT+ASRSCBG": //生成报告
                if (viewDelegate.currStep == 4) {
                    ((ExamineActivity) getActivity()).showReport();
                }
                return;
        }

    }
}
