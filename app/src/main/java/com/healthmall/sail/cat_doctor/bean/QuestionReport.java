package com.healthmall.sail.cat_doctor.bean;

import android.text.TextUtils;

import com.healthmall.sail.cat_doctor.R;
import com.mai.xmai_fast_lib.utils.MLog;

import java.util.List;

/**
 * Created by mai on 2017/12/6.
 */
public class QuestionReport {

    private String questionAnswerId;

    public String getQuestionAnswerId() {
        return questionAnswerId;
    }

    public void setQuestionAnswerId(String questionAnswerId) {
        this.questionAnswerId = questionAnswerId;
    }

    private String questionResultName;
    private List<ResultDTOs> resultDTOs;
    private List<String> descriptionList;

    private int maxScore;

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public String getQuestionResultName() {
        return questionResultName;
    }

    public String getQuestionResultNameReal() {
        return questionResultName.split(",")[0];
    }

    public int getQuestionResultIcon(){
        switch (getQuestionResultNameReal()) {
            case "平和质":
                return R.mipmap.question_icon_pinghe;
            case "气郁质":
                return R.mipmap.question_icon_qiyu;
            case "阳虚质":
                return R.mipmap.question_icon_yangxu;
            case "痰湿质":
                return R.mipmap.question_icon_tanshi;
            case "特禀质":
                return R.mipmap.question_icon_telin;
            case "阴虚质":
                return R.mipmap.question_icon_yinxu;
            case "湿热质":
                return R.mipmap.question_icon_shire;
            case "气虚质":
                return R.mipmap.question_icon_qixu;
            case "血瘀质":
                return R.mipmap.question_icon_xueyu;
        }
        return 0;
    }

    public int getQuestionResultBg(){
        switch (getQuestionResultNameReal()) {
            case "平和质":
                return R.mipmap.report_question_bg_pinghe;
            case "气郁质":
                return R.mipmap.report_question_bg_qiyu;
            case "阳虚质":
                return R.mipmap.report_question_bg_yangxu;
            case "痰湿质":
                return R.mipmap.report_question_bg_tanshi;
            case "特禀质":
                return R.mipmap.report_question_bg_telin;
            case "阴虚质":
                return R.mipmap.report_question_bg_yinxu;
            case "湿热质":
                return R.mipmap.report_question_bg_shire;
            case "气虚质":
                return R.mipmap.report_question_bg_qixu;
            case "血瘀质":
                return R.mipmap.report_question_bg_xueyu;
        }
        return 0;
    }

    public void setQuestionResultName(String questionResultName) {
        this.questionResultName = questionResultName;
    }

    public List<ResultDTOs> getResultDTOs() {
        return resultDTOs;
    }

    public void setResultDTOs(List<ResultDTOs> resultDTOs) {
        this.resultDTOs = resultDTOs;
    }

    public List<String> getDescriptionList() {
        return descriptionList;
    }

    public String getDescription() {
        if (descriptionList != null && descriptionList.size() > 0) {
            return "";
        }
        return descriptionList.get(0);
    }

    public void setDescriptionList(List<String> descriptionList) {
        this.descriptionList = descriptionList;
    }

    public static class ResultDTOs {
        /**
         * name : 体质A
         * score : 21
         */

        private String name;
        private int score;
        private String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    /*public int[] getResultScore() {

        int[] score = new int[9];

        if (resultDTOs == null)
            return score;
        for (ResultDTOs resultDTO : resultDTOs) {
            switch (resultDTO.getName()) {
                case "平和质":
                    score[0] = resultDTO.getScore();
                    break;
                case "气郁质":
                    score[1] = resultDTO.getScore();
                    break;
                case "阳虚质":
                    score[2] = resultDTO.getScore();
                    break;
                case "痰湿质":
                    score[3] = resultDTO.getScore();
                    break;
                case "特禀质":
                    score[4] = resultDTO.getScore();
                    break;
                case "血虚质":
                    score[5] = resultDTO.getScore();
                    break;
                case "湿热质":
                    score[6] = resultDTO.getScore();
                    break;
                case "气虚质":
                    score[7] = resultDTO.getScore();
                    break;
                case "血瘀质":
                    score[8] = resultDTO.getScore();
                    break;
            }
        }

        return score;
    }*/

    public boolean isFinish() {
        return !TextUtils.isEmpty(questionAnswerId);
    }

}
