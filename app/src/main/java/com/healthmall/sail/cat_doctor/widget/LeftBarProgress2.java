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
public class LeftBarProgress2 extends View {

    Paint progressPaint, textPaint, linePaint, nomarlPaint;

    float high = 56.3f;
    float nomalHigh = 52.9f;
    float nomalLow = 49.5f;
    float low = 46;

    float textHeight;
    float progress = 27;

    float max = 66;
    float min = 30;

    int greenColor;
    int blueColor;
    int orangeColor;
    int transColor;


    private final float progressPlus = 5;

    private float progressIndex = 0;


    public LeftBarProgress2(Context context) {
        super(context);
        initView();
    }

    public LeftBarProgress2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LeftBarProgress2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setRange(List<Float> ranges) {
        low = ranges.get(0);
        nomalLow = ranges.get(1);
        nomalHigh = ranges.get(2);
        high = ranges.get(3);
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

        MLog.log("low:" + low + "  nomalLow:" + nomalLow + "  nomalHigh:" +
                nomalHigh + "  high:" +
                high);

        progressIndex += progressPlus;

        if (progressIndex > progress)
            progressIndex = progress;

        drawProgress(canvas, progressIndex, progressIndex == progress);

        if (progressIndex < progress)
            postInvalidate();

    }

    private void drawBg(Canvas canvas) {
        canvas.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight(), linePaint);

        canvas.drawText("%", 0, 1, 30, textHeight, linePaint);

        float high1Y = getHeight() / 5f;
        canvas.drawLine(getWidth(), high1Y, getWidth() - 8, high1Y, linePaint);
        canvas.drawText(FloatUtils.getString(high), 10, high1Y + textHeight / 2f, linePaint);

        float highY = getHeight() * 2f / 5f;
        canvas.drawLine(getWidth(), highY, getWidth() - 8, highY, linePaint);
        canvas.drawText(FloatUtils.getString(nomalHigh), 10, highY + textHeight / 2f, linePaint);

        float lowY = getHeight() * 3f / 5f;
        canvas.drawLine(getWidth(), lowY, getWidth() - 8, lowY, linePaint);
        canvas.drawText(FloatUtils.getString(nomalLow), 10, lowY + textHeight / 2f, linePaint);

        float low1Y = getHeight() * 4f / 5f;
        canvas.drawLine(getWidth(), low1Y, getWidth() - 8, low1Y, linePaint);
        canvas.drawText(FloatUtils.getString(low), 10, low1Y + textHeight / 2f, linePaint);

        canvas.drawLine(getWidth() - 4, highY, getWidth() - 4, lowY, nomarlPaint);
    }


    private void drawProgress(Canvas canvas, float progress, boolean isFinish) {
        float progressY;

        Shader linearShader;
        if (progress > nomalHigh) {

            if (progress > high) {
                progressY = getHeight() / 5f - getHeight() / 5f * (progress - high) / (max - high);
            } else {
                progressY = getHeight() * 2f / 5f - getHeight() / 5f * (progress - nomalHigh) / (high - nomalHigh);
            }
            linearShader = new LinearGradient(25, progressY, 25, getHeight(), orangeColor, transColor, Shader.TileMode.CLAMP);
        } else if (progress < nomalLow) {
            if (progress > low) {
                progressY = getHeight() * 4f / 5f - getHeight() / 5f * (progress - low) / (nomalLow - low);
            } else {
                progressY = getHeight() - getHeight() / 5f * (progress - min) / (low - min);
            }
            linearShader = new LinearGradient(25, progressY, 25, getHeight(), blueColor, transColor, Shader.TileMode.CLAMP);
        } else {
            progressY = getHeight() * 3f / 5f - getHeight() / 5f * (progress - nomalLow) / (nomalHigh - nomalLow);
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
