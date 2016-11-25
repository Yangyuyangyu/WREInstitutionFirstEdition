package com.example.administrator.weiraoeducationinstitution.activity;


import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
import com.example.administrator.weiraoeducationinstitution.utils.UserInfoSharePreference;
import com.example.administrator.weiraoeducationinstitution.view.TipsDialog;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;
    private Button exit_btn;
    private TipsDialog dialog;
    private RelativeLayout changeRe, feedback, about_us;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        dialog = new TipsDialog(this);
        exit_btn = customFindViewById(R.id.exit_acount_btn);
        weiraoToolbarTop.setTitle("设置");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changeRe = customFindViewById(R.id.setting_changePwdRe);
        feedback = customFindViewById(R.id.setting_feedBack_);
        about_us = customFindViewById(R.id.setting_about_us);
        exit_btn.setOnClickListener(this);
        changeRe.setOnClickListener(this);
        about_us.setOnClickListener(this);
        feedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_acount_btn:
                dialog.myFunction(new TipsDialog.DialogCallBack() {
                    @Override
                    public void setContent(TextView v) {
                        v.setText("是否退出账号?");
                    }

                    @Override
                    public void setConfirmOnClickListener(Button btn) {
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dissMiss();
                                UserInfoSharePreference.getInstance().setCUSTOMERNAME("");
                                UserInfoSharePreference.getInstance().setCUSTOMERID("");
                                startActivityWithoutExtras(Login_loginActivity.class);
                                FinishUitl.clearActivity();
                            }
                        });
                    }
                });
                break;
            case R.id.setting_changePwdRe:
                startActivityWithoutExtras(ChangePasswordActivity.class);
                break;
            case R.id.setting_feedBack_:
                startActivityWithoutExtras(FeedBackActivity.class);
                break;
            case R.id.setting_about_us:
                startActivityWithoutExtras(AboutActivity.class);
                break;
        }

    }
}
