package com.example.administrator.weiraoeducationinstitution.activity.manage.havingclass;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.manage.havingclass.PinnedHeaderExpandableAdapter;
import com.example.administrator.weiraoeducationinstitution.adapter.manage.havingclass.StudentCallExpandableAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.manage.havingclass.HavingCheckGradeBean;
import com.example.administrator.weiraoeducationinstitution.bean.manage.leave.HavingCheckLeaveBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.example.administrator.weiraoeducationinstitution.view.PinnedHeaderExpandableListView;
import com.example.administrator.weiraoeducationinstitution.view.TipsDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 审核报告查看评分
 */
public class HavingClassCheckActivity extends BaseActivity {
    private Context mContext;
    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;
    private String the_id, the_course_student_id;
    private TipsDialog dialog;

    private PinnedHeaderExpandableListView expandableListView;
    private int expandFlag = -1;//控制列表的展开
    private PinnedHeaderExpandableAdapter adapter;
    private StudentCallExpandableAdapter bdapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_having_class_check;
    }

    @Override
    protected void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            the_id = bundle.getString("the_havingclass_id");//上课记录审核学生打分
            the_course_student_id = bundle.getString("the_course_student");//课程详情学生点名数据
        }
    }

    @Override
    protected void initView() {
        mContext = this;
        dialog = new TipsDialog(this);
        ButterKnife.bind(this);
        if (the_id != null) {
            weiraoToolbarTop.setTitle("上课评分记录");
        } else if (the_course_student_id != null) {
            weiraoToolbarTop.setTitle("学生上课记录");
        }
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        expandableListView = customFindViewById(R.id.explistview);
    }

    @Override
    protected void initData() {
        if (the_id != null) {
            getCheckGrade(the_id);
        }
        if (the_course_student_id != null) {
            getCourseStudent(the_course_student_id);
        }

    }

    /**
     * 查看学生点名
     */
    private void getCourseStudent(String id) {
        String reqUrl = Apis.GetCourseStudent(id);
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
                        HavingCheckLeaveBean hclb = gson.fromJson(jsonObject.toString(),
                                new TypeToken<HavingCheckLeaveBean>() {
                                }.getType());
                        //设置悬浮头部VIEW
                        String[] group = new String[]{"请假", "迟到", "缺席", "准时"};
                        expandableListView.setHeaderView(getLayoutInflater().inflate(R.layout.group_head,
                                expandableListView, false));
                        bdapter = new StudentCallExpandableAdapter(hclb, group, mContext, expandableListView);
                        bdapter.secallItemPhoneClcik(new StudentCallExpandableAdapter.callItemPhoneClcik() {
                            @Override
                            public void clickphone(final String phone) {
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
                        expandableListView.setAdapter(bdapter);
                        //设置单个分组展开
                        expandableListView.setOnGroupClickListener(new GroupClickListener());
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

    /**
     * 查看学生打分
     */
    private void getCheckGrade(String id) {
        String reqUrl = Apis.GetCheckGrade(id);
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
                        HavingCheckGradeBean hcgb = gson.fromJson(jsonObject.toString(),
                                new TypeToken<HavingCheckGradeBean>() {
                                }.getType());
                        //设置悬浮头部VIEW
                        expandableListView.setHeaderView(getLayoutInflater().inflate(R.layout.group_head,
                                expandableListView, false));
                        adapter = new PinnedHeaderExpandableAdapter(hcgb, mContext, expandableListView);
                        expandableListView.setAdapter(adapter);
                        //设置单个分组展开
                        expandableListView.setOnGroupClickListener(new GroupClickListener());
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

    class GroupClickListener implements ExpandableListView.OnGroupClickListener {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v,
                                    int groupPosition, long id) {
            if (expandFlag == -1) {
                // 展开被选的group
                expandableListView.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                expandableListView.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            } else if (expandFlag == groupPosition) {
                expandableListView.collapseGroup(expandFlag);
                expandFlag = -1;
            } else {
                expandableListView.collapseGroup(expandFlag);
                // 展开被选的group
                expandableListView.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                expandableListView.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            }
            return true;
        }
    }

}
