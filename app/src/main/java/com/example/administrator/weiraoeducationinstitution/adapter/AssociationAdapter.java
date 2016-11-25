package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.SubJectActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AssociationBean;

import java.util.List;


/**
 * Created by Administrator on 2016/4/29.
 */
public class AssociationAdapter extends SolidRVBaseAdapter<AssociationBean> {

    private ChangeManagerListener mChangeManagerListener;

    public AssociationAdapter(Context context, List<AssociationBean> beans) {
        super(context, beans);
    }


    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_association;
    }

    @Override
    protected void onItemClick(int position) {
//        mContext.startActivity(intent);
    }

    public String getItemAsId(int position) {
        return mBeans.get(position).getId();
    }
    public String getItemAsName(int position) {
        return mBeans.get(position).getName();
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final AssociationBean bean) {
        holder.setText(R.id.association_content, bean.getBrief());
        holder.setText(R.id.association_manager, "管理老师:" + bean.getAdmins());
        holder.setText(R.id.association_name, bean.getName());
        holder.setText(R.id.association_subjectNumber, bean.getSubjectNum() + "个科目");
        if (!bean.getImg().equals("")) {
            holder.setImageFromInternet(R.id.association_img, bean.getImg());
        }
        holder.setOnItemClickListener(R.id.association_changeManger_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChangeManagerListener.showPopView(holder.getAdapterPosition()-1);
            }
        });
        holder.setOnItemClickListener(R.id.association_checkSubject_text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SubJectActivity.class);
                intent.putExtra("the_association_id", bean.getId());
                mContext.startActivity(intent);
            }
        });
    }


    public void setChangeManagerListener(ChangeManagerListener listener) {
        this.mChangeManagerListener = listener;
    }


    public interface ChangeManagerListener {
        void showPopView(int position);
    }

}
