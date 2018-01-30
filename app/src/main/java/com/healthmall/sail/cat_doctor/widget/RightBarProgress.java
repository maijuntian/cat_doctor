package com.healthmall.sail.cat_doctor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.utils.FloatUtils;
import com.mai.xmai_fast_lib.utils.MLog;

import java.util.List;

/**
 * Created by mai on 2018/1/12.
 */
public class RightBarProgress extends View {

    Paint progressPaint, textPaint, linePaint, nomarlPaint;

    float nomalHigh = 28f;
    float nomalLow = 25f;

    float textHeight;
    float progress = 27;

    float max = 80;
    float min = 5;

    int greenColor;
    int blueColor;
    int orangeColor;
    int transColor;


    private final float progressPlus = 5;

    private float progressIndex = 0;


    public RightBarProgress(Context context) {
        super(context);
        initView();
    }

    public RightBarProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RightBarProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setRange(List<Float> ranges){
        nomalLow = ranges.get(0);
        nomalHigh = ranges.get(1);
    }

    public void setProgress(float progress) {
        this.progressIndex = 0;
        this.progress = progress;
        postInvalidate();
    }

    private void initView() {
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeWidth(50);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setTextSize(24);
        textPaint.setAntiAlias(true);
        textPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setTextSize(16);
        linePaint.setColor(ContextCompat.getColor(getContext(), R.color.mgray));

        nomarlPaint = new Paint();
        nomarlPaint.setAntiAlias(true);
        nomarlPaint.setStrokeWidth(8);
        nomarlPaint.setColor(Color.parseColor("#cdf1ec"));

        Rect rect = new Rect();
        linePaint.getTextBounds("2", 0, 1, rect);
        textHeight = rect.height();

        greenColor = Color.parseColor("#00ba9e");
        blueColor = Color.parseColor("#00dcff");
        orangeColor = Color.parseColor("#fec100");
        transColor = Color.parseColor("#00ffffff");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBg(canvas);
        MLog.log("nomalh-->" + nomalHigh + " nomall" + nomalLow + "  progress" + progress);



        progressIndex += progressPlus;

        if (progressIndex > progress)
            progressIndex = progress;

        drawProgress(canvas, progressIndex, progressIndex == progress);

        if (progressIndex < progress)
            postInvalidate();

    }

    private void drawBg(Canvas canvas) {
        canvas.drawLine(0, 0, 0, getHeight(), linePaint);

        canvas.drawText("%", 0, 1, 10, textHeight, linePaint);

        float highY = getHeight() / 3f;
        canvas.drawLine(0, highY, 8, highY, linePaint);
        canvas.drawText(FloatUtils.getString(nomalHigh), 12, highY + textHeight / 2f, linePaint);

        float lowY = highY * 2;
        canvas.drawLine(0, lowY, 8, lowY, linePaint);
        canvas.drawText(FloatUtils.getString(nomalLow), 12, lowY + textHeight / 2f, linePaint);

        canvas.drawLine(4, highY, 4, lowY, nomarlPaint);
    }


    private void drawProgress(Canvas canvas, float progress, boolean isFinish) {
        float progressY;

        Shader linearShader;
        if (progress > nomalHigh) {
            progressY = getHeight() / 3f - getHeight() / 3f * (progress - nomalHigh) / (max - nomalHigh);

            linearShader = new LinearGradient(25, progressY, 25, getHeight(), orangeColor, transColor, Shader.TileMode.CLAMP);
        } else if (progress < nomalLow) {
            progressY = getHeight() - getHeight() / 3f * (progress - min) / (nomalLow - min);
            linearShader = new LinearGradient(25, progressY, 25, getHeight(), blueColor, transColor, Shader.TileMode.CLAMP);
        } else {
            progressY = getHeight() * 2f / 3f - getHeight() / 3f * (progress - nomalLow) / (nomalHigh - nomalLow);
            linearShader = new LinearGradient(25, progressY, 25, getHeight(), greenColor, transColor, Shader.TileMode.CLAMP);
        }
        progressPaint.setShader(linearShader);

        canvas.drawLine(25, getHeight(), 25, progressY, progressPaint);

        if (isFinish) {
            String progressStr = FloatUtils.getString(progress);
            Rect valueRect = new Rect();
            textPaint.getTextBounds(progressStr, 0, progressStr.length(), valueRect);
            canvas.drawText(progressStr, (getWidth() - valueRect.width()) / 2f, progressY + valueRect.height() / 2f, textPaint);
        }
    }

}
