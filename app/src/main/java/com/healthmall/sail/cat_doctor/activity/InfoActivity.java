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


public class InfoActivity extends BaseSoftActivity<InfoDelegate> {


    @OnClick(R.id.tv_save)
    public void tv_saveClick() {

        final int sex = viewDelegate.rbMan.isChecked() ? 1 : 0;
        final int age = Integer.parseInt(viewDelegate.getViewText(R.id.tv_age));

        CatDoctorApi.getInstance().saveUserInfo(new MParams()
                .add("sex", sex)
                .add("age", age), this)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        MyApplication.get().getCurrUser().setMemberAge(age);
                        MyApplication.get().getCurrUser().setMemberSex(sex);

                        startActivity(MainActivity.class, true);
                    }
                }, new MyThrowable());
    }

    @OnClick(R.id.tv_age)
    public void tv_ageClick() {
        DialogUtils.showAgeDialog(this, new Action1<Integer>() {
            @Override
            public void call(Integer age) {
                viewDelegate.setTextViewText(R.id.tv_age, age + "");
            }
        });
    }
}


