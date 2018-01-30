package com.healthmall.sail.cat_doctor.delegate;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.R;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import butterknife.Bind;


public class InfoDelegate extends AppDelegate {


    @Bind(R.id.iv_logout)
    ImageView ivLogout;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.rb_man)
    public RadioButton rbMan;
    @Bind(R.id.rb_women)
    public RadioButton rbWomen;
    @Bind(R.id.rl_birthday)
    RelativeLayout rlBirthday;
    @Bind(R.id.rg_sex)
    RadioGroup rgSex;
    @Bind(R.id.iv_sex_man)
    ImageView ivSexMan;
    @Bind(R.id.iv_sex_women)
    ImageView ivSexWomen;
    @Bind(R.id.tv_birthday)
    public TextView tvBirthday;
    AnimationDrawable ad;


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_info;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                ad.stop();
                if (checkedId == R.id.rb_man) { //男士
                    ivSexMan.setImageResource(R.drawable.info_sex_man_sel);
                    ivSexWomen.setImageResource(R.mipmap.info_sex_women_n);

                    ad = (AnimationDrawable) ivSexMan.getDrawable();
                    if (ad != null) {
                        if (!ad.isRunning()) {
                            ad.start();
                        }
                    }
                } else {
                    ivSexMan.setImageResource(R.mipmap.info_sex_man_n);
                    ivSexWomen.setImageResource(R.drawable.info_sex_women_sel);

                    ad = (AnimationDrawable) ivSexWomen.getDrawable();
                    if (ad != null) {
                        if (!ad.isRunning()) {
                            ad.start();
                        }
                    }
                }
            }
        });

        ad = (AnimationDrawable) ivSexMan.getDrawable();
        if (ad != null) {
            if (!ad.isRunning()) {
                ad.start();
            }
        }
    }
}


