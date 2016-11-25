package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.teacher.TeacherCenterActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AllTeacherBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/5.
 */
public class AllTeacherAdapter extends SolidRVBaseAdapter<AllTeacherBean> {

    public AllTeacherAdapter(Context context, List<AllTeacherBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_allteacher;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, TeacherCenterActivity.class);
        intent.putExtra("theTeacherId", mBeans.get(position - 1).getId());
        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final AllTeacherBean bean) {
        holder.setText(R.id.item_allteacher_name, bean.getName());
        holder.setText(R.id.item_allteacher_teachingcourse, "授课:" + bean.getCourseNum());
        if (!bean.getHead().equals("")) {
            holder.setImageFromInternet(R.id.item_allteacher_head, bean.getHead());
        }
        holder.setOnItemClickListener(R.id.item_allteacher_telephone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + bean.getPhone()));
                mContext.startActivity(intent);
            }
        });
    }
}
