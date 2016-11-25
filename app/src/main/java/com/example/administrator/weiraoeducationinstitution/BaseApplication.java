package com.example.administrator.weiraoeducationinstitution;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.weiraoeducationinstitution.bean.CustomerInfo;
import com.example.administrator.weiraoeducationinstitution.utils.MySnackbar;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;

import ren.solid.skinloader.base.SkinBaseApplication;

/**
 * Created by Administrator on 2016/4/26.
 */
public class BaseApplication extends SkinBaseApplication {
    private static BaseApplication mInstance;
    public static SharedPreferences preferences;
    public static CustomerInfo user_info;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ToastUtils.init(this);
        MySnackbar.init(this);
        preferences = getSharedPreferences("weirao_userinfo", Context.MODE_PRIVATE);
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }
}