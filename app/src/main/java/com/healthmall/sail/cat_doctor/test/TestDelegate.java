package com.healthmall.sail.cat_doctor.test;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.widget.WaterView;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import butterknife.Bind;

/**
 * Created by mai on 2017/12/8.
 */
public class TestDelegate extends AppDelegate {
    @Bind(R.id.wv_bo)
    WaterView wvBo;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        wvBo.setProgress(98);
    }
}
