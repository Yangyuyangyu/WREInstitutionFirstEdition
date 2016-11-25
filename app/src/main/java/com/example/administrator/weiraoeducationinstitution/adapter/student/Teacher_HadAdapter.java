package com.example.administrator.weiraoeducationinstitution.adapter.student;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AllTeacherBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public class Teacher_HadAdapter extends SolidRVBaseAdapter<AllTeacherBean> {
    private teacherHadOnclick mListener;

    public Teacher_HadAdapter(Context context, List<AllTeacherBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_teacher_had;
    }

    @Override
    protected void onItemClick(int position) {
        mListener.teacherHadItemClick(mBeans.get(position - 1).getPhone());
    }

    public String getItemTeacherId(int position) {
        return mBeans.get(position).getId();
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final AllTeacherBean bean) {
        holder.setText(R.id.item_teacher_had_name, bean.getName());
        if (!bean.getHead().equals("")) {
            holder.setImageFromInternet(R.id.item_teacher_had_head, bean.getHead());
        }
        holder.setOnItemClickListener(R.id.item_teacher_had_telephone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "打电话" + bean.getPhone(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + bean.getPhone()));
                mContext.startActivity(intent);
            }
        });
    }

    public void setteacherHadOnclick(teacherHadOnclick listener) {
        this.mListener = listener;
    }

    public interface teacherHadOnclick {
        void teacherHadItemClick(String phone);
    }
}
