package com.healthmall.sail.cat_doctor.activity;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseActivity;
import com.healthmall.sail.cat_doctor.base.BaseSoftActivity;
import com.healthmall.sail.cat_doctor.base.MyThrowable;
import com.healthmall.sail.cat_doctor.delegate.InfoDelegate;
import com.healthmall.sail.cat_doctor.http.CatDoctorApi;
import com.healthmall.sail.cat_doctor.utils.DialogUtils;
import com.mai.xmai_fast_lib.basehttp.MParams;

import butterknife.OnClick;
import rx.functions.Action1;
import rx.functions.Action3;


public class InfoActivity extends BaseSoftActivity<InfoDelegate> {

    String year = "2000", month = "06", day = "15";


    @OnClick(R.id.iv_save)
    public void tv_saveClick() {

        final int sex = viewDelegate.rbMan.isChecked() ? 1 : 0;

        CatDoctorApi.getInstance().saveUserInfo(new MParams()
                .add("sex", sex)
                .add("interfaceVer", 1)
                .add("birthday", year + "-" + month + "-" + day), this)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        MyApplication.get().getCurrUser().setBirthday(year + "-" + month + "-" + day);
                        MyApplication.get().getCurrUser().setMemberSex(sex);

                        startActivity(TipActivity.class, true);
                    }
                }, new MyThrowable());
    }

    @OnClick(R.id.rl_birthday)
    public void rl_birthdayClick() {
        DialogUtils.showAgeDialog(this, year, month, day, new Action3<String, String, String>() {
            @Override
            public void call(String year, String month, String day) {
                InfoActivity.this.year = year;
                InfoActivity.this.month = month;
                InfoActivity.this.day = day;
                viewDelegate.tvBirthday.setText(year + "  |  " + month + "  |  " + day);
            }
        });
    }
}


