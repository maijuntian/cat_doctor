package com.healthmall.sail.cat_doctor.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.healthmall.sail.cat_doctor.R;

/**
 * Created by mai on 2017/11/13.
 */
public class CircleProgressView extends View {

    private Paint bgPaint, progressPaint;

    private int progress = 90;

    private int progressIndex = 0;

    private final int progressPlus = 3;

    private float strokeWidth;

    public CircleProgressView(Context context) {
        super(context);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }

    public void initView(Context context, AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);

        int progressColor = ta.getColor(R.styleable.CircleProgressView_progressColor, Color.GRAY);
        strokeWidth = ta.getDimension(R.styleable.CircleProgressView_progressWidth, getResources().getDimension(R.dimen.px15));

        ta.recycle();

        bgPaint = new Paint();
        bgPaint.setColor(ContextCompat.getColor(getContext(), R.color.mmmgray));
        bgPaint.setStrokeWidth(strokeWidth);
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);

        progressPaint = new Paint();
        progressPaint.setColor(progressColor);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setProgress(int progress) {
        this.progress = progress;

        postInvalidate();
    }

    public void setProgressIndex(int progressIndex) {
        this.progressIndex = progressIndex;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBg(canvas);

        drawProgress(canvas);
    }

    private void drawBg(Canvas canvas) {

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, (getWidth() - strokeWidth) / 2, bgPaint);
    }

    private void drawProgress(Canvas canvas) {

        progressIndex += progressPlus;

        if (progressIndex > progress)
            progressIndex = progress;

        float sweepAngle = progressIndex * 360 / 100;

        canvas.drawArc(new RectF(getLeft() + strokeWidth / 2, getTop() + strokeWidth / 2, getRight() - strokeWidth / 2, getBottom() - strokeWidth / 2), 270 - sweepAngle, sweepAngle, false, progressPaint);

        if (progressIndex < progress)
            postInvalidate();
    }
}
