package com.healthmall.sail.cat_doctor.utils;

import android.app.Activity;
import android.app.Dialog;
import android.net.wifi.WifiConfiguration;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.widget.CountDownView;
import com.healthmall.sail.cat_doctor.widget.TimePickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.functions.Action0;
import rx.functions.Action1;

public class DialogUtils {

    public static void showLogoutDialog(Activity act, final Action0 logout) {
        final Dialog dialog = new Dialog(act, R.style.LoadingDialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_logout);

        dialog.findViewById(R.id.iv_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                logout.call();
            }
        });
        dialog.findViewById(R.id.iv_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showErrorDialog(Activity act, final Action0 logout, final Action0 reExamine) {
        final Dialog dialog = new Dialog(act, R.style.LoadingDialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_error);

        final CountDownView countDownView = new CountDownView(15000, 1000, (TextView) dialog.findViewById(R.id.tv_count_down), new Action0() {
            @Override
            public void call() {
                dialog.dismiss();
                logout.call();
            }
        });
        countDownView.start();
        dialog.findViewById(R.id.iv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownView.cancel();
                dialog.dismiss();
                logout.call();
            }
        });
        dialog.findViewById(R.id.iv_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownView.cancel();
                dialog.dismiss();
                reExamine.call();
            }
        });
        dialog.show();
    }

    public static void showWifiPwdDialog(Activity act, String wifiName, final Action1<String> connectCallBack) {
        final Dialog dialog = new Dialog(act, R.style.LoadingDialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_wifi_pwd);
        dialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
        int screenWidth = act.getWindowManager().getDefaultDisplay().getWidth();
        dialog.getWindow().getAttributes().width = screenWidth;

        final EditText etPwd = dialog.findViewById(R.id.et_pwd);
        ImageView ivConnect = dialog.findViewById(R.id.iv_connect);

        CheckBox cbShowPwd = dialog.findViewById(R.id.cb_show_pwd);
        CheckBox cbAutoConnect = dialog.findViewById(R.id.cb_auto_connect);

        TextView tvTitle = dialog.findViewById(R.id.tv_title);
        tvTitle.setText("请输入" + wifiName + "网络密码");

        cbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                etPwd.setSelection(etPwd.length());
            }
        });

        ivConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectCallBack != null)
                    connectCallBack.call(etPwd.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public static void showPowerDialog(Activity act, final Action0 powerOff, final Action0 restart) {
        final Dialog dialog = new Dialog(act, R.style.LoadingDialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_power);

        dialog.findViewById(R.id.iv_power_off).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                powerOff.call();
            }
        });
        dialog.findViewById(R.id.iv_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                restart.call();
            }
        });
        dialog.show();
    }

    public static void showAgeDialog(Activity act, final Action1<Integer> ageCallBack) {
        final Dialog dialog = new Dialog(act, R.style.LoadingDialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_age);

        final TimePickerView tpvAge = dialog.findViewById(R.id.tpv_age);

        tpvAge.setNotCycle(true);
        tpvAge.setTextSizeSacle(10f);

        List<String> datas = new ArrayList<>();

        for (int i = 14; i <= 100; i++) {
            datas.add(i + "");
        }

        tpvAge.setData(datas);

        tpvAge.setSelected("18");
        tpvAge.setSelecteBg(ContextCompat.getColor(act, R.color.black));

        dialog.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                ageCallBack.call(Integer.parseInt(tpvAge.getSelectedText()));
            }
        });

        dialog.show();
    }
}
