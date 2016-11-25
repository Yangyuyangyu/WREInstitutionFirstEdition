package com.example.administrator.weiraoeducationinstitution.adapter.change;

import android.content.Context;
import android.view.View;


import com.example.administrator.weiraoeducationinstitution.R;


import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.change.CallNameBean;

import java.util.List;

/**
 * Created by waycubeoxa on 16/9/22.上课记录所有学生考勤状态
 */
public class AllStudentStateAdapter extends SolidRVBaseAdapter<CallNameBean> {
    private phoneItemClick phoneItemClick;

    public AllStudentStateAdapter(Context context, List<CallNameBean> beans) {
        super(context, beans);
    }


    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_allstudent;
    }

    @Override
    protected void onItemClick(int position) {
//        Intent intent = new Intent(mContext, StudentCenterActivity.class);
//        intent.putExtra("theStudentId", mBeans.get(position - 1).getId());
//        mContext.startActivity(intent);
    }


    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final CallNameBean bean) {
        if (bean.getCall_state().equals("1")) {
            holder.setText(R.id.item_allstudent_age, "准时");
        } else if (bean.getCall_state().equals("2")) {
            holder.setText(R.id.item_allstudent_age, "迟到");
        } else if (bean.getCall_state().equals("3")) {
            holder.setText(R.id.item_allstudent_age, "请假");
            holder.setTextColor(R.id.item_allstudent_age, mContext.getResources().getColor(R.color.bank_type_color));
        } else if (bean.getCall_state().equals("4")) {
            holder.setText(R.id.item_allstudent_age, "未到");
            holder.setTextColor(R.id.item_allstudent_age, mContext.getResources().getColor(R.color.weirao_title_color));
        }

        holder.setText(R.id.item_allstudent_name, bean.getName());
        if (!bean.getHead().equals("") || bean.getHead() != null) {
            holder.setImageFromInternet(R.id.item_allstudent_head, bean.getHead());
        }

        holder.setOnItemClickListener(R.id.item_allstudent_telephone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneItemClick.callOnClick(bean.getPhone());
            }
        });

    }


    public void setPhoneItemClick(phoneItemClick listener) {
        this.phoneItemClick = listener;
    }

    public interface phoneItemClick {
        void callOnClick(String phoneNumber);
    }
}
