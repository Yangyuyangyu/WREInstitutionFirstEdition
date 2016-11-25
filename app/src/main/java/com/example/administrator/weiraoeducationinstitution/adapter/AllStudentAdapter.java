package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.student.StudentCenterActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AllStudentBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/5.
 */
public class AllStudentAdapter extends SolidRVBaseAdapter<AllStudentBean> {
    private phoneItemClick phoneItemClick;

    public AllStudentAdapter(Context context, List<AllStudentBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_allstudent;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, StudentCenterActivity.class);
        intent.putExtra("theStudentId", mBeans.get(position - 1).getId());
        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final AllStudentBean bean) {
        holder.setText(R.id.item_allstudent_age, bean.getAge() + "岁");
        holder.setText(R.id.item_allstudent_name, bean.getName());
        holder.setText(R.id.item_allstudent_class, bean.getGroup_name());
        if (!bean.getHead().equals("")) {
            holder.setImageFromInternet(R.id.item_allstudent_head, bean.getHead());
        }
        holder.setOnItemClickListener(R.id.item_allstudent_telephone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneItemClick.callOnClick( bean.getPhone());
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
