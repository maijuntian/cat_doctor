package com.healthmall.sail.cat_doctor.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.VideoView;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseActivity;
import com.healthmall.sail.cat_doctor.delegate.VideoDelegate;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.serialport.SerialPortEngine;
import com.mai.xmai_fast_lib.utils.MLog;
import com.mai.xmai_fast_lib.utils.XAppManager;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;


public class VideoActivity extends BaseActivity<VideoDelegate> {

    @Bind(R.id.vv_advert)
    VideoView vvAdvert;

//    Subscription sbCheckPerson;

    boolean lastReult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //本地的视频 需要在手机SD卡根目录添加一个 fl1234.mp4 视频
        String videoUrl = Environment.getExternalStorageDirectory().getPath() + "/index.mp4";

        vvAdvert.setVideoURI(Uri.parse(videoUrl));
        vvAdvert.start();
        vvAdvert.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });


        SerialPortCmd.checkPerson();

        /*sbCheckPerson = Observable.interval(2000, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                SerialPortCmd.checkPerson();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });*/
    }

    @Override
    public void serialPortCallBack(String msg) {
        super.serialPortCallBack(msg);

        boolean result = msg.equals(SerialPortCmd.OK_SMONE);
        if (result && !lastReult) {
            viewDelegate.showTip();
        }
        lastReult = result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        vvAdvert.resume();
        vvAdvert.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        vvAdvert.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if (sbCheckPerson != null) {
            sbCheckPerson.unsubscribe();
        }*/
        SerialPortCmd.stopCheckPerson();
        vvAdvert.destroyDrawingCache();
    }


    @OnClick(R.id.rl_root)
    public void rl_rootClick() {
        MLog.log("点击了");
        if (XAppManager.getInstance().findActivity(IndexActivity.class) != null) {
            finish();
        } else {
            startActivity(IndexActivity.class, true);
        }
    }
}


