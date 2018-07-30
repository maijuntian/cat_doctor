package com.healthmall.sail.cat_doctor.bean;

import android.graphics.Bitmap;

/**
 * Created by mai on 2017/12/6.
 */
public class FaceTonReport {
    boolean isFinishFace;
    boolean isFinishTon;

    String faceUrl;
    String tonUrl;

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getTonUrl() {
        return tonUrl;
    }

    public void setTonUrl(String tonUrl) {
        this.tonUrl = tonUrl;
    }

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
