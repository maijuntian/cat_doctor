package com.healthmall.sail.cat_doctor.fragment;

import android.content.Intent;
import android.provider.Settings;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseFragment;
import com.healthmall.sail.cat_doctor.delegate.ManagerAboutDelegate;
import com.healthmall.sail.cat_doctor.delegate.ManagerSysSetDelegate;
import com.mai.xmai_fast_lib.mvvm.presenter.ActivityPresenter;

import butterknife.OnClick;

/**
 * Created by mai on 2017/11/21.
 */
public class ManagerSysSetFragment extends BaseFragment<ManagerSysSetDelegate> {

    @OnClick(R.id.iv_confirm)
    public void iv_confirmClick() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
    }
}
