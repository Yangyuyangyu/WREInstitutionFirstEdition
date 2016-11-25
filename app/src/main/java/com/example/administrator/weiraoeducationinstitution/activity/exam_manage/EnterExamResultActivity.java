package com.example.administrator.weiraoeducationinstitution.activity.exam_manage;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.EnterExamGrageBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EnterExamResultActivity extends BaseActivity {

    @Bind(R.id.include_setting_introduction_back)
    ImageView back;
    @Bind(R.id.include_setting_introduction_title)
    TextView title;
    @Bind(R.id.include_setting_introduction_ok)
    TextView confirm;
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;
    private int mCurrentAction = ACTION_REFRESH;
    private int mCurrentPageIndex = 1;

    private String getCourseId;
    private int isErrorIndex = 0;//下标

    private ExamGradeEditAdapter mExamGradeEditAdapter;
    private XRecyclerView mRecyclerView;
    private static List<EnterExamGrageBean> mBeanList = null;
    private StringBuilder sidList = new StringBuilder(), scoreList = new StringBuilder();


    @Override
    protected void init() {
        getCourseId = getIntent().getStringExtra("the_exam_course_id");
        mBeanList = new ArrayList<EnterExamGrageBean>();
    }

    @Override
    protected void initData() {
        mRecyclerView.setRefreshing(true);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_enter_exam_result;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        title.setText("考试管理");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (int i = 0; i < hashMap.size(); i++) {
//                    Toast.makeText(EnterExamResultActivity.this, "学生id:" + hashMapId.get(i) + " 成绩:" + hashMap.get(i), Toast.LENGTH_SHORT).show();
//                }
                if (hashMap.size() < mBeanList.size()) {
                    ToastUtils.getInstance().showToast((mBeanList.size() - hashMap.size()) + "位学生未完成分数填写");
                    return;
                }

                if (hashMap.size() == hashMapId.size()) {
                    for (int i = 0; i < hashMap.size(); i++) {
                        sidList.append(hashMapId.get(i));
                        scoreList.append(hashMap.get(i));
                        if (i != hashMap.size() - 1) {
                            sidList.append(",");
                            scoreList.append(",");
                        }

                    }
                    saveStudentGrade(getCourseId, sidList.toString(), scoreList.toString());
                } else {
                    ToastUtils.getInstance().showToast("分数提交失败");
                }

            }
        });

        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        mRecyclerView = customFindViewById(R.id.enter_exam_result_recyclerview);
        mExamGradeEditAdapter = new ExamGradeEditAdapter(this, new ArrayList<EnterExamGrageBean>());
        mRecyclerView.setAdapter(mExamGradeEditAdapter);
        mRecyclerView.setLayoutManager(LayoutManager);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                switchAction(ACTION_REFRESH);
            }

            @Override
            public void onLoadMore() {
                switchAction(ACTION_LOAD_MORE);
            }
        });
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);

    }

    private void getData() {
        String reqUrl = Apis.GetExamStudent(getCourseId);
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("code").equals("0")) {
                        Gson gson = new Gson();
                        List<EnterExamGrageBean> list = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<List<EnterExamGrageBean>>() {
                                }.getType());
                        mExamGradeEditAdapter.addAll(list);
                        mBeanList = list;
                        loadComplete();
                        mRecyclerView.setLoadingMoreEnabled(false);
                        mRecyclerView.setPullRefreshEnabled(false);
                    } else {
                        mRecyclerView.refreshComplete();
                        ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                ToastUtils.getInstance().showToast(e.getMessage());
            }
        });
    }

    private void loadComplete() {
        if (mCurrentAction == ACTION_REFRESH)
            mRecyclerView.refreshComplete();
        if (mCurrentAction == ACTION_LOAD_MORE)
            mRecyclerView.loadMoreComplete();
    }

    private void switchAction(int action) {
        mCurrentAction = action;
        switch (mCurrentAction) {
            case ACTION_REFRESH:
                mExamGradeEditAdapter.clear();
                mCurrentPageIndex = 1;
                break;
            case ACTION_LOAD_MORE:
                mCurrentPageIndex++;
                break;
        }
        getData();
    }

    /**
     * 保存学生成绩
     */

    private void saveStudentGrade(String id, String sid, String score) {
        String reqUrl = Apis.GetSaveScore(id, sid, score);
        Log.e("录入分数", reqUrl);
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("code").equals("0")) {
                        ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
                        finish();
                    } else {
                        ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                ToastUtils.getInstance().showToast(e.getMessage());
            }
        });
    }


    private HashMap<Integer, String> hashMap = new HashMap<Integer, String>();//存放EditText的值
    private HashMap<Integer, String> hashMapId = new HashMap<Integer, String>();//存放id的值

    class ExamGradeEditAdapter extends SolidRVBaseAdapter<EnterExamGrageBean> {
        public ExamGradeEditAdapter(Context context, List<EnterExamGrageBean> beans) {
            super(context, beans);
        }

        @Override
        public int getItemLayoutID(int vieWType) {
            return R.layout.item_enter_exam_grade;
        }

        @Override
        protected void onItemClick(int position) {
        }

        @Override
        protected void onBindDataToView(final SolidCommonViewHolder holder, final EnterExamGrageBean bean) {
            holder.setText(R.id.item_enter_exam_name, bean.getName());
            final EditText editText = holder.getView(R.id.item_enter_exam_grade);
            // 为editText设置TextChangedListener，每次改变的值设置到hashMap
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (holder.getAdapterPosition() - 1 == 0) {
                        hashMap.clear();
                        hashMapId.clear();
                    }
                    hashMap.put(holder.getAdapterPosition() - 1, s.toString());
                    hashMapId.put(holder.getAdapterPosition() - 1, bean.getId());
                    if (editText.getText().toString() == null || editText.getText().toString().equals("")) {
                        holder.setTextColor(R.id.item_enter_exam_name, Color.RED);
                    } else {
                        holder.setTextColor(R.id.item_enter_exam_name, getResources().getColor(R.color.commo_text_color));
                    }
                }
            });
        }


    }


    @Override
    protected void onDestroy() {
        mBeanList = null;
        sidList = null;
        scoreList = null;
        super.onDestroy();
    }

}
