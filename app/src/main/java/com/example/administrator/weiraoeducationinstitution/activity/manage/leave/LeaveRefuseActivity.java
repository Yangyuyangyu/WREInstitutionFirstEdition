package com.example.administrator.weiraoeducationinstitution.activity.manage.leave;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.MySnackbar;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LeaveRefuseActivity extends BaseActivity {
    @Bind(R.id.include_setting_introduction_back)
    ImageView back;
    @Bind(R.id.include_setting_introduction_title)
    TextView title;
    @Bind(R.id.include_setting_introduction_ok)
    TextView ok;

    private String the_leave_id = null, the_temporary_id = null, the_having_id = null;
    private EditText content;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_leave_refuse;
    }

    @Override
    protected void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            the_leave_id = bundle.getString("the_leave_id");
            the_temporary_id = bundle.getString("the_temporary_id");
            the_having_id = bundle.getString("the_havingclass_id");
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        title.setText("请假审批");
        ok.setText("确认");
        content = customFindViewById(R.id.refuse_content);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agreeLeave(the_leave_id, "2", content.getText().toString());
                agreeTemporary(the_temporary_id, "2", content.getText().toString());
                audit(the_having_id, "2", content.getText().toString());
            }
        });
    }

    private void agreeLeave(String id, String result, String refuse) {
        if (the_leave_id == null) {
            return;
        }
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

    private void agreeTemporary(String id, String result, String refuse) {
        if (the_temporary_id == null) {
            return;
        }
        String reqUrl = Apis.GetTmpAuth(id, result, refuse);
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

    /**
     * 审核是否通过
     */
    private void audit(String id, String result, String reason) {
        if (the_having_id == null) {
            return;
        }
        String reqUrl = Apis.GetReportAuth(id, result, reason);
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
                ToastUtils.getInstance().showToast(e.getMessage());
            }
        });
    }
}
