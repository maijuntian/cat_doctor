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
 * Created by mai on 2018/1/9.
 */
public class MuscleProgressView extends View {

    Paint borderPaint, progressPaint;

    final int padding = 19;

    int progress;

    int progressIndex;

    private final int progressPlus = 3;

    public MuscleProgressView(Context context) {
        super(context);
        initView();
    }

    public MuscleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MuscleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(1);
        borderPaint.setAntiAlias(true);

        progressPaint = new Paint();
        progressPaint.setStrokeWidth(8);
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (progress == 0)
            return;

        progressIndex += progressPlus;

        if (progressIndex > progress)
            progressIndex = progress;

        Shader borderShader = new LinearGradient(getWidth() / 2, 0, getWidth() / 2, getHeight(), Color.parseColor("#4d0077fb"), Color.parseColor("#4d00dcff"), Shader.TileMode.CLAMP);
        borderPaint.setShader(borderShader);

        Shader progressShader = new LinearGradient(getWidth() / 2, padding, getWidth() / 2, getHeight() - padding, Color.parseColor("#0077fb"), Color.parseColor("#00dcff"), Shader.TileMode.CLAMP);
        progressPaint.setShader(progressShader);

        canvas.drawArc(0, 0, getWidth(), getHeight(), 135, 270, false, borderPaint);

        canvas.drawArc(padding, padding, getWidth() - padding, getHeight() - padding, 135, progressIndex * 170 / 100f, false, progressPaint);


        if (progressIndex < progress)
            postInvalidate();
    }

}
