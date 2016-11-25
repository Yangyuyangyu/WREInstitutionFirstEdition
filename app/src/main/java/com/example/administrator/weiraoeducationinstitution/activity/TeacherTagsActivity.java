package com.example.administrator.weiraoeducationinstitution.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.db.TeacherTagsManager;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
import com.example.administrator.weiraoeducationinstitution.view.TagGroup;


import butterknife.Bind;
import butterknife.ButterKnife;

public class TeacherTagsActivity extends BaseActivity {


    @Bind(R.id.include_setting_introduction_back)
    ImageView back;
    @Bind(R.id.include_setting_introduction_title)
    TextView title;
    @Bind(R.id.include_setting_introduction_ok)
    TextView ok;

    private TagGroup mTagGroup;
    private TeacherTagsManager mTagsManager;

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_teacher_tags;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        title.setText("老师特点");
        ok.setText("完成");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTagGroup.getTags() == null) {
                    return;
                }
                mTagsManager.updateTags(mTagGroup.getTags());
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTagsManager.updateTags(mTagGroup.getTags());
                finish();
            }
        });
        mTagGroup = customFindViewById(R.id.teacher_tag_group);
        mTagsManager = TeacherTagsManager.getInstance(getApplicationContext());
        String[] tags = mTagsManager.getTags();
        mTagGroup.setTags(tags);

    }

    @Override
    public void onBackPressed() {
        if (mTagGroup.getTags() != null) {
            mTagsManager.updateTags(mTagGroup.getTags());
        }
        super.onBackPressed();
    }
}
