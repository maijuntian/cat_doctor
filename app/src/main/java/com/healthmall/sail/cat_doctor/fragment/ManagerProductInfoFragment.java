package com.healthmall.sail.cat_doctor.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.healthmall.sail.cat_doctor.Constant;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseFragment;
import com.healthmall.sail.cat_doctor.bean.CDRespone;
import com.healthmall.sail.cat_doctor.delegate.ManagerBasicDelegate;
import com.healthmall.sail.cat_doctor.delegate.ManagerProductInfoDelegate;
import com.healthmall.sail.cat_doctor.http.CatDoctorApi;
import com.healthmall.sail.cat_doctor.utils.Keys;
import com.healthmall.sail.cat_doctor.websocket.CatWebSocketClient;
import com.mai.xmai_fast_lib.basehttp.MyAction;
import com.mai.xmai_fast_lib.mvvm.presenter.ActivityPresenter;
import com.mai.xmai_fast_lib.utils.SharedPreferencesHelper;

import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by mai on 2017/11/21.
 */
public class ManagerProductInfoFragment extends BaseFragment<ManagerProductInfoDelegate> {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewDelegate.initDeviceId();
    }

    @OnClick(R.id.iv_commit)
    public void iv_commitClick() {
        String deviceId = viewDelegate.getViewText(R.id.et_device_id);

        if (TextUtils.isEmpty(deviceId)) {
            showToast("设备编号不能为空！");
            return;
        }

        if (deviceId.length() != 6) {
            showToast("设备编号必须为6位");
        }

        final String deviceIdF = Constant.DEVICE_ID_PRE + deviceId;
        CatDoctorApi.getInstance().bindDevice(deviceIdF, getActivity()).subscribe(new Action1<CDRespone>() {
            @Override
            public void call(CDRespone cdRespone) {
                if (cdRespone.getCode() == 2000) {
                    SharedPreferencesHelper.getInstance(getContext()).putStringValue(Keys.KEY_DEVICE_ID, deviceIdF);

                    CatWebSocketClient.getInstance().connect();

                    viewDelegate.initDeviceId();
                } else {
                    showToast(cdRespone.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
