package com.healthmall.sail.cat_doctor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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

    float progressWidth, progressHeight, dividerWidth;

    final int maxItem = 33;

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

        setMeasuredDimension((int) ((maxItem * progressWidth) + (maxItem - 1) * dividerWidth), (int) progressHeight);
    }

    public void initView() {
        progressWidth = getResources().getDimension(R.dimen.px12);
        progressHeight = getResources().getDimension(R.dimen.px30);
        dividerWidth = getResources().getDimension(R.dimen.px2);


        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeWidth(progressWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int progressItem = (int) (maxItem * progress / 100f);

        for (int i = 0; i < maxItem; i++) {
            if (i <= progressItem) {
                progressPaint.setColor(ContextCompat.getColor(getContext(), R.color.xblue));
            } else {
                progressPaint.setColor(ContextCompat.getColor(getContext(), R.color.mmgray));
            }

            float x = progressWidth * (i + 0.5f) + dividerWidth * i;

            canvas.drawLine(x, 0, x, getHeight(), progressPaint);
        }
    }
}
