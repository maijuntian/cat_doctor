package com.healthmall.sail.cat_doctor.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.healthmall.sail.cat_doctor.R;

/**
 * Created by mai on 2017/11/13.
 */
public class ArcProgressView extends View {

    private Paint bgPaint, progressPaint;

    private int progress = 90;

    private int maxProgress = 100;

    private float strokeWidth;

    private int progressIndex = 0;

    private final int progressPlus = 3;


    public ArcProgressView(Context context) {
        super(context);
    }

    public ArcProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);
    }

    public ArcProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }

    public void initView(Context context, AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);

        strokeWidth = ta.getDimension(R.styleable.CircleProgressView_progressWidth, getResources().getDimension(R.dimen.px15));

        ta.recycle();


        bgPaint = new Paint();
        bgPaint.setColor(ContextCompat.getColor(getContext(), R.color.mmmgray));
        bgPaint.setStrokeWidth(strokeWidth);
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);

        progressPaint = new Paint();
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);


    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
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

        canvas.drawArc(new RectF(getLeft() + strokeWidth / 2, getTop() + strokeWidth / 2, getRight() - strokeWidth / 2, getBottom() - strokeWidth / 2), 125, 290, false, bgPaint);
    }

    private void drawProgress(Canvas canvas) {

        Shader shader = new LinearGradient(getWidth() / 2, 0, getWidth() / 2, getHeight(), new int[]{ContextCompat.getColor(getContext(), R.color.bh_normal), ContextCompat.getColor(getContext(), R.color.bh_mid), ContextCompat.getColor(getContext(), R.color.bh_low_high)}, null, Shader.TileMode.CLAMP);
        progressPaint.setShader(shader);

        if (progressIndex > progress)
            progressIndex = progress;
        progressIndex += progressPlus;

        float sweepAngle = progressIndex * 290 / maxProgress;

        canvas.drawArc(new RectF(getLeft() + strokeWidth / 2, getTop() + strokeWidth / 2, getRight() - strokeWidth / 2, getBottom() - strokeWidth / 2), 125, sweepAngle, false, progressPaint);

        if(progressIndex < progress)
            postInvalidate();
    }
}
