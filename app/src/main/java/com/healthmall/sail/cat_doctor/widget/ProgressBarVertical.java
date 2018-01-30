package com.healthmall.sail.cat_doctor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mai on 2018/1/15.
 */
public class ProgressBarVertical extends View {

    Paint progressPaint, bgPaint;

    int index = 1, max = 200;

    public ProgressBarVertical(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        initView();
    }

    public ProgressBarVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initView();
    }

    public ProgressBarVertical(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView();
    }

    public void setIndex(int index) {
        this.index = index;
        postInvalidate();
    }

    public void setMax(int max) {
        this.max = max;
    }

    private void initView() {
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeWidth(8);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStyle(Paint.Style.STROKE);


        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStrokeWidth(8);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        bgPaint.setColor(Color.parseColor("#80f2f2f2"));
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        Shader shader = new LinearGradient(0, 0, 0, getHeight(), Color.parseColor("#0072ff"), Color.parseColor("#00c6ff"), Shader.TileMode.CLAMP);
        progressPaint.setShader(shader);

        canvas.drawLine(4, 4, 4, getHeight() - 4, bgPaint);

        canvas.drawLine(4, 4, 4, (getHeight() - 4) * index / max, progressPaint);
    }
}