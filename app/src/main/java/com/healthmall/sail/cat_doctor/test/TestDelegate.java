package com.healthmall.sail.cat_doctor.test;

import android.graphics.Color;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.utils.WaveHelper;
import com.healthmall.sail.cat_doctor.widget.WaterView;
import com.healthmall.sail.cat_doctor.widget.WaveView;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import butterknife.Bind;

/**
 * Created by mai on 2017/12/8.
 */
public class TestDelegate extends AppDelegate {
    @Bind(R.id.wv_bo)
    WaterView wvBo;
    @Bind(R.id.wave)
    WaveView wave;
    @Bind(R.id.wave2)
    WaveView wave2;

    WaveHelper waveHelper;
    WaveHelper waveHelper2;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        wvBo.setProgress(98);
        wave.setWaveColor(Color.parseColor("#5Cfec600"), Color.parseColor("#5Cf83900"), Color.parseColor("#38fec600"), Color.parseColor("#38f83900"));
        wave2.setWaveColor(Color.parseColor("#5C00c6ff"), Color.parseColor("#5C0072ff"), Color.parseColor("#3800c6ff"), Color.parseColor("#380072ff"));

        waveHelper = new WaveHelper(wave);
        waveHelper2 = new WaveHelper(wave2);

    }

    @Override
    public void onResume() {
        super.onResume();
        waveHelper.start();
        waveHelper2.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        waveHelper.cancel();
        waveHelper2.cancel();
    }
}
