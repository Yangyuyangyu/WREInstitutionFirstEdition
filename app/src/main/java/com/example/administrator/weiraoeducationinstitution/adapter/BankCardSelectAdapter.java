package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.FundCardTypeSelectActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.BankCardSelectBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/11.
 */
public class BankCardSelectAdapter extends SolidRVBaseAdapter<BankCardSelectBean> {
    private BankCardCheckedClick mBankCardCheckedClick;

    public BankCardSelectAdapter(Context context, List<BankCardSelectBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_bankcard_select;
    }

    @Override
    protected void onItemClick(int position) {
        mBankCardCheckedClick.setBankcard(position - 1);
    }

    public String getItemName(int position) {
        return mBeans.get(position).getBankName();
    }

    public String getItemBankID(int position) {
        return mBeans.get(position).getId();
    }

    public String getItemBankNumber(int position) {
        return mBeans.get(position).getAccount_num();
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, BankCardSelectBean bean) {
        holder.setText(R.id.item_bankcard_select_name, bean.getBankName());
        if (bean.getId().equals(FundCardTypeSelectActivity.indexId)) {
            holder.setChecked(R.id.item_bankcard_select_cb, true);
        } else {
            holder.setChecked(R.id.item_bankcard_select_cb, false);
        }
    }

    public void setBankCardCheckedClick(BankCardCheckedClick listener) {
        this.mBankCardCheckedClick = listener;
    }

    public interface BankCardCheckedClick {
        void setBankcard(int position);
    }
}
