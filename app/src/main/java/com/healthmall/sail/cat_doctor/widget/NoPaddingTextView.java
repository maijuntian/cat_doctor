package com.healthmall.sail.cat_doctor.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class NoPaddingTextView extends TextView {
    Paint.FontMetricsInt fontMetricsInt;

    public NoPaddingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setIncludeFontPadding(false);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "arial.ttf");
        this.setTypeface(typeface);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if (fontMetricsInt == null) {
//            fontMetricsInt = new Paint.FontMetricsInt();
//            getPaint().getFontMetricsInt(fontMetricsInt);
//        }
//        setMeasuredDimension(getMeasuredWidth(), (int) getPaint().getTextSize() - fontMetricsInt.bottom);

    }


}