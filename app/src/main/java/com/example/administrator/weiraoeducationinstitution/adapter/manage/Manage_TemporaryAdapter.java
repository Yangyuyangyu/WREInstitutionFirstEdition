package com.example.administrator.weiraoeducationinstitution.adapter.manage;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.temporary.TemporaryDetailActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.manage.Manage_TemporaryBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/29.
 */
public class Manage_TemporaryAdapter extends SolidRVBaseAdapter<Manage_TemporaryBean> {
    public Manage_TemporaryAdapter(Context context, List<Manage_TemporaryBean> beans) {
        super(context, beans);
    }


    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_manage_temporary_account;
    }

    @Override
    protected void onItemClick(int position) {
        String[] content = new String[]{mBeans.get(position - 1).getId(), mBeans.get(position - 1).getName(), mBeans.get(position - 1).getCourse(), mBeans.get(position - 1).getReason(),
                mBeans.get(position - 1).getRemark(),
                mBeans.get(position - 1).getPhone(),mBeans.get(position-1).getAccount(),mBeans.get(position-1).getClass_time(),mBeans.get(position-1).getStatus()};
        Intent intent = new Intent(mContext, TemporaryDetailActivity.class);
        intent.putExtra("the_temporary_content", content);
        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, Manage_TemporaryBean bean) {
        holder.setText(R.id.item_manage_temporary_content, bean.getRemark());
        holder.setText(R.id.item_manage_temporary_name, bean.getName());
        holder.setText(R.id.item_manage_temporary_reason, bean.getReason());
        if (!bean.getHead().equals("")) {
            holder.setImageFromInternet(R.id.item_manage_temporary_headimg, bean.getHead());
        }
    }
}
