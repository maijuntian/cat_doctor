package com.healthmall.sail.cat_doctor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.healthmall.sail.cat_doctor.R;


/**
 * Created by mai on 2017/12/25.
 */
public class WaterView extends View {

    Paint lineaPaint, progressPaint, realProgressPaint, greenPaint;

    PointF progressRectLeft, progressRectTop, progressRectRight;

    PointF rectLeft, rectTop, rectRight, rectBottom;
    PointF rectGreenLeft, rectGreenTop;

    int progressLeft = 46, progressTop = 106, progressRight = 244, progressBottom = 304;

    private final int progressPlus = 3;

    private int progressIndex = 0;

    private int progress = 0;

    public WaterView(Context context) {
        super(context);
        initView();
    }

    public WaterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public WaterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        lineaPaint = new Paint();
        lineaPaint.setStrokeWidth(1);
        lineaPaint.setAntiAlias(true);
        lineaPaint.setStyle(Paint.Style.STROKE);
        lineaPaint.setColor(Color.parseColor("#b4b7bb"));

        progressPaint = new Paint();
        progressPaint.setStrokeWidth(73);
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setColor(Color.parseColor("#e8e8e8"));


        realProgressPaint = new Paint();
        realProgressPaint.setStrokeWidth(73);
        realProgressPaint.setAntiAlias(true);
        realProgressPaint.setStyle(Paint.Style.STROKE);
        realProgressPaint.setStrokeCap(Paint.Cap.ROUND);

        greenPaint = new Paint();
        greenPaint.setStrokeWidth(8);
        greenPaint.setAntiAlias(true);
        greenPaint.setStyle(Paint.Style.STROKE);
        greenPaint.setColor(ContextCompat.getColor(getContext(), R.color.good_green_trans));

        rectLeft = new PointF(42.5f, 102.5f);
        rectTop = new PointF(145, 0);
        rectRight = new PointF(247.5f, 102.5f);
        rectBottom = new PointF(145f, 205f);

        rectGreenLeft = new PointF(45.5f, 105.5f);
        rectGreenTop = new PointF(148, 3);

        progressRectLeft = new PointF(75f, 135f);
        progressRectTop = new PointF(145f, 65f);
        progressRectRight = new PointF(215f, 135f);


        Shader shader = new LinearGradient(rectTop.x, rectTop.y, rectTop.x, 350, Color.parseColor("#fbb709"), Color.parseColor("#f32307"), Shader.TileMode.CLAMP);
        realProgressPaint.setShader(shader);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setProgressIndex(int progressIndex) {
        this.progressIndex = progressIndex;
    }

    public void setProgress(int progress) {
        this.progress = progress;

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBorder(canvas);

        if (progress == 0)
            return;

        progressIndex += progressPlus;

        if (progressIndex > progress)
            progressIndex = progress;

        drawProgress(canvas, progressIndex);

        if (progressIndex < progress)
            postInvalidate();
    }


    private void drawBorder(Canvas canvas) {

        canvas.drawArc(0, 60, 290, 350, -45, 270, false, lineaPaint);

        drawLine(canvas, rectLeft, rectTop, lineaPaint);
        drawLine(canvas, rectTop, rectRight, lineaPaint);

        canvas.drawArc(progressLeft, progressTop, progressRight, progressBottom, -45, 270, false, progressPaint);

        drawLine(canvas, progressRectLeft, progressRectTop, progressPaint);
        drawLine(canvas, progressRectTop, progressRectRight, progressPaint);

        canvas.drawArc(4, 64, 286, 346, 155, 70, false, greenPaint);

        drawLine(canvas, rectGreenLeft, rectGreenTop, greenPaint);

    }

    private void drawLine(Canvas canvas, PointF startF, PointF endF, Paint paint) {
        canvas.drawLine(startF.x, startF.y, endF.x, endF.y, paint);
    }


    private void drawProgress(Canvas canvas, int progress) {
        if (progress <= 80) {
            float y = progress / 80f * (progressRectRight.y - progressRectTop.y) + progressRectTop.y;
            float x = progress / 80f * (progressRectRight.x - progressRectTop.x) + progressRectTop.x;

            PointF endPoint = new PointF(x, y);
            drawLine(canvas, progressRectTop, endPoint, realProgressPaint);
        } else if (progress <= 89) { //80--->89   一共90°
            drawLine(canvas, progressRectTop, progressRectRight, realProgressPaint);
            canvas.drawArc(progressLeft, progressTop, progressRight, progressBottom, -45, 90 * (progress - 80) / 9f, false, realProgressPaint);

        } else if (progress <= 94) {//90--->94   一共110°
            drawLine(canvas, progressRectTop, progressRectRight, realProgressPaint);
            canvas.drawArc(progressLeft, progressTop, progressRight, progressBottom, -45, 110 * (progress - 89) / 4f + 90, false, realProgressPaint);

        } else if (progress <= 97) { //95-->97  一共70°
            drawLine(canvas, progressRectTop, progressRectRight, realProgressPaint);
            canvas.drawArc(progressLeft, progressTop, progressRight, progressBottom, -45, 70 * (progress - 94) / 3f + 200, false, realProgressPaint);

        } else { // 97 --> 100

            drawLine(canvas, progressRectTop, progressRectRight, realProgressPaint);
            canvas.drawArc(progressLeft, progressTop, progressRight, progressBottom, -45, 270, false, realProgressPaint);


            float y = (100 - progress) / 3f * (progressRectLeft.y - progressRectTop.y) + progressRectTop.y;
            float x = (100 - progress) / 3f * (progressRectLeft.x - progressRectTop.x) + progressRectTop.x;

            PointF endPoint = new PointF(x, y);
            drawLine(canvas, progressRectLeft, endPoint, realProgressPaint);
        }
    }
}
