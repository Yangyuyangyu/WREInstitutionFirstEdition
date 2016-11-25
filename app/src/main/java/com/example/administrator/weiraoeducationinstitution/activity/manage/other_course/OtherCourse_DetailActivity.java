package com.example.administrator.weiraoeducationinstitution.activity.manage.other_course;


import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OtherCourse_DetailActivity extends BaseActivity {
    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;

    private String[] condition = new String[5];
    private TextView name, time, teacher1, teacher2, content, feed;//名字，时间，老师，老师建议，反馈

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_other_course_detail;
    }

    @Override
    protected void init() {
        condition = getIntent().getStringArrayExtra("the_other_course_condition");
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        weiraoToolbarTop.setTitle("其他课详情");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        name = customFindViewById(R.id.other_course_detail_name);
        time = customFindViewById(R.id.other_course_detail_time);
        teacher1 = customFindViewById(R.id.other_course_detail_teacher1);
        teacher2 = customFindViewById(R.id.other_course_detail_teacher2);
        content = customFindViewById(R.id.other_course_detail_t1_content);
        feed = customFindViewById(R.id.other_course_detail_t2_feed);

    }

    @Override
    protected void initData() {
        if (condition != null) {
            name.setText(condition[0]);
            time.setText(condition[1]);
            teacher1.setText(condition[2]);
            teacher2.setText(condition[2]);
            content.setText(condition[3]);
            feed.setText(condition[4]);
        }
    }
}
