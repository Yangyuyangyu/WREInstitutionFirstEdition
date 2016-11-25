package com.example.administrator.weiraoeducationinstitution.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.activity.manage.havingclass.HavingClassCheckActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.CourseDetailAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.CourseDetailBean;
import com.example.administrator.weiraoeducationinstitution.bean.CourseDetailTInfo;
import com.example.administrator.weiraoeducationinstitution.bean.CourseEvalueteBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CourseDetailActivity extends BaseActivity {
    private static String TAG = "CourseDetailActivity";
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mIvBook;

    private CourseDetailBean mBean;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private String theCourseId;//全部课程列表点击
    private String[] theCourse = new String[2];//课程表点击 课程详情cid  和点名数据 id
    public static List<CourseEvalueteBean> commentList = null;

    //中间参数
    private TextView coursename, fit, agency, addr, teacher;
    private ImageView teacher_head;
    private Button check;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_course_detail;
    }


    @Override
    protected void initView() {
        //设置Toolbar
        mToolbar = customFindViewById(R.id.course_detail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//决定左上角的图标是否可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//决定左上角图标的右侧是否有向左的小箭头
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mCollapsingToolbarLayout = customFindViewById(R.id.course_detail_collapsing_toolbar_layout);
        mIvBook = customFindViewById(R.id.course_detail_image);
        mViewPager = customFindViewById(R.id.course_detail_viewpager);
        mTabLayout = customFindViewById(R.id.sliding_tabs);
        mTabLayout.addTab(mTabLayout.newTab().setText("课程说明"));
        mTabLayout.addTab(mTabLayout.newTab().setText("查看详情"));
        mTabLayout.addTab(mTabLayout.newTab().setText("评价"));
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.weirao_title_color));

        coursename = customFindViewById(R.id.course_detail_content_name);
        fit = customFindViewById(R.id.course_detail_content_fit);
        agency = customFindViewById(R.id.course_detail_content_agency);
        addr = customFindViewById(R.id.course_detail_content_addr);
        teacher = customFindViewById(R.id.course_detail_content_teacher);
        teacher_head = customFindViewById(R.id.course_detail_content_head);
        check = customFindViewById(R.id.course_detail_content_check);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("the_course_student", theCourse[1]);
                startActivityWithExtras(HavingClassCheckActivity.class, bundle);
            }
        });
    }


    @Override
    protected void init() {
        theCourseId = getIntent().getStringExtra("theCourseId");
        theCourse = getIntent().getStringArrayExtra("theCourseIdArray");
        commentList = new ArrayList<CourseEvalueteBean>();
        FinishUitl.activityList.add(this);
    }

    @Override
    protected void initData() {
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//位于Title的颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);//扩张的颜色
        mCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.weirao_title_color));
        if (theCourseId != null) {
            getData(theCourseId);
        } else if (theCourse != null) {
            getData(theCourse[0]);
            check.setVisibility(View.VISIBLE);
        }
    }

    private void getData(String id) {
        String reqUrl = Apis.GetCourseInfo(id);
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
                        CourseDetailObject co = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<CourseDetailObject>() {
                                }.getType());
                        mBean = co.getInfo();
                        if (!co.getInfo().getImg().equals("")) {
                            HttpUtils.getInstance().loadImage(co.getInfo().getImg(), mIvBook);
                        }
                        if (co.getInfo().getName() != null) {
                            mCollapsingToolbarLayout.setTitle(co.getInfo().getName());
                            coursename.setText(co.getInfo().getName());
                        } else {
                            mCollapsingToolbarLayout.setTitle("");
                        }
                        fit.setText(co.getInfo().getFit_crowd());
                        agency.setText(co.getInfo().getAgency());
                        addr.setText(co.getInfo().getAddress());
                        teacher.setText("主讲老师   " + co.getInfo().getTeacher_name());
                        if (!co.gettInfo().getHead().equals("")) {
                            HttpUtils.getInstance().loadImage(co.gettInfo().getHead(), teacher_head);
                        }
                        if (co.getComment() != null) {
                            commentList = co.getComment();
                        }
                    } else {
                        ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CourseDetailAdapter adapter = new CourseDetailAdapter(CourseDetailActivity.this, mBean, getSupportFragmentManager());
                mViewPager.setAdapter(adapter);
                mTabLayout.setupWithViewPager(mViewPager);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        commentList = null;
        super.onDestroy();
    }

    class CourseDetailObject {
        private CourseDetailBean info;
        private CourseDetailTInfo tInfo;
        private List<CourseEvalueteBean> comment;

        public CourseDetailTInfo gettInfo() {
            return tInfo;
        }

        public void settInfo(CourseDetailTInfo tInfo) {
            this.tInfo = tInfo;
        }

        public List<CourseEvalueteBean> getComment() {
            return comment;
        }

        public void setComment(List<CourseEvalueteBean> comment) {
            this.comment = comment;
        }

        public CourseDetailBean getInfo() {
            return info;
        }

        public void setInfo(CourseDetailBean info) {
            this.info = info;
        }
    }
}
