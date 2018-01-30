package com.healthmall.sail.cat_doctor.bean;

import android.graphics.Bitmap;

/**
 * Created by mai on 2017/12/6.
 */
public class FaceTonReport {
    boolean isFinishFace;
    boolean isFinishTon;


    public boolean isFinish() {
        return isFinishFace && isFinishTon;
    }

    public boolean isFinishFace() {
        return isFinishFace;
    }

    public void setFinishFace(boolean finishFace) {
        isFinishFace = finishFace;
    }

    public boolean isFinishTon() {
        return isFinishTon;
    }

    public void setFinishTon(boolean finishTon) {
        isFinishTon = finishTon;
    }

}
