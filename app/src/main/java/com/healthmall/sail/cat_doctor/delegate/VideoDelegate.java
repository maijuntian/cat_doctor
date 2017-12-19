package com.healthmall.sail.cat_doctor.delegate;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseDelegate;
import com.healthmall.sail.cat_doctor.widget.MyVideoView;
import com.mai.xmai_fast_lib.utils.MLog;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class VideoDelegate extends BaseDelegate {

    @Bind(R.id.vv_advert)
    MyVideoView vvAdvert;
    @Bind(R.id.iv_tip)
    ImageView ivTip;
    @Bind(R.id.rl_root)
    RelativeLayout rlRoot;

    Animation showAnim, hideAnim;

    Subscription sbTip;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        showAnim = AnimationUtils.loadAnimation(mContext, R.anim.alert_in_anim);
        hideAnim = AnimationUtils.loadAnimation(mContext, R.anim.alert_out_anim);

    }

    public void showTip(){
        MLog.log("开始提示--->");
        ivTip.startAnimation(showAnim);
        ivTip.setVisibility(View.VISIBLE);

        sbTip = Observable.timer(5000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                hideTip();
            }
        });
    }


    public void hideTip(){
        ivTip.startAnimation(hideAnim);
        ivTip.setVisibility(View.GONE);
    }

    @Override
    public void onDestory() {
        if(sbTip != null){
            sbTip.unsubscribe();
        }
        super.onDestory();
    }
}


