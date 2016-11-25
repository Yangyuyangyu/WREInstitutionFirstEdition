package com.example.administrator.weiraoeducationinstitution.adapter.manage.telephone;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.manage.telephone.Telephone_Two;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.manage.TelephoneOneBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class TelephoneOneAdapter extends SolidRVBaseAdapter<TelephoneOneBean> {
    public TelephoneOneAdapter(Context context, List<TelephoneOneBean> beans) {
        super(context, beans);
    }


    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_telephone_one;
    }

    @Override
    protected void onItemClick(int position) {
//        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final TelephoneOneBean bean) {
        holder.setText(R.id.item_telephone_one_studentname, bean.getStudent());
        holder.setText(R.id.item_telephone_one_teachername, bean.getTeacher());
        holder.setText(R.id.item_telephone_one_time, bean.getTime());
        holder.setOnItemClickListener(R.id.item_telephone_one_checkmore, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] content = new String[]{bean.getStudent(), bean.getTeacher(), bean.getGroup_name(), bean.getContent()
                        , bean.getFeedback(), bean.getSolution(), bean.getReason(), bean.getSolve_time(), bean.getIs_solved()};
                Intent intent = new Intent(mContext, Telephone_Two.class);
                intent.putExtra("telephone_interview_content", content);
                mContext.startActivity(intent);
            }
        });
    }
}
