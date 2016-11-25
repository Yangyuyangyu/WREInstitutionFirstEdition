package com.example.administrator.weiraoeducationinstitution.adapter.repair;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.manage.repair.RepairDetailActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.manage.RepairOneBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 */
public class RepairOneAdapter extends SolidRVBaseAdapter<RepairOneBean> {
    public RepairOneAdapter(Context context, List<RepairOneBean> beans) {
        super(context, beans);
    }


    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_repair_one;
    }

    @Override
    protected void onItemClick(int position) {
//        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final RepairOneBean bean) {
        holder.setText(R.id.item_repair_one_name, bean.getUser_name());
        holder.setText(R.id.item_repair_one_association, bean.getSubject());
        holder.setText(R.id.item_repair_one_course, bean.getCourse());
        holder.setText(R.id.item_repair_one_material, bean.getInstrument());
        holder.setText(R.id.item_repair_one_time, bean.getTime());
        holder.setOnItemClickListener(R.id.item_repair_one_checkmore, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] content = new String[]{bean.getUser_name(), bean.getSubject(), bean.getInstrument(), bean.getRemark(), bean.getGroup(),bean.getCourse()};
                Intent intent = new Intent(mContext, RepairDetailActivity.class);
                intent.putExtra("the_repair_condition", content);
                mContext.startActivity(intent);
            }
        });
    }
}
