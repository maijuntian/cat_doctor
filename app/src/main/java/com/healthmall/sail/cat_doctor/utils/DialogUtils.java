package com.healthmall.sail.cat_doctor.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.net.wifi.WifiConfiguration;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
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
import com.mai.xmai_fast_lib.utils.MLog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.ButterKnife;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action3;

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

    public static void showReportDialog(Activity act, final Action0 identify) {
        final Dialog dialog = new Dialog(act, R.style.LoadingDialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_report);

        dialog.findViewById(R.id.iv_identify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                identify.call();
            }
        });
        dialog.findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static Dialog showExitDialog(Activity act, final Action0 exit, final Action0 cancel) {
        final Dialog dialog = new Dialog(act, R.style.LoadingDialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_exit);

        dialog.findViewById(R.id.iv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                exit.call();
            }
        });
        dialog.findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                cancel.call();
            }
        });
        dialog.show();
        return dialog;
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

    public static void showAgeDialog(Activity act, String year, String month, String day, final Action3<String, String, String> ageCallBack) {
        final Dialog dialog = new Dialog(act, R.style.LoadingDialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_age);
        dialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;

        final TimePickerView tpv_year = dialog.findViewById(R.id.tpv_year);
        final TimePickerView tpv_month = dialog.findViewById(R.id.tpv_month);
        final TimePickerView tpv_day = dialog.findViewById(R.id.tpv_day);

        dialog.findViewById(R.id.tv_confirm).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (ageCallBack != null)
                            ageCallBack.call(tpv_year.getSelectedText(), tpv_month.getSelectedText(), tpv_day.getSelectedText());
                    }
                });

        tpv_year.setOnSelectListener(new TimePickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {
                MLog.log("选中" + text + tpv_month.getSelectedText());
                String dayTemp = tpv_day.getSelectedText();
                setDayData(tpv_day, text, tpv_month.getSelectedText());
                int result = tpv_day.setSelected(dayTemp);
                if (result == -1) {
                    tpv_day.setSelected(tpv_day.getDataSize() - 1);
                }
            }
        });
        tpv_month.setOnSelectListener(new TimePickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {
                MLog.log("选中" + tpv_year.getSelectedText() + text);
                String dayTemp = tpv_day.getSelectedText();
                setDayData(tpv_day, tpv_year.getSelectedText(), text);
                int result = tpv_day.setSelected(dayTemp);
                if (result == -1) {
                    tpv_day.setSelected(tpv_day.getDataSize() - 1);
                }
            }
        });

        initTimePickerView(tpv_year);
        initTimePickerView(tpv_month);
        initTimePickerView(tpv_day);

        setYearData(tpv_year);
        setMonthData(tpv_month);
        setDayData(tpv_day, year, month);

        tpv_year.setSelected(year);
        tpv_month.setSelected(month);
        tpv_day.setSelected(day);

        dialog.show();
    }

    public static void initTimePickerView(TimePickerView timePickerView) {
        timePickerView.setLayerType(View.LAYER_TYPE_HARDWARE, null); // 三星手机硬件加速
        timePickerView.setSelectTextColor(Color.parseColor("#fffefe"));
        timePickerView.setTextColor(Color.parseColor("#b3fffefe"));
        timePickerView.setNotCycle(true);
        timePickerView.setTextSizeSacle(10f);
        timePickerView.MARGIN_ALPHA = 4f;
    }


    private static void setYearData(TimePickerView tpv_year) {
        List<String> days = new ArrayList<String>();
        int minYear = Calendar.getInstance().get(Calendar.YEAR) - 100;
        for (int j = 0; j <= 82; j++) {
            days.add((minYear + j) + "");
        }
        tpv_year.setData(days);
    }

    private static void setMonthData(TimePickerView tpv_month) {
        List<String> days = new ArrayList<String>();
        for (int j = 1; j <= 12; j++) {
            days.add(j < 10 ? "0" + j : "" + j);
        }
        tpv_month.setData(days);
    }

    private static void setDayData(TimePickerView tpv_day, String year, String month) {
        List<String> days = new ArrayList<String>();
        int maxDay = getMaxDay(Integer.parseInt(year), Integer.parseInt(month));
        for (int j = 1; j <= maxDay; j++) {
            days.add(j < 10 ? "0" + j : "" + j);
        }
        tpv_day.setData(days);
    }

    /**
     * 获取该年该月多少天
     *
     * @param year
     * @param month
     * @return
     */
    private static int getMaxDay(int year, int month) {
        Calendar calendar = new GregorianCalendar(year, month, 0);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
