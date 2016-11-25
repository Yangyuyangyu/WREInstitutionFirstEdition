package com.example.administrator.weiraoeducationinstitution.adapter.manage;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.manage.grade.GradeTwoActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AssociationBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class Manage_GradeProjectAdapter extends SolidRVBaseAdapter<AssociationBean> {
    public Manage_GradeProjectAdapter(Context context, List<AssociationBean> beans) {
        super(context, beans);
    }


    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_manage_gradeproject;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, GradeTwoActivity.class);
        intent.putExtra("the_grade_association_id", mBeans.get(position - 1).getId());
        mContext.startActivity(intent);

    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, AssociationBean bean) {
        holder.setText(R.id.item_manage_grade_name, bean.getName());
//        if (bean.getIsSetting().equals("0")) {
//            holder.setText(R.id.item_manage_grade_isSetting, "未设置");
//            holder.setTextColor(R.id.item_manage_grade_isSetting, Color.RED);
//        } else if (bean.getIsSetting().equals("1")) {
//            holder.setText(R.id.item_manage_grade_isSetting, "已设置");
//        }
    }
}
