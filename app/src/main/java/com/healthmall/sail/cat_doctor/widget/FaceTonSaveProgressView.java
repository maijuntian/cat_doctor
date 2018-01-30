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
import com.mai.xmai_fast_lib.utils.MLog;

/**
 * Created by mai on 2017/11/13.
 */
public class FaceTonSaveProgressView extends View {

    Paint progressPaint;

    float progress = 0;

    public FaceTonSaveProgressView(Context context) {
        super(context);
    }

    public FaceTonSaveProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    public FaceTonSaveProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public void initView() {

        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(18);
        progressPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Shader shader = new LinearGradient(getWidth() / 2f, 0, getWidth() / 2f, getHeight(), Color.parseColor("#0072ff"), Color.parseColor("#00ddff"), Shader.TileMode.CLAMP);
        progressPaint.setShader(shader);

        canvas.drawArc(9, 9, getWidth()-9, getHeight()-9, -90f, progress * 360f / 100f, false, progressPaint);
    }
}
