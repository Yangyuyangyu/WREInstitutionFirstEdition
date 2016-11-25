package com.example.administrator.weiraoeducationinstitution.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by _SOLID
 * Date:2016/4/20
 * Time:16:44
 */
public class ToastUtils {

    private static Context mContext;
    private static ToastUtils mInstance;
    private Toast mToast;

    public static ToastUtils getInstance() {
        return mInstance;
    }

    public static void init(Context ctx) {
        mInstance = new ToastUtils(ctx);
    }

    private ToastUtils(Context ctx) {
        mContext = ctx;
    }

    public void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }
    public void showToastCenter(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_LONG);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
