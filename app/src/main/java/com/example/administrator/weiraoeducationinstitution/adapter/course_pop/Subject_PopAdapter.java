package com.example.administrator.weiraoeducationinstitution.adapter.course_pop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.bean.SubjectBean;
import com.example.administrator.weiraoeducationinstitution.view.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/5/25.
 */
public class Subject_PopAdapter extends BaseAdapter {
    private Context mContext;

    public List<SubjectBean> reviewList;

    public Subject_PopAdapter(Context mContext, List<SubjectBean> list) {
        super();
        this.mContext = mContext;
        this.reviewList = list;
    }

    public void changeSubjectData(List<SubjectBean> list) {
        this.reviewList = list;
        notifyDataSetChanged();
    }

    public String getItemName(int position) {
        return reviewList.get(position).getName();
    }


    public String getItemSubjectId(int position) {
        return reviewList.get(position).getId();
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
                    R.layout.item_pop_exam, parent, false);
        }
        TextView tv = BaseViewHolder.get(convertView, R.id.item_pop_exam_subject_name);
        if (reviewList == null || reviewList.size() == 0) {
            return convertView;
        }
        tv.setText(reviewList.get(position).getName());

        return convertView;
    }
}
