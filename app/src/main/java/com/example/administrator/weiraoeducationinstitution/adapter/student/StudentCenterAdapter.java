package com.example.administrator.weiraoeducationinstitution.adapter.student;

import android.content.Context;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.StudentGroupBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/13.
 */
public class StudentCenterAdapter extends SolidRVBaseAdapter<StudentGroupBean> {

    public StudentCenterAdapter(Context context, List<StudentGroupBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_student_center;
    }

    @Override
    protected void onItemClick(int position) {
//        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final StudentGroupBean bean) {
        holder.setText(R.id.item_student_center_groupName, bean.getName());
        holder.setText(R.id.item_student_center_subjectNumber, bean.getSubjectNum() + "个科目");
        holder.setText(R.id.item_student_center_manager, bean.getAdmin_name());
        holder.setText(R.id.item_student_center_content, bean.getBrief());
        if (!bean.getImg().equals("")) {
            holder.setImageFromInternet(R.id.item_student_center_groupimg, bean.getImg());
        }

    }
}
