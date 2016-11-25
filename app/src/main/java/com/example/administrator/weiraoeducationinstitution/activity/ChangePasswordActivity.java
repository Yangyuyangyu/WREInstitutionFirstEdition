package com.example.administrator.weiraoeducationinstitution.activity;


import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;

    private CheckBox old_cb, new_cb;
    private EditText newPwd, oldPwd;
    private Button ok;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        weiraoToolbarTop.setTitle("修改密码");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        newPwd = customFindViewById(R.id.change_pwd_new_edit);
        oldPwd = customFindViewById(R.id.change_pwd_old_edit);
        old_cb = customFindViewById(R.id.change_pwd_old_cb);
        new_cb = customFindViewById(R.id.change_pwd_new_cb);
        ok = customFindViewById(R.id.changePwd_btn);
        old_cb.setOnClickListener(this);
        new_cb.setOnClickListener(this);
        ok.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_pwd_old_cb:
                if (old_cb.isChecked()) {
                    //设置EditText的密码为可见的
                    oldPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置密码为隐藏的
                    oldPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.change_pwd_new_cb:
                if (new_cb.isChecked()) {
                    //设置EditText的密码为可见的
                    newPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置密码为隐藏的
                    newPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.changePwd_btn:
                if (!TextUtil.isEmpty(oldPwd.getText().toString()) && !TextUtil.isEmpty(newPwd.getText().toString())) {
                    changePwd(BaseApplication.user_info.getId(), oldPwd.getText().toString(), newPwd.getText().toString());
                } else {
                    MySnackbar.getmInstance().showMessage(ok, "密码不能为空");
                }
                break;
        }
    }

    private void changePwd(String id, String oldPass, String newPass) {
        String reqUrl = Apis.GetEditPass(id, oldPass, newPass);
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("code").equals("0")) {
                        MySnackbar.getmInstance().showMessage(ok, jsonObject.getString("msg"));
                        //结束activity，返回登录界面
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
