package com.healthmall.sail.cat_doctor.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.activity.ExamineActivity;
import com.healthmall.sail.cat_doctor.base.BaseFragment;
import com.healthmall.sail.cat_doctor.base.MyThrowable;
import com.healthmall.sail.cat_doctor.bean.Question;
import com.healthmall.sail.cat_doctor.bean.QuestionReport;
import com.healthmall.sail.cat_doctor.delegate.QuestionDelegate;
import com.healthmall.sail.cat_doctor.http.CatDoctorApi;
import com.healthmall.sail.cat_doctor.utils.CommonUtils;
import com.mai.xmai_fast_lib.basehttp.MParams;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.OnClick;
import butterknife.OnItemClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by mai on 2017/11/15.
 */
public class QuestionFragment extends BaseFragment<QuestionDelegate> {

    QuestionReport currQuestionReport;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currQuestionReport = MyApplication.get().getCurrUserReport().getQuestionReport();

        start();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    private void start() {

        if (currQuestionReport.isFinish()) {
            if (currQuestionReport.getResultDTOs() == null || currQuestionReport.getResultDTOs().isEmpty()) {
                getResult(currQuestionReport.getQuestionAnswerId());
            } else {
                viewDelegate.showReuslt(currQuestionReport);
            }
        } else {
            getQuestion();
        }
    }

    private void commitQuestion(String subjectId, final String questionAnswerId, String answerContent) {

        CatDoctorApi.getInstance().questionAnswer(new MParams()
                .add("bottomButtonType", "3")
                .add("subjectId", subjectId)
                .add("questionAnswerId", questionAnswerId)
                .add("answerContent", CommonUtils.toArray(answerContent)), getActivity()).subscribe(new Action1<Question>() {
            @Override
            public void call(Question question) {
                currQuestionReport.setQuestionAnswerId(questionAnswerId);
                viewDelegate.showCommit();
            }
        }, new MyThrowable());

       /* CatDoctorApi.getInstance().commitQuestion(new MParams()
                .add("bottomButtonType", "3")
                .add("subjectId", subjectId)
                .add("questionAnswerId", questionAnswerId)
                .add("answerContent", CommonUtils.toArray(answerContent)), getActivity())
                .subscribe(new Action1<QuestionReport>() {
                    @Override
                    public void call(QuestionReport questionReport) {

                        MyApplication.get().getCurrUserReport().setQuestionReport(questionReport);
                        currQuestionReport = MyApplication.get().getCurrUserReport().getQuestionReport();
                        viewDelegate.showReuslt(currQuestionReport);

                    }
                }, new MyThrowable());*/
    }

    private void getPreQuestion(String questionAnswerId, String subjectId) {
        CatDoctorApi.getInstance().preQuestion(new MParams()
                .add("questionAnswerId", questionAnswerId)
                .add("subjectId", subjectId), getActivity())
                .subscribe(new Action1<Question>() {
                    @Override
                    public void call(Question question) {
                        viewDelegate.initQuestion(question);
                    }
                }, new MyThrowable());
    }

    private void nextQuestion(String subjectId, String questionAnswerId, String answerContent) {

        CatDoctorApi.getInstance().questionAnswer(new MParams()
                .add("bottomButtonType", "2")
                .add("subjectId", subjectId)
                .add("questionAnswerId", questionAnswerId)
                .add("answerContent", CommonUtils.toArray(answerContent)), getActivity()).subscribe(new Action1<Question>() {
            @Override
            public void call(Question question) {
                viewDelegate.initQuestion(question);
            }
        }, new MyThrowable());
    }

    private void getQuestion() {
        CatDoctorApi.getInstance().questionAnswer(new MParams()
                .add("bottomButtonType", "1"), getActivity()).subscribe(new Action1<Question>() {
            @Override
            public void call(Question question) {
                viewDelegate.showQuestion();
                viewDelegate.initQuestion(question);
            }
        }, new MyThrowable());
    }


    private void getResult(final String questionAnswerId) {
        CatDoctorApi.getInstance().questionResult(new MParams().add("questionAnswerId", questionAnswerId), getActivity()).subscribe(new Action1<QuestionReport>() {
            @Override
            public void call(QuestionReport questionReport) {
                questionReport.setQuestionAnswerId(questionAnswerId);

                MyApplication.get().getCurrUserReport().setQuestionReport(questionReport);
                currQuestionReport = MyApplication.get().getCurrUserReport().getQuestionReport();

                ((ExamineActivity)getActivity()).notifyMenu();
                viewDelegate.showReuslt(currQuestionReport);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    @OnItemClick(R.id.gv_answers)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        viewDelegate.setCheckQuestion(position);

        Question.SubjectDTO.OptionList optionList = viewDelegate.options.get(position);

        if (viewDelegate.currQuestion.getSubjectDTO().getTotalSubjectNum() == viewDelegate.currQuestion.getSubjectDTO().getSubjectIndex()) {
            //最后一题
            commitQuestion(viewDelegate.currQuestion.getSubjectDTO().getSubjectId(), viewDelegate.currQuestion.getQuestionAnswerId(), optionList.getOption());
        } else {
            nextQuestion(viewDelegate.currQuestion.getSubjectDTO().getSubjectId(), viewDelegate.currQuestion.getQuestionAnswerId(), optionList.getOption());
        }

    }

    @OnClick(R.id.iv_pre_question)
    public void iv_pre_questionClick() {
        getPreQuestion(viewDelegate.currQuestion.getQuestionAnswerId(), viewDelegate.currQuestion.getSubjectDTO().getSubjectId());
    }

    @OnClick(R.id.tv_reexamine)
    public void tv_reexamineClick() {

        currQuestionReport.setQuestionAnswerId("");
        currQuestionReport.setQuestionResultName("");
        ((ExamineActivity)getActivity()).notifyMenu();
        start();
    }

    @OnClick(R.id.iv_commit)
    public void iv_commitClick() {

        getResult(currQuestionReport.getQuestionAnswerId());
    }
}
