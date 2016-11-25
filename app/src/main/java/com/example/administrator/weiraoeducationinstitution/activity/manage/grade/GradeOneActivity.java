package com.example.administrator.weiraoeducationinstitution.activity.manage.grade;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GradeOneActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;

    private TextView complete, earnest, work;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_grade_one;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        weiraoToolbarTop.setTitle("打分项目");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        complete = customFindViewById(R.id.grade_one_course_complete);
        earnest = customFindViewById(R.id.grade_one_course_earnest);
        work = customFindViewById(R.id.grade_one_course_work);
        complete.setOnClickListener(this);
        earnest.setOnClickListener(this);
        work.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.grade_one_course_complete:
                Bundle bundle1 = new Bundle();
                bundle1.putString("course_condition", "course_complete");
                startActivityWithExtras(GradeTwoActivity.class, bundle1);
                break;
            case R.id.grade_one_course_earnest:
                Bundle bundle2 = new Bundle();
                bundle2.putString("course_condition", "course_earnest");
                startActivityWithExtras(GradeTwoActivity.class, bundle2);
                break;
            case R.id.grade_one_course_work:
                Bundle bundle3 = new Bundle();
                bundle3.putString("course_condition", "course_work");
                startActivityWithExtras(GradeTwoActivity.class, bundle3);
                break;
        }
    }
}
