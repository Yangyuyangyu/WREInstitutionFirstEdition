package com.example.administrator.weiraoeducationinstitution.activity.manage.telephone;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.course_pop.Association_PopAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AssociationBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.MySnackbar;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import timeselector.Utils.TextUtil;

/**
 * 如果传入 参数 已经解决则隐藏 底部按钮
 */
public class Telephone_Two extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;

    private EditText studentname, teachername, association, interview, feed, solution, condition;
    private LinearLayout pop, bottom, mid;
    private CheckBox cb_ok, cb_cancel;
    private Button ok, cancel;
    private TextView isSolve, solveTime;//是否解决

    private PopupWindow popWindow;// 弹窗
    private ListView pop_list;// 弹窗list
    private Association_PopAdapter mAdapter;
    private String association_Id = null;
    private String isSolved = "2";


    private String[] conditionStr = new String[9];//学生，老师，部门，内容，反馈，解决，原因，解决时间，是否解决

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_telephone_two;
    }

    @Override
    protected void init() {
        conditionStr = getIntent().getStringArrayExtra("telephone_interview_content");
        mAdapter = new Association_PopAdapter(Telephone_Two.this, new ArrayList<AssociationBean>());
    }


    @Override
    protected void initView() {
        ButterKnife.bind(this);
        if (conditionStr != null) {
            weiraoToolbarTop.setTitle("追访详情");
        } else {
            weiraoToolbarTop.setTitle("添加追访");
        }
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        studentname = customFindViewById(R.id.telephone_two_studentname);
        teachername = customFindViewById(R.id.telephone_two_teachername);
        association = customFindViewById(R.id.telephone_two_associationname);
        interview = customFindViewById(R.id.telephone_two_content_interview);
        feed = customFindViewById(R.id.telephone_two_content_feed);
        solution = customFindViewById(R.id.telephone_two_content_solution);
        condition = customFindViewById(R.id.telephone_two_content_condition);
        pop = customFindViewById(R.id.telephone_two_pop);
        cb_cancel = customFindViewById(R.id.telephone_two_cb_cancel);
        cb_ok = customFindViewById(R.id.telephone_two_cb_ok);
        ok = customFindViewById(R.id.telephone_two_ok);
        cancel = customFindViewById(R.id.telephone_two_cancel);
        bottom = customFindViewById(R.id.telephone_two_bottomLL);
        mid = customFindViewById(R.id.telephone_two_midLL);
        isSolve = customFindViewById(R.id.telephone_two_isText);
        solveTime = customFindViewById(R.id.telephone_two_content_solvetime);

        pop.setOnClickListener(this);
        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);
        cb_ok.setOnClickListener(this);
        cb_cancel.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (conditionStr != null) {
            pop.setVisibility(View.INVISIBLE);
            bottom.setVisibility(View.GONE);
            mid.setVisibility(View.GONE);
            isSolve.setVisibility(View.VISIBLE);
            if (conditionStr[8].equals("1")) {
                isSolve.setText("已解决");
            } else {
                isSolve.setText("未解决");
                isSolve.setTextColor(getResources().getColor(R.color.weirao_title_color));
            }
            studentname.setText(conditionStr[0]);
            teachername.setText(conditionStr[1]);
            association.setText(conditionStr[2]);
            interview.setText(conditionStr[3]);
            feed.setText(conditionStr[4]);
            solution.setText(conditionStr[5]);
            condition.setText(conditionStr[6]);
            solveTime.setText(conditionStr[7]);
        } else {
            getAssociation();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.telephone_two_cancel:
                finish();
                break;
            case R.id.telephone_two_ok:
                if (TextUtil.isEmpty(studentname.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(studentname, "请输入学生姓名");
                    return;
                }
                if (TextUtil.isEmpty(teachername.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(studentname, "请输入老师姓名");
                    return;
                }
                if (TextUtil.isEmpty(interview.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(studentname, "请输入追访内容");
                    return;
                }
                if (TextUtil.isEmpty(feed.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(studentname, "请输入反馈内容");
                    return;
                }
                if (TextUtil.isEmpty(solution.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(studentname, "请输入解决方法");
                    return;
                }
                if (association_Id == null) {
                    MySnackbar.getmInstance().showMessage(studentname, "请选择所在部门");
                    return;
                }
                addInterview(studentname.getText().toString(), teachername.getText().toString(), association_Id, interview.getText().toString(), feed.getText().toString()
                        , solution.getText().toString(), condition.getText().toString(), isSolved);
                break;
            case R.id.telephone_two_cb_ok:
                isSolved = "1";
                cb_ok.setChecked(true);
                cb_cancel.setChecked(false);
                break;
            case R.id.telephone_two_cb_cancel:
                isSolved = "2";
                cb_ok.setChecked(false);
                cb_cancel.setChecked(true);
                break;
            case R.id.telephone_two_pop:
                showPopwindown();
                break;
        }
    }

    private void addInterview(String student, String teacher, String groupId, String content, String feedback, String solution, String reason, String solved) {
        String reqUrl = Apis.GetAddFollow(student, teacher, groupId, content, feedback, solution, reason, solved);
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

            }
        });
    }


    private void showPopwindown() {
        if (popWindow == null) {
            View view = getLayoutInflater().inflate(
                    R.layout.pop_exam_menue, null);
            popWindow = new PopupWindow(view,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            pop_list = (ListView) view
                    .findViewById(R.id.pop_exam_list);
            pop_list.setAdapter(mAdapter);
            pop_list.setItemsCanFocus(false);
            pop_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            pop_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    popWindow.dismiss();
                    association_Id = mAdapter.getItemAssociationId(position);
                    association.setText(mAdapter.getItemName(position));
                }
            });
        }
        ColorDrawable cd = new ColorDrawable(0x000000);
        popWindow.setBackgroundDrawable(cd);
        popWindow.setOutsideTouchable(true);
        popWindow.setFocusable(true);
        popWindow.showAtLocation(customFindViewById(R.id.telephone_two_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popWindow.setAnimationStyle(R.style.pop_anim);
        popWindow.update();
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                popWindow = null;
            }
        });

    }

    /**
     * 获取社团列表
     */
    private void getAssociation() {
        String reqUrl = Apis.GetGetGroups(BaseApplication.user_info.getId());
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Gson gson = new Gson();
                    List<AssociationBean> list = gson.fromJson(
                            jsonObject.getString("data"),
                            new TypeToken<List<AssociationBean>>() {
                            }.getType());
                    mAdapter.changeManagerData(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }
}
