package com.healthmall.sail.cat_doctor.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.Constant;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseSoftActivity;
import com.healthmall.sail.cat_doctor.delegate.ManagerLoginDelegate;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.utils.Keys;
import com.mai.xmai_fast_lib.mvvm.presenter.ActivityPresenter;
import com.mai.xmai_fast_lib.utils.SharedPreferencesHelper;

import butterknife.OnClick;


public class ManagerLoginActivity extends BaseSoftActivity<ManagerLoginDelegate> {


    public String DEFAULT_MANAGER_USER = "admin";
    public String DEFAULT_MANAGER_PWD = "admin";

    boolean isLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String managerPwd = SharedPreferencesHelper.getInstance(getApplicationContext()).getStringValue(Keys.KEY_MANAGER_PWD);

        if (!TextUtils.isEmpty(managerPwd)) {
            DEFAULT_MANAGER_PWD = managerPwd;
        }

    }
    @OnClick(R.id.tv_update_pwd)
    public void tv_update_pwdClick(TextView v) {

        isLogin = !isLogin;

        if (isLogin) {
            viewDelegate.showLoginView();
        } else {
            viewDelegate.showUpdateView();
        }
    }


    @OnClick(R.id.iv_login)
    public void iv_loginClick() {
        if (isLogin) {
            if (viewDelegate.etAccount.length() == 0) {
                showToast("请输入管理员账号");
                return;
            }
            if (viewDelegate.pvPwd.length() == 0) {
                showToast("请输入登录密码");
                return;
            }

            if (viewDelegate.etAccount.getText().toString().equals(DEFAULT_MANAGER_USER)
                    && viewDelegate.pvPwd.getText().equals(DEFAULT_MANAGER_PWD)) {
            } else {
                viewDelegate.showError("*用户名或密码错误");
            }

            SerialPortCmd.admin();
            startActivity(ManagerMainActivity.class, true);
        } else {
            if (viewDelegate.pvOldPwd.length() == 0) {
                showToast("请输入原始密码");
                return;
            }

            if (viewDelegate.pvPwd.length() == 0) {
                showToast("请设置新密码");
                return;
            }

            if (viewDelegate.pvOldPwd.getText().equals(DEFAULT_MANAGER_PWD)) {
                SharedPreferencesHelper.getInstance(getApplicationContext()).putStringValue(Keys.KEY_MANAGER_PWD, viewDelegate.pvPwd.getText());
                DEFAULT_MANAGER_PWD = viewDelegate.pvPwd.getText();

            } else {
                viewDelegate.showError("*原始密码错误");
            }
        }
    }

    @OnClick(R.id.iv_delete)
    public void iv_deleteClick() {
        viewDelegate.etAccount.setText("");
    }

    @OnClick(R.id.iv_exit)
    public void ivExitClick() {
        finish();
    }
}


