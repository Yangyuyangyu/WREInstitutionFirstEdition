package com.example.administrator.weiraoeducationinstitution.activity.manage.leave;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.example.administrator.weiraoeducationinstitution.view.TipsDialog;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AskLeaveDetailActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;

    private String[] getLeave = new String[9];//请假id，名字，课程，事由，开始、束时间，备注，电话,状态
    private Button refuse, ok;
    private TextView name, course, reason, time, remark, status;
    private LinearLayout phone, refuseLL;
    private TipsDialog dialog;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_ask_leave_detail;
    }

    @Override
    protected void init() {
        getLeave = getIntent().getStringArrayExtra("the_leave_content");
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        weiraoToolbarTop.setTitle("请假审批");
        setSupportActionBar(weiraoToolbarTop);
        dialog = new TipsDialog(this);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refuse = customFindViewById(R.id.leave_detail_refuse);
        ok = customFindViewById(R.id.leave_detail_ok);
        phone = customFindViewById(R.id.leave_detail_phoneLL);
        name = customFindViewById(R.id.leave_detail_name);
        course = customFindViewById(R.id.leave_detail_course);
        reason = customFindViewById(R.id.leave_detail_reason);
        time = customFindViewById(R.id.leave_detail_time);
        remark = customFindViewById(R.id.leave_detail_remark);
        status = customFindViewById(R.id.leave_detail_status_text);
        refuseLL = customFindViewById(R.id.leave_detail_refuseLL);
        refuse.setOnClickListener(this);
        ok.setOnClickListener(this);
        phone.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        name.setText(getLeave[1]);
        course.setText(getLeave[2]);
        reason.setText(getLeave[3]);
        time.setText(getLeave[4] + "~" + getLeave[5]);
        remark.setText(getLeave[6]);
        if (getLeave[8].equals("1")) {
            status.setVisibility(View.VISIBLE);
            refuseLL.setVisibility(View.GONE);
        } else if (getLeave[8].equals("2")) {
            status.setText("已拒绝");
            status.setTextColor(getResources().getColor(R.color.weirao_title_color));
            status.setVisibility(View.VISIBLE);
            refuseLL.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leave_detail_refuse:
                Bundle bundle = new Bundle();
                bundle.putString("the_leave_id", getLeave[0]);
                startActivityWithExtras(LeaveRefuseActivity.class, bundle);
                break;
            case R.id.leave_detail_ok:
                agree(getLeave[0], "1", "");
                break;
            case R.id.leave_detail_phoneLL:
                dialog.myFunction(new TipsDialog.DialogCallBack() {
                    @Override
                    public void setContent(TextView v) {
                        v.setText("是否拨打电话: " + getLeave[7]);
                    }

                    @Override
                    public void setConfirmOnClickListener(Button btn) {
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getLeave[7]));
                                startActivity(intent);
                                dialog.dissMiss();
                            }
                        });
                    }
                });
                break;
        }
    }

    private void agree(String id, String result, String refuse) {
        String reqUrl = Apis.GetLeaveAuth(id, result, refuse);

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

    @Override
    protected void onDestroy() {
        getLeave = null;
        super.onDestroy();
    }
}
