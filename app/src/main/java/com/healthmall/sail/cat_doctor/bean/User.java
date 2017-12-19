package com.healthmall.sail.cat_doctor.bean;

/**
 * Created by mai on 2017/11/27.
 */
public class User {

    private String mallId;
    private String memberName;
    private Integer memberSex;
    private Integer memberAge;
    private String memHeadImg;
    private String accessToken;
    private String questionAnswerId;
    private String questionResultName;
    private boolean authentication;
    private boolean talent;

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
        return memberAge;
    }

    public void setMemberAge(Integer memberAge) {
        this.memberAge = memberAge;
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
}
