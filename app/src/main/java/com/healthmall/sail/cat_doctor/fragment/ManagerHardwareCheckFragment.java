package com.healthmall.sail.cat_doctor.fragment;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseFragment;
import com.healthmall.sail.cat_doctor.delegate.ManagerHardwareCheckDelegate;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;

import butterknife.OnClick;

/**
 * Created by mai on 2017/11/21.
 */
public class ManagerHardwareCheckFragment extends BaseFragment<ManagerHardwareCheckDelegate> {


    @OnClick(R.id.iv_tip)
    public void iv_tipClick() {
        if (viewDelegate.rbCheck.isChecked()) {
            viewDelegate.showChecking();
            SerialPortCmd.hadTest();
        } else {
            viewDelegate.showCalibartioning();
            SerialPortCmd.calibration();
        }
    }


    @Override
    public void serialPortCallBack(String msg) {
        super.serialPortCallBack(msg);

        if (msg.startsWith(SerialPortCmd.OK_BCALIBRATION)) {
            viewDelegate.showBloodOResult(true);
            viewDelegate.showBodyResult(true);
            viewDelegate.showBPHRResult(true);
            SerialPortCmd.stopHadTest();

        } else if (msg.startsWith(SerialPortCmd.OK_HADTEST)) {
            viewDelegate.showBloodOResult(true);
            viewDelegate.showBodyResult(true);
            viewDelegate.showBPHRResult(true);
            SerialPortCmd.stopCalibration();
        }
    }
}
