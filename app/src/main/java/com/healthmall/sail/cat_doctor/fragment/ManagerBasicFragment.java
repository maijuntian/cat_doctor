package com.healthmall.sail.cat_doctor.fragment;

import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseFragment;
import com.healthmall.sail.cat_doctor.delegate.ManagerBasicDelegate;
import com.healthmall.sail.cat_doctor.utils.DialogUtils;
import com.healthmall.sail.cat_doctor.utils.Keys;
import com.healthmall.sail.cat_doctor.wifi.XPGNetstatusListener;
import com.healthmall.sail.cat_doctor.wifi.XPGWifiAdmin;
import com.healthmall.sail.cat_doctor.wifi.XPGWifiScanListener;
import com.mai.xmai_fast_lib.utils.MLog;
import com.mai.xmai_fast_lib.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemClick;
import rx.functions.Action1;

/**
 * Created by mai on 2017/11/21.
 */
public class ManagerBasicFragment extends BaseFragment<ManagerBasicDelegate> {

    XPGWifiAdmin xpgWifiAdmin;

    List<ScanResult> wifiList = new ArrayList<>();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        xpgWifiAdmin = XPGWifiAdmin.getInstance(getActivity());
        xpgWifiAdmin.setXpgScanListener(new XPGWifiScanListener() {
            @Override
            public void wifiScanEnd(List<ScanResult> scanResults) {
                MLog.log("wifi回调--->" + scanResults.size());

                notifyWifi();
            }
        });

        xpgWifiAdmin.registerXPGNetStatusListener(getActivity(), new XPGNetstatusListener() {
            @Override
            public void netStatusChange(NetworkInfo netInfo, WifiInfo currentWifi) {
                notifyWifi();
            }

            @Override
            public void conntivityStatusChange(boolean isConnectivity) {
            }
            @Override
            public void wifiStatus(int status) {
               notifyWifi();
            }
        });

        notifyWifi();
    }


    public void notifyWifi() {
        dismissLoadingDialog();
        wifiList.clear();
        if (xpgWifiAdmin.isWifiEnabled()) {
            xpgWifiAdmin.startScan();
            MLog.log("搜索到WiFi数量：" + xpgWifiAdmin.getScanResult().size());
            wifiList.addAll(xpgWifiAdmin.getScanResult());
        }

        MLog.log("当前连接的wifi" + xpgWifiAdmin.getSSID() + "  " + xpgWifiAdmin.getBSSID());
        viewDelegate.setWifiSwitch(xpgWifiAdmin.isWifiEnabled());
        viewDelegate.initWlanList(wifiList, xpgWifiAdmin.getBSSID());
    }

    @OnItemClick(R.id.lv_wifi)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final ScanResult selWifi = wifiList.get(position);

        DialogUtils.showWifiPwdDialog(getActivity(), selWifi.SSID, new Action1<String>() {
            @Override
            public void call(String pwd) {
                showLoadingDialog();
                xpgWifiAdmin.connectWifi(selWifi, pwd);
            }
        });
    }

    @OnCheckedChanged(R.id.cb_wifi)
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        viewDelegate.setWifiSwitch(checked);

        if (checked) {
            xpgWifiAdmin.openWifi();
        } else {
            xpgWifiAdmin.closeWifi();
        }

    }

    @OnClick(R.id.iv_commit)
    public void iv_commitClick(){
        if (viewDelegate.oldPwd.length() == 0) {
            showToast("请输入原始密码");
            return;
        }

        if (viewDelegate.newPwd1.length() == 0) {
            showToast("请设置新密码");
            return;
        }

        if (viewDelegate.newPwd2.length() == 0) {
            showToast("请输入确认密码");
            return;
        }

        if (viewDelegate.newPwd1.length() <5) {
            showToast("新密码长度不对");
            return;
        }

        if (!viewDelegate.newPwd1.getText().equals(viewDelegate.newPwd2.getText())) {
            showToast("新密码和确认密码不一致");
            return;
        }

        if (!viewDelegate.oldPwd.getText().equals(viewDelegate.newPwd1.getText())) {
            showToast("新密码和旧密码一样，不需要修改");
            return;
        }


        String DEFAULT_MANAGER_PWD = "admin";


        String managerPwd = SharedPreferencesHelper.getInstance(getActivity().getApplicationContext()).getStringValue(Keys.KEY_MANAGER_PWD);

        if (!TextUtils.isEmpty(managerPwd)) {
            DEFAULT_MANAGER_PWD = managerPwd;
        }

        if (viewDelegate.oldPwd.getText().equals(DEFAULT_MANAGER_PWD)) {
            SharedPreferencesHelper.getInstance(getActivity()).putStringValue(Keys.KEY_MANAGER_PWD, viewDelegate.newPwd1.getText());
            showToast("密码修改成功");

            viewDelegate.oldPwd.setText("");
            viewDelegate.newPwd1.setText("");
            viewDelegate.newPwd2.setText("");
        } else {
            showToast("原始密码错误");
        }
    }

    @Override
    public void onDestroy() {
        xpgWifiAdmin.unregisterXPGNetStatusListener(getActivity());
        super.onDestroy();
    }
}
