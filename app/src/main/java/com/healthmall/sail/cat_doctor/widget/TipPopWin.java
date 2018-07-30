package com.healthmall.sail.cat_doctor.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.R;

/**
 * Created by mai on 2018/1/3.
 */
public class TipPopWin extends PopupWindow {


    public TipPopWin(Context mContext, int layoutId) {

        View view = LayoutInflater.from(mContext).inflate(layoutId, null);

        // 设置外部可点击
        this.setOutsideTouchable(false);

        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(false);

        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.tip_anim);
    }
}
