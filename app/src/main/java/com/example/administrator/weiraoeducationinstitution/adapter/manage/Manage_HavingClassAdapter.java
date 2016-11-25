package com.example.administrator.weiraoeducationinstitution.adapter.manage;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.manage.havingclass.HavingClassOneActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.manage.havingclass.reportGroupEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class Manage_HavingClassAdapter extends SolidRVBaseAdapter<reportGroupEntity> {
    public Manage_HavingClassAdapter(Context context, List<reportGroupEntity> beans) {
        super(context, beans);
    }


    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_manage_having_class;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, HavingClassOneActivity.class);
        intent.putExtra("the_association_id", mBeans.get(position - 1).getId());
        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, reportGroupEntity bean) {
        holder.setText(R.id.item_manage_having_class_name, bean.getName());
        if (bean.getReportNum().equals("") || bean.getReportNum().equals("0")) {
            holder.setVisible(R.id.item_manage_having_class_number, false);
        } else {
            holder.setText(R.id.item_manage_having_class_number, bean.getReportNum());
        }

    }
}
