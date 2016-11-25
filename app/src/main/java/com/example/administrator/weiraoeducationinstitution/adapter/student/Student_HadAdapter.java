package com.example.administrator.weiraoeducationinstitution.adapter.student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.student.Student_Select_AddActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AllTeacherBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/27.
 */
public class Student_HadAdapter extends SolidRVBaseAdapter<AllTeacherBean> {
    private teacherHadOnclick mListener;

    public Student_HadAdapter(Context context, List<AllTeacherBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_teacher_had;
    }

    @Override
    protected void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("the_sutdent_id", mBeans.get(position - 1).getId());
        Intent intent = new Intent(mContext, Student_Select_AddActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);

    }

    public String getItemTeacherId(int position) {
        return mBeans.get(position).getId();
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final AllTeacherBean bean) {
        holder.setText(R.id.item_teacher_had_name, bean.getName());
        if (!bean.getHead().equals("")) {
            holder.setImageFromInternet(R.id.item_teacher_had_head, bean.getHead());
        }
        holder.setOnItemClickListener(R.id.item_teacher_had_telephone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.teacherHadItemClick(bean.getPhone());
            }
        });
    }

    public void setteacherHadOnclick(teacherHadOnclick listener) {
        this.mListener = listener;
    }

    public interface teacherHadOnclick {
        void teacherHadItemClick(String phone);
    }
}
