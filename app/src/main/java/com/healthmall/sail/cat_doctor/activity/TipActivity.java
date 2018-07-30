package com.healthmall.sail.cat_doctor.activity;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseActivity;
import com.healthmall.sail.cat_doctor.base.BaseSoftActivity;
import com.healthmall.sail.cat_doctor.delegate.TipDelegate;
import com.mai.xmai_fast_lib.mvvm.presenter.ActivityPresenter;

import butterknife.OnClick;

/**
 * Created by maijuntian on 2018/7/12.
 */
public class TipActivity extends BaseSoftActivity<TipDelegate> {

    @OnClick(R.id.tv_know)
    public void tip() {
        startActivity(MainActivity.class, true);
    }

}
