package com.healthmall.sail.cat_doctor.bean;

import java.util.List;

/**
 * Created by mai on 2017/12/21.
 */
public class BodyRespone {


    /**
     * waistToHipRatioResult : 梨型
     * bodyFatRang : [8.82,10.584]
     * bodyFatResult : 肥胖
     * muscleWeightRang : [17.64,19.992]
     * muscleWeightResult : 偏高
     * totalMoistureRang : [31.5168,33.516]
     * totalMoistureResult : 偏胖
     * proteinRang : [9.408,11.76]
     * proteinResult : 偏高
     * bodyFatRateRang : [0.15,0.18]
     * bodyFatRateResult : 肥胖
     * muscleRateRang : [0.3,0.34]
     * muscleRateResult : 偏高
     * bodyMoistureRateRang : [0.536,0.57]
     * bodyMoistureRateResult : 偏胖
     * bmiRang : [18.5,23.9]
     * bmiResult : 正常
     * upperLimbBalance : 不均衡
     * lowerLimbBalance : 均衡
     * upperLimbDeveloped : 发达
     * lowerLimbDeveloped : 发达
     */

    private String waistToHipRatioResult;
    private String bodyFatResult;
    private String muscleWeightResult;
    private String totalMoistureResult;
    private String proteinResult;
    private String bodyFatRateResult;
    private String muscleRateResult;
    private String bodyMoistureRateResult;
    private String bmiResult;
    private String upperLimbBalance;
    private String lowerLimbBalance;
    private String upperLimbDeveloped;
    private String lowerLimbDeveloped;
    private List<Double> bodyFatRang;
    private List<Double> muscleWeightRang;
    private List<Double> totalMoistureRang;
    private List<Double> proteinRang;
    private List<Double> bodyFatRateRang;
    private List<Double> muscleRateRang;
    private List<Double> bodyMoistureRateRang;
    private List<Double> bmiRang;

    public String getWaistToHipRatioResult() {
        return waistToHipRatioResult;
    }

    public void setWaistToHipRatioResult(String waistToHipRatioResult) {
        this.waistToHipRatioResult = waistToHipRatioResult;
    }

    public String getBodyFatResult() {
        return bodyFatResult;
    }

    public void setBodyFatResult(String bodyFatResult) {
        this.bodyFatResult = bodyFatResult;
    }

    public String getMuscleWeightResult() {
        return muscleWeightResult;
    }

    public void setMuscleWeightResult(String muscleWeightResult) {
        this.muscleWeightResult = muscleWeightResult;
    }

    public String getTotalMoistureResult() {
        return totalMoistureResult;
    }

    public void setTotalMoistureResult(String totalMoistureResult) {
        this.totalMoistureResult = totalMoistureResult;
    }

    public String getProteinResult() {
        return proteinResult;
    }

    public void setProteinResult(String proteinResult) {
        this.proteinResult = proteinResult;
    }

    public String getBodyFatRateResult() {
        return bodyFatRateResult;
    }

    public void setBodyFatRateResult(String bodyFatRateResult) {
        this.bodyFatRateResult = bodyFatRateResult;
    }

    public String getMuscleRateResult() {
        return muscleRateResult;
    }

    public void setMuscleRateResult(String muscleRateResult) {
        this.muscleRateResult = muscleRateResult;
    }

    public String getBodyMoistureRateResult() {
        return bodyMoistureRateResult;
    }

    public void setBodyMoistureRateResult(String bodyMoistureRateResult) {
        this.bodyMoistureRateResult = bodyMoistureRateResult;
    }

    public String getBmiResult() {
        return bmiResult;
    }

    public void setBmiResult(String bmiResult) {
        this.bmiResult = bmiResult;
    }

    public String getUpperLimbBalance() {
        return upperLimbBalance;
    }

    public void setUpperLimbBalance(String upperLimbBalance) {
        this.upperLimbBalance = upperLimbBalance;
    }

    public String getLowerLimbBalance() {
        return lowerLimbBalance;
    }

    public void setLowerLimbBalance(String lowerLimbBalance) {
        this.lowerLimbBalance = lowerLimbBalance;
    }

    public String getUpperLimbDeveloped() {
        return upperLimbDeveloped;
    }

    public void setUpperLimbDeveloped(String upperLimbDeveloped) {
        this.upperLimbDeveloped = upperLimbDeveloped;
    }

    public String getLowerLimbDeveloped() {
        return lowerLimbDeveloped;
    }

    public void setLowerLimbDeveloped(String lowerLimbDeveloped) {
        this.lowerLimbDeveloped = lowerLimbDeveloped;
    }

    public List<Double> getBodyFatRang() {
        return bodyFatRang;
    }

    public void setBodyFatRang(List<Double> bodyFatRang) {
        this.bodyFatRang = bodyFatRang;
    }

    public List<Double> getMuscleWeightRang() {
        return muscleWeightRang;
    }

    public void setMuscleWeightRang(List<Double> muscleWeightRang) {
        this.muscleWeightRang = muscleWeightRang;
    }

    public List<Double> getTotalMoistureRang() {
        return totalMoistureRang;
    }

    public void setTotalMoistureRang(List<Double> totalMoistureRang) {
        this.totalMoistureRang = totalMoistureRang;
    }

    public List<Double> getProteinRang() {
        return proteinRang;
    }

    public void setProteinRang(List<Double> proteinRang) {
        this.proteinRang = proteinRang;
    }

    public List<Double> getBodyFatRateRang() {
        return bodyFatRateRang;
    }

    public void setBodyFatRateRang(List<Double> bodyFatRateRang) {
        this.bodyFatRateRang = bodyFatRateRang;
    }

    public List<Double> getMuscleRateRang() {
        return muscleRateRang;
    }

    public void setMuscleRateRang(List<Double> muscleRateRang) {
        this.muscleRateRang = muscleRateRang;
    }

    public List<Double> getBodyMoistureRateRang() {
        return bodyMoistureRateRang;
    }

    public void setBodyMoistureRateRang(List<Double> bodyMoistureRateRang) {
        this.bodyMoistureRateRang = bodyMoistureRateRang;
    }

    public List<Double> getBmiRang() {
        return bmiRang;
    }

    public void setBmiRang(List<Double> bmiRang) {
        this.bmiRang = bmiRang;
    }
}
