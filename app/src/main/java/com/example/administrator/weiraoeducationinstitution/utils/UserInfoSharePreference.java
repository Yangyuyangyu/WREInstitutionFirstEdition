package com.example.administrator.weiraoeducationinstitution.utils;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;

/**
 * Created by Administrator on 2016/5/12.
 */
public class UserInfoSharePreference {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static final UserInfoSharePreference userInforSp = new UserInfoSharePreference();

    public static final String CUSTOMERNAME = "customername";
    public static final String CUSTOMERID = "CustomerID";

    public static UserInfoSharePreference getInstance() {
        return userInforSp;
    }

    @SuppressLint("CommitPrefEdits")
    private UserInfoSharePreference() {
        sp = BaseApplication.preferences;
        editor = sp.edit();
    }


    public String getCUSTOMERNAME() {
        return sp.getString(CUSTOMERNAME, "");
    }

    public void setCUSTOMERNAME(String username) {
        editor.putString(CUSTOMERNAME, username);
        editor.commit();
    }


    public String getCUSTOMERID() {
        return sp.getString(CUSTOMERID, "");
    }

    public void setCUSTOMERID(String customerid) {
        editor.putString(CUSTOMERID, customerid);
        editor.commit();
    }
}
