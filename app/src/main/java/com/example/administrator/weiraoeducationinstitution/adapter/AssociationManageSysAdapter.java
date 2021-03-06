package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.manage.dynamic.DynamicDetail;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AssociationBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/11.社团管理制度
 */
public class AssociationManageSysAdapter extends SolidRVBaseAdapter<AssociationBean> {
    public AssociationManageSysAdapter(Context context, List<AssociationBean> beans) {
        super(context, beans);
    }


    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_manage_trainging;
    }

    @Override
    protected void onItemClick(int position) {
        String[] str=new String[]{mBeans.get(position - 1).getName(),mBeans.get(position - 1).getId()};
        Intent intent = new Intent(mContext, DynamicDetail.class);
        intent.putExtra("the_association_manage_id", str);
        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, AssociationBean bean) {
        holder.setText(R.id.item_manage_training_name, bean.getName() + "管理制度");
    }
}
