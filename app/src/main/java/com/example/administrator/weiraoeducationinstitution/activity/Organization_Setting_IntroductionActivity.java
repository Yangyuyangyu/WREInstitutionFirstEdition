package com.example.administrator.weiraoeducationinstitution.activity;


import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.MySnackbar;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timeselector.Utils.TextUtil;

public class Organization_Setting_IntroductionActivity extends BaseActivity {

    @Bind(R.id.include_setting_introduction_back)
    ImageView includeSettingIntroductionBack;
    @Bind(R.id.include_setting_introduction_title)
    TextView includeSettingIntroductionTitle;
    @Bind(R.id.include_setting_introduction_ok)
    TextView includeSettingIntroductionOk;

    private EditText content;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_organization_setting_introduction;
    }

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        includeSettingIntroductionTitle.setText("修改");
        includeSettingIntroductionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        includeSettingIntroductionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtil.isEmpty(content.getText().toString())) {
                    upContent(BaseApplication.user_info.getId(), "3", content.getText().toString());
                } else {
                    MySnackbar.getmInstance().showMessage(content, "简介内容不能为空");
                }
            }
        });

        content = customFindViewById(R.id.organization_content);
    }

    private void upContent(String id, String type, final String content) {
        String reqUrl = Apis.GetEditInfo(id, type, content);
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("code").equals("0")) {
                        OrganizationSettingActivity.intro.setText(content);
                        OrganizationSettingActivity.mid_intro.setText(content);
                        ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
                        BaseApplication.user_info.setBrief(content);
                        finish();
                    } else {
                        MySnackbar.getmInstance().showMessage(customFindViewById(R.id.organization_content), jsonObject.getString("msg"));
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


}
