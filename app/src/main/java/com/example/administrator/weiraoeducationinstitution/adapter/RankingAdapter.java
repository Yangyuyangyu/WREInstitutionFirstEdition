package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.adapter.base.SolidRVBaseAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.RankingBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/28.
 */
public class RankingAdapter extends SolidRVBaseAdapter<RankingBean> {
    public RankingAdapter(Context context, List<RankingBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_ranking;
    }

    @Override
    protected void onItemClick(int position) {
//        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, RankingBean bean) {
        holder.setText(R.id.item_rank_name, bean.getName());
        holder.setText(R.id.item_rank_grade, "平均分:" + bean.getScore());
        holder.setText(R.id.item_rank_subject, "社团:" + bean.getSearch_name());
        if (!bean.getHead().equals("")) {
            holder.setImageFromInternet(R.id.item_rank_head, bean.getHead());
        }
//        if (holder.getAdapterPosition()==1) {
//            Drawable rank_1 = mContext.getResources().getDrawable(R.mipmap.icon_rank_1);
//            holder.setTextViewDrawable(R.id.item_rank_ranking, rank_1);
//        } else if (holder.getAdapterPosition()==2) {
//            Drawable rank_2 = mContext.getResources().getDrawable(R.mipmap.icon_rank_2);
//            holder.setTextViewDrawable(R.id.item_rank_ranking, rank_2);
//        } else if (holder.getAdapterPosition()==3) {
//            Drawable rank_3 = mContext.getResources().getDrawable(R.mipmap.icon_rank_3);
//            holder.setTextViewDrawable(R.id.item_rank_ranking, rank_3);
//        } else {
        holder.setText(R.id.item_rank_ranking, bean.getMy_order());

    }
}
