package com.healthmall.sail.cat_doctor.delegate;

import android.app.Service;
import android.media.AudioManager;
import android.net.wifi.ScanResult;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.adapter.WifiAdapter;
import com.healthmall.sail.cat_doctor.widget.PasswordView2;
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
    @Bind(R.id.rb_pwd)
    RadioButton rbPwd;

    @Bind(R.id.ll_update_pwd)
    LinearLayout llUpdatePwd;

    @Bind(R.id.old_pwd)
    public PasswordView2 oldPwd;
    @Bind(R.id.new_pwd1)
    public PasswordView2 newPwd1;
    @Bind(R.id.new_pwd2)
    public PasswordView2 newPwd2;
    @Bind(R.id.iv_commit)
    ImageView ivCommit;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_basic_set;
    }

    private void changeCommit() {
        if(oldPwd.getText().length() > 0 && newPwd1.length() > 0 && newPwd2.length() > 0){
            ivCommit.setImageResource(R.mipmap.pwd_commit_s);
            ivCommit.setEnabled(true);
        } else {
            ivCommit.setImageResource(R.mipmap.pwd_commit_n);
            ivCommit.setEnabled(false);
        }
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
                    llUpdatePwd.setVisibility(View.GONE);
                } else if (resId == R.id.rb_voice) {
                    llWlan.setVisibility(View.GONE);
                    llVoice.setVisibility(View.VISIBLE);
                    llUpdatePwd.setVisibility(View.GONE);
                } else {
                    llWlan.setVisibility(View.GONE);
                    llVoice.setVisibility(View.GONE);
                    llUpdatePwd.setVisibility(View.VISIBLE);
                }
            }
        });
        oldPwd.getEtPassword().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeCommit();
            }
        });

        newPwd1.getEtPassword().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeCommit();
            }
        });

        newPwd2.getEtPassword().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeCommit();
            }
        });

        rbWlan.setChecked(true);

        newPwd1.setLabel("新设密码");
        newPwd1.setHint("请输入5-12位登录密码");
        newPwd2.setLabel("再次确认");
        newPwd2.setHint("请输入5-12位登录密码");

        initVoice();

        oldPwd.setCheck(true);
        newPwd1.setCheck(false);
        newPwd2.setCheck(false);
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
        tvVoice.setText(current + "");

        sbVoice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                tvVoice.setText(progress + "");
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
