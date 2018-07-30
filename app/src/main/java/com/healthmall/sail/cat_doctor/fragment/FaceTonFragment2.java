package com.healthmall.sail.cat_doctor.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.activity.ExamineActivity;
import com.healthmall.sail.cat_doctor.base.BaseFragment;
import com.healthmall.sail.cat_doctor.bean.FaceTonReport;
import com.healthmall.sail.cat_doctor.delegate.FaceTonDelegate2;
import com.healthmall.sail.cat_doctor.http.CatDoctorApi;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmdSmall;
import com.mai.xmai_fast_lib.basehttp.UploadListener;
import com.wonderkiln.camerakit.CameraKitEventCallback;
import com.wonderkiln.camerakit.CameraKitImage;

import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by mai on 2017/11/15.
 */
public class FaceTonFragment2 extends BaseFragment<FaceTonDelegate2> {

    FaceTonReport currFaceTonReport;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currFaceTonReport = MyApplication.get().getCurrUserReport().getFaceTonReport();

        if (currFaceTonReport.isFinish()) {
            viewDelegate.showSaveTonSucc(true, currFaceTonReport);
        } else {
            viewDelegate.showStep1();
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (hidden) {
            viewDelegate.hidePopWin();
            viewDelegate.onPause();
            SerialPortCmd.stpFillin();
            SerialPortCmdSmall.stp();
        } else {
            if (currFaceTonReport.isFinish()) {
                viewDelegate.showSaveTonSucc(true, currFaceTonReport);
            } else {
                viewDelegate.showStep1();
            }
            viewDelegate.onResume();
        }
    }

    @OnClick(R.id.tv_start)
    public void iv_goClick() {

        viewDelegate.showStep2();
        SerialPortCmdSmall.face_ton();
    }

    public void iv_nextClick() { //下一步
        ((ExamineActivity) getActivity()).showNextExamine();
    }

    public void iv_remakeClick() { //重新测量

        if (viewDelegate.vpResult.getCurrentItem() == 0) { //重新测量面诊
            currFaceTonReport.setFinishFace(false);
            SerialPortCmdSmall.face();
        } else {
            currFaceTonReport.setFinishTon(false);
            SerialPortCmdSmall.ton();
        }
        viewDelegate.showStep2();

        ((ExamineActivity) getActivity()).notifyMenu();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_next: //下一步
                iv_nextClick();
                break;
            case R.id.tv_report:
                ((ExamineActivity) getActivity()).showReport();
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
            case "AT+ASRKSCL": //开始测量
                if (viewDelegate.currStep == 1) {
                    iv_goClick();
                }
                return;
            case "AT+ASRCXPS": //重新拍摄
                if (viewDelegate.currStep == 3) {
                    iv_remakeClick();
                }
                return;
            case "AT+ASRCLXYX": //测量下一项
                if (viewDelegate.currStep == 3) {
                    ((ExamineActivity) getActivity()).showNextExamine();
                }
                return;
            case "AT+ASRSCBG": //生成报告
                if (viewDelegate.currStep == 3) {
                    ((ExamineActivity) getActivity()).showReport();
                }
                return;
        }

    }

    @Override
    public void serialPortSmallCallBack(String msg) {
        super.serialPortSmallCallBack(msg);

        finishSpeek();
        SerialPortCmdSmall.parseFaceTonReport(msg, currFaceTonReport);
        viewDelegate.showSaveTonSucc(false, currFaceTonReport);
        ((ExamineActivity) getActivity()).notifyMenu();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SerialPortCmd.stpFillin();
    }
}
