package com.healthmall.sail.cat_doctor.delegate;

import android.text.TextUtils;
import android.widget.ImageView;

import com.healthmall.sail.cat_doctor.Constant;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.base.BaseDelegate;
import com.healthmall.sail.cat_doctor.utils.Keys;
import com.healthmall.sail.cat_doctor.utils.QrCodeUtils;
import com.mai.xmai_fast_lib.utils.SharedPreferencesHelper;

import butterknife.Bind;


public class IndexDelegate extends BaseDelegate {

    @Bind(R.id.iv_qrcode)
    ImageView ivQrcode;
    @Bind(R.id.iv_video)
    ImageView ivVideo;
    @Bind(R.id.iv_manager)
    ImageView ivManager;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_index;
    }


    public void initQrCode() {
        String deviceId = SharedPreferencesHelper.getInstance(mContext.getApplicationContext()).getStringValue(Keys.KEY_DEVICE_ID);
        if (!TextUtils.isEmpty(deviceId)) {
            ivQrcode.setImageBitmap(QrCodeUtils.createImage(Constant.DEVICE_QR_CODE_URL_PRE + deviceId, 200, 200));
        }
    }

    @Override
    public void onResume() {
        initQrCode();
    }
}


