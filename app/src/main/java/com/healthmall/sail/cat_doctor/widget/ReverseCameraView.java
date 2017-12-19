package com.healthmall.sail.cat_doctor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.wonderkiln.camerakit.CameraView;

/**
 * Created by mai on 2017/12/13.
 */
public class ReverseCameraView extends CameraView {
    public boolean isReverse = true;

    public ReverseCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void dispatchDraw(Canvas canvas) {

        if (isReverse)
            canvas.scale(-1, 1, getWidth() / 2, getHeight() / 2);

        super.dispatchDraw(canvas);
    }
}
