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
 * Created by mai on 2017/12/25.
 */
public class ProgressView extends View {

    Paint bgPaint, progressPaint, textPaint;
    int progress = 50;

    public ProgressView(Context context) {
        super(context);
        initView();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

        bgPaint = new Paint();
        bgPaint.setStrokeWidth(19);
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        bgPaint.setStrokeCap(Paint.Cap.ROUND);

        progressPaint = new Paint();
        progressPaint.setStrokeWidth(19);
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.FILL);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        textPaint.setTextSize(18);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(10, getHeight() / 2, getWidth() - 10, getHeight() / 2, bgPaint);

        float progressX = getWidth() * progress / 100f;
        Shader shader = new LinearGradient(0, getHeight() / 2f, getWidth(), getHeight() / 2f, Color.parseColor("#0072FF"), Color.parseColor("#00C6FF"), Shader.TileMode.CLAMP);
        progressPaint.setShader(shader);
        canvas.drawLine(10, getHeight() / 2, progressX - 10, getHeight() / 2, progressPaint);

        float textX = progressX - textPaint.measureText(progress + "%") - 5;

        canvas.drawText(progress + "%", textX, getHeight() - 5, textPaint);
    }
}
