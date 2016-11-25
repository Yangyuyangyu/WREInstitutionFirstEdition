package com.example.administrator.weiraoeducationinstitution.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;

/**
 * Created by Administrator on 2016/5/4.
 */
public class PopView_Ranking extends PopupWindow {
    private View mainView;
    private TextView day_text, exam_text;
    private ImageView day_img, exam_img;
    private LinearLayout dayLL, examLL;

    public PopView_Ranking(Activity paramActivity, View.OnClickListener paramOnClickListener, int paramInt1, int paramInt2) {
        super(paramActivity);
        //窗口布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.pop_ranking, null);
        day_text = ((TextView) mainView.findViewById(R.id.pop_ranking_day_text));
        exam_text = (TextView) mainView.findViewById(R.id.pop_ranking_exam_text);
        day_img = ((ImageView) mainView.findViewById(R.id.pop_ranking_day_img));
        exam_img = ((ImageView) mainView.findViewById(R.id.pop_ranking_exam_img));
        dayLL = (LinearLayout) mainView.findViewById(R.id.pop_ranking_dayLL);
        examLL = (LinearLayout) mainView.findViewById(R.id.pop_ranking_examLL);
        if (paramOnClickListener != null) {
            dayLL.setOnClickListener(paramOnClickListener);
            examLL.setOnClickListener(paramOnClickListener);
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

    public void setPopRankingView(int i) {
        switch (i) {
            case 0:
                break;
            case 1:
                day_text.setTextColor(Color.rgb(255, 104, 100));
                exam_text.setTextColor(Color.rgb(51, 51, 51));
                day_img.setVisibility(View.VISIBLE);
                exam_img.setVisibility(View.INVISIBLE);
                break;
            case 2:
                exam_text.setTextColor(Color.rgb(255, 104, 100));
                day_text.setTextColor(Color.rgb(51, 51, 51));
                exam_img.setVisibility(View.VISIBLE);
                day_img.setVisibility(View.INVISIBLE);
                break;

        }
    }
}
