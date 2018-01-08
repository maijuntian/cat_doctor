package com.healthmall.sail.cat_doctor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.healthmall.sail.cat_doctor.R;
import com.mai.xmai_fast_lib.utils.MLog;

/**
 * Created by mai on 2017/12/28.
 */
public class HeartRateView extends View {
    Paint progressPaint, bgOutPaint, bgInPaint;


    private final int progressPlus = 5;

    private int progressIndex = 0;

    private int progress = 0;


    public HeartRateView(Context context) {
        super(context);
        initView();
    }

    public HeartRateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HeartRateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.FILL);

        bgOutPaint = new Paint();
        bgOutPaint.setAntiAlias(true);
        bgOutPaint.setStyle(Paint.Style.FILL);

        bgInPaint = new Paint();
        bgInPaint.setAntiAlias(true);
        bgInPaint.setStyle(Paint.Style.FILL);
        bgInPaint.setColor(Color.WHITE);
    }

    public void setProgressIndex(int progressIndex) {
        this.progressIndex = progressIndex;
    }

    public void setProgress(int progress) {
        if(progress > 100)
            progress = 100;
        this.progress = progress;

        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Shader shader = new LinearGradient(getWidth() / 2f, 0, getWidth() / 2f, getHeight(), Color.parseColor("#f83900"), Color.parseColor("#fec600"), Shader.TileMode.CLAMP);
        progressPaint.setShader(shader);

        Shader bgShader = new LinearGradient(getWidth() / 2f, 0, getWidth() / 2f, getHeight(), Color.parseColor("#ffffff"), Color.parseColor("#e9e8e8"), Shader.TileMode.CLAMP);
        bgOutPaint.setShader(bgShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(0, 0, getWidth(), getHeight(), -90, 360, true, bgOutPaint);


        if (progress == 0)
            return;

        progressIndex += progressPlus;

        if (progressIndex > progress)
            progressIndex = progress;


        float angle = 0;
        if (progressIndex > 110) {
            angle = 360;
        } else if (progressIndex > 100) {
            angle = 300;
        } else if (progressIndex >= 60) {
            angle = 90 + (180 * (progressIndex - 60) / 40f);
        } else {
            angle = 90 * progressIndex / 60f;
        }

        MLog.log("旋转多少度--->" + angle);
        canvas.drawArc(0, 0, getWidth(), getHeight(), -90, angle, true, progressPaint);
        canvas.drawArc(27, 0, getWidth() - 27, getHeight() - 26, -90, 360, true, bgInPaint);

        if (progressIndex < progress)
            postInvalidate();
    }

}
