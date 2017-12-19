package com.healthmall.sail.cat_doctor.bean;

import android.text.TextUtils;

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

    public void setQuestionResultName(String questionResultName) {
        this.questionResultName = questionResultName;
    }

    public List<ResultDTOs> getResultDTOs() {
        return resultDTOs;
    }

    public void setResultDTOs(List<ResultDTOs> resultDTOs) {
        this.resultDTOs = resultDTOs;
    }

    public static class ResultDTOs {
        /**
         * name : 体质A
         * score : 21
         */

        private String name;
        private int score;

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
    }

    public int[] getResultScore() {

        int[] score = new int[9];

        if(resultDTOs == null)
            return score;
        for (ResultDTOs resultDTO : resultDTOs) {
            switch (resultDTO.getName()){
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
                case "特素质":
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
    }

    public boolean isFinish() {
        return !TextUtils.isEmpty(questionAnswerId);
    }

}
