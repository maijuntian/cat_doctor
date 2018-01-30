package com.healthmall.sail.cat_doctor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mai on 2018/1/20.
 */
public class BlueCircleProgressView extends View {
    Paint paint;

    float progress;

    float max;

    public BlueCircleProgressView(Context context) {
        super(context);

        initView();
    }

    public BlueCircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    public BlueCircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    public void setMax(float max) {
        this.max = max;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if (progress == 0)
            return;

        float endAngle;

        if (progress > max) {
            endAngle = 360 * 15 / 16f;
        } else {
            endAngle = progress / max * 360 * 7 / 8;
        }

        Shader shader = new LinearGradient(0, 0, 0, getHeight(), Color.parseColor("#007dff"), Color.parseColor("#00dcff"), Shader.TileMode.CLAMP);

        paint.setShader(shader);

        canvas.drawArc(4, 4, getWidth() - 4, getHeight() - 4, -90, endAngle, false, paint);
    }
}
