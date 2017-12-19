package com.healthmall.sail.cat_doctor.base;

import rx.functions.Action1;

/**
 * Created by mai on 2017/12/9.
 */
public class MyThrowable implements Action1<Throwable> {
    @Override
    public void call(Throwable throwable) {
        throwable.printStackTrace();
    }
}
