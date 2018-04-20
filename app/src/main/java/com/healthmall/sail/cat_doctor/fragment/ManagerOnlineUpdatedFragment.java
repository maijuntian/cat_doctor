package com.healthmall.sail.cat_doctor.fragment;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.activity.ManagerMainActivity;
import com.healthmall.sail.cat_doctor.base.BaseFragment;
import com.healthmall.sail.cat_doctor.delegate.ManagerHardwareCheckDelegate;
import com.healthmall.sail.cat_doctor.delegate.ManagerOnlineUpdatedDelegate;
import com.mai.xmai_fast_lib.mvvm.presenter.ActivityPresenter;

import butterknife.OnClick;

/**
 * Created by mai on 2017/11/21.
 */
public class ManagerOnlineUpdatedFragment extends BaseFragment<ManagerOnlineUpdatedDelegate> {


    public void notifyNewVersion() {
        viewDelegate.newVersion();
    }

    @OnClick(R.id.iv_online_update)
    public void iv_online_updateClick() {
        ((ManagerMainActivity) getActivity()).download();
    }
}
