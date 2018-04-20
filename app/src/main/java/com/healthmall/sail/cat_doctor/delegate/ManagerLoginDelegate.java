package com.healthmall.sail.cat_doctor.delegate;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.widget.PasswordView;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import butterknife.Bind;


public class ManagerLoginDelegate extends AppDelegate {

    @Bind(R.id.et_account)
    public EditText etAccount;
    @Bind(R.id.fl_old_pwd)
    FrameLayout flOldPwd;
    @Bind(R.id.iv_login)
    ImageView ivLogin;
    @Bind(R.id.tv_error)
    TextView tvError;
    @Bind(R.id.tv_update_pwd)
    TextView tvUpdatePwd;
    @Bind(R.id.tv_count_down)
    TextView tvCountDown;
    @Bind(R.id.tv_owner)
    TextView tvOwner;
    @Bind(R.id.tv_phone)
    TextView tvPhone;

    @Bind(R.id.rl_account)
    RelativeLayout rlAccount;

    @Bind(R.id.rl_old_pwd)
    public PasswordView pvOldPwd;

    @Bind(R.id.rl_pwd)
    public PasswordView pvPwd;

    @Override
    public void initWidget() {
        super.initWidget();

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_login;
    }


    /*public void showLoginView() {
        ivLogin.setImageResource(R.mipmap.login_login);
        rlAccount.setVisibility(View.VISIBLE);
        flOldPwd.setVisibility(View.GONE);

        pvPwd.setHint("登录密码");
        tvUpdatePwd.setText("修改密码");
        tvError.setVisibility(View.INVISIBLE);
        tvCountDown.setVisibility(View.INVISIBLE);
    }

    public void showUpdateView() {

        ivLogin.setImageResource(R.mipmap.login_confirm);
        rlAccount.setVisibility(View.GONE);
        flOldPwd.setVisibility(View.VISIBLE);

        pvPwd.setHint("设置新密码");
        tvUpdatePwd.setText("去登录");
        tvError.setVisibility(View.INVISIBLE);
        tvCountDown.setVisibility(View.INVISIBLE);
    }*/

    public void showError(String text){
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(text);
    }
}


