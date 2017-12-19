package com.healthmall.sail.cat_doctor.delegate;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.utils.Keys;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;
import com.mai.xmai_fast_lib.utils.MLog;
import com.mai.xmai_fast_lib.utils.SharedPreferencesHelper;

import butterknife.Bind;

/**
 * Created by mai on 2017/11/21.
 */
public class ManagerProductInfoDelegate extends AppDelegate {
    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.rl_name)
    RelativeLayout rlName;
    @Bind(R.id.divider)
    View divider;
    @Bind(R.id.tv_device_id_tip)
    TextView tvDeviceIdTip;
    @Bind(R.id.tv_device_id)
    TextView tvDeviceId;
    @Bind(R.id.et_device_id)
    EditText etDeviceId;
    @Bind(R.id.rl_device_id)
    RelativeLayout rlDeviceId;
    @Bind(R.id.iv_commit)
    ImageView ivCommit;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_product_info;
    }

    public void initDeviceId() {

        String deviceId = SharedPreferencesHelper.getInstance(mContext.getApplicationContext()).getStringValue(Keys.KEY_DEVICE_ID);

        if (!TextUtils.isEmpty(deviceId)) {
            tvDeviceId.setVisibility(View.VISIBLE);
            etDeviceId.setVisibility(View.GONE);
            ivCommit.setVisibility(View.GONE);
            tvDeviceId.setText(deviceId);
        } else {
            tvDeviceId.setVisibility(View.GONE);
            etDeviceId.setVisibility(View.VISIBLE);
            ivCommit.setVisibility(View.VISIBLE);

        }
    }
}
