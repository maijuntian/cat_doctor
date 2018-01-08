package com.healthmall.sail.cat_doctor.fragment;


import com.healthmall.sail.cat_doctor.base.BaseFragment;
import com.healthmall.sail.cat_doctor.delegate.FaceTonDelegate;
import com.healthmall.sail.cat_doctor.delegate.TemperatureDelegate;

/**
 * Created by mai on 2018/1/5.
 */
public class TemperatureFragment extends BaseFragment<TemperatureDelegate> {

    public void serialPortCallBack(String msg) {  //串口通信的回调
    }
}
