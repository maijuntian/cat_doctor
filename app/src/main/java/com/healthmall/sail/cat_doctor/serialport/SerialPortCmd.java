package com.healthmall.sail.cat_doctor.serialport;

import com.healthmall.sail.cat_doctor.bean.BloodOxygenReport;
import com.healthmall.sail.cat_doctor.bean.BloodPressureReport;
import com.healthmall.sail.cat_doctor.bean.BodyReport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mai on 2017/12/1.
 */
public class SerialPortCmd {

    private static final String AT_PRE = "AT+";


    public static final String OK_SMONE = "OK+SMONE"; //有人
    public static final String OK_ONONE = "OK+ONONE"; //没人

    public static final String OK_HEIGHT = "OK+HEIGHT";
    public static final String ERR_HEIGHT = "ERR+HEIGHT";

    public static final String OK_BODYTEMP = "OK+BODYTEMP";
    public static final String ERR_BODYTEMP = "ERR+BODYTEMP";

    public static final String OK_WEIGHT = "OK+WEIGHT";
    public static final String ERR_WEIGHT = "ERR+WEIGHT";

    public static final String OK_BODYFAT = "OK+BODYFAT";
    public static final String ERR_BODYFAT = "ERR+BODYFAT";

    public static final String OK_BLOODOX = "OK+BLOODOX";
    public static final String ERR_BLOODOX = "ERR+BLOODOX";
    public static final String OK_BLOODPRS = "OK+BLOODPRS";

    public static final String OK_BCALIBRATION = "OK+CAL";
    public static final String OK_HADTEST = "OK+HADTEST";

    public static final String ERR_BBLOODPRS = "ERR+BLOODPRS";


    public static void reset() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "RESET");
    }

    public static void checkPerson() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "SMONE");
    }


    public static void stopCheckPerson() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "STPSMONE");
    }

    public static void admin() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "ADMIN");
    }

    public static void scanSucc() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "CODESCANSU");
    }

    public static void height() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "HEIGHT");
    }

    public static void stopHeight() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "STPHEIGHT");
    }

    public static void bodyTemp() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "BODYTEMP");
    }

    public static void stopTemp() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "STPBODYTEMP");
    }

    public static void weight() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "WEIGHT");
    }

    public static void stopWeight() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "STPWEIGHT");
    }

    public static void stopFat() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "STPBODYFAT");
    }

    public static void bodyFat(int sex, int age, String wh, String hh) {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "BODYFAT+WH=" + wh + "+HH=" + hh + "+SX=" + sex + "+AGE=" + age);
    }

    public static void bloodOX() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "BLOODOX");
    }

    public static void stopBloodOX() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "STPBLOODOX");
    }

    public static void bloodOPRS() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "BLOODPRS");
    }

    public static void stopBloodOPRS() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "STPBLOODPRS");
    }

    public static void hadTest() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "HADTEST");
    }

    public static void stopHadTest() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "STPHADTEST");
    }


    public static void calibration() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "CALIBRATION");
    }

    public static void stopCalibration() {
        SerialPortEngine.getInstance().sendMsg(AT_PRE + "STPCALIBRATION");
    }


    public static void parseHeight(String msg, BodyReport bodyReport) {
        bodyReport.setBm_height(divideTenFloat(msg.replace(OK_HEIGHT + "=", "")));
    }

    public static String parseHeight2(String msg) {
        return msg.replace(OK_HEIGHT + "=", "");
    }

    public static void parseTemp(String msg, BodyReport bodyReport) {
        bodyReport.setBm_bdtemp(divideTenFloat(msg.replace(OK_BODYTEMP + "=", "")));
    }

    public static void parseWeight(String msg, BodyReport bodyReport) {
        bodyReport.setBm_weight(divideTenFloat(msg.replace(OK_WEIGHT + "=", "")));
    }

    public static String parseWeight2(String msg) {
        return msg.replace(OK_WEIGHT + "=", "");
    }

    public static String parseTemp2(String msg) {
        return msg.replace(OK_BODYTEMP + "=", "");
    }

    public static void parseBodyFat(String msg, BodyReport bodyReport) {
        Map<String, String> mapTemp = new HashMap<>();

        msg = msg.replace(OK_BODYFAT + "+", "");
        String[] results = msg.split("\\+");
        for (String result : results) {
            String[] tmp = result.split("=");
            mapTemp.put(tmp[0], tmp[1]);
        }

        bodyReport.setBm_bf_bf(divideTenFloat(mapTemp.get("BF")));
        bodyReport.setBm_bf_mw(divideTenFloat(mapTemp.get("MW")));
        bodyReport.setBm_bf_tm(divideTenFloat(mapTemp.get("TM")));
        bodyReport.setBm_bf_ef(divideTenFloat(mapTemp.get("EF")));
        bodyReport.setBm_bf_if(divideTenFloat(mapTemp.get("IF")));
        bodyReport.setBm_bf_rfbw(divideTenFloat(mapTemp.get("RFBW")));
        bodyReport.setBm_bf_bonysalts(divideTenFloat(mapTemp.get("BONYSALTS")));
        bodyReport.setBm_bf_protein(divideTenFloat(mapTemp.get("PROTEIN")));
        bodyReport.setBm_bf_sm(divideTenFloat(mapTemp.get("SM")));
        bodyReport.setBm_bf_bmi(divideTenFloat(mapTemp.get("BMI")));
        bodyReport.setBm_bf_bm(mapTemp.get("BM"));
        bodyReport.setBm_bf_bfr(divideTenFloat(mapTemp.get("BFR")));
        bodyReport.setBm_bf_bmr(divideTenFloat(mapTemp.get("BMR")));
        bodyReport.setBm_bf_tfr(divideTenFloat(mapTemp.get("TFR")));
        bodyReport.setBm_bf_rhfr(divideTenFloat(mapTemp.get("RHFR")));
        bodyReport.setBm_bf_lhfr(divideTenFloat(mapTemp.get("LHFR")));
        bodyReport.setBm_bf_rlfr(divideTenFloat(mapTemp.get("RLFR")));
        bodyReport.setBm_bf_lffr(divideTenFloat(mapTemp.get("LFFR")));
        bodyReport.setBm_bf_tmm(divideTenFloat(mapTemp.get("TMM")));
        bodyReport.setBm_bf_rhmm(divideTenFloat(mapTemp.get("RHMM")));
        bodyReport.setBm_bf_lhmm(divideTenFloat(mapTemp.get("LHMM")));
        bodyReport.setBm_bf_rlmm(divideTenFloat(mapTemp.get("RLMM")));
        bodyReport.setBm_bf_llmm(divideTenFloat(mapTemp.get("LLMM")));
        bodyReport.setBm_bf_sdm(divideTenFloat(mapTemp.get("SDM")));
        bodyReport.setBm_bf_sw(divideTenFloat(mapTemp.get("SW")));
        bodyReport.setBm_bf_vfl(divideTenFloat(mapTemp.get("VFL")));
        bodyReport.setBm_bf_ba(mapTemp.get("BA"));
        bodyReport.setBm_bf_cs(mapTemp.get("CS"));
        bodyReport.setBm_bf_ed(mapTemp.get("ED"));
        bodyReport.setBm_bf_doo(divideTenFloat(mapTemp.get("DOO")));
        bodyReport.setBm_bf_mc(dividePmTenFloat(mapTemp.get("MC")));
        bodyReport.setBm_bf_wc(dividePmTenFloat(mapTemp.get("WC")));
        bodyReport.setBm_bf_fc(dividePmTenFloat(mapTemp.get("FC")));

        bodyReport.setBm_bf_nc(divideTenFloat(mapTemp.get("NC")));
        bodyReport.setBm_bf_wwc(divideTenFloat(mapTemp.get("WWC")));
        bodyReport.setBm_bf_hip(divideTenFloat(mapTemp.get("HIP")));
        bodyReport.setBm_bf_bust(divideTenFloat(mapTemp.get("BUST")));
        bodyReport.setBm_bf_rac(divideTenFloat(mapTemp.get("RAC")));
        bodyReport.setBm_bf_lac(divideTenFloat(mapTemp.get("LAC")));
        bodyReport.setBm_bf_rtc(divideTenFloat(mapTemp.get("RTC")));
        bodyReport.setBm_bf_ltc(divideTenFloat(mapTemp.get("LTC")));

        float wthr = Float.parseFloat(bodyReport.getBm_bf_wwc()) / Float.parseFloat(bodyReport.getBm_bf_hip());
        bodyReport.setBm_bf_wthr(((Math.round(wthr * 10)) / 10f) + "");
    }

    public static void parseBloodOX(String msg, BloodOxygenReport bloodOxygenReport) {
        Map<String, String> mapTemp = new HashMap<>();

        msg = msg.replace(OK_BLOODOX + "+", "");
        String[] results = msg.split("\\+");
        for (String result : results) {
            String[] tmp = result.split("=");
            mapTemp.put(tmp[0], tmp[1]);
        }

        bloodOxygenReport.setBm_blox_spO2(mapTemp.get("SPO2"));
        bloodOxygenReport.setBm_blox_pr(mapTemp.get("PR"));
        bloodOxygenReport.setBm_blox_pi(mapTemp.get("PI"));
    }

    public static String[] parseBloodOX2(String msg) {
        Map<String, String> mapTemp = new HashMap<>();

        msg = msg.replace(OK_BLOODOX + "+", "");
        String[] results = msg.split("\\+");
        for (String result : results) {
            String[] tmp = result.split("=");
            mapTemp.put(tmp[0], tmp[1]);
        }

        String[] datas = new String[2];

        datas[0] = mapTemp.get("SPO2");
        datas[1] = mapTemp.get("PR");

        return datas;
    }

    public static void parseBloodOPRS(String msg, BloodPressureReport bloodPressureReport) {
        Map<String, String> mapTemp = new HashMap<>();

        msg = msg.replace(OK_BLOODPRS + "+", "");
        String[] results = msg.split("\\+");
        for (String result : results) {
            String[] tmp = result.split("=");
            mapTemp.put(tmp[0], tmp[1]);
        }

        bloodPressureReport.setBm_sph_sp(mapTemp.get("SP"));
        bloodPressureReport.setBm_sph_dp(mapTemp.get("DP"));
        bloodPressureReport.setBm_sph_hr(mapTemp.get("HR"));
    }

    private static String divideTen(String data) {
        return (Integer.parseInt(data) / 10) + "";
    }

    private static String divideTenFloat(String data) {
        return (Integer.parseInt(data) / 10f) + "";
    }

    private static String dividePmTenFloat(String data) {
        int dataInt = Integer.parseInt(data);
        if (dataInt > 32768) {
            dataInt = 32768 - dataInt;
        }
        return (dataInt / 10f) + "";
    }
}

