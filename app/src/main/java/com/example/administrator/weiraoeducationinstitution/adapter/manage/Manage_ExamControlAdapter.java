package com.example.administrator.weiraoeducationinstitution.adapter.manage;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.exam_manage.EnterExamResultActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.Manage_ExamBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class Manage_ExamControlAdapter extends SolidRVBaseAdapter<Manage_ExamBean> {
    public Manage_ExamControlAdapter(Context context, List<Manage_ExamBean> beans) {
        super(context, beans);
    }


    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_manage_exam_control;
    }

    @Override
    protected void onItemClick(int position) {

    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final Manage_ExamBean bean) {
        holder.setText(R.id.item_manage_exam_control_coursename, "考试课程: " + bean.getCourse());
        holder.setText(R.id.item_manage_exam_control_coursetime, "考试时间: " + bean.getTime());

        holder.setOnItemClickListener(R.id.item_manage_exam_control_inputResult, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EnterExamResultActivity.class);
                intent.putExtra("the_exam_course_id", bean.getId());
                mContext.startActivity(intent);
            }
        });
    }
}
