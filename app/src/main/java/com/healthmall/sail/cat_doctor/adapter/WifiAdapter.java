package com.healthmall.sail.cat_doctor.adapter;

import android.net.wifi.ScanResult;
import android.view.View;

import com.healthmall.sail.cat_doctor.R;
import com.mai.xmai_fast_lib.baseadapter.BaseListViewAdapter;
import com.mai.xmai_fast_lib.baseadapter.BaseViewHolder;
import com.mai.xmai_fast_lib.utils.MLog;

import java.util.List;

/**
 * Created by mai on 2017/11/24.
 */
public class WifiAdapter extends BaseListViewAdapter<ScanResult> {

    public static final String WIFI_AUTH_ROAM = "[ESS]";

    private String connectWifiBSSID = "";

    public void setConnectWifiBSSID(String connectWifiBSSID) {
        this.connectWifiBSSID = connectWifiBSSID;
    }

    public WifiAdapter(List<ScanResult> mData) {
        super(mData);
    }

    @Override
    protected int bindLayoutId(int position) {
        return R.layout.item_wifi;
    }

    @Override
    protected void initView(ScanResult data, BaseViewHolder viewHolder) {
        MLog.log(data.BSSID + "  " + connectWifiBSSID + "  " + data.capabilities);
        viewHolder.setText(R.id.tv_wifi_name, data.SSID)
                .setVisibility(R.id.iv_choose_wifi, View.INVISIBLE, data.BSSID.equals(connectWifiBSSID))
                .setVisibility(R.id.iv_lock, View.INVISIBLE, data.capabilities.endsWith(WIFI_AUTH_ROAM));

    }
}
