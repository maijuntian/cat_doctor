package com.healthmall.sail.cat_doctor.delegate;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseDelegate;
import com.healthmall.sail.cat_doctor.bean.User;
import com.healthmall.sail.cat_doctor.utils.Configs;
import com.healthmall.sail.cat_doctor.utils.MGlide;

import butterknife.Bind;

/**
 * Created by mai on 2017/11/10.
 */
public class MainDelegate extends BaseDelegate {

    @Bind(R.id.iv_body)
    ImageView ivBody;
    @Bind(R.id.iv_temp)
    ImageView ivTemp;
    @Bind(R.id.iv_blood_o)
    ImageView ivBloodO;
    @Bind(R.id.iv_bp_hp)
    ImageView ivBpHp;
    @Bind(R.id.iv_face_ton)
    ImageView ivFaceTon;
    @Bind(R.id.iv_question)
    ImageView ivQuestion;
    @Bind(R.id.iv_logout)
    ImageView ivLogout;
    @Bind(R.id.tv_nick_name)
    TextView tvNickName;
    @Bind(R.id.cb_voice)
    public CheckBox cbVoice;
    @Bind(R.id.iv_head_icon)
    ImageView ivHeadIcon;
    @Bind(R.id.iv_vip)
    ImageView ivVip;
    @Bind(R.id.iv_divider)
    ImageView ivDivider;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        initUser();

        cbVoice.setChecked(MyApplication.get().getCurrUser().isVoice());

        if (!Configs.useTemp) {
            ivDivider.setVisibility(View.GONE);
            ivTemp.setVisibility(View.GONE);
        }
    }

    public void initUser() {
        User currUser = MyApplication.get().getCurrUser();
        if (currUser == null)
            ((Activity) mContext).finish();

        tvNickName.setText(currUser.getMemberName());
        MGlide.loadCircle(mContext, currUser.getMemHeadImg(), ivHeadIcon);


        if (currUser.isTalent()) {
            ivVip.setVisibility(View.VISIBLE);
        } else {
            ivVip.setVisibility(View.INVISIBLE);
        }
    }
}
