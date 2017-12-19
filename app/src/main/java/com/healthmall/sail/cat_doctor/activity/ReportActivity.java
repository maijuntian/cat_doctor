package com.healthmall.sail.cat_doctor.activity;

import android.os.Bundle;
import android.view.View;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseActivity;
import com.healthmall.sail.cat_doctor.delegate.ReportDelegate;
import com.mai.xmai_fast_lib.utils.XAppManager;

import butterknife.OnClick;


public class ReportActivity extends BaseActivity<ReportDelegate> {

    @OnClick(R.id.iv_return)
    public void iv_returnClick() {
        XAppManager.getInstance().finishActivity(MainActivity.class);
        MyApplication.get().logout();
        finish();
    }

    @OnClick(R.id.tv_examine_now)
    public void tv_examine_nowClick() {

        Bundle bundle = new Bundle();
        bundle.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_QUETION_EXAMINE);
        startActivity(ExamineActivity.class, bundle, true);
    }

    @OnClick({R.id.iv_examine1, R.id.iv_examine2, R.id.iv_examine3, R.id.iv_examine4})
    public void exam(View v) {
        switch (v.getId()) {
            case R.id.iv_examine1:
                Bundle bundle1 = new Bundle();
                bundle1.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_BODY_EXAMINE);
                startActivity(ExamineActivity.class, bundle1, true);
                break;
            case R.id.iv_examine2:

                Bundle bundle2 = new Bundle();
                bundle2.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_BLOODO_EXAMINE);
                startActivity(ExamineActivity.class, bundle2, true);
                break;
            case R.id.iv_examine3:

                Bundle bundle3 = new Bundle();
                bundle3.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_BLOOD_HEART_EXAMINE);
                startActivity(ExamineActivity.class, bundle3, true);
                break;
            case R.id.iv_examine4:

                Bundle bundle4 = new Bundle();
                bundle4.putInt(ExamineActivity.EXAMINE_MENU, ExamineActivity.SHOW_FACE_TON_EXAMINE);
                startActivity(ExamineActivity.class, bundle4, true);
                break;
        }
    }
}


