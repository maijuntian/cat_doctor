package com.healthmall.sail.cat_doctor.bean;

/**
 * Created by mai on 2017/11/20.
 */
public class BodyReport {

    private boolean isFinishHeight;
    private boolean isFinishWeight;
    private boolean isFinishFat;

    private int source;

    private int age;
    private int sex;
    private String deviceId;//设备号
    private String mallId; //n
    private String bm_bdtemp;//体温
    private String bm_height;//身高
    private String bm_weight;//体重
    private String bm_bf_tm;//总水分
    private String bm_bf_protein;//蛋白质
    private String bm_bf_bf;//体脂肪
    private String bm_bf_sm;//骨骼肌肉量
    private String bm_bf_ef;//细胞外液
    private String bm_bf_if;//细胞内液
    private String bm_bf_vf;//内脏脂肪
    private String bm_bf_bonysalts;//骨盐量
    private String bm_bf_bfr;//体脂率
    private String bm_bf_bmr;//含水比率
    private String bm_bf_tfr;//躯干脂肪率
    private String bm_bf_rhfr;//右手脂肪率
    private String bm_bf_lhfr;//左手脂肪率
    private String bm_bf_rlfr;//右腿脂肪率
    private String bm_bf_lffr;//左脚脂肪率
    private String bm_bf_tmm;//躯干肌肉量
    private String bm_bf_rhmm;//右手肌肉量
    private String bm_bf_lhmm;//左手肌肉量
    private String bm_bf_rlmm;//右腿肌肉量
    private String bm_bf_llmm;//左腿肌肉量
    private String bm_bf_sdm;//标准肌肉
    private String bm_bf_bmi;//BMI
    private String bm_bf_bm;//基础代谢
    private String bm_bf_sw;//标准体重
    private String bm_bf_vfl;//内脏脂肪等级
    private String bm_bf_ba;//身体年龄
    private String bm_bf_cs;//身体评分
    private String bm_bf_ed;//水肿度
    private String bm_bf_doo;//肥胖度
    private String bm_bf_mc;//肌肉控制
    private String bm_bf_wc;//体重控制
    private String bm_bf_fc;//脂肪控制
    private String bm_bf_wthr;//腰臀比

    private String bm_bf_mw;//肌肉重
    private String bm_bf_rfbw;//瘦体重

    private String bm_bf_nc;
    private String bm_bf_wwc;
    private String bm_bf_hip;
    private String bm_bf_bust;
    private String bm_bf_rac;
    private String bm_bf_lac;
    private String bm_bf_rtc;
    private String bm_bf_ltc;

    public BodyReport(String deviceId, String mallId) {
        this.deviceId = deviceId;
        this.mallId = mallId;
    }

    public boolean isFinish() {
        return isFinishHeight && isFinishWeight  && isFinishFat;
    }

    public boolean isFinishHeight() {
        return isFinishHeight;
    }

    public void setFinishHeight(boolean finishHeight) {
        isFinishHeight = finishHeight;
    }

    public boolean isFinishWeight() {
        return isFinishWeight;
    }

    public void setFinishWeight(boolean finishWeight) {
        isFinishWeight = finishWeight;
    }

    public boolean isFinishFat() {
        return isFinishFat;
    }

    public void setFinishFat(boolean finishFat) {
        isFinishFat = finishFat;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public String getBm_bdtemp() {
        return bm_bdtemp;
    }

    public void setBm_bdtemp(String bm_bdtemp) {
        this.bm_bdtemp = bm_bdtemp;
    }

    public String getBm_height() {
        return bm_height;
    }

    public void setBm_height(String bm_height) {
        this.bm_height = bm_height;
    }

    public String getBm_weight() {
        return bm_weight;
    }

    public void setBm_weight(String bm_weight) {
        this.bm_weight = bm_weight;
    }

    public String getBm_bf_tm() {
        return bm_bf_tm;
    }

    public void setBm_bf_tm(String bm_bf_tm) {
        this.bm_bf_tm = bm_bf_tm;
    }

    public String getBm_bf_protein() {
        return bm_bf_protein;
    }

    public void setBm_bf_protein(String bm_bf_protein) {
        this.bm_bf_protein = bm_bf_protein;
    }

    public String getBm_bf_bf() {
        return bm_bf_bf;
    }

    public void setBm_bf_bf(String bm_bf_bf) {
        this.bm_bf_bf = bm_bf_bf;
    }

    public String getBm_bf_sm() {
        return bm_bf_sm;
    }

    public void setBm_bf_sm(String bm_bf_sm) {
        this.bm_bf_sm = bm_bf_sm;
    }

    public String getBm_bf_ef() {
        return bm_bf_ef;
    }

    public void setBm_bf_ef(String bm_bf_ef) {
        this.bm_bf_ef = bm_bf_ef;
    }

    public String getBm_bf_if() {
        return bm_bf_if;
    }

    public void setBm_bf_if(String bm_bf_if) {
        this.bm_bf_if = bm_bf_if;
    }

    public String getBm_bf_vf() {
        return bm_bf_vf;
    }

    public void setBm_bf_vf(String bm_bf_vf) {
        this.bm_bf_vf = bm_bf_vf;
    }

    public String getBm_bf_bonysalts() {
        return bm_bf_bonysalts;
    }

    public void setBm_bf_bonysalts(String bm_bf_bonysalts) {
        this.bm_bf_bonysalts = bm_bf_bonysalts;
    }

    public String getBm_bf_bfr() {
        return bm_bf_bfr;
    }

    public void setBm_bf_bfr(String bm_bf_bfr) {
        this.bm_bf_bfr = bm_bf_bfr;
    }

    public String getBm_bf_bmr() {
        return bm_bf_bmr;
    }

    public void setBm_bf_bmr(String bm_bf_bmr) {
        this.bm_bf_bmr = bm_bf_bmr;
    }

    public String getBm_bf_tfr() {
        return bm_bf_tfr;
    }

    public void setBm_bf_tfr(String bm_bf_tfr) {
        this.bm_bf_tfr = bm_bf_tfr;
    }

    public String getBm_bf_rhfr() {
        return bm_bf_rhfr;
    }

    public void setBm_bf_rhfr(String bm_bf_rhfr) {
        this.bm_bf_rhfr = bm_bf_rhfr;
    }

    public String getBm_bf_lhfr() {
        return bm_bf_lhfr;
    }

    public void setBm_bf_lhfr(String bm_bf_lhfr) {
        this.bm_bf_lhfr = bm_bf_lhfr;
    }

    public String getBm_bf_rlfr() {
        return bm_bf_rlfr;
    }

    public void setBm_bf_rlfr(String bm_bf_rlfr) {
        this.bm_bf_rlfr = bm_bf_rlfr;
    }

    public String getBm_bf_lffr() {
        return bm_bf_lffr;
    }

    public void setBm_bf_lffr(String bm_bf_lffr) {
        this.bm_bf_lffr = bm_bf_lffr;
    }

    public String getBm_bf_tmm() {
        return bm_bf_tmm;
    }

    public void setBm_bf_tmm(String bm_bf_tmm) {
        this.bm_bf_tmm = bm_bf_tmm;
    }

    public String getBm_bf_rhmm() {
        return bm_bf_rhmm;
    }

    public void setBm_bf_rhmm(String bm_bf_rhmm) {
        this.bm_bf_rhmm = bm_bf_rhmm;
    }

    public String getBm_bf_lhmm() {
        return bm_bf_lhmm;
    }

    public void setBm_bf_lhmm(String bm_bf_lhmm) {
        this.bm_bf_lhmm = bm_bf_lhmm;
    }

    public String getBm_bf_rlmm() {
        return bm_bf_rlmm;
    }

    public void setBm_bf_rlmm(String bm_bf_rlmm) {
        this.bm_bf_rlmm = bm_bf_rlmm;
    }

    public String getBm_bf_llmm() {
        return bm_bf_llmm;
    }

    public void setBm_bf_llmm(String bm_bf_llmm) {
        this.bm_bf_llmm = bm_bf_llmm;
    }

    public String getBm_bf_sdm() {
        return bm_bf_sdm;
    }

    public void setBm_bf_sdm(String bm_bf_sdm) {
        this.bm_bf_sdm = bm_bf_sdm;
    }

    public String getBm_bf_bmi() {
        return bm_bf_bmi;
    }

    public void setBm_bf_bmi(String bm_bf_bmi) {
        this.bm_bf_bmi = bm_bf_bmi;
    }

    public String getBm_bf_bm() {
        return bm_bf_bm;
    }

    public void setBm_bf_bm(String bm_bf_bm) {
        this.bm_bf_bm = bm_bf_bm;
    }

    public String getBm_bf_sw() {
        return bm_bf_sw;
    }

    public void setBm_bf_sw(String bm_bf_sw) {
        this.bm_bf_sw = bm_bf_sw;
    }

    public String getBm_bf_vfl() {
        return bm_bf_vfl;
    }

    public void setBm_bf_vfl(String bm_bf_vfl) {
        this.bm_bf_vfl = bm_bf_vfl;
    }

    public String getBm_bf_ba() {
        return bm_bf_ba;
    }

    public void setBm_bf_ba(String bm_bf_ba) {
        this.bm_bf_ba = bm_bf_ba;
    }

    public String getBm_bf_cs() {
        return bm_bf_cs;
    }

    public void setBm_bf_cs(String bm_bf_cs) {
        this.bm_bf_cs = bm_bf_cs;
    }

    public String getBm_bf_ed() {
        return bm_bf_ed;
    }

    public void setBm_bf_ed(String bm_bf_ed) {
        this.bm_bf_ed = bm_bf_ed;
    }

    public String getBm_bf_doo() {
        return bm_bf_doo;
    }

    public void setBm_bf_doo(String bm_bf_doo) {
        this.bm_bf_doo = bm_bf_doo;
    }

    public String getBm_bf_mc() {
        return bm_bf_mc;
    }

    public void setBm_bf_mc(String bm_bf_mc) {
        this.bm_bf_mc = bm_bf_mc;
    }

    public String getBm_bf_wc() {
        return bm_bf_wc;
    }

    public void setBm_bf_wc(String bm_bf_wc) {
        this.bm_bf_wc = bm_bf_wc;
    }

    public String getBm_bf_fc() {
        return bm_bf_fc;
    }

    public void setBm_bf_fc(String bm_bf_fc) {
        this.bm_bf_fc = bm_bf_fc;
    }

    public String getBm_bf_wthr() {
        return bm_bf_wthr;
    }

    public void setBm_bf_wthr(String bm_bf_wthr) {
        this.bm_bf_wthr = bm_bf_wthr;
    }

    public String getBm_bf_mw() {
        return bm_bf_mw;
    }

    public void setBm_bf_mw(String bm_bf_mw) {
        this.bm_bf_mw = bm_bf_mw;
    }

    public String getBm_bf_rfbw() {
        return bm_bf_rfbw;
    }

    public void setBm_bf_rfbw(String bm_bf_rfbw) {
        this.bm_bf_rfbw = bm_bf_rfbw;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBm_bf_nc() {
        return bm_bf_nc;
    }

    public void setBm_bf_nc(String bm_bf_nc) {
        this.bm_bf_nc = bm_bf_nc;
    }

    public String getBm_bf_wwc() {
        return bm_bf_wwc;
    }

    public void setBm_bf_wwc(String bm_bf_wwc) {
        this.bm_bf_wwc = bm_bf_wwc;
    }

    public String getBm_bf_hip() {
        return bm_bf_hip;
    }

    public void setBm_bf_hip(String bm_bf_hip) {
        this.bm_bf_hip = bm_bf_hip;
    }

    public String getBm_bf_bust() {
        return bm_bf_bust;
    }

    public void setBm_bf_bust(String bm_bf_bust) {
        this.bm_bf_bust = bm_bf_bust;
    }

    public String getBm_bf_rac() {
        return bm_bf_rac;
    }

    public void setBm_bf_rac(String bm_bf_rac) {
        this.bm_bf_rac = bm_bf_rac;
    }

    public String getBm_bf_lac() {
        return bm_bf_lac;
    }

    public void setBm_bf_lac(String bm_bf_lac) {
        this.bm_bf_lac = bm_bf_lac;
    }

    public String getBm_bf_rtc() {
        return bm_bf_rtc;
    }

    public void setBm_bf_rtc(String bm_bf_rtc) {
        this.bm_bf_rtc = bm_bf_rtc;
    }

    public String getBm_bf_ltc() {
        return bm_bf_ltc;
    }

    public void setBm_bf_ltc(String bm_bf_ltc) {
        this.bm_bf_ltc = bm_bf_ltc;
    }
}
