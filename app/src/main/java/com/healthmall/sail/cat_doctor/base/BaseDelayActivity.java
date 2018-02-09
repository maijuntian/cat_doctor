package com.healthmall.sail.cat_doctor.base;

import android.os.CountDownTimer;
import android.view.MotionEvent;

/**
 * Created by mai on 2018/1/25.
 */
public class BaseDelayActivity <T extends BaseDelegate> extends BaseActivity<T>{

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

}
