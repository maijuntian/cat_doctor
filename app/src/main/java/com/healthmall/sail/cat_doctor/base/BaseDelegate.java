package com.healthmall.sail.cat_doctor.base;

import android.widget.TextView;

import com.healthmall.sail.cat_doctor.R;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;

/**
 * Created by mai on 2017/11/14.
 */
public abstract class BaseDelegate extends AppDelegate {


    @Bind(R.id.tv_time)
    TextView tvTime;

    @Override
    public void initWidget() {
        super.initWidget();

        notifySysTime();
    }

    public void notifySysTime() {
        tvTime.setText(new SimpleDateFormat("yyyy年M月d日  E  HH:mm", Locale.SIMPLIFIED_CHINESE).format(new Date()));
    }
}
