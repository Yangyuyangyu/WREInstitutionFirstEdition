package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.CourseEvalueteBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public class CourseEvalueteAdapter extends SolidRVBaseAdapter<CourseEvalueteBean> {
    public CourseEvalueteAdapter(Context context, List<CourseEvalueteBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_course_evaluate;
    }

    @Override
    protected void onItemClick(int position) {
//        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, CourseEvalueteBean bean) {
        holder.setText(R.id.item_course_evaluete_content, bean.getContent());
        holder.setText(R.id.item_course_evaluete_name, bean.getUser_name());
        holder.setText(R.id.item_course_evaluete_time, bean.getTime());
        if (!bean.getUser_img().equals("")) {
            holder.setImageFromInternet(R.id.item_course_evaluete_headimg, bean.getUser_img());
        }

    }
}
