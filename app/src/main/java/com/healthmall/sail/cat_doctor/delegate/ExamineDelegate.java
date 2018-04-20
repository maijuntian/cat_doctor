package com.healthmall.sail.cat_doctor.delegate;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.activity.ExamineActivity;
import com.healthmall.sail.cat_doctor.base.BaseDelegate;
import com.healthmall.sail.cat_doctor.base.BaseFragment;
import com.healthmall.sail.cat_doctor.bean.UserReport;
import com.healthmall.sail.cat_doctor.fragment.BloodHeartFragment;
import com.healthmall.sail.cat_doctor.fragment.BloodoFragment;
import com.healthmall.sail.cat_doctor.fragment.BodyFragment;
import com.healthmall.sail.cat_doctor.fragment.FaceTonFragment;
import com.healthmall.sail.cat_doctor.fragment.QuestionFragment;
import com.healthmall.sail.cat_doctor.fragment.TemperatureFragment;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.utils.Configs;
import com.healthmall.sail.cat_doctor.utils.MGlide;

import butterknife.Bind;


public class ExamineDelegate extends BaseDelegate {

    BodyFragment bodyFragment;
    TemperatureFragment temperatureFragment;
    BloodoFragment bloodoFragment;
    BloodHeartFragment bloodHeartFragment;
    FaceTonFragment faceTonFragment;
    QuestionFragment questionFragment;

    BaseFragment currFragment;

    @Bind(R.id.fl_content)
    FrameLayout flContent;
    @Bind(R.id.iv_logout)
    ImageView ivLogout;
    @Bind(R.id.rb_body)
    RadioButton rbBody;
    @Bind(R.id.rb_bloodo)
    RadioButton rbBloodo;
    @Bind(R.id.rb_face_ton)
    RadioButton rbFaceTon;
    @Bind(R.id.rb_question)
    RadioButton rbQuestion;
    @Bind(R.id.rg_menu)
    RadioGroup rgMenu;
    @Bind(R.id.rb_blood_heart)
    RadioButton rbBloodHeart;
    @Bind(R.id.rb_temp)
    RadioButton rbTemp;
    @Bind(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.cb_voice)
    public CheckBox cbVoice;
    @Bind(R.id.iv_vip)
    ImageView ivVip;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_examine1;
    }


    @Override
    public void initWidget() {
        super.initWidget();

        tvUsername.setText(MyApplication.get().getCurrUser().getMemberName());
        MGlide.loadCircle(mContext, MyApplication.get().getCurrUser().getMemHeadImg(), ivUserIcon);


        initGroup();

        if (MyApplication.get().getCurrUser().isTalent()) {
            ivVip.setVisibility(View.VISIBLE);
        } else {
            ivVip.setVisibility(View.GONE);
        }

        if(!Configs.useTemp){
            rbTemp.setVisibility(View.GONE);
        }
    }

    private void initParams(RadioButton[] rbs, int resId) {
        for (RadioButton rb : rbs) {
            RadioGroup.LayoutParams params = (RadioGroup.LayoutParams) rb.getLayoutParams();
            if (rb.getId() == resId) {
                params.width = 232;
                params.height = 161;
                rb.setLayoutParams(params);
            } else {
                params.width = 166;
                params.height = 124;
                rb.setLayoutParams(params);
            }

        }
    }

    private void initGroup() {

        rgMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int resId) {

                SerialPortCmd.face1();
                initParams(new RadioButton[]{rbBody, rbTemp, rbBloodo, rbBloodHeart, rbFaceTon, rbQuestion}, resId);
                switch (resId) {
                    case R.id.rb_body:
                        showBodyFragment();
                        break;
                    case R.id.rb_temp:
                        showTempFragment();
                        break;
                    case R.id.rb_bloodo:
                        showBloodoFragment();
                        break;
                    case R.id.rb_blood_heart:
                        showBloodHeartFragment();
                        break;
                    case R.id.rb_face_ton:
                        showFaceTonFragment();
                        break;
                    case R.id.rb_question:
                        showQuestionFragment();
                        break;
                }

            }
        });
    }


    public void checkMenu(int menu) {
        switch (menu) {
            case ExamineActivity.SHOW_BODY_EXAMINE:
                rbBody.setChecked(true);
                break;
            case ExamineActivity.SHOW_TEMPERATURE:
                rbTemp.setChecked(true);
                break;
            case ExamineActivity.SHOW_BLOODO_EXAMINE:
                rbBloodo.setChecked(true);
                break;
            case ExamineActivity.SHOW_BLOOD_HEART_EXAMINE:
                rbBloodHeart.setChecked(true);
                break;
            case ExamineActivity.SHOW_FACE_TON_EXAMINE:
                rbFaceTon.setChecked(true);
                break;
            case ExamineActivity.SHOW_QUETION_EXAMINE:
                rbQuestion.setChecked(true);
                break;
        }
    }


    private void showBodyFragment() {

        if (currFragment != null && currFragment == bodyFragment)
            return;

        FragmentTransaction transaction = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();

        if (bodyFragment == null) {
            bodyFragment = new BodyFragment();
            transaction.add(R.id.fl_content, bodyFragment);
        } else {
            transaction.show(bodyFragment);
        }
        if (temperatureFragment != null)
            transaction.hide(temperatureFragment);

        if (bloodoFragment != null)
            transaction.hide(bloodoFragment);

        if (bloodHeartFragment != null)
            transaction.hide(bloodHeartFragment);

        if (faceTonFragment != null)
            transaction.hide(faceTonFragment);

        if (questionFragment != null)
            transaction.hide(questionFragment);

        transaction.commitAllowingStateLoss();

        currFragment = bodyFragment;
    }

    private void showTempFragment() {
        if (currFragment != null && currFragment == temperatureFragment)
            return;

        FragmentTransaction transaction = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();

        if (temperatureFragment == null) {
            temperatureFragment = new TemperatureFragment();
            transaction.add(R.id.fl_content, temperatureFragment);
        } else {
            transaction.show(temperatureFragment);
        }

        if (bodyFragment != null)
            transaction.hide(bodyFragment);

        if (bloodoFragment != null)
            transaction.hide(bloodoFragment);

        if (bloodHeartFragment != null)
            transaction.hide(bloodHeartFragment);

        if (faceTonFragment != null)
            transaction.hide(faceTonFragment);

        if (questionFragment != null)
            transaction.hide(questionFragment);

        transaction.commitAllowingStateLoss();

        currFragment = temperatureFragment;
    }


    private void showBloodoFragment() {

        if (currFragment != null && currFragment == bloodoFragment)
            return;

        FragmentTransaction transaction = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();


        if (bodyFragment != null)
            transaction.hide(bodyFragment);

        if (temperatureFragment != null)
            transaction.hide(temperatureFragment);

        if (bloodoFragment == null) {
            bloodoFragment = new BloodoFragment();
            transaction.add(R.id.fl_content, bloodoFragment);
        } else {
            transaction.show(bloodoFragment);
        }


        if (bloodHeartFragment != null)
            transaction.hide(bloodHeartFragment);

        if (faceTonFragment != null)
            transaction.hide(faceTonFragment);

        if (questionFragment != null)
            transaction.hide(questionFragment);

        transaction.commitAllowingStateLoss();

        currFragment = bloodoFragment;
    }

    private void showBloodHeartFragment() {

        if (currFragment != null && currFragment == bloodHeartFragment)
            return;

        FragmentTransaction transaction = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();

        if (bodyFragment != null)
            transaction.hide(bodyFragment);

        if (temperatureFragment != null)
            transaction.hide(temperatureFragment);

        if (bloodoFragment != null)
            transaction.hide(bloodoFragment);

        if (bloodHeartFragment == null) {
            bloodHeartFragment = new BloodHeartFragment();
            transaction.add(R.id.fl_content, bloodHeartFragment);
        } else {
            transaction.show(bloodHeartFragment);
        }

        if (faceTonFragment != null)
            transaction.hide(faceTonFragment);

        if (questionFragment != null)
            transaction.hide(questionFragment);

        transaction.commitAllowingStateLoss();

        currFragment = bloodHeartFragment;
    }

    private void showFaceTonFragment() {


        if (currFragment != null && currFragment == faceTonFragment)
            return;

        FragmentTransaction transaction = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();

        if (bodyFragment != null)
            transaction.hide(bodyFragment);

        if (temperatureFragment != null)
            transaction.hide(temperatureFragment);

        if (bloodoFragment != null)
            transaction.hide(bloodoFragment);

        if (bloodHeartFragment != null)
            transaction.hide(bloodHeartFragment);

        if (faceTonFragment == null) {
            faceTonFragment = new FaceTonFragment();
            transaction.add(R.id.fl_content, faceTonFragment);
        } else {
            transaction.show(faceTonFragment);
        }

        if (questionFragment != null)
            transaction.hide(questionFragment);

        transaction.commitAllowingStateLoss();

        currFragment = faceTonFragment;
    }

    private void showQuestionFragment() {

        if (currFragment != null && currFragment == questionFragment)
            return;

        FragmentTransaction transaction = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();

        if (bodyFragment != null)
            transaction.hide(bodyFragment);

        if (temperatureFragment != null)
            transaction.hide(temperatureFragment);

        if (bloodoFragment != null)
            transaction.hide(bloodoFragment);

        if (bloodHeartFragment != null)
            transaction.hide(bloodHeartFragment);

        if (faceTonFragment != null)
            transaction.hide(faceTonFragment);

        if (questionFragment == null) {
            questionFragment = new QuestionFragment();
            transaction.add(R.id.fl_content, questionFragment);
        } else {
            transaction.show(questionFragment);
        }

        transaction.commitAllowingStateLoss();

        currFragment = questionFragment;
    }

    public void serialPortCallBack(String msg) {

        currFragment.serialPortCallBack(msg);
/*
        if (bodyFragment != null) {
            bodyFragment.serialPortCallBack(msg);
        }
        if (temperatureFragment != null) {
            temperatureFragment.serialPortCallBack(msg);
        }
        if (bloodoFragment != null) {
            bloodoFragment.serialPortCallBack(msg);
        }
        if (bloodHeartFragment != null) {
            bloodHeartFragment.serialPortCallBack(msg);
        }
        if (faceTonFragment != null) {
            faceTonFragment.serialPortCallBack(msg);
        }
        if (questionFragment != null) {
            questionFragment.serialPortCallBack(msg);
        }*/
    }

    public void serialPortIng(String msg) {

        if (bodyFragment != null) {
            bodyFragment.serialPortIng(msg);
        }
        if (temperatureFragment != null) {
            temperatureFragment.serialPortIng(msg);
        }
        if (bloodoFragment != null) {
            bloodoFragment.serialPortIng(msg);
        }
        if (bloodHeartFragment != null) {
            bloodHeartFragment.serialPortIng(msg);
        }
        if (faceTonFragment != null) {
            faceTonFragment.serialPortIng(msg);
        }
        if (questionFragment != null) {
            questionFragment.serialPortIng(msg);
        }
    }

    public void notifyMenu() {
        UserReport currUserReport = MyApplication.get().getCurrUserReport();

        if (currUserReport.getBodyReport().isFinish()) {
            rbBody.setButtonDrawable(R.drawable.examine_menu1_finish_selector);
        } else {
            rbBody.setButtonDrawable(R.drawable.examine_menu1_unfinish_selector);
        }

        if (currUserReport.getTemperatureReport().isFinish()) {
            rbTemp.setButtonDrawable(R.drawable.examine_menu11_finish_selector);
        } else {
            rbTemp.setButtonDrawable(R.drawable.examine_menu11_unfinish_selector);
        }

        if (currUserReport.getBloodOxygenReport().isFinish()) {
            rbBloodo.setButtonDrawable(R.drawable.examine_menu2_finish_selector);
        } else {
            rbBloodo.setButtonDrawable(R.drawable.examine_menu2_unfinish_selector);
        }

        if (currUserReport.getBloodPressureReport().isFinish()) {
            rbBloodHeart.setButtonDrawable(R.drawable.examine_menu3_finish_selector);
        } else {
            rbBloodHeart.setButtonDrawable(R.drawable.examine_menu3_unfinish_selector);
        }

        if (currUserReport.getFaceTonReport().isFinish()) {
            rbFaceTon.setButtonDrawable(R.drawable.examine_menu4_finish_selector);
        } else {
            rbFaceTon.setButtonDrawable(R.drawable.examine_menu4_unfinish_selector);
        }

        if (currUserReport.getQuestionReport().isFinish()) {
            rbQuestion.setButtonDrawable(R.drawable.examine_menu5_finish_selector);
        } else {
            rbQuestion.setButtonDrawable(R.drawable.examine_menu5_unfinish_selector);
        }

      /*  if (currUserReport.isFinishOne()) {
            ivReport.setVisibility(View.VISIBLE);
        } else {
            ivReport.setVisibility(View.INVISIBLE);
        }*/
    }

    public void nextExamine() {
        UserReport currUserReport = MyApplication.get().getCurrUserReport();
        int currIndex;
        if (currFragment == bodyFragment)
            currIndex = ExamineActivity.SHOW_BODY_EXAMINE;
        else if (currFragment == temperatureFragment)
            currIndex = ExamineActivity.SHOW_TEMPERATURE;
        else if (currFragment == bloodoFragment)
            currIndex = ExamineActivity.SHOW_BLOODO_EXAMINE;
        else if (currFragment == bloodHeartFragment)
            currIndex = ExamineActivity.SHOW_BLOOD_HEART_EXAMINE;
        else if (currFragment == faceTonFragment)
            currIndex = ExamineActivity.SHOW_FACE_TON_EXAMINE;
        else
            currIndex = ExamineActivity.SHOW_QUETION_EXAMINE;

        int nextIndex = currUserReport.nextIndex(currIndex);

        checkMenu(nextIndex);
    }
}


