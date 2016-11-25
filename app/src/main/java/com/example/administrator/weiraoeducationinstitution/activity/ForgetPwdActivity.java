package com.example.administrator.weiraoeducationinstitution.activity;


import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.MySnackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ForgetPwdActivity";
    private EditText forget_username, forget_code, forget_password;
    private Button send_code, ok;
    private CheckBox pwd_cb;

    private String long_id;//短信记录id

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
    }

    @Override
    protected void initView() {
        forget_username = customFindViewById(R.id.forgetPwd_username);
        forget_code = customFindViewById(R.id.forgetPwd_usercode);
        forget_password = customFindViewById(R.id.forgetPwd_userpassword);
        send_code = customFindViewById(R.id.forgetPwd_btn_code);
        ok = customFindViewById(R.id.forgetPwd_btn);
        pwd_cb = customFindViewById(R.id.forgetPwd_cb);
        pwd_cb.setOnClickListener(this);
        ok.setOnClickListener(this);
        send_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgetPwd_cb:
                if (pwd_cb.isChecked()) {
                    //设置EditText的密码为可见的
                    forget_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置密码为隐藏的
                    forget_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.forgetPwd_btn_code:
                if (!TextUtils.isEmpty(forget_username.getText())) {
                    sendMobileCode(forget_username.getText().toString());
                } else {
                    MySnackbar.getmInstance().showMessage(customFindViewById(R.id.forgetPwd_btn), "用户名不能为空");
                }
                break;

            case R.id.forgetPwd_btn:
                if (!TextUtils.isEmpty(forget_username.getText()) && !TextUtils.isEmpty(forget_password.getText())) {
                    forgetPwd(forget_username.getText().toString(), forget_code.getText().toString(), forget_password.getText().toString(), long_id);
                } else if (!TextUtils.isEmpty(forget_code.getText())) {
                    MySnackbar.getmInstance().showMessage(customFindViewById(R.id.forgetPwd_btn), "验证码不能为空");
                } else {
                    MySnackbar.getmInstance().showMessage(customFindViewById(R.id.forgetPwd_btn), "用户名或新密码不能为空");
                }

                break;
        }
    }

    private void sendMobileCode(String mobile) {
        String reqUrl = Apis.GetSmsCode(mobile);
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("code").equals("0")) {
                        long_id = jsonObject.getString("log_id");
                        MySnackbar.getmInstance().showMessage(customFindViewById(R.id.forgetPwd_btn), jsonObject.getString("msg"));
                        send_code.setEnabled(false);
                        send_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_rounded_gray_frame));
                        send_code.setTextColor(Color.WHITE);
                        timer.start();
                    } else {
                        MySnackbar.getmInstance().showMessage(customFindViewById(R.id.forgetPwd_btn), jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "onError:" + e);
            }
        });

    }

    private void forgetPwd(String mobile, String code, String pass, String log_id) {
        String reqUrl = Apis.GetEditPwd(mobile, code, pass, log_id);
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("code").equals("0")) {
                        startActivityWithoutExtras(Login_loginActivity.class);
                        MySnackbar.getmInstance().showMessage(customFindViewById(R.id.forgetPwd_btn), "设置新密码成功");
                    } else {
                        MySnackbar.getmInstance().showMessage(customFindViewById(R.id.forgetPwd_btn), jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "onError:" + e);
            }
        });
    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            send_code.setText((millisUntilFinished / 1000) + "秒后重新获取");
        }

        @Override
        public void onFinish() {
            send_code.setEnabled(true);
            send_code.setText("重新获取验证码");
            send_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_rounded_red_frame));
            send_code.setTextColor(getResources().getColor(R.color.weirao_title_color));
        }
    };

    @Override
    protected void onDestroy() {
        timer.cancel();
        timer.onFinish();
        super.onDestroy();
    }
}
