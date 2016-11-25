package com.example.administrator.weiraoeducationinstitution.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/6.
 */
public class FinishUitl {
    //登录成功后结束activity
    public static List<Activity> activityList = new ArrayList<Activity>();

    public static void clearActivity() {
        for (int i = 0; i < activityList.size(); i++) {
            if (activityList.get(i) != null) {
                activityList.get(i).finish();
            }
        }
    }

}
