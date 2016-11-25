package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.SubjectBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/29.
 */
public class SubjectAdapter extends SolidRVBaseAdapter<SubjectBean> {
    public SubjectAdapter(Context context, List<SubjectBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_subject;
    }

    @Override
    protected void onItemClick(int position) {
//        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, SubjectBean bean) {
        holder.setText(R.id.subject_club, "社团:" + bean.getGroup());
        holder.setText(R.id.subject_content, bean.getBrief());
        holder.setText(R.id.subject_manager, "管理老师:" + bean.getAdmin());
        holder.setText(R.id.subject_title, bean.getName());
        if (!bean.getImg().equals("")) {
            holder.setImageFromInternet(R.id.subject_img, bean.getImg());
        }

    }

}
