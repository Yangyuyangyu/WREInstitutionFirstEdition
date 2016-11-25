package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.AssociationActivity;
import com.example.administrator.weiraoeducationinstitution.bean.PopAssociationManagerBean;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.example.administrator.weiraoeducationinstitution.view.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/5/10.
 */
public class PopAssociationManagerAdapter extends BaseAdapter {
    private Context mContext;

    public List<PopAssociationManagerBean> reviewList;

    public PopAssociationManagerAdapter(Context mContext, List<PopAssociationManagerBean> list) {
        super();
        this.mContext = mContext;
        this.reviewList = list;
    }

    public void changeManagerData(List<PopAssociationManagerBean> list) {
        this.reviewList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return reviewList != null ? reviewList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_pop_association_change_manager, parent, false);
        }
        TextView tv = BaseViewHolder.get(convertView, R.id.item_pop_association_name);
        final CheckBox cb = BaseViewHolder.get(convertView, R.id.item_pop_association_cb);
        if (reviewList == null || reviewList.size() == 0) {
            return convertView;
        }
        tv.setText(reviewList.get(position).getName());
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb.isChecked()) {
                    AssociationActivity.the_manage_id = reviewList.get(position).getId();
                } else {
                    AssociationActivity.the_manage_id =null;
                }
            }
        });
        return convertView;
    }
}
