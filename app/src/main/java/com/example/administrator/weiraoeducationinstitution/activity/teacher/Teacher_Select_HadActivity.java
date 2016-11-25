package com.example.administrator.weiraoeducationinstitution.activity.teacher;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.student.Teacher_HadAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AllTeacherBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.example.administrator.weiraoeducationinstitution.view.ClearEditText;
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

public class Teacher_Select_HadActivity extends BaseActivity implements View.OnClickListener {
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

    private RelativeLayout bottomView;
    private TextView newAdd;
    private Button add;
    private ImageView search;
    private ClearEditText input;
    private XRecyclerView mRecyclerView;
    private Teacher_HadAdapter mBookAdapter;

    private String theTeacherId = null;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_teacher_select_had;
    }

    @Override
    protected void initData() {
        //最近新增
        mRecyclerView.setRefreshing(true);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        title.setText("选择已有老师");
        includeSettingIntroductionOk.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bottomView = customFindViewById(R.id.teacher_had_bottom_view);
        add = customFindViewById(R.id.teacher_had_add);
        newAdd = customFindViewById(R.id.teacher_had_newAdd_text);
        search = customFindViewById(R.id.teacher_had_search_img);
        input = customFindViewById(R.id.teacher_had_search_input);
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(input.getText())) {
                        hideInput();
                    }
                }
                return true;
            }
        });
        mRecyclerView = customFindViewById(R.id.teacher_had_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mBookAdapter = new Teacher_HadAdapter(this, new ArrayList<AllTeacherBean>());
        mBookAdapter.setteacherHadOnclick(new Teacher_HadAdapter.teacherHadOnclick() {
            @Override
            public void teacherHadItemClick(String phone) {
                input.setText(phone);
                hideInput();
            }
        });
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
        add.setOnClickListener(this);
        search.setOnClickListener(this);
        showInput();
    }

    private void getData(String mobile) {
        String reqUrl = Apis.GetChoose(mobile);
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
                            theTeacherId = mBookAdapter.getItemTeacherId(0);
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

    /**
     * 新增搜索的老师
     *
     * @param tid
     */
    private void saveTheTeacher(String tid) {
        String reqUrl = Apis.GetSave(BaseApplication.user_info.getId(), tid);
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
                loadComplete();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.teacher_had_add:
                if (theTeacherId == null) {
                    ToastUtils.getInstance().showToast("搜索结果不存在");
                    return;
                }
                saveTheTeacher(theTeacherId);
                break;
            case R.id.teacher_had_search_img:
                if (TextUtils.isEmpty(input.getText())) {
                    return;
                }
                hideInput();
                break;
        }
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

    /**
     * 搜索内容为空时还原
     */
    private void showInput() {
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(input.getText().toString())) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    newAdd.setText("最近新增");
                    newAdd.setHeight(75);
                    add.setVisibility(View.GONE);
                    mBookAdapter.clear();
                    getData(null);
                    bottomView.setVisibility(View.GONE);
                }
            }
        });

    }
}
