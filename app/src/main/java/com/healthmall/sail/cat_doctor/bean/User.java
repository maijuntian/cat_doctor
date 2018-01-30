package com.healthmall.sail.cat_doctor.bean;

import android.text.TextUtils;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.mai.xmai_fast_lib.utils.MLog;

import java.util.Calendar;

/**
 * Created by mai on 2017/11/27.
 */
public class User {

    private String mallId;
    private String memberName;
    private Integer memberSex;
    private String memHeadImg;
    private String accessToken;
    private String questionAnswerId;
    private String questionResultName;
    private String birthday;
    private boolean authentication;
    private boolean talent;
    private boolean isUsed;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getQuestionAnswerId() {
        return questionAnswerId;
    }

    public void setQuestionAnswerId(String questionAnswerId) {
        this.questionAnswerId = questionAnswerId;
    }

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemHeadImg() {
        return memHeadImg;
    }

    public void setMemHeadImg(String memHeadImg) {
        this.memHeadImg = memHeadImg;
    }

    public Integer getMemberSex() {
        return memberSex;
    }

    public void setMemberSex(Integer memberSex) {
        this.memberSex = memberSex;
    }

    public Integer getMemberAge() {
        if (TextUtils.isEmpty(birthday))
            return 10;
        int year = Integer.parseInt(birthday.split("-")[0]);
        int age = Calendar.getInstance().get(Calendar.YEAR) - year;
        MLog.log("年龄-->" + age);
        return age;
    }


    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public String getQuestionResultName() {
        return questionResultName;
    }

    public void setQuestionResultName(String questionResultName) {
        this.questionResultName = questionResultName;
    }

    public boolean isTalent() {
        return talent;
    }

    public void setTalent(boolean talent) {
        this.talent = talent;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
