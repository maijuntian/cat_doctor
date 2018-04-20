package com.healthmall.sail.cat_doctor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.healthmall.sail.cat_doctor.R;

/**
 * Created by mai on 2018/1/20.
 */
public class GreenHorizontalProgress extends View {


    Paint paint, bgPaint, textPaint;

    float progress;

    float max = 100f;

    public GreenHorizontalProgress(Context context) {
        super(context);

        initView();
    }

    public GreenHorizontalProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    public GreenHorizontalProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    public void setMax(float max) {
        this.max = max;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }

    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setStrokeCap(Paint.Cap.ROUND);

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStrokeWidth(20);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);

        bgPaint.setColor(Color.parseColor("#eeeeee"));


        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        textPaint.setTextSize(14);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawLine(10, getHeight() / 2, getWidth() - 10, getHeight() / 2, bgPaint);

        if (progress == 0)
            return;

        float endX = progress / max * getWidth();

        Shader shader = new LinearGradient(10, getHeight() / 2, endX - 10, getHeight() / 2, Color.parseColor("#4cbaef"), Color.parseColor("#4ce3cd"), Shader.TileMode.CLAMP);

        paint.setShader(shader);

        canvas.drawLine(10, getHeight() / 2, endX - 10, getHeight() / 2, paint);


        float textX = endX - textPaint.measureText(progress + "%") - 5;

        canvas.drawText(progress + "%", textX, getHeight() - 5, textPaint);
    }
}
