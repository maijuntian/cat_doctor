package com.healthmall.sail.cat_doctor.widget;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.R;

import butterknife.ButterKnife;

/**
 * Created by mai on 2017/11/21.
 */
public class PasswordView2 extends LinearLayout {

    TextView tvLabel;

    EditText etPassword;

    CheckBox cb_check;

    public PasswordView2(Context context) {
        super(context);
    }

    public PasswordView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PasswordView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHint(String text) {
        etPassword.setHint(text);
    }

    public String getText(){
        return etPassword.getText().toString();
    }

    public void setText(String text){
        etPassword.setText(text);
    }

    public int length(){
        return etPassword.length();
    }

    public void setLabel(String text) {
        tvLabel.setText(text);
    }

    public void setCheck(boolean check){
        cb_check.setChecked(check);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    public EditText getEtPassword() {
        return etPassword;
    }

    private void initView() {
        tvLabel = ButterKnife.findById(this, R.id.tv_label);
        etPassword = ButterKnife.findById(this, R.id.et_pwd);
        cb_check = ButterKnife.findById(this, R.id.cb_check);

        cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
}