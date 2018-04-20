package com.healthmall.sail.cat_doctor.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.healthmall.sail.cat_doctor.Constant;
import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseActivity;
import com.healthmall.sail.cat_doctor.base.BaseSoftActivity;
import com.healthmall.sail.cat_doctor.bean.Version;
import com.healthmall.sail.cat_doctor.delegate.ManagerMainDelegate;
import com.healthmall.sail.cat_doctor.download.DownloadUtils;
import com.healthmall.sail.cat_doctor.http.CatDoctorApi;
import com.healthmall.sail.cat_doctor.utils.DialogUtils;
import com.healthmall.sail.cat_doctor.utils.Keys;
import com.healthmall.sail.cat_doctor.utils.VersionUtils;
import com.healthmall.sail.cat_doctor.widget.GreenHorizontalProgress;
import com.mai.xmai_fast_lib.basehttp.MParams;
import com.mai.xmai_fast_lib.mvvm.presenter.ActivityPresenter;
import com.mai.xmai_fast_lib.utils.MLog;
import com.mai.xmai_fast_lib.utils.SharedPreferencesHelper;
import com.mai.xmai_fast_lib.utils.XAppManager;

import java.io.File;
import java.io.IOException;

import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;


public class ManagerMainActivity extends BaseSoftActivity<ManagerMainDelegate> {

    Dialog upGradeDialog;

    GreenHorizontalProgress upGradeProgress;

    public Version newVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String deviceId = SharedPreferencesHelper.getInstance(this).getStringValue(Keys.KEY_DEVICE_ID);

        if (!TextUtils.isEmpty(deviceId)) {

            CatDoctorApi.getInstance().getNewVersion(new MParams().add("deviceId", deviceId)
                    .add("version", VersionUtils.getVersionName(this))
                    .add("deviceType", 7)
                    .add("manufacturerId", 7), this)
                    .subscribe(new Action1<Version>() {
                        @Override
                        public void call(Version version) {
                            MLog.log("服务器的版本-->" + version.getVersion());
                            MLog.log("当前版本-->" + (Float.parseFloat(VersionUtils.getVersionName(getApplicationContext()))));
                            if ((version.getVersion()+"").compareTo(VersionUtils.getVersionName(getApplicationContext())) > 0) {
                                viewDelegate.ivNewVersion.setVisibility(View.VISIBLE);
                                newVersion = version;
                                DialogUtils.showNewVersionDialog(ManagerMainActivity.this, new Action0() {
                                    @Override
                                    public void call() {
                                        download();
                                    }
                                });

                                if (viewDelegate.onlineUpdatedFragment != null) {
                                    viewDelegate.onlineUpdatedFragment.notifyNewVersion();
                                }
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    });
        }
    }

    @OnClick(R.id.iv_logout)
    public void iv_logoutClick() {
        finish();
    }

    @OnClick(R.id.iv_power_off)
    public void iv_power_offClick() {
        //重启
//        reboot();
        DialogUtils.showPowerDialog(this, new Action0() {
            @Override
            public void call() { //关机
                powerOff();
            }
        }, new Action0() {
            @Override
            public void call() { //重启
                reboot();
            }
        });
    }


    public int powerOff() {
        int r;
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot -p"});
            r = process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
            r = -1;
        } catch (InterruptedException e) {
            e.printStackTrace();
            r = -1;
        }
        return r;
    }

    public int reboot() {
        int r;
        try {
            Process process = Runtime.getRuntime().exec("su -c \"reboot\"");
            r = process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
            r = -1;
        } catch (InterruptedException e) {
            e.printStackTrace();
            r = -1;
        }
        log("result: " + r);
        return r;
    }

    public void serialPortCallBack(String msg) {  //串口通信的回调
        if (viewDelegate.hardwareCheckFragment != null)
            viewDelegate.hardwareCheckFragment.serialPortCallBack(msg);
    }


    /**
     * 下载新apk
     */
    public void download() {

        if (upGradeDialog == null) {
            upGradeDialog = DialogUtils.showUpgradeDialog(this);
        } else {
            upGradeDialog.show();
        }

        upGradeProgress = upGradeDialog.findViewById(R.id.pb_progress);

        DownloadUtils.getInstance().download(newVersion.getUrl(), Constant.SDCARD_DOWNLOAD_PATH, new DownloadUtils.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String path) {
                upGradeDialog.dismiss();

                XAppManager.getInstance().AppExit(MyApplication.get().getApplicationContext(), true);

                File outputFile = new File(path);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setDataAndType(Uri.fromFile(outputFile),
                        "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onDownloading(int progress) {
                upGradeProgress.setProgress(progress);
            }

            @Override
            public void onDownloadFailed() {
                upGradeDialog.dismiss();
                DialogUtils.showUpgradeFail(ManagerMainActivity.this);
            }
        });
    }
}


