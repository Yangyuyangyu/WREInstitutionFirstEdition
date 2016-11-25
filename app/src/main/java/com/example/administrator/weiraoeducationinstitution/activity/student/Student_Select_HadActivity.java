package com.example.administrator.weiraoeducationinstitution.activity.student;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.student.Student_HadAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AllTeacherBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.example.administrator.weiraoeducationinstitution.view.ClearEditText;
import com.example.administrator.weiraoeducationinstitution.view.TipsDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Student_Select_HadActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.include_setting_introduction_back)
    ImageView back;
    @Bind(R.id.include_setting_introduction_title)
    TextView title;
    @Bind(R.id.include_setting_introduction_ok)
    TextView includeSettingIntroductionOk;

    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;
    private int mCurrentAction = ACTION_REFRESH;
    private String mCurrentKeyWord;
    private int mPageSize = 10;
    private int mCurrentPageIndex = 1;

    private TipsDialog dialog;
    private View bottomView;
    private TextView newAdd;
    private Button add;
    private ImageView search;
    private ClearEditText input;
    private XRecyclerView mRecyclerView;
    private Student_HadAdapter mBookAdapter;

    private String theStudentId;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_student_select_had;
    }

    @Override
    protected void initData() {
        //最近新增
        mRecyclerView.setRefreshing(true);
    }


    @Override
    protected void initView() {
        ButterKnife.bind(this);
        title.setText("选择已有学生");
        includeSettingIntroductionOk.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dialog = new TipsDialog(this);
        bottomView = customFindViewById(R.id.student_had_bottom_view);
        add = customFindViewById(R.id.student_had_add);
        newAdd = customFindViewById(R.id.student_had_newAdd_text);
        search = customFindViewById(R.id.student_had_search_img);
        input = customFindViewById(R.id.student_had_search_input);
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideInput();
                }
                return true;
            }
        });
        mRecyclerView = customFindViewById(R.id.student_had_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mBookAdapter = new Student_HadAdapter(this, new ArrayList<AllTeacherBean>());
        mRecyclerView.setAdapter(mBookAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
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

        mBookAdapter.setteacherHadOnclick(new Student_HadAdapter.teacherHadOnclick() {
            @Override
            public void teacherHadItemClick(final String phone) {
                dialog.myFunction(new TipsDialog.DialogCallBack() {
                    @Override
                    public void setContent(TextView v) {
                        v.setText("是否拨打电话 " + phone);
                    }

                    @Override
                    public void setConfirmOnClickListener(Button btn) {
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dissMiss();
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
        search.setOnClickListener(this);
        add.setOnClickListener(this);
    }

    private void getData(String mobile) {
        String reqUrl = Apis.GetChooseStudent(mobile);
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
                        List<AllTeacherBean> list = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<List<AllTeacherBean>>() {
                                }.getType());
                        mBookAdapter.addAll(list);
                        loadComplete();
                        mRecyclerView.setPullRefreshEnabled(false);
                        mRecyclerView.setLoadingMoreEnabled(false);
                        if (list.size() == 1) {
                            theStudentId = mBookAdapter.getItemTeacherId(0);
                            ToastUtils.getInstance().showToast("学生id" + theStudentId);
                        }
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
                loadComplete();
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
                mBookAdapter.clear();
                mCurrentPageIndex = 1;
                break;
            case ACTION_LOAD_MORE:
                mCurrentPageIndex++;
                break;
        }
        getData(null);
    }

    private void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        newAdd.setText("");
        newAdd.setHeight(15);
        add.setVisibility(View.VISIBLE);
        mBookAdapter.clear();
        getData(input.getText().toString());
        bottomView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.student_had_add:
                //新增跳转到直接新增界面选择社团  theStudentId
                Bundle bundle = new Bundle();
                bundle.putString("the_sutdent_id", theStudentId);
                startActivityWithExtras(Student_Select_AddActivity.class, bundle);
                break;
            case R.id.student_had_search_img:
                hideInput();
                break;
        }
    }


}
