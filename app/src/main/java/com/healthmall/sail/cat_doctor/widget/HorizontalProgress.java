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

import java.util.List;

/**
 * Created by mai on 2018/1/20.
 */
public class HorizontalProgress extends View {

    Paint paint, bgPaint;

    float normalHigh, normalLow;

    float progress;

    float max;

    public HorizontalProgress(Context context) {
        super(context);

        initView();
    }

    public HorizontalProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    public HorizontalProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    public void setRange(List<Float> ranges) {
        normalLow = ranges.get(0);
        normalHigh = ranges.get(1);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }

    public void setMax(float max) {
        this.max = max;
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
        Shader shader;

        if (progress > normalHigh) {
            endX = getWidth() * 2 / 3f + (progress - normalHigh) / (max - normalHigh) * getWidth() / 3f;
            shader = new LinearGradient(0, 0, endX, 0, Color.parseColor("#ffbc00"), Color.parseColor("#ffbc00"), Shader.TileMode.CLAMP);
        } else if (progress < normalLow) {
            endX = progress / normalLow * getWidth() / 3f;
            shader = new LinearGradient(0, 0, endX, 0, Color.parseColor("#007aff"), Color.parseColor("#00ddff"), Shader.TileMode.CLAMP);
        } else {
            endX = getWidth() * 1 / 3f + (progress - normalLow) / (normalHigh - normalLow) * getWidth() / 3f;
            shader = new LinearGradient(0, 0, endX, 0, Color.parseColor("#00b99e"), Color.parseColor("#00ffda"), Shader.TileMode.CLAMP);

        }
        paint.setShader(shader);

        canvas.drawLine(7, getHeight() / 2, endX-7, getHeight() / 2, paint);
    }
}
