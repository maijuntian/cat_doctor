package com.healthmall.sail.cat_doctor.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mai.xmai_fast_lib.utils.MLog;

import java.util.Arrays;

/**
 * Created by mai on 2017/12/8.
 */
@SuppressLint("AppCompatCustomView")
public class QuestionResultView extends ImageView {

    Paint pointPaint, linePaint;

    private int[] datas;

    private PointF[] pointFS = new PointF[9];

    private PointF[] boardPointFS = new PointF[8];

    private final int pointR = 6;

    private final int cirR = 260;

    private float maxScoure = 10f;


    public QuestionResultView(Context context) {
        super(context);
        initView();
    }

    public QuestionResultView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public QuestionResultView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    public void setMaxScoure(float maxScoure) {
        this.maxScoure = maxScoure;
    }

    public void setDatas(int[] datas) {
        this.datas = datas;

        MLog.log("图数据：" + Arrays.toString(datas));
        postInvalidate();
    }

    private void initView() {
        pointPaint = new Paint();
        pointPaint.setColor(Color.parseColor("#00B99E"));
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#9600B99E"));
        linePaint.setStrokeWidth(12);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.FILL);

        boardPointFS[0] = new PointF(168.7f, 199.4f);
        boardPointFS[1] = new PointF(256.8f, 45.4f);
        boardPointFS[2] = new PointF(225.5f, 131.5f);
        boardPointFS[3] = new PointF(88.3f, 245.5f);


        boardPointFS[4] = new PointF(86.5f, 245.5f);
        boardPointFS[5] = new PointF(224.7f, 132.6f);
        boardPointFS[6] = new PointF(257f, 44.1f);
        boardPointFS[7] = new PointF(167.7f, 200.2f);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (datas == null)
            return;

        initPoint();
        drawPath(canvas);
        drawPoint(canvas);
    }

    private void initPoint() {

        MLog.log("宽度：" + getWidth());

        float centerX = getWidth() / 2;
        float centerY = centerX;

        pointFS[0] = new PointF(centerX, centerY - (cirR * datas[0] / maxScoure));

        pointFS[1] = new PointF(centerX + (boardPointFS[0].x * datas[1] / maxScoure), centerY - (boardPointFS[0].y * datas[1] / maxScoure));

        pointFS[2] = new PointF(centerX + (boardPointFS[1].x * datas[2] / maxScoure), centerY - (boardPointFS[1].y * datas[2] / maxScoure));

        pointFS[3] = new PointF(centerX + (boardPointFS[2].x * datas[3] / maxScoure), centerY + (boardPointFS[2].y * datas[3] / maxScoure));

        pointFS[4] = new PointF(centerX + (boardPointFS[3].x * datas[4] / maxScoure), centerY + (boardPointFS[3].y * datas[4] / maxScoure));


        pointFS[5] = new PointF(centerX - (boardPointFS[4].x * datas[5] / maxScoure), centerY + (boardPointFS[4].y * datas[5] / maxScoure));

        pointFS[6] = new PointF(centerX - (boardPointFS[5].x * datas[6] / maxScoure), centerY + (boardPointFS[5].y * datas[6] / maxScoure));

        pointFS[7] = new PointF(centerX - (boardPointFS[6].x * datas[7] / maxScoure), centerY - (boardPointFS[6].y * datas[7] / maxScoure));

        pointFS[8] = new PointF(centerX - (boardPointFS[7].x * datas[8] / maxScoure), centerY - (boardPointFS[7].y * datas[8] / maxScoure));

    }


    private void drawPoint(Canvas canvas) {

        for (PointF pointF : pointFS) {
            MLog.log("点坐标--->" + pointF.x + "," + pointF.y);
            canvas.drawCircle(pointF.x, pointF.y, pointR, pointPaint);
        }
    }

    private void drawPath(Canvas canvas) {
        Path path = new Path();

        for (int i = 0; i < pointFS.length; i++) {
            if (i == 0) {
                path.moveTo(pointFS[i].x, pointFS[i].y);

            } else if (i == pointFS.length - 1) {
                path.lineTo(pointFS[i].x, pointFS[i].y);
                path.lineTo(pointFS[0].x, pointFS[0].y);
            } else {
                path.lineTo(pointFS[i].x, pointFS[i].y);
            }

        }

        canvas.drawPath(path, linePaint);
    }
}
