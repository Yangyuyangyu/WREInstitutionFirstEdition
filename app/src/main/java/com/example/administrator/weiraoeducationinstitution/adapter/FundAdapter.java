package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.FundBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/29.
 */
public class FundAdapter extends SolidRVBaseAdapter<FundBean> {
    public FundAdapter(Context context, List<FundBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_fund;
    }

    @Override
    protected void onItemClick(int position) {
//        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, FundBean bean) {
        holder.setText(R.id.fund_money, bean.getMoney());
        holder.setText(R.id.fund_time, bean.getTime());
        if (bean.getType().equals("2")) {
            holder.setText(R.id.fund_title, "提款");
            if (bean.getStatus().equals("0")) {
                holder.setText(R.id.fund_result, "提款失败");
            } else if (bean.getStatus().equals("1")) {
                holder.setText(R.id.fund_result, "提款成功");
            }
            holder.setImage(R.id.fund_type_img, R.drawable.icon_fund_withdraw);//提款
        } else if (bean.getType().equals("1")) {
            holder.setText(R.id.fund_title, "收入");
            if (bean.getStatus().equals("0")) {
                holder.setText(R.id.fund_result, "未收入");
            } else if (bean.getStatus().equals("1")) {
                holder.setText(R.id.fund_result, "已收入");
            }
            holder.setText(R.id.fund_result, "已收入");
            holder.setImage(R.id.fund_type_img, R.drawable.icon_fund_collection);//收入
        }


    }
}
