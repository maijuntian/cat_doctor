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
public class BlueHorizontalProgress extends View {


    Paint paint, bgPaint;

    float progress;

    float max;

    public BlueHorizontalProgress(Context context) {
        super(context);

        initView();
    }

    public BlueHorizontalProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    public BlueHorizontalProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        paint.setStrokeWidth(14);
        paint.setStrokeCap(Paint.Cap.ROUND);

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStrokeWidth(14);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);

        bgPaint.setColor(Color.parseColor("#eeeeee"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawLine(7, getHeight() / 2, getWidth() - 7, getHeight() / 2, bgPaint);

        if (progress == 0)
            return;

        float endX;

        if (progress > max) {
            endX = getWidth() * 15 / 16f;
        } else {
            endX = progress / max * getWidth() * 7 / 8;
        }

        Shader shader = new LinearGradient(7, getHeight() / 2, endX, getHeight() / 2, Color.parseColor("#007dff"), Color.parseColor("#00dcff"), Shader.TileMode.CLAMP);

        paint.setShader(shader);

        canvas.drawLine(7, getHeight() / 2, endX - 7, getHeight() / 2, paint);
    }
}
