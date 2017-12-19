package com.healthmall.sail.cat_doctor.bean;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by mai on 2017/12/8.
 */
public class Question {


    /**
     * questionAnswerId : d824042dd9a511e7b9def48e38c3c5b0
     * bottomButtonType : 2
     * preSubjectStatus : 1
     * chronicDTOs : null
     * subjectDTO : {"id":"710001b0d9a711e7b9def48e38c3c5b0","subjectIndex":13,"problemId":"d824042dd9a511e7b9def48e38c3c5b0","type":0,"optionList":[{"selected":false,"content":"没有","source":1,"index":"A","subjectIndex":null,"detailsContent":null,"option":"A","map":null},{"selected":false,"content":"很少","source":2,"index":"B","subjectIndex":null,"detailsContent":null,"option":"B","map":null},{"selected":false,"content":"有时","source":3,"index":"C","subjectIndex":null,"detailsContent":null,"option":"C","map":null},{"selected":false,"content":"经常","source":4,"index":"D","subjectIndex":null,"detailsContent":null,"option":"D","map":null},{"selected":false,"content":"总是","source":5,"index":"E","subjectIndex":null,"detailsContent":null,"option":"E","map":null}],"content":"(6)您面部两颧潮红或偏红吗？","subjectId":"710001b0d9a711e7b9def48e38c3c5b0","questionId":"d824042dd9a511e7b9def48e38c3c5b0","totalSubjectNum":65,"answerContent":"B"}
     * resultUrl : null
     */

    private String questionAnswerId;
    private int bottomButtonType;
    private int preSubjectStatus;
    private SubjectDTO subjectDTO;

    public String getQuestionAnswerId() {
        return questionAnswerId;
    }

    public void setQuestionAnswerId(String questionAnswerId) {
        this.questionAnswerId = questionAnswerId;
    }

    public int getBottomButtonType() {
        return bottomButtonType;
    }

    public void setBottomButtonType(int bottomButtonType) {
        this.bottomButtonType = bottomButtonType;
    }

    public int getPreSubjectStatus() {
        return preSubjectStatus;
    }

    public void setPreSubjectStatus(int preSubjectStatus) {
        this.preSubjectStatus = preSubjectStatus;
    }


    public SubjectDTO getSubjectDTO() {
        return subjectDTO;
    }

    public void setSubjectDTO(SubjectDTO subjectDTO) {
        this.subjectDTO = subjectDTO;
    }

    public static class SubjectDTO {
        /**
         * id : 710001b0d9a711e7b9def48e38c3c5b0
         * subjectIndex : 13
         * problemId : d824042dd9a511e7b9def48e38c3c5b0
         * type : 0
         * optionList : [{"selected":false,"content":"没有","source":1,"index":"A","subjectIndex":null,"detailsContent":null,"option":"A","map":null},{"selected":false,"content":"很少","source":2,"index":"B","subjectIndex":null,"detailsContent":null,"option":"B","map":null},{"selected":false,"content":"有时","source":3,"index":"C","subjectIndex":null,"detailsContent":null,"option":"C","map":null},{"selected":false,"content":"经常","source":4,"index":"D","subjectIndex":null,"detailsContent":null,"option":"D","map":null},{"selected":false,"content":"总是","source":5,"index":"E","subjectIndex":null,"detailsContent":null,"option":"E","map":null}]
         * content : (6)您面部两颧潮红或偏红吗？
         * subjectId : 710001b0d9a711e7b9def48e38c3c5b0
         * questionId : d824042dd9a511e7b9def48e38c3c5b0
         * totalSubjectNum : 65
         * answerContent : B
         */

        private String id;
        private int subjectIndex;
        private String problemId;
        private int type;
        private String content;
        private String subjectId;
        private String questionId;
        private int totalSubjectNum;
        private String answerContent;
        private List<OptionList> optionList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getSubjectIndex() {
            return subjectIndex;
        }

        public void setSubjectIndex(int subjectIndex) {
            this.subjectIndex = subjectIndex;
        }

        public String getProblemId() {
            return problemId;
        }

        public void setProblemId(String problemId) {
            this.problemId = problemId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public int getTotalSubjectNum() {
            return totalSubjectNum;
        }

        public void setTotalSubjectNum(int totalSubjectNum) {
            this.totalSubjectNum = totalSubjectNum;
        }

        public String getAnswerContent() {
            return answerContent;
        }

        public void setAnswerContent(String answerContent) {
            this.answerContent = answerContent;
        }

        public List<OptionList> getOptionList() {
            /**
             * 解析答案
             */
            if (optionList == null)
                return null;

            if (!TextUtils.isEmpty(answerContent)) {
                for (OptionList list : optionList) {
                    if (answerContent.contains(list.getOption())) {
                        list.setSelected(true);
                    }
                }
            }

            return optionList;
        }

        public void setOptionList(List<OptionList> optionList) {
            this.optionList = optionList;
        }

        public static class OptionList {
            /**
             * selected : false
             * content : 没有
             * source : 1
             * index : A
             * subjectIndex : null
             * detailsContent : null
             * option : A
             * map : null
             */

            private boolean selected;
            private String content;
            private int source;
            private String index;
            private Object subjectIndex;
            private Object detailsContent;
            private String option;
            private Object map;

            public boolean isSelected() {
                return selected;
            }

            public void setSelected(boolean selected) {
                this.selected = selected;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getSource() {
                return source;
            }

            public void setSource(int source) {
                this.source = source;
            }

            public String getIndex() {
                return index;
            }

            public void setIndex(String index) {
                this.index = index;
            }

            public Object getSubjectIndex() {
                return subjectIndex;
            }

            public void setSubjectIndex(Object subjectIndex) {
                this.subjectIndex = subjectIndex;
            }

            public Object getDetailsContent() {
                return detailsContent;
            }

            public void setDetailsContent(Object detailsContent) {
                this.detailsContent = detailsContent;
            }

            public String getOption() {
                return option;
            }

            public void setOption(String option) {
                this.option = option;
            }

            public Object getMap() {
                return map;
            }

            public void setMap(Object map) {
                this.map = map;
            }
        }
    }
}
