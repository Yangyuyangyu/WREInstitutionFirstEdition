package com.example.administrator.weiraoeducationinstitution.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;

/**
 * Created by Administrator on 2016/5/12.
 */
public class MySnackbar {
    private static Context mContext;
    private static MySnackbar mInstance;
    private Snackbar sBar;

    public static void init(Context ctx) {
        mInstance = new MySnackbar(ctx);
    }

    private MySnackbar(Context ctx) {
        mContext = ctx;
    }

    public static MySnackbar getmInstance() {
        return mInstance;
    }

    public void showMessage(View v, String message) {
        sBar = Snackbar.make(v, message, Snackbar.LENGTH_SHORT);
        Snackbar.SnackbarLayout ve = (Snackbar.SnackbarLayout) sBar.getView();
        ((TextView) ve.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FFFFFF"));
        sBar.show();
    }


    public void cancelMySnackbar() {
        if (sBar != null) {
            sBar.dismiss();
        }
    }

}
