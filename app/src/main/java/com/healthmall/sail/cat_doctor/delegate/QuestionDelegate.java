package com.healthmall.sail.cat_doctor.delegate;

import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthmall.sail.cat_doctor.MyApplication;
import com.healthmall.sail.cat_doctor.R;
import com.healthmall.sail.cat_doctor.bean.Question;
import com.healthmall.sail.cat_doctor.bean.QuestionReport;
import com.healthmall.sail.cat_doctor.serialport.SerialPortCmd;
import com.healthmall.sail.cat_doctor.widget.NoPaddingTextView;
import com.healthmall.sail.cat_doctor.widget.ProgressBarVertical;
import com.healthmall.sail.cat_doctor.widget.TipPopWin;
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
    BaseListViewAdapter adapter;

    public List<Question.SubjectDTO.OptionList> options = new ArrayList<>();
    public Question currQuestion;
    @Bind(R.id.pb_progress)
    ProgressBarVertical pbProgress;
    @Bind(R.id.tv_step1)
    TextView tvStep1;
    @Bind(R.id.tv_step2)
    TextView tvStep2;
    @Bind(R.id.ll_progress)
    LinearLayout llProgress;
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.lv_answers)
    ListView lvAnswers;
    @Bind(R.id.rl_question1)
    RelativeLayout rlQuestion1;
    @Bind(R.id.tv_index)
    TextView tvIndex;
    @Bind(R.id.ll_index)
    LinearLayout llIndex;
    @Bind(R.id.et_answer)
    public EditText etAnswer;
    @Bind(R.id.rl_content)
    RelativeLayout rlContent;
    @Bind(R.id.tv_commit)
    TextView tvCommit;
    @Bind(R.id.rl_question2)
    RelativeLayout rlQuestion2;
    @Bind(R.id.ll_result)
    LinearLayout llResult;
    @Bind(R.id.tv_question_content)
    TextView tvQuestionContent;
    @Bind(R.id.tv_question_content2)
    TextView tvQuestionContent2;
    @Bind(R.id.tv_habits)
    TextView tvHabits;

    TipPopWin step3PopWin;
    @Bind(R.id.tv_progress_index)
    NoPaddingTextView tvProgressIndex;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.iv_icon)
    ImageView ivIcon;
    @Bind(R.id.tv_question_label)
    TextView tvQuestionLabel;

    public int currStep = -1;

    Handler showPopHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (step3PopWin != null)
                step3PopWin.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        }
    };

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_examine_question1;
    }

    public void hidePopWin() {

        if (step3PopWin != null) {
            showPopHandler.removeMessages(0);
            step3PopWin.dismiss();
        }
    }


    public void showQuestion() {

        llProgress.setVisibility(View.VISIBLE);
        llResult.setVisibility(View.GONE);
        ivLeft.setVisibility(View.GONE);
        tvQuestionLabel.setText("中医体质辨识");
        if (step3PopWin != null)
            step3PopWin.dismiss();
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

        currStep = 1;

        currQuestion = question;

        llIndex.setVisibility(View.VISIBLE);
        pbProgress.setMax(question.getSubjectDTO().getTotalSubjectNum());
        pbProgress.setIndex(question.getSubjectDTO().getSubjectIndex());
        tvProgressIndex.setText(question.getSubjectDTO().getSubjectIndex() + "");
        tvStep2.setText(question.getSubjectDTO().getTotalSubjectNum() + "");

        if (question.getSubjectDTO().getType() == 2) { //描述题
            rlQuestion1.setVisibility(View.GONE);
            rlQuestion2.setVisibility(View.VISIBLE);
            tvQuestionContent2.setText(question.getSubjectDTO().getContent());

        } else { //选择题
            rlQuestion1.setVisibility(View.VISIBLE);
            rlQuestion2.setVisibility(View.GONE);
            tvQuestionContent.setText(question.getSubjectDTO().getContent());
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
                lvAnswers.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }

        ivLeft.setVisibility(View.VISIBLE);
        if (question.getSubjectDTO().getSubjectIndex() == 1) { //没有上一题
            ivLeft.setImageResource(R.mipmap.examine_index_left_n);
            ivLeft.setEnabled(false);
        } else {
            ivLeft.setImageResource(R.mipmap.examine_index_left_s);
            ivLeft.setEnabled(true);
        }

    }

    public void showReuslt(QuestionReport currQuestionReport, boolean isDelayShow) {

        currStep = 2;
        rlQuestion1.setVisibility(View.GONE);
        rlQuestion2.setVisibility(View.GONE);
        llResult.setVisibility(View.VISIBLE);
        ivLeft.setVisibility(View.GONE);

        llProgress.setVisibility(View.GONE);
        llIndex.setVisibility(View.GONE);
        tvQuestionLabel.setText("中医体质辨识结果");

        if (step3PopWin == null) {
            step3PopWin = new TipPopWin(mContext, R.layout.dialog_tip_result);
            step3PopWin.getContentView().findViewById(R.id.tv_next).setOnClickListener(mOnClickListener);
            step3PopWin.getContentView().findViewById(R.id.tv_reexamine).setOnClickListener(mOnClickListener);
            step3PopWin.getContentView().findViewById(R.id.tv_report).setOnClickListener(mOnClickListener);

            step3PopWin.getContentView().findViewById(R.id.tv_reexamine).setBackgroundResource(R.drawable.button_rerecognize_selector);
        }

        if (isDelayShow) {
            showPopHandler.sendEmptyMessageDelayed(0, 500);
            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    step3PopWin.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
                }
            }, 1000);*/
        } else {
            step3PopWin.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        }

        if (MyApplication.get().getCurrUserReport().isFinish()) { //已全部完成
            step3PopWin.getContentView().findViewById(R.id.tv_next).setVisibility(View.INVISIBLE);
        } else {
            step3PopWin.getContentView().findViewById(R.id.tv_next).setVisibility(View.VISIBLE);
        }

        tvHabits.setText(currQuestionReport.getQuestionResultNameReal());
        ivIcon.setImageResource(currQuestionReport.getQuestionResultIcon());
        tvDescription.setText(currQuestionReport.getDescription());

        SerialPortCmd.face4();
    }

}
