package com.healthmall.sail.cat_doctor.bean;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.utils.Configs;
import com.healthmall.sail.cat_doctor.utils.Keys;
import com.mai.xmai_fast_lib.utils.SharedPreferencesHelper;

/**
 * //去掉体温的时候，直接让它完成
 * Created by mai on 2017/12/2.
 */
public class UserReport {
    BodyReport bodyReport;
    TemperatureReport temperatureReport;
    BloodOxygenReport bloodOxygenReport;
    BloodPressureReport bloodPressureReport;
    FaceTonReport faceTonReport;
    QuestionReport questionReport;


    public UserReport() {
        String deviceId = SharedPreferencesHelper.getInstance(MyApplication.get().getApplicationContext()).getStringValue(Keys.KEY_DEVICE_ID);

        bodyReport = new BodyReport(deviceId, MyApplication.get().getCurrUser().getMallId());
        temperatureReport = new TemperatureReport(deviceId, MyApplication.get().getCurrUser().getMallId());
        if (!Configs.useTemp) //去掉体温的时候，直接让它完成
            temperatureReport.setFinish(true);
        bloodOxygenReport = new BloodOxygenReport(deviceId, MyApplication.get().getCurrUser().getMallId());
        bloodPressureReport = new BloodPressureReport(deviceId, MyApplication.get().getCurrUser().getMallId());
        faceTonReport = new FaceTonReport();
        questionReport = new QuestionReport();
    }

    public BodyReport getBodyReport() {
        return bodyReport;
    }

    public void setBodyReport(BodyReport bodyReport) {
        this.bodyReport = bodyReport;
    }

    public BloodOxygenReport getBloodOxygenReport() {
        return bloodOxygenReport;
    }

    public void setBloodOxygenReport(BloodOxygenReport bloodOxygenReport) {
        this.bloodOxygenReport = bloodOxygenReport;
    }

    public BloodPressureReport getBloodPressureReport() {
        return bloodPressureReport;
    }

    public void setBloodPressureReport(BloodPressureReport bloodPressureReport) {
        this.bloodPressureReport = bloodPressureReport;
    }

    public FaceTonReport getFaceTonReport() {
        return faceTonReport;
    }

    public void setFaceTonReport(FaceTonReport faceTonReport) {
        this.faceTonReport = faceTonReport;
    }

    public QuestionReport getQuestionReport() {
        return questionReport;
    }

    public void setQuestionReport(QuestionReport questionReport) {
        this.questionReport = questionReport;
    }

    public TemperatureReport getTemperatureReport() {
        return temperatureReport;
    }

    public void setTemperatureReport(TemperatureReport temperatureReport) {
        this.temperatureReport = temperatureReport;
    }

    public boolean isFinishOne() {
        if (!Configs.useTemp) {
            return bodyReport.isFinish() || bloodOxygenReport.isFinish() || bloodPressureReport.isFinish() || faceTonReport.isFinish() || questionReport.isFinish();

        }
        return bodyReport.isFinish() || temperatureReport.isFinish() || bloodOxygenReport.isFinish() || bloodPressureReport.isFinish() || faceTonReport.isFinish() || questionReport.isFinish();
    }

    public boolean isFinish() {
        return bodyReport.isFinish() && temperatureReport.isFinish() && bloodOxygenReport.isFinish() && bloodPressureReport.isFinish() && faceTonReport.isFinish() && questionReport.isFinish();
    }

    public int nextIndex(int currIndex) {
        switch (currIndex) {
            case 0:
                if (!temperatureReport.isFinish())
                    return 1;
                if (!bloodOxygenReport.isFinish())
                    return 2;
                if (!faceTonReport.isFinish())
                    return 3;
                if (!questionReport.isFinish())
                    return 4;
                if (!bloodPressureReport.isFinish())
                    return 5;
                break;
            case 1:
                if (!bloodOxygenReport.isFinish())
                    return 2;
                if (!faceTonReport.isFinish())
                    return 3;
                if (!questionReport.isFinish())
                    return 4;
                if (!bloodPressureReport.isFinish())
                    return 5;
                if (!bodyReport.isFinish())
                    return 0;
                break;
            case 2:
                if (!faceTonReport.isFinish())
                    return 3;
                if (!questionReport.isFinish())
                    return 4;
                if (!bloodPressureReport.isFinish())
                    return 5;
                if (!bodyReport.isFinish())
                    return 0;
                if (!temperatureReport.isFinish())
                    return 1;
                break;
            case 3:
                if (!questionReport.isFinish())
                    return 4;
                if (!bloodPressureReport.isFinish())
                    return 5;
                if (!bodyReport.isFinish())
                    return 0;
                if (!temperatureReport.isFinish())
                    return 1;
                if (!bloodOxygenReport.isFinish())
                    return 2;
                break;
            case 4:
                if (!bloodPressureReport.isFinish())
                    return 5;
                if (!bodyReport.isFinish())
                    return 0;
                if (!temperatureReport.isFinish())
                    return 1;
                if (!bloodOxygenReport.isFinish())
                    return 2;
                if (!faceTonReport.isFinish())
                    return 3;
                break;
            case 5:
                if (!bodyReport.isFinish())
                    return 0;
                if (!temperatureReport.isFinish())
                    return 1;
                if (!bloodOxygenReport.isFinish())
                    return 2;
                if (!faceTonReport.isFinish())
                    return 3;
                if (!questionReport.isFinish())
                    return 4;
        }
        return 0;
    }
}
