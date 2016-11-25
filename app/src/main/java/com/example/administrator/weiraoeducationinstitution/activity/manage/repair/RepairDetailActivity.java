package com.example.administrator.weiraoeducationinstitution.activity.manage.repair;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.course_pop.Course_PopAdapter;
import com.example.administrator.weiraoeducationinstitution.adapter.course_pop.Subject_PopAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AllCourseBean;
import com.example.administrator.weiraoeducationinstitution.bean.SubjectBean;
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

public class RepairDetailActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;

    private String[] the_repair = new String[6];//名字，科目，器材，说明,社团id,课程
    private LinearLayout pop, bottom;
    private TextView name, subject, instrument, remark;
    private Button cancel, ok;
    private PopupWindow popWindow;// 弹窗
    private ListView pop_list;// 弹窗list
    private Subject_PopAdapter mAdapter;//课程adapter
    private String the_subject_id;
    private String getTheAsId;

    //添加课程
    private LinearLayout pop_course, courseLL;
    private EditText course;
    private PopupWindow popWindow_c;// 课程弹窗
    private ListView pop_list_c;// 课程弹窗list
    private Course_PopAdapter mAdapter_c;//课程adapter
    private String the_course_id;//课程id

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_repair_detail;
    }


    @Override
    protected void init() {
        the_repair = getIntent().getStringArrayExtra("the_repair_condition");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            getTheAsId = bundle.getString("the_association_id");
        }
        mAdapter = new Subject_PopAdapter(RepairDetailActivity.this, new ArrayList<SubjectBean>());
        mAdapter_c = new Course_PopAdapter(RepairDetailActivity.this, new ArrayList<AllCourseBean>());
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        weiraoToolbarTop.setTitle("乐器维修");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pop = customFindViewById(R.id.repair_detail_pop);
        bottom = customFindViewById(R.id.repair_detail_bottomLL);

        name = customFindViewById(R.id.repair_detail_name);
        subject = customFindViewById(R.id.repair_detail_subject);
        instrument = customFindViewById(R.id.repair_detail_instrument);
        remark = customFindViewById(R.id.repair_detail_remark);
        cancel = customFindViewById(R.id.repair_detail_cancel);
        ok = customFindViewById(R.id.repair_detail_ok);
        pop.setOnClickListener(this);
        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);

        pop_course = customFindViewById(R.id.repair_detail_pop_course);
        course = customFindViewById(R.id.repair_detail_course);
        courseLL = customFindViewById(R.id.repair_detail_courseLL);
        pop_course.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (the_repair != null) {
            bottom.setVisibility(View.GONE);
            pop.setVisibility(View.INVISIBLE);
            pop_course.setVisibility(View.GONE);
            name.setText(the_repair[0]);
            subject.setText(the_repair[1]);
            instrument.setText(the_repair[2]);
            remark.setText(the_repair[3]);
            course.setText(the_repair[5]);
        }
        if (getTheAsId != null) {
            getSubjectData();
        }
        getDrawerCourse();

    }

    private void showPopwindown_course() {
        if (popWindow_c == null) {
            View view = getLayoutInflater().inflate(
                    R.layout.pop_exam_menue, null);
            popWindow_c = new PopupWindow(view,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            pop_list_c
                    = (ListView) view
                    .findViewById(R.id.pop_exam_list);
            pop_list_c.setAdapter(mAdapter_c);
            pop_list_c.setItemsCanFocus(false);
            pop_list_c.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            pop_list_c.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    popWindow_c.dismiss();
                    the_course_id = mAdapter_c
                            .getItemCourseId(position);
                    course.setText(mAdapter_c.getItemName(position));
                }
            });
        }
        ColorDrawable cd = new ColorDrawable(0x000000);
        popWindow_c.setBackgroundDrawable(cd);
        popWindow_c.setOutsideTouchable(true);
        popWindow_c.setFocusable(true);
        popWindow_c.showAsDropDown(course);
        popWindow_c.setAnimationStyle(R.style.pop_anim);
        popWindow_c.update();
        popWindow_c.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                popWindow_c = null;
            }
        });

    }

    /**
     * 获取课程列表
     */
    private void getDrawerCourse() {
        String reqUrl = Apis.GetMyCourse(BaseApplication.user_info.getId());
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Gson gson = new Gson();
                    List<AllCourseBean> list = gson.fromJson(
                            jsonObject.getString("data"),
                            new TypeToken<List<AllCourseBean>>() {
                            }.getType());
                    mAdapter_c.changeManagerData(list);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.repair_detail_cancel:
                finish();
                break;
            case R.id.repair_detail_ok:
                if (TextUtil.isEmpty(name.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(name, "请输入报修人姓名");
                    return;
                }
                if (TextUtil.isEmpty(subject.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(name, "请选择科目名称");
                    return;
                }
                if (TextUtil.isEmpty(course.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(name, "请选择课程名称");
                    return;
                }
                if (TextUtil.isEmpty(instrument.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(name, "请输入报修器材");
                    return;
                }
                addRepair(name.getText().toString(), the_subject_id, getTheAsId, instrument.getText().toString(), remark.getText().toString(), the_course_id);
                break;
            case R.id.repair_detail_pop:
                showPopwindown();
                break;
            case R.id.repair_detail_pop_course:
                showPopwindown_course();
                break;
        }
    }

    private void addRepair(String user_name, String subject, String group, String instrument, String remark, String course) {
        String reqUrl = Apis.GetAddRepair(user_name, subject, group, instrument, remark, course);
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
                    the_subject_id = mAdapter.getItemSubjectId(position);
                    subject.setText(mAdapter.getItemName(position));
                }
            });
        }
        ColorDrawable cd = new ColorDrawable(0x000000);
        popWindow.setBackgroundDrawable(cd);
        popWindow.setOutsideTouchable(true);
        popWindow.setFocusable(true);
        popWindow.showAtLocation(customFindViewById(R.id.repair_detail_view), Gravity.BOTTOM, 0, 0);
        popWindow.setAnimationStyle(R.style.pop_anim);
        popWindow.update();
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                popWindow = null;
            }
        });

    }

    private void getSubjectData() {
        String reqUrl = Apis.GetSubjectOfGroup(getTheAsId);
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
                        List<SubjectBean> list = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<List<SubjectBean>>() {
                                }.getType());
                        mAdapter.changeSubjectData(list);
                    } else {
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
}
