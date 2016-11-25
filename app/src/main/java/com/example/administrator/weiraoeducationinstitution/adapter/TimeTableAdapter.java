package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.CourseDetailActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.TimeTableBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/28.
 */
public class TimeTableAdapter extends SolidRVBaseAdapter<TimeTableBean> {
    public TimeTableAdapter(Context context, List<TimeTableBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_time_table;
    }

    @Override
    protected void onItemClick(int position) {
        String[] str = new String[]{mBeans.get(position - 1).getCid(), mBeans.get(position - 1).getId()};
        Intent intent = new Intent(mContext, CourseDetailActivity.class);
        intent.putExtra("theCourseIdArray", str);
        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, TimeTableBean bean) {
        holder.setText(R.id.time_table_title, bean.getName());
        holder.setText(R.id.time_table_content, bean.getBrief());
        holder.setText(R.id.time_table_teacher, "老师:" + bean.getTeacher_name());
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
