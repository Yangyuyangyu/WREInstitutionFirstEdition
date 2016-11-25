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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.MySnackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    private EditText forget_username, forget_code, forget_password, organizationName, user_organization, pwd_comefirm;
    private Button send_code, ok;
    private TextView organization, school;
    private ImageView triangle_organization, triangle_school;
    private RelativeLayout organizationRe, schoolRe;
    private CheckBox pwd_cb, pwd_cb_comefirm;
    private int type = 1;//注册账号类型，默认为机构 1
    private String long_id;//短信记录id

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
    }

    @Override
    protected void initView() {
        forget_username = customFindViewById(R.id.register_username);
        forget_code = customFindViewById(R.id.register_usercode);
        forget_password = customFindViewById(R.id.register_userpassword);
        send_code = customFindViewById(R.id.register_btn_code);
        ok = customFindViewById(R.id.register_btn);
        organization = customFindViewById(R.id.register_organization);
        school = customFindViewById(R.id.register_school);
        organizationRe = customFindViewById(R.id.register_organizationRe);
        schoolRe = customFindViewById(R.id.register_schoolRe);
        triangle_organization = customFindViewById(R.id.register_triangle1);
        triangle_school = customFindViewById(R.id.register_triangle2);
        pwd_cb = customFindViewById(R.id.register_pwd_cb);
        pwd_cb_comefirm = customFindViewById(R.id.register_pwd_cb_comefirm);
        organizationName = customFindViewById(R.id.register_organizationName);
        user_organization = customFindViewById(R.id.register_user_organization);
        pwd_comefirm = customFindViewById(R.id.register_userpassword_comefirm);
        organizationRe.setOnClickListener(this);
        schoolRe.setOnClickListener(this);
        pwd_cb.setOnClickListener(this);
        pwd_cb_comefirm.setOnClickListener(this);
        ok.setOnClickListener(this);
        send_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_organizationRe:
                organization.setTextColor(getResources().getColor(R.color.weirao_title_color));
                school.setTextColor(getResources().getColor(R.color.weirao_text_normal_color));
                triangle_organization.setVisibility(View.VISIBLE);
                triangle_school.setVisibility(View.INVISIBLE);
                type = 1;
                break;
            case R.id.register_schoolRe:
                school.setTextColor(getResources().getColor(R.color.weirao_title_color));
                organization.setTextColor(getResources().getColor(R.color.weirao_text_normal_color));
                triangle_school.setVisibility(View.VISIBLE);
                triangle_organization.setVisibility(View.INVISIBLE);
                type = 2;
                break;
            case R.id.register_pwd_cb:
                if (pwd_cb.isChecked()) {
                    //设置EditText的密码为可见的
                    forget_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置密码为隐藏的
                    forget_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.register_pwd_cb_comefirm:
                if (pwd_cb_comefirm.isChecked()) {
                    //设置EditText的密码为可见的
                    pwd_comefirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置密码为隐藏的
                    pwd_comefirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.register_btn_code:
                if (!TextUtils.isEmpty(forget_username.getText())) {
                    sendMobileCode(forget_username.getText().toString());
                } else {
                    MySnackbar.getmInstance().showMessage(customFindViewById(R.id.register_btn), "信息未完善");
                }
                break;

            case R.id.register_btn:
                if (TextUtils.isEmpty(user_organization.getText())) {
                    MySnackbar.getmInstance().showMessage(customFindViewById(R.id.register_btn), "注册账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(organizationName.getText())) {
                    MySnackbar.getmInstance().showMessage(customFindViewById(R.id.register_btn), "注册名称不能为空");
                    return;
                }
                if (!pwd_comefirm.getText().toString().equals(forget_password.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(customFindViewById(R.id.register_btn), "两次密码不一致");
                    return;
                }
                if (!TextUtils.isEmpty(forget_username.getText()) && !TextUtils.isEmpty(forget_password.getText())) {
                    reGister(user_organization.getText().toString(), organizationName.getText().toString(), forget_username.getText().toString(), forget_code.getText().toString(), forget_password.getText().toString(), long_id);
                } else if (!TextUtils.isEmpty(forget_code.getText())) {
                    MySnackbar.getmInstance().showMessage(customFindViewById(R.id.register_btn), "验证码不能为空");
                } else {
                    MySnackbar.getmInstance().showMessage(customFindViewById(R.id.register_btn), "用户名或密码不能为空");
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
                        MySnackbar.getmInstance().showMessage(customFindViewById(R.id.register_btn), jsonObject.getString("msg"));
                        send_code.setEnabled(false);
                        send_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_rounded_gray_frame));
                        send_code.setTextColor(Color.WHITE);
                        timer.start();
                    } else {
                        MySnackbar.getmInstance().showMessage(customFindViewById(R.id.register_btn), jsonObject.getString("msg"));
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

    private void reGister(String account, String name, String mobile, String code, String pass, String log_id) {
        String reqUrl = Apis.GetRegister(account, name, mobile, code, pass, log_id);
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
                        MySnackbar.getmInstance().showMessage(customFindViewById(R.id.register_btn), "注册成功");
                    } else {
                        MySnackbar.getmInstance().showMessage(customFindViewById(R.id.register_btn), jsonObject.getString("msg"));
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
