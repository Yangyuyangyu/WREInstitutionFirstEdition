package com.example.administrator.weiraoeducationinstitution.adapter.manage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.manage.leave.AskLeaveDetailActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.manage.leave.LeaveBean;

import java.util.List;


/**
 * Created by Administrator on 2016/5/3.
 */
public class Manage_AskForLeaveAdapter extends SolidRVBaseAdapter<LeaveBean> {
    public Manage_AskForLeaveAdapter(Context context, List<LeaveBean> beans) {
        super(context, beans);
    }


    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_fragment_askleave;
    }

    @Override
    protected void onItemClick(int position) {
        String[] leave = new String[]{mBeans.get(position - 1).getId(), mBeans.get(position - 1).getName(), mBeans.get(position - 1).getCourse()
                , mBeans.get(position - 1).getReason(), mBeans.get(position - 1).getStart(), mBeans.get(position - 1).getEnd(),
                mBeans.get(position - 1).getRemark(), mBeans.get(position - 1).getPhone(), mBeans.get(position - 1).getStatus()};
        Intent intent = new Intent(mContext, AskLeaveDetailActivity.class);
        intent.putExtra("the_leave_content", leave);
        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, LeaveBean bean) {
        holder.setText(R.id.item_manage_leave_teacher_course, bean.getCourse());
        holder.setText(R.id.item_manage_leave_teacher_name, bean.getName());
        holder.setText(R.id.item_manage_leave_teacher_reason, bean.getReason());
        if (!bean.getImg().equals("")) {
            holder.setImageFromInternet(R.id.item_manage_leave_teacher_headimg, bean.getImg());
        }
        if (bean.getStatus().equals("0")) {
            holder.setText(R.id.item_manage_leave_status, "未审批");
            holder.setTextColor(R.id.item_manage_leave_status, Color.BLUE);
        } else if (bean.getStatus().equals("1")) {
            holder.setText(R.id.item_manage_leave_status, "已批准");
            holder.setTextColor(R.id.item_manage_leave_status, Color.GREEN);
        } else if (bean.getStatus().equals("2")) {
            holder.setText(R.id.item_manage_leave_status, "已拒绝");
            holder.setTextColor(R.id.item_manage_leave_status, R.color.weirao_title_color);
        }

    }
}
