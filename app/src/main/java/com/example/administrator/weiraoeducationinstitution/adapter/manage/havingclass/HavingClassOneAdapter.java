package com.example.administrator.weiraoeducationinstitution.adapter.manage.havingclass;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.manage.havingclass.HavingClassDetailActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.manage.havingclass.HavingClassOneBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 */
public class HavingClassOneAdapter extends SolidRVBaseAdapter<HavingClassOneBean> {
    public HavingClassOneAdapter(Context context, List<HavingClassOneBean> beans) {
        super(context, beans);
    }


    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_having_class;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, HavingClassDetailActivity.class);
        intent.putExtra("the_having_class_id", mBeans.get(position - 1).getId());
        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, HavingClassOneBean bean) {
        if (bean.getStatus().equals("0")) {//未审核
            holder.setImage(R.id.having_class_status_img, R.drawable.ic_gougou_gray);
            Drawable bg_no = mContext.getResources().getDrawable(R.drawable.ic_having_class_no);
            holder.setLLDrawable(R.id.having_class_bgLL, bg_no);
        } else if (bean.getStatus().equals("1")) {//通过
            holder.setImage(R.id.having_class_status_img, R.drawable.ic_gougou_green);
            Drawable bg_ok = mContext.getResources().getDrawable(R.drawable.ic_having_class_ok);
            holder.setLLDrawable(R.id.having_class_bgLL, bg_ok);
        }else if(bean.getStatus().equals("2")){//拒绝

        }
        holder.setText(R.id.having_class_title, bean.getName());
        holder.setText(R.id.having_class_teacher, "授课老师: " + bean.getTeacher());
        holder.setText(R.id.having_class_time, "上课时间:  " + bean.getClass_time());
        if (!bean.getImg().equals("")) {
            holder.setImageFromInternet(R.id.having_class_headimg, bean.getImg());
        }
    }
}
