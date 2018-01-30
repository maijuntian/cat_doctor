package com.healthmall.sail.cat_doctor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.healthmall.sail.cat_doctor.R;
import com.mai.xmai_fast_lib.utils.MLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mai on 2017/12/21.
 */
public class PulseRateView extends View {

    Paint textPaint, chartPaint;

    Paint linePaint, lineBgPaint;

    Paint greenTextPaint, greenBigTextPaint, greenBgPaint;

    Paint valuePaint;

    int startX = 0; //x轴的起始值

    int endX = 55; //x轴的结束值

    int xSize = 11;

    int ySize = 10;

    int paddingY = 33;

    List<Integer> mData = new ArrayList<>();

    int yBottom;
    int yTop = 40;
    int xLeft = 42;
    int xRight;

    float perWidth; //横坐标1格的宽度；
    float perHeight; //纵坐标1格的高度；

    boolean isFinish;

    public PulseRateView(Context context) {
        super(context);
        initView();
    }

    public PulseRateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PulseRateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public List<Integer> getmData() {
        return mData;
    }

    public void clearData() {
        mData.clear();
        isFinish = false;
        postInvalidate();
    }

    public void setmData(List<Integer> mData) {
        this.mData = mData;
    }

    public void addData(int data) {
        mData.add(data);
        if (mData != null) {
            if (mData.size() > endX + 1) {
                startX = mData.size() - endX - 1;
                this.mData = mData.subList(startX, mData.size() - 1);

                endX += startX;
            } else {
                startX = 0;
            }
        }
        MLog.log("当前的数值-->" + mData.toString());

        postInvalidate();
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    private void initView() {

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(18);
        textPaint.setColor(Color.parseColor("#caccce"));


        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setStyle(Paint.Style.FILL);
        valuePaint.setTextSize(60);
        valuePaint.setColor(Color.parseColor("#0072ff"));


        greenTextPaint = new Paint();
        greenTextPaint.setAntiAlias(true);
        greenTextPaint.setStyle(Paint.Style.FILL);
        greenTextPaint.setTextSize(18);
        greenTextPaint.setColor(Color.parseColor("#00b99e"));

        greenBigTextPaint = new Paint();
        greenBigTextPaint.setAntiAlias(true);
        greenBigTextPaint.setStyle(Paint.Style.FILL);
        greenBigTextPaint.setTextSize(24);
        greenBigTextPaint.setColor(Color.parseColor("#00b99e"));


        greenBgPaint = new Paint();
        greenBgPaint.setStrokeWidth(1);
        greenBgPaint.setAntiAlias(true);
        greenBgPaint.setStyle(Paint.Style.FILL);
        greenBgPaint.setColor(Color.parseColor("#1a00b99e"));


        chartPaint = new Paint();
        chartPaint.setStrokeWidth(1);
        chartPaint.setAntiAlias(true);
        chartPaint.setStyle(Paint.Style.FILL);
        chartPaint.setColor(Color.parseColor("#dbdcde"));

        linePaint = new Paint();
        linePaint.setStrokeWidth(5);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);

        lineBgPaint = new Paint();
        lineBgPaint.setAntiAlias(true);
        lineBgPaint.setStyle(Paint.Style.FILL);
        lineBgPaint.setColor(Color.parseColor("#2000C6FF"));

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        xRight = getWidth() - 45;
        yBottom = getHeight() - 32;

        perWidth = (xRight - xLeft) / (endX - startX);
        perHeight = paddingY * 10f / 200f;
        Shader shader = new LinearGradient(xLeft, yBottom - yTop / 2, xRight, yBottom - yTop / 2, Color.parseColor("#0072FF"), Color.parseColor("#00C6FF"), Shader.TileMode.CLAMP);
        linePaint.setShader(shader);

        drawChart(canvas);

        if (mData != null && !mData.isEmpty()) {
            drawLine(canvas);
        }
    }


    private void drawChart(Canvas canvas) {

        int paddingX = 66;
        int paddingY = 33;


        canvas.drawText("(bpm)", 0, yTop - 13, textPaint);

        String tip = "次/分钟";
        float tipTextWidth = textPaint.measureText(tip);
        canvas.drawText(tip, xRight - tipTextWidth, yTop - 13, textPaint);

        float textWidth = textPaint.measureText("05");

        int greenLeft = 0, greenRight = 0, greenTop = 0, greenBottom = 0;


        for (int i = 0; i <= xSize; i++) {
            int xReal = xLeft + i * paddingX;

            if (i > 0) {
                String textX = ((i - 1) * 5 + 5 + startX) + "";

                if (textX.length() == 1)
                    textX = "0" + textX;

                canvas.drawText(textX, xReal - textWidth / 2f, getHeight() - 3, textPaint);

                canvas.drawCircle(xReal, yBottom - 4, 4, chartPaint);
                if (i == xSize) {
                    greenRight = xReal;
                    canvas.drawText("(s)", xReal + textWidth / 2f + 12, getHeight() - 3, textPaint);
                }
            }

            canvas.drawLine(xReal, yTop, xReal, yBottom, chartPaint);
        }


        for (int i = 0; i <= ySize; i++) {
            int realY = yBottom - i * paddingY;
            if (i == 0) {
                canvas.drawLine(xLeft, yBottom, xRight, yBottom, chartPaint);
            } else {
                canvas.drawLine(xLeft, realY, xLeft + 10, realY, chartPaint);

                String textY = (20 * i) + "";

                Rect rect = new Rect();
                textPaint.getTextBounds(textY, 0, textY.length(), rect);

                if (2 < i && i < 6) {
                    canvas.drawText(textY, xLeft - rect.width() - 5, realY + 10, greenTextPaint);
                    if (i == 3) {
                        greenBottom = realY;
                    } else if (i == 5) {
                        greenTop = realY;
                    }
                } else {
                    canvas.drawText(textY, xLeft - rect.width() - 5, realY + 10, textPaint);
                }
            }
        }

        greenLeft = xLeft;

        canvas.drawRect(greenLeft, greenTop, greenRight, greenBottom, greenBgPaint);
    }

    private void drawLine(Canvas canvas) {
        for (int i = 0, size = mData.size() - 1; i < size; i++) {
            PointF startPoint1 = transforPoint(i, mData.get(i));
            PointF endPoint1 = transforPoint(i + 1, mData.get(i + 1));
            drawPoint(canvas, startPoint1, endPoint1);
            drawBg(canvas, startPoint1, endPoint1);

            if (isFinish && i == size - 1) {
                String value = mData.get(i + 1) + "";
                Rect rect = new Rect();
                valuePaint.getTextBounds(value, 0, value.length(), rect);

                canvas.drawText(value, endPoint1.x - (rect.width() / 2f), endPoint1.y - rect.height() - 20, valuePaint);
            }
        }
    }

    private PointF transforPoint(int i, Integer value) {
        float x = xLeft + perWidth * i;

        float y = yBottom - perHeight * value;

        return new PointF(x, y);
    }

    private void drawPoint(Canvas canvas, PointF startPoint, PointF endPoint) {

        Path path = new Path();

        float wx = (startPoint.x + endPoint.x) / 2;
        PointF controlPoint1 = new PointF(wx, startPoint.y);
        PointF controlPoint2 = new PointF(wx, endPoint.y);
        path.moveTo(startPoint.x, startPoint.y);
        path.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y, endPoint.x, endPoint.y);

        canvas.drawPath(path, linePaint);
    }


    private void drawBg(Canvas canvas, PointF startPoint, PointF endPoint) {

        Path path = new Path();

        float wx = (startPoint.x + endPoint.x) / 2;
        PointF controlPoint1 = new PointF(wx, startPoint.y);
        PointF controlPoint2 = new PointF(wx, endPoint.y);
        path.moveTo(startPoint.x, startPoint.y);
        path.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y, endPoint.x, endPoint.y);

        path.lineTo(endPoint.x, yBottom);
        path.lineTo(startPoint.x, yBottom);
        path.close();

        canvas.drawPath(path, lineBgPaint);
    }
}
