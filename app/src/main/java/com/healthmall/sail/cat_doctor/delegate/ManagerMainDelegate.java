package com.healthmall.sail.cat_doctor.delegate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.fragment.ManagerAboutFragment;
import com.healthmall.sail.cat_doctor.fragment.ManagerBasicFragment;
import com.healthmall.sail.cat_doctor.fragment.ManagerHardwareCheckFragment;
import com.healthmall.sail.cat_doctor.fragment.ManagerOnlineUpdatedFragment;
import com.healthmall.sail.cat_doctor.fragment.ManagerProductInfoFragment;
import com.healthmall.sail.cat_doctor.fragment.ManagerSysSetFragment;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import butterknife.Bind;


public class ManagerMainDelegate extends AppDelegate {

    @Bind(R.id.rg_nav)
    RadioGroup rgNav;
    @Bind(R.id.iv_logout)
    ImageView ivLogout;
    @Bind(R.id.tv_owner)
    TextView tvOwner;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.fl_content)
    FrameLayout flContent;

    ManagerBasicFragment basicFragment;
    ManagerProductInfoFragment productInfoFragment;
    ManagerSysSetFragment sysSetFragment;
    public ManagerHardwareCheckFragment hardwareCheckFragment;
    public ManagerOnlineUpdatedFragment onlineUpdatedFragment;
    ManagerAboutFragment aboutFragment;

    Fragment currFragment;
    @Bind(R.id.rb_basic)
    RadioButton rbBasic;
    @Bind(R.id.rb_product_info)
    RadioButton rbProductInfo;
    @Bind(R.id.rb_sys_set)
    RadioButton rbSysSet;
    @Bind(R.id.rb_hardware_check)
    RadioButton rbHardwareCheck;
    @Bind(R.id.rb_online_update)
    RadioButton rbOnlineUpdate;
    @Bind(R.id.rb_about)
    RadioButton rbAbout;
    @Bind(R.id.iv_new_version)
    public ImageView ivNewVersion;
    @Bind(R.id.iv_power_off)
    ImageView ivPowerOff;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_manager;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        rgNav.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int resId) {
                switch (resId) {
                    case R.id.rb_basic:
                        showBasicFragment();
                        break;
                    case R.id.rb_product_info:
                        showProductInfoFragment();
                        break;
                    case R.id.rb_sys_set:
                        showSysSetFragment();
                        break;
                    case R.id.rb_hardware_check:
                        showHardwareCheckFragment();
                        break;
                    case R.id.rb_online_update:
                        showOnlineUpdatedFragment();
                        break;
                    case R.id.rb_about:
                        showAboutFragment();
                        break;

                }
            }
        });
        rbBasic.setChecked(true);
    }

    private void showBasicFragment() {

        if (currFragment != null && currFragment == basicFragment)
            return;

        FragmentTransaction transaction = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();

        if (basicFragment == null) {
            basicFragment = new ManagerBasicFragment();
            transaction.add(R.id.fl_content, basicFragment);
        } else {
            transaction.show(basicFragment);
        }

        if (productInfoFragment != null)
            transaction.hide(productInfoFragment);

        if (sysSetFragment != null)
            transaction.hide(sysSetFragment);

        if (hardwareCheckFragment != null)
            transaction.hide(hardwareCheckFragment);

        if (onlineUpdatedFragment != null)
            transaction.hide(onlineUpdatedFragment);

        if (aboutFragment != null)
            transaction.hide(aboutFragment);

        transaction.commitAllowingStateLoss();

        currFragment = basicFragment;
    }

    private void showProductInfoFragment() {

        if (currFragment != null && currFragment == productInfoFragment)
            return;

        FragmentTransaction transaction = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();

        if (basicFragment != null)
            transaction.hide(basicFragment);

        if (productInfoFragment == null) {
            productInfoFragment = new ManagerProductInfoFragment();
            transaction.add(R.id.fl_content, productInfoFragment);
        } else {
            transaction.show(productInfoFragment);
        }

        if (sysSetFragment != null)
            transaction.hide(sysSetFragment);

        if (hardwareCheckFragment != null)
            transaction.hide(hardwareCheckFragment);

        if (onlineUpdatedFragment != null)
            transaction.hide(onlineUpdatedFragment);

        if (aboutFragment != null)
            transaction.hide(aboutFragment);

        transaction.commitAllowingStateLoss();

        currFragment = productInfoFragment;
    }

    private void showSysSetFragment() {

        if (currFragment != null && currFragment == sysSetFragment)
            return;

        FragmentTransaction transaction = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();

        if (basicFragment != null)
            transaction.hide(basicFragment);

        if (productInfoFragment != null)
            transaction.hide(productInfoFragment);

        if (sysSetFragment == null) {
            sysSetFragment = new ManagerSysSetFragment();
            transaction.add(R.id.fl_content, sysSetFragment);
        } else {
            transaction.show(sysSetFragment);
        }

        if (hardwareCheckFragment != null)
            transaction.hide(hardwareCheckFragment);

        if (onlineUpdatedFragment != null)
            transaction.hide(onlineUpdatedFragment);

        if (aboutFragment != null)
            transaction.hide(aboutFragment);

        transaction.commitAllowingStateLoss();

        currFragment = sysSetFragment;
    }

    private void showHardwareCheckFragment() {

        if (currFragment != null && currFragment == hardwareCheckFragment)
            return;

        FragmentTransaction transaction = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();

        if (basicFragment != null)
            transaction.hide(basicFragment);


        if (productInfoFragment != null)
            transaction.hide(productInfoFragment);

        if (sysSetFragment != null)
            transaction.hide(sysSetFragment);

        if (hardwareCheckFragment == null) {
            hardwareCheckFragment = new ManagerHardwareCheckFragment();
            transaction.add(R.id.fl_content, hardwareCheckFragment);
        } else {
            transaction.show(hardwareCheckFragment);
        }

        if (onlineUpdatedFragment != null)
            transaction.hide(onlineUpdatedFragment);

        if (aboutFragment != null)
            transaction.hide(aboutFragment);

        transaction.commitAllowingStateLoss();

        currFragment = hardwareCheckFragment;
    }

    private void showOnlineUpdatedFragment() {

        ivNewVersion.setVisibility(View.GONE);
        if (currFragment != null && currFragment == onlineUpdatedFragment)
            return;

        FragmentTransaction transaction = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();

        if (basicFragment != null)
            transaction.hide(basicFragment);

        if (productInfoFragment != null)
            transaction.hide(productInfoFragment);

        if (sysSetFragment != null)
            transaction.hide(sysSetFragment);

        if (hardwareCheckFragment != null)
            transaction.hide(hardwareCheckFragment);

        if (onlineUpdatedFragment == null) {
            onlineUpdatedFragment = new ManagerOnlineUpdatedFragment();
            transaction.add(R.id.fl_content, onlineUpdatedFragment);
        } else {
            transaction.show(onlineUpdatedFragment);
        }

        if (aboutFragment != null)
            transaction.hide(aboutFragment);

        transaction.commitAllowingStateLoss();

        currFragment = onlineUpdatedFragment;
    }

    private void showAboutFragment() {

        if (currFragment != null && currFragment == aboutFragment)
            return;

        FragmentTransaction transaction = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();

        if (basicFragment != null)
            transaction.hide(basicFragment);

        if (productInfoFragment != null)
            transaction.hide(productInfoFragment);

        if (sysSetFragment != null)
            transaction.hide(sysSetFragment);

        if (hardwareCheckFragment != null)
            transaction.hide(hardwareCheckFragment);

        if (onlineUpdatedFragment != null)
            transaction.hide(onlineUpdatedFragment);

        if (aboutFragment == null) {
            aboutFragment = new ManagerAboutFragment();
            transaction.add(R.id.fl_content, aboutFragment);
        } else {
            transaction.show(aboutFragment);
        }

        transaction.commitAllowingStateLoss();

        currFragment = aboutFragment;
    }

}


