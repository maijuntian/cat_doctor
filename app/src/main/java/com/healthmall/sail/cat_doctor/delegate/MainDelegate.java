package com.healthmall.sail.cat_doctor.delegate;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseDelegate;
import com.healthmall.sail.cat_doctor.bean.User;
import com.healthmall.sail.cat_doctor.utils.MGlide;

import butterknife.Bind;

/**
 * Created by mai on 2017/11/10.
 */
public class MainDelegate extends BaseDelegate {
    @Bind(R.id.iv_head_icon)
    ImageView ivHeadIcon;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.iv_body)
    ImageView ivBody;
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
    @Bind(R.id.iv_vip)
    ImageView ivVip;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        initUser();
    }

    public void initUser() {
        User currUser = MyApplication.get().getCurrUser();
        tvNickname.setText(currUser.getMemberName());
        MGlide.loadCircle(currUser.getMemHeadImg(), ivHeadIcon);

        if (currUser.isTalent())
            ivVip.setVisibility(View.VISIBLE);
        else
            ivVip.setVisibility(View.INVISIBLE);
    }
}
