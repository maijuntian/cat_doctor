package com.healthmall.sail.cat_doctor.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.healthmall.sail.cat_doctor.R;

import java.util.List;

/**
 * Created by mai on 2018/1/20.
 */
public class BloodoHorizontalProgressView extends View {

    Paint progressPaint, textPaint;

    int normalLow = 94, normalHigh = 100;

    int progress = 97;

    int max = 100;

    float plus = 0.2f;

    Bitmap bmpDown, bmpUp;

    public void setMax(int max) {
        this.max = max;
    }

    public BloodoHorizontalProgressView(Context context) {
        super(context);
        initView();
    }

    public BloodoHorizontalProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BloodoHorizontalProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;

        postInvalidate();
    }

    public void setRang(int normalLow, int normalHigh){
        this.normalLow = normalLow;
        this.normalHigh = normalHigh;
    }

    public void setPlus(float plus) {
        this.plus = plus;
    }

    public void initView() {
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStrokeWidth(80);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);

        bmpDown = BitmapFactory.decodeResource(getResources(), R.mipmap.report_bloodo_down);
        bmpUp = BitmapFactory.decodeResource(getResources(), R.mipmap.report_bloodo_up);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float endX = getWidth() - (max - progress) / plus * 10 - 40;

        Shader shader;
        if (progress > normalHigh) {
            shader = new LinearGradient(0, 0, endX, 0, Color.parseColor("#00ffffff"), Color.parseColor("#ff4102"), Shader.TileMode.CLAMP);
            progressPaint.setShader(shader);
            canvas.drawLine(0, 40, endX, 40, progressPaint);

            canvas.drawBitmap(bmpUp, endX - 43, 23, textPaint);
        } else if (progress < normalLow) {
            shader = new LinearGradient(0, 0, endX, 0, Color.parseColor("#00ffffff"), Color.parseColor("#0082ff"), Shader.TileMode.CLAMP);
            progressPaint.setShader(shader);
            canvas.drawLine(0, 40, endX, 40, progressPaint);
            canvas.drawBitmap(bmpDown, endX - 43, 23, textPaint);
        } else {
            shader = new LinearGradient(0, 0, endX, 0, Color.parseColor("#00ffffff"), Color.parseColor("#00b99e"), Shader.TileMode.CLAMP);
            progressPaint.setShader(shader);
            canvas.drawLine(0, 40, endX, 40, progressPaint);
        }

        canvas.drawText(progress + "", endX - 61 - textPaint.measureText(progress + ""), 63, textPaint);
    }
}
