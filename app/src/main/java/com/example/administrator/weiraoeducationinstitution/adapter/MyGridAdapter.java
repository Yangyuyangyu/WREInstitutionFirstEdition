package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.view.BaseViewHolder;

/**
 * @author http://blog.csdn.net/finddreams
 * @Description:gridview的Adapter
 */
public class MyGridAdapter extends BaseAdapter {
    private Context mContext;

    public String[] img_text = {"学生", "社团建设表", "社团", "上课记录", "课程表", "开设科目", "请假审批", "电话追访", "资金"};

    public int[] imgs = {R.drawable.icon_main_student, R.drawable.icon_main_association_construction, R.drawable.icon_main_association, R.drawable.manage_class_condition,
            R.drawable.icon_main_time_table, R.drawable.icon_main_subject, R.drawable.manage_leave, R.drawable.manage_phone, R.drawable.icon_main_money};

    public MyGridAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return img_text.length;
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
                    R.layout.item_main_gridview, parent, false);
        }
        final TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
        iv.setBackgroundResource(imgs[position]);
        tv.setText(img_text[position]);
        return convertView;
    }

}
