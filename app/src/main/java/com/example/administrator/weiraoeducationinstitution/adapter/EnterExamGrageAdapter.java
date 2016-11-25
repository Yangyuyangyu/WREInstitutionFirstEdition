package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.EnterExamGrageBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/10.
 */
public class EnterExamGrageAdapter extends SolidRVBaseAdapter<EnterExamGrageBean> {
    public EnterExamGrageAdapter(Context context, List<EnterExamGrageBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_enter_exam_grade;
    }

    @Override
    protected void onItemClick(int position) {
//        mContext.startActivity(intent);
    }


    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, EnterExamGrageBean bean) {
        holder.setText(R.id.item_enter_exam_name, "姓名:  " + bean.getName());
    }


}
