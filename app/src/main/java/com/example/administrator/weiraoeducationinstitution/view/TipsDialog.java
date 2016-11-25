package com.example.administrator.weiraoeducationinstitution.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;

/**
 * Created by Administrator on 2016/5/6.
 */
public class TipsDialog {
    private Activity m_activity;
    private View CustomView;
    private AlertDialog dialog;

    public TipsDialog(Activity m_activity) {
        this.m_activity = m_activity;
    }

    public void myFunction(final DialogCallBack cb) {
        AlertDialog.Builder builder = myBuilder();
        dialog = builder.show();
        DisplayMetrics dm = new DisplayMetrics();
        m_activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int mScreenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
        // int mScreenHeight = dm.heightPixels;
        dialog.getWindow().setContentView(CustomView);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = mScreenWidth / 3 * 2;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.setCanceledOnTouchOutside(false);
        // 确认
        final Button confirm = (Button) CustomView
                .findViewById(R.id.tips_dialog_confirm);
        TextView content = (TextView) CustomView
                .findViewById(R.id.tips_dialog_content);
        cb.setContent(content);
        cb.setConfirmOnClickListener(confirm);
        // 取消
        Button cancel = (Button) CustomView
                .findViewById(R.id.tips_dialog_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    protected AlertDialog.Builder myBuilder() {
        final LayoutInflater inflater = m_activity.getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(m_activity);
        CustomView = inflater
                .inflate(R.layout.tips_dialog, null);
        builder.create();
        return builder;
    }

    public interface DialogCallBack {
        public abstract void setContent(TextView v);

        public abstract void setConfirmOnClickListener(Button btn);
    }

    public void dissMiss() {
        dialog.dismiss();
    }
}
