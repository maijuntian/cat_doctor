package com.healthmall.sail.cat_doctor.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.bean.TemperatureReport;
import com.mai.xmai_fast_lib.utils.MLog;


/**
 * Created by mai on 2018/1/10.
 * 33°C起始点距离左边46px，改变蒙版宽度到具体温度，每0.1°是10px
 */
public class TemperatureView extends View {

    Paint whiteBgPaint, textPaint;

    Bitmap bmIndex;

    float temperature;

    private final float progressPlus = 5;

    private float progressIndex = 0;

    private float progress = 0;

    OnFinishListener onFinishListener;

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    public TemperatureView(Context context) {
        super(context);
        initView();
    }

    public TemperatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TemperatureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        whiteBgPaint = new Paint();
        whiteBgPaint.setStyle(Paint.Style.FILL);
        whiteBgPaint.setAntiAlias(true);
        whiteBgPaint.setColor(Color.parseColor("#ccffffff"));

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(49);
        textPaint.setColor(Color.parseColor("#08a99b"));

        bmIndex = BitmapFactory.decodeResource(getResources(), R.mipmap.temp_ruler_index);
    }

    public void clearTemp() {
        progress = 0;
        postInvalidate();
    }

    public void setTemperature(float temp) {
        temperature = temp;
        measureProgress(temp);
        invalidate();
    }

    private void measureProgress(float temp) { //计算距离左边的像素，33°C起始点距离左边46px，改变蒙版宽度到具体温度，每0.1°是10px
        progress = 46 + (temp - 33) * 98.5f;
        MLog.log("计算的位移--->" + progress);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        MLog.log("---绘制---");
        if (progress == 0) {
            drawMengBan(canvas, 0);
            return;
        }

        progressIndex += progressPlus;

        if (progressIndex > progress)
            progressIndex = progress;

        drawMengBan(canvas, progressIndex);

        if (progressIndex == progress) {
            drawTextIndex(canvas, temperature, progressIndex);
            if (onFinishListener != null)
                onFinishListener.finish(temperature);
        } else {
            postInvalidate();
        }
    }

    private void drawMengBan(Canvas canvas, float marginLeftPx) {
        MLog.log("绘制模板--->" + marginLeftPx);
        canvas.drawRect(marginLeftPx, getHeight() - 79, getWidth(), getHeight(), whiteBgPaint);
    }

    private void drawTextIndex(Canvas canvas, float temp, float marginLeftPx) {
        String text = temp + "°c";

        Rect rect = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), rect);

        textPaint.setColor(TemperatureReport.getTextColor(getContext(), temp));
        canvas.drawText(text, marginLeftPx - rect.width() / 2f, rect.height(), textPaint);

        canvas.drawBitmap(bmIndex, marginLeftPx - bmIndex.getWidth() / 2f - 2, rect.height() + 8, textPaint);
    }

    public interface OnFinishListener {
        void finish(float temp);
    }
}
