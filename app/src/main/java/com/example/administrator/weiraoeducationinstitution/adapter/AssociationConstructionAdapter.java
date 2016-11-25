package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AssociationConstructionBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/29.
 */
public class AssociationConstructionAdapter extends SolidRVBaseAdapter<AssociationConstructionBean> {
    public AssociationConstructionAdapter(Context context, List<AssociationConstructionBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_association_construction;
    }

    @Override
    protected void onItemClick(int position) {
//        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, AssociationConstructionBean bean) {
        holder.setText(R.id.activity_association_manager, "管理老师:" + bean.getAdmins());
        holder.setText(R.id.activity_association_name, bean.getName());
        holder.setText(R.id.activity_association_number, bean.getStudentNum() + "学生");
        holder.setText(R.id.activity_association_time, bean.getCtime());
    }
}
