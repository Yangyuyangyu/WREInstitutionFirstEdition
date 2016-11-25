package com.example.administrator.weiraoeducationinstitution.adapter.manage;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.manage.other_course.OtherCourse_DetailActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.manage.Manage_OtherBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class Manage_OtherCourseAdapter extends SolidRVBaseAdapter<Manage_OtherBean> {
    public Manage_OtherCourseAdapter(Context context, List<Manage_OtherBean> beans) {
        super(context, beans);
    }


    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_manage_other_course;
    }

    @Override
    protected void onItemClick(int position) {
//        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final Manage_OtherBean bean) {
        holder.setText(R.id.item_manage_othercourse_name, bean.getName());
        holder.setText(R.id.item_manage_othercourse_time, bean.getClass_time());
        holder.setText(R.id.item_manage_othercourse_teacher, bean.getTeacher());
        holder.setOnItemClickListener(R.id.item_manage_othercourse_checkmore, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] content = new String[]{bean.getName(), bean.getClass_time(), bean.getTeacher(), bean.getAdvice(), bean.getExecution()};
                Intent intent = new Intent(mContext, OtherCourse_DetailActivity.class);
                intent.putExtra("the_other_course_condition", content);
                mContext.startActivity(intent);
            }
        });
    }
}
