package com.healthmall.sail.cat_doctor.delegate;

import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.bean.Question;
import com.healthmall.sail.cat_doctor.bean.QuestionReport;
import com.healthmall.sail.cat_doctor.widget.QuestionResultView;
import com.mai.xmai_fast_lib.baseadapter.BaseListViewAdapter;
import com.mai.xmai_fast_lib.baseadapter.BaseViewHolder;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;
import com.mai.xmai_fast_lib.utils.MLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by mai on 2017/11/15.
 */
public class QuestionDelegate extends AppDelegate {
    @Bind(R.id.tv_question_progress)
    TextView tvQuestionProgress;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.tv_progress)
    TextView tvProgress;
    @Bind(R.id.iv_reexamine)
    ImageView ivReexamine;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.iv_pre_question)
    ImageView ivPreQuestion;
    @Bind(R.id.iv_commit)
    ImageView ivCommit;
    @Bind(R.id.gv_answers)
    GridView gvAnswers;
    @Bind(R.id.rl_question)
    RelativeLayout rlQuestion;
    @Bind(R.id.tv_habits)
    TextView tvHabits;
    @Bind(R.id.tv_reexamine)
    TextView tvReexamine;
    @Bind(R.id.rl_result)
    RelativeLayout rlResult;
    @Bind(R.id.rl_root)
    RelativeLayout rlRoot;
    @Bind(R.id.iv_next)
    ImageView ivNext;
    BaseListViewAdapter adapter;

    public List<Question.SubjectDTO.OptionList> options = new ArrayList<>();
    public Question currQuestion;

    @Bind(R.id.qrv_result)
    QuestionResultView qrvResult;
    @Bind(R.id.tv_question_result_tip)
    TextView tvQuestionResultTip;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_examine_question;
    }


    public void showQuestion() {
        rlRoot.setBackgroundResource(R.mipmap.examine_main_menu5_bg);
        rlQuestion.setVisibility(View.VISIBLE);
        rlResult.setVisibility(View.GONE);

        tvQuestionResultTip.setVisibility(View.GONE);
        tvQuestionProgress.setVisibility(View.VISIBLE);
        pbProgress.setVisibility(View.VISIBLE);
        tvProgress.setVisibility(View.VISIBLE);
        tvProgress.setText("");

        ivNext.setVisibility(View.INVISIBLE);
    }

    public void setCheckQuestion(int index) {
        for (int i = 0; i < options.size(); i++) {
            if (i == index)
                options.get(i).setSelected(true);
            else
                options.get(i).setSelected(false);
        }
        adapter.notifyDataSetChanged();
    }

    public void initQuestion(Question question) {
        currQuestion = question;

        pbProgress.setMax(question.getSubjectDTO().getTotalSubjectNum());
        pbProgress.setProgress(question.getSubjectDTO().getSubjectIndex());
        tvProgress.setText(question.getSubjectDTO().getSubjectIndex() + "/" + question.getSubjectDTO().getTotalSubjectNum());

        tvContent.setText(question.getSubjectDTO().getContent());
        options.clear();
        options.addAll(question.getSubjectDTO().getOptionList());
        if (adapter == null) {
            adapter = new BaseListViewAdapter<Question.SubjectDTO.OptionList>(options) {
                @Override
                protected int bindLayoutId(int position) {
                    return R.layout.item_answer;
                }

                @Override
                protected void initView(Question.SubjectDTO.OptionList data, BaseViewHolder viewHolder) {
                    MLog.log("data:" + data.getOption() + "  " + data.isSelected());
                    viewHolder.setText(R.id.rb_answer, data.getContent())
                            .setChecked(R.id.rb_answer, data.isSelected());
                }
            };
            gvAnswers.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        if (question.getSubjectDTO().getSubjectIndex() == 1) { //没有上一题
            ivPreQuestion.setVisibility(View.INVISIBLE);
        } else {
            ivPreQuestion.setVisibility(View.VISIBLE);
        }

        if (question.getSubjectDTO().getSubjectIndex() == question.getSubjectDTO().getTotalSubjectNum()) { //显示提交
//            ivCommit.setVisibility(View.VISIBLE);
        } else {
            ivCommit.setVisibility(View.INVISIBLE);
        }
    }

    public void showCommit(){
        ivCommit.setVisibility(View.VISIBLE);
    }

    public void showReuslt(QuestionReport currQuestionReport) {
        rlRoot.setBackgroundResource(R.mipmap.examine_main_menu55_bg);
        rlResult.setVisibility(View.VISIBLE);
        rlQuestion.setVisibility(View.GONE);

        tvQuestionProgress.setVisibility(View.GONE);
        pbProgress.setVisibility(View.GONE);
        tvProgress.setVisibility(View.GONE);
        tvQuestionResultTip.setVisibility(View.VISIBLE);

        if (!MyApplication.get().getCurrUserReport().isFinish()) {
            ivNext.setVisibility(View.VISIBLE);
        } else {
            ivNext.setVisibility(View.INVISIBLE);
        }
        qrvResult.setMaxScoure(currQuestionReport.getMaxScore());
        qrvResult.setDatas(currQuestionReport.getResultScore());
        tvHabits.setText("您的体制为" + currQuestionReport.getQuestionResultName());
    }

}
