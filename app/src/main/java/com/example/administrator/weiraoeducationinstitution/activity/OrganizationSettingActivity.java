package com.example.administrator.weiraoeducationinstitution.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.db.TagsManager;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
import com.example.administrator.weiraoeducationinstitution.view.TagGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.weirao.wheelview.activity.LocationActivity;

public class OrganizationSettingActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;

    private LinearLayout locationLL, introductionLL, tagLL;
    public static TextView addr;
    private TagGroup mTagGroup;
    private TagsManager mTagsManager;
    public static TextView intro,mid_intro;

    private TagGroup.OnTagClickListener mTagClickListener = new TagGroup.OnTagClickListener() {
        @Override
        public void onTagClick(String tag) {
            Toast.makeText(OrganizationSettingActivity.this, tag, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_organization_setting;
    }

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
        mTagsManager = TagsManager.getInstance(getApplicationContext());
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        weiraoToolbarTop.setTitle("机构主页");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addr = customFindViewById(R.id.organization_setting_location);
        mTagGroup = customFindViewById(R.id.organization_tag_group);
        tagLL = customFindViewById(R.id.organization_setting_tagLL);
        intro = customFindViewById(R.id.organization_setting_introduction_txt);
        mid_intro=customFindViewById(R.id.organization_setting_introduction_txt_mid);
        locationLL = customFindViewById(R.id.organization_setting_locationLL);
        introductionLL = customFindViewById(R.id.organization_setting_introductionLL);
        locationLL.setOnClickListener(this);
        introductionLL.setOnClickListener(this);
        tagLL.setOnClickListener(this);
        mTagGroup.setOnTagClickListener(mTagClickListener);
        if (BaseApplication.user_info.getFeature() != null) {
            String str = BaseApplication.user_info.getFeature();
            String[] split = str.split(",");
            mTagGroup.setTags(split);
        }

    }

    @Override
    protected void initData() {
        addr.setText(BaseApplication.user_info.getLocation());
        intro.setText(BaseApplication.user_info.getBrief());
        mid_intro.setText(BaseApplication.user_info.getBrief());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.organization_setting_locationLL:
                startActivityWithoutExtras(LocationActivity.class);
                break;
            case R.id.organization_setting_introductionLL:
                startActivityWithoutExtras(Organization_Setting_IntroductionActivity.class);
                break;
            case R.id.organization_setting_tagLL:
                startActivityWithoutExtras(AssociationTagGoupActivity.class);
                break;
        }
    }

    @Override
    protected void onRestart() {
        String[] tags = mTagsManager.getTags();
        mTagGroup.setTags(tags);
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        addr = null;
        intro = null;
        mid_intro=null;
    }
}
