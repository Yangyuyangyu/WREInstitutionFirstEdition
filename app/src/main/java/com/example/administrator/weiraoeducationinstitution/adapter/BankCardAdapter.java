package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.BankCardActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.BankCardBean;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/5/6.银行卡logo未解决
 */
public class BankCardAdapter extends SolidRVBaseAdapter<BankCardBean> {
    public BankCardAdapter(Context context, List<BankCardBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_bankcard;
    }

    @Override
    protected void onItemClick(int position) {
//        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final BankCardBean bean) {
        holder.setText(R.id.item_bankcard_bankname, bean.getBankName());
//        holder.setText(R.id.item_bankcard_type, bean.getBank());
        String num = bean.getAccount_num();
        if (num.length() > 4) {
            String str = num.substring(num.length() - 4, num.length());
            holder.setText(R.id.item_bankcard_cardNumber, "**** **** **** ***" + str);
        } else {
            holder.setText(R.id.item_bankcard_cardNumber, num);
        }


//        holder.setImageFromInternet(R.id.item_allstudent_head, bean.getHeadimg());
        if (BankCardActivity.isEdit) {
            holder.setVisible(R.id.item_bankcard_cb, true);
        } else {
            holder.setVisible(R.id.item_bankcard_cb, false);
        }

        final CheckBox cb = holder.getView(R.id.item_bankcard_cb);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb.isChecked()) {
                    BankCardActivity.map_carId.put(holder.getAdapterPosition() - 1, bean.getId());
                } else {
                    BankCardActivity.map_carId.remove(holder.getAdapterPosition() - 1);
                }
                ToastUtils.getInstance().showToast(BankCardActivity.map_carId.toString());

            }
        });

    }


}
