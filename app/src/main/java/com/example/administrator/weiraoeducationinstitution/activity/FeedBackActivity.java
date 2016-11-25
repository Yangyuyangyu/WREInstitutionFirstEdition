package com.example.administrator.weiraoeducationinstitution.activity;


import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

public class FeedBackActivity extends BaseActivity {

    @Bind(R.id.include_setting_introduction_back)
    ImageView includeSettingIntroductionBack;
    @Bind(R.id.include_setting_introduction_title)
    TextView includeSettingIntroductionTitle;
    @Bind(R.id.include_setting_introduction_ok)
    TextView includeSettingIntroductionOk;

    private EditText content, email;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void init() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        FinishUitl.activityList.add(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        content = customFindViewById(R.id.feedback_conatent);
        email = customFindViewById(R.id.feedback_email);
        includeSettingIntroductionTitle.setText("意见反馈");
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
                    if (TextUtil.isEmpty(email.getText().toString())) {
                        feed(BaseApplication.user_info.getId(), content.getText().toString(), null);
                    } else {
                        feed(BaseApplication.user_info.getId(), content.getText().toString(), email.getText().toString());
                    }
                } else {
                    MySnackbar.getmInstance().showMessage(customFindViewById(R.id.feedback_conatent), "请输入反馈内容");
                }

            }
        });

    }


    private void feed(String id, String content, String email) {
        String reqUrl = Apis.GetFeedback(id, content, email);
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
                        MySnackbar.getmInstance().showMessage(customFindViewById(R.id.feedback_conatent), jsonObject.getString("msg"));
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
