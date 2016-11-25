package com.example.administrator.weiraoeducationinstitution.adapter.manage;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.manage.telephone.Telephone_One;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AssociationBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class Manage_TelePhoneInterViewAdapter extends SolidRVBaseAdapter<AssociationBean> {
    public Manage_TelePhoneInterViewAdapter(Context context, List<AssociationBean> beans) {
        super(context, beans);
    }


    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_manage_trainging;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, Telephone_One.class);
        intent.putExtra("the_association_id", mBeans.get(position - 1).getId());
        mContext.startActivity(intent);


    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, AssociationBean bean) {
        holder.setText(R.id.item_manage_training_name, bean.getName());

    }

}
