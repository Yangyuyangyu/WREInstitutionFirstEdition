package com.example.administrator.weiraoeducationinstitution.activity;


import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.MainActivity;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.bean.CustomerInfo;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.CrazyClickUtils;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.MySnackbar;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.example.administrator.weiraoeducationinstitution.utils.UserInfoSharePreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import timeselector.Utils.TextUtil;

public class Login_loginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "Login_loginActivity";
    private Button ok, loginWeb;
    private EditText login_username, login_pwd;
    private TextView forgetPwd, register;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_login_login;
    }

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
    }

    @Override
    protected void initView() {
        ok = customFindViewById(R.id.login_login_btn);
        login_username = customFindViewById(R.id.login_username);
        login_pwd = customFindViewById(R.id.login_userpassword);
        loginWeb = customFindViewById(R.id.login_webBtn);
        String name = UserInfoSharePreference.getInstance().getCUSTOMERNAME();
        if (name.trim().length() != 0) {
            login_username.setText(name);
        }
        register = customFindViewById(R.id.login_register_btn);
        forgetPwd = customFindViewById(R.id.login_forgetPwd);
        register.setOnClickListener(this);
        forgetPwd.setOnClickListener(this);
        ok.setOnClickListener(this);
        loginWeb.setOnClickListener(this);
        login_pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE) {
                    login(login_username.getText().toString(), login_pwd.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login_btn:
                if (!TextUtil.isEmpty(login_username.getText().toString()) && !TextUtil.isEmpty(login_pwd.getText().toString())) {
                    login(login_username.getText().toString(), login_pwd.getText().toString());
                } else {
                    MySnackbar.getmInstance().showMessage(ok, "用户名或密码不能为空");
                }
                break;
            case R.id.login_register_btn:
                startActivityWithoutExtras(RegisterActivity.class);
                break;
            case R.id.login_forgetPwd:
                startActivityWithoutExtras(ForgetPwdActivity.class);
                break;
            case R.id.login_webBtn:
                //网页登录

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse(Apis.WeiRao_WebHttp);
                intent.setData(content_url);
                startActivity(Intent.createChooser(intent, "请选择浏览器"));
                break;
        }
    }

    private void login(String mobile, String pass) {
        String reqUrl = Apis.GetLogin(mobile, pass);
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(final String resultHttp) {
                try {
                    JSONObject jsonObject = new JSONObject(resultHttp);
                    if (jsonObject.getString("code").equals("0")) {
                        Gson gson = new Gson();
                        CustomerInfo user = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<CustomerInfo>() {
                                }.getType());
                        BaseApplication.user_info = user;
                        UserInfoSharePreference.getInstance().setCUSTOMERID(user.getId());
                        UserInfoSharePreference.getInstance().setCUSTOMERNAME(login_username.getText().toString());
                        startActivityWithoutExtras(MainActivity.class);
                    } else {
                        ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
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
}
