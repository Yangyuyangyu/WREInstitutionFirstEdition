package com.example.administrator.weiraoeducationinstitution.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;

/**
 * Created by Administrator on 2016/4/28.
 */
public class PopView_AllTeacherOrCourse extends PopupWindow {

    private View mainView;
    private TextView had, add;

    public PopView_AllTeacherOrCourse(Activity paramActivity, View.OnClickListener paramOnClickListener, int paramInt1, int paramInt2) {
        super(paramActivity);
        //窗口布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.pop_all_teacher_course, null);
        //活动
        had = ((TextView) mainView.findViewById(R.id.pop_allTeacherOrCourse_had));
        //机构
        add = (TextView) mainView.findViewById(R.id.pop_allTeacherOrCourse_add);
        //设置每个子布局的事件监听器
        if (paramOnClickListener != null) {
            had.setOnClickListener(paramOnClickListener);
            add.setOnClickListener(paramOnClickListener);
        }
        setContentView(mainView);
        //设置宽度
        setWidth(paramInt1);
        //设置高度
        setHeight(paramInt2);
        //设置显示隐藏动画
        setAnimationStyle(R.style.pop_anim);
        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0));
    }
}
