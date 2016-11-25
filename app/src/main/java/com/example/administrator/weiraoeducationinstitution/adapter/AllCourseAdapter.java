package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.CourseDetailActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AllCourseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/13.
 */
public class AllCourseAdapter extends SolidRVBaseAdapter<AllCourseBean> {
    public AllCourseAdapter(Context context, List<AllCourseBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_time_table;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, CourseDetailActivity.class);
        intent.putExtra("theCourseId", mBeans.get(position - 1).getId());
        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, AllCourseBean bean) {
        holder.setText(R.id.time_table_title, bean.getName());
        holder.setText(R.id.time_table_content, bean.getBrief());
        holder.setText(R.id.time_table_teacher, "老师:" + bean.getTeacher());
        if (bean.getClass_time().trim().length() > 15) {
            String time = bean.getClass_time();
            String result = time.substring(time.length() - 20, time.length());
            holder.setText(R.id.time_table_time, result);
        } else {
            holder.setText(R.id.time_table_time, bean.getClass_time());
        }

        if (bean.getType().equals("1")) {
            holder.setText(R.id.time_table_teach_type, "【一对多】");
        } else if (bean.getType().equals("2")) {
            holder.setText(R.id.time_table_teach_type, "【一对一】");
        }
        holder.setImageFromInternet(R.id.time_table_headimg, bean.getImg());
    }
}
