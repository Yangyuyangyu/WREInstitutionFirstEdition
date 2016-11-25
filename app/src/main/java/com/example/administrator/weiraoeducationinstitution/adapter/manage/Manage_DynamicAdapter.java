package com.example.administrator.weiraoeducationinstitution.adapter.manage;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.PhotoActivity;
import com.example.administrator.weiraoeducationinstitution.activity.manage.dynamic.DynamicDetail;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.manage.Manage_DynamicBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/29.
 */
public class Manage_DynamicAdapter extends SolidRVBaseAdapter<Manage_DynamicBean> {
    public Manage_DynamicAdapter(Context context, List<Manage_DynamicBean> beans) {
        super(context, beans);
    }


    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_manage_dynamic;
    }

    @Override
    protected void onItemClick(int position) {
        String[] str = new String[]{mBeans.get(position - 1).getTitle(), mBeans.get(position - 1).getId()};
        Intent intent = new Intent(mContext, DynamicDetail.class);
        intent.putExtra("the_dynamic_id", str);
        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, Manage_DynamicBean bean) {
        holder.setText(R.id.item_manage_dynamic_content, bean.getBrief());
        holder.setText(R.id.item_manage_dynamic_time, bean.getTime());
        holder.setText(R.id.item_manage_dynamic_title, bean.getTitle());
        if (!bean.getImg().equals("")) {
            holder.setImageFromInternet(R.id.item_manage_dynamic_img, bean.getImg());
        }


    }
}
