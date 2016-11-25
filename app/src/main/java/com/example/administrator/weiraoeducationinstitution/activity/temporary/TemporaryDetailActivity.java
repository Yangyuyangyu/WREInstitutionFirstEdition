package com.example.administrator.weiraoeducationinstitution.activity.temporary;


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
import com.example.administrator.weiraoeducationinstitution.activity.manage.leave.LeaveRefuseActivity;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.MySnackbar;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.example.administrator.weiraoeducationinstitution.view.TipsDialog;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TemporaryDetailActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;

    private TipsDialog dialog;
    private String[] getTemContent = new String[9];//临时id ，名字，课程，reason，remark，phone,account,classtime,status
    private Button refuse, ok;
    private TextView name, course, reason, time;
    private TextView reamark, account;
    private LinearLayout phone, bottomLL;
    private TextView status;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_temporary_detail;
    }

    @Override
    protected void init() {
        getTemContent = getIntent().getStringArrayExtra("the_temporary_content");
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        weiraoToolbarTop.setTitle("临时账号审批");
        setSupportActionBar(weiraoToolbarTop);
        dialog = new TipsDialog(this);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refuse = customFindViewById(R.id.temporary_detail_refuse);
        ok = customFindViewById(R.id.temporary_detail_ok);
        name = customFindViewById(R.id.temporary_detail_name);
        course = customFindViewById(R.id.temporary_detail_course);
        reason = customFindViewById(R.id.temporary_detail_reason);
        reamark = customFindViewById(R.id.temporary_Detail_remark);
        account = customFindViewById(R.id.temporary_detail_account);
        time = customFindViewById(R.id.temporary_detail_time);
        phone = customFindViewById(R.id.temporary_detail_phoneLL);
        status = customFindViewById(R.id.temporary_detail_status);
        bottomLL = customFindViewById(R.id.temporary_detail_bottomLL);
        refuse.setOnClickListener(this);
        ok.setOnClickListener(this);
        phone.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (getTemContent != null) {
            name.setText(getTemContent[1]);
            course.setText(getTemContent[2]);
            reason.setText(getTemContent[3]);
            reamark.setText(getTemContent[4]);
            account.setText(getTemContent[6]);
            time.setText(getTemContent[7]);
            if (getTemContent[8] == null) {
                return;
            }
            if (getTemContent[8].equals("1")) {
                status.setVisibility(View.VISIBLE);
                bottomLL.setVisibility(View.GONE);
            } else if (getTemContent[8].equals("2")) {
                status.setText("已拒绝");
                status.setVisibility(View.VISIBLE);
                bottomLL.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.temporary_detail_refuse:
                Bundle bundle = new Bundle();
                bundle.putString("the_temporary_id", getTemContent[0]);
                startActivityWithExtras(LeaveRefuseActivity.class, bundle);
                break;
            case R.id.temporary_detail_ok:
                agreeCourse(getTemContent[0], "1", null);
                break;
            case R.id.temporary_detail_phoneLL:
                dialog.myFunction(new TipsDialog.DialogCallBack() {
                    @Override
                    public void setContent(TextView v) {
                        v.setText("是否拨打电话: " + getTemContent[5]);
                    }

                    @Override
                    public void setConfirmOnClickListener(Button btn) {
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getTemContent[5]));
                                startActivity(intent);
                                dialog.dissMiss();
                            }
                        });
                    }
                });
                break;
        }
    }

    /**
     * 审核通过
     */
    private void agreeCourse(String id, String result, String refuse) {
        String reqUrl = Apis.GetTmpAuth(id, result, refuse);
        ToastUtils.getInstance().showToast(reqUrl);
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
                        MySnackbar.getmInstance().showMessage(ok, jsonObject.getString("msg"));
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
