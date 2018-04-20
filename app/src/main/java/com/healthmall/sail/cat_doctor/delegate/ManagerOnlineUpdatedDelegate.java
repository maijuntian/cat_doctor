package com.healthmall.sail.cat_doctor.delegate;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.activity.ManagerMainActivity;
import com.healthmall.sail.cat_doctor.utils.VersionUtils;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import butterknife.Bind;

/**
 * Created by mai on 2017/11/21.
 */
public class ManagerOnlineUpdatedDelegate extends AppDelegate {
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_last_version)
    TextView tvLastVersion;
    @Bind(R.id.iv_online_update)
    ImageView ivOnlineUpdate;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_online_updated;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        tvVersion.setText("当前版本：" + VersionUtils.getVersionName(mContext));
        tvTime.setText("更新时间：" + getString(R.string.version_time));

        if (((ManagerMainActivity) mContext).newVersion != null)
            newVersion();
    }

    public void newVersion() {
        tvLastVersion.setVisibility(View.GONE);
        ivOnlineUpdate.setVisibility(View.VISIBLE);
    }
}
