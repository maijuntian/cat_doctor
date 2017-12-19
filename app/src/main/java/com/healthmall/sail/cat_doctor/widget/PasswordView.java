package com.healthmall.sail.cat_doctor.widget;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.healthmall.sail.cat_doctor.R;

import butterknife.ButterKnife;

/**
 * Created by mai on 2017/11/21.
 */
public class PasswordView extends RelativeLayout {

    EditText etPassword;

    CheckBox cb_check;

    public PasswordView(Context context) {
        super(context);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHint(String text) {
        etPassword.setHint(text);
    }

    public String getText(){
        return etPassword.getText().toString();
    }

    public int length(){
        return etPassword.length();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
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