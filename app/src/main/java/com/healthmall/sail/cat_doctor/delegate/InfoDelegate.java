package com.healthmall.sail.cat_doctor.delegate;

import android.widget.RadioButton;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.R;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import butterknife.Bind;


public class InfoDelegate extends AppDelegate {


    @Bind(R.id.rb_women)
    public RadioButton rbWomen;
    @Bind(R.id.rb_man)
    public RadioButton rbMan;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.tv_save)
    TextView tvSave;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_info;
    }

    @Override
    public void initWidget() {
        super.initWidget();
    }
}


