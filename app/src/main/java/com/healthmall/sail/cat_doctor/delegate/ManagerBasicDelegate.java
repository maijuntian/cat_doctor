package com.healthmall.sail.cat_doctor.delegate;

import android.app.Service;
import android.media.AudioManager;
import android.net.wifi.ScanResult;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.adapter.WifiAdapter;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import java.util.List;

import butterknife.Bind;

/**
 * Created by mai on 2017/11/21.
 */
public class ManagerBasicDelegate extends AppDelegate {
    @Bind(R.id.rb_wlan)
    RadioButton rbWlan;
    @Bind(R.id.rb_voice)
    RadioButton rbVoice;
    @Bind(R.id.lv_wifi)
    ListView lvWifi;
    @Bind(R.id.ll_wlan)
    LinearLayout llWlan;
    @Bind(R.id.sb_voice)
    SeekBar sbVoice;
    @Bind(R.id.ll_voice)
    LinearLayout llVoice;
    @Bind(R.id.rg_set)
    RadioGroup rgSet;
    @Bind(R.id.cb_wifi)
    CheckBox cbWifi;
    @Bind(R.id.tv_voice)
    TextView tvVoice;

    WifiAdapter wifiAdapter;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_basic_set;
    }


    @Override
    public void initWidget() {
        super.initWidget();

        rgSet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int resId) {
                if (resId == R.id.rb_wlan) {
                    llWlan.setVisibility(View.VISIBLE);
                    llVoice.setVisibility(View.GONE);
                } else {
                    llWlan.setVisibility(View.GONE);
                    llVoice.setVisibility(View.VISIBLE);
                }
            }
        });
        rbWlan.setChecked(true);

        initVoice();

    }

    public void setWifiSwitch(boolean wifiSwitch) {
        cbWifi.setChecked(wifiSwitch);
    }

    public void initWlanList(List<ScanResult> wifiList, String connectSSID) {

        ScanResult connectWifi = null;
        for (ScanResult scanResult : wifiList) {
            if (scanResult.BSSID.equals(connectSSID))
                connectWifi = scanResult;
        }

        if (connectWifi != null) {
            wifiList.remove(connectWifi);
            wifiList.add(0, connectWifi);
        }

        if (wifiAdapter == null) {
            wifiAdapter = new WifiAdapter(wifiList);
            wifiAdapter.setConnectWifiBSSID(connectSSID);

            lvWifi.setAdapter(wifiAdapter);
        } else {
            wifiAdapter.setConnectWifiBSSID(connectSSID);
            wifiAdapter.notifyDataSetChanged();
        }
    }

    public void initVoice() {
        final AudioManager audioManager = (AudioManager) mContext.getSystemService(Service.AUDIO_SERVICE);
        int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        sbVoice.setMax(max);
        sbVoice.setProgress(current);
        tvVoice.setText(current+"");

        sbVoice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                tvVoice.setText(progress+"");
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_PLAY_SOUND);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
