package com.example.administrator.weiraoeducationinstitution.adapter.manage.havingclass;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.bean.manage.havingclass.HavingCheckGradeBean;
import com.example.administrator.weiraoeducationinstitution.view.PinnedHeaderExpandableListView;

public class PinnedHeaderExpandableAdapter extends BaseExpandableListAdapter implements PinnedHeaderExpandableListView.HeaderAdapter {
    private HavingCheckGradeBean data;
    private Context context;
    private PinnedHeaderExpandableListView listView;
    private LayoutInflater inflater;

    public PinnedHeaderExpandableAdapter(HavingCheckGradeBean mdata
            , Context context, PinnedHeaderExpandableListView listView) {
        this.data = mdata;
        this.context = context;
        this.listView = listView;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.getData().get(groupPosition).getScore_info().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return Long.valueOf(data.getData().get(groupPosition).getScore_info().get(childPosition).getItem());
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = createChildrenView();
        }
        TextView text = (TextView) view.findViewById(R.id.childto);
        if (data.getData().get(groupPosition).getScore_info().get(childPosition) != null) {
            StringBuilder str = new StringBuilder();
            str.append(data.getData().get(groupPosition).getScore_info().get(childPosition).getName());
            str.append(data.getData().get(groupPosition).getScore_info().get(childPosition).getScore());
            str.append("分");
            text.setText(str.toString());
        } else {
            text.setText("暂无评分");
        }
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.getData().get(groupPosition).getScore_info().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.getData().get(groupPosition).getName();
    }

    @Override
    public int getGroupCount() {
        return data.getData().size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return Long.valueOf(data.getData().get(groupPosition).getId());
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = createGroupView();
        }

        ImageView iv = (ImageView) view.findViewById(R.id.groupIcon);

        if (isExpanded) {
            iv.setImageResource(R.drawable.icon_down_gray);
        } else {
            iv.setImageResource(R.drawable.icon_next_gray);
        }

        TextView text = (TextView) view.findViewById(R.id.groupto);
        if (data.getData().get(groupPosition) != null) {
            StringBuilder str = new StringBuilder();
            str.append(data.getData().get(groupPosition).getName());
            str.append(data.getData().get(groupPosition).getAverage());
            str.append("分");
            text.setText(str.toString());
        }
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private View createChildrenView() {
        return inflater.inflate(R.layout.child, null);
    }

    private View createGroupView() {
        return inflater.inflate(R.layout.group, null);
    }

    @Override
    public int getHeaderState(int groupPosition, int childPosition) {
        final int childCount = getChildrenCount(groupPosition);
        if (childPosition == childCount - 1) {
            return PINNED_HEADER_PUSHED_UP;
        } else if (childPosition == -1
                && !listView.isGroupExpanded(groupPosition)) {
            return PINNED_HEADER_GONE;
        } else {
            return PINNED_HEADER_VISIBLE;
        }
    }

    @Override
    public void configureHeader(View header, int groupPosition,
                                int childPosition, int alpha) {
        StringBuilder str = new StringBuilder();
        if (this.data.getData().get(groupPosition) != null) {
            str.append(this.data.getData().get(groupPosition).getName());
            str.append(this.data.getData().get(groupPosition).getAverage());
            str.append("分");
        }
        ((TextView) header.findViewById(R.id.groupto)).setText(str.toString());

    }

    private SparseIntArray groupStatusMap = new SparseIntArray();

    @Override
    public void setGroupClickStatus(int groupPosition, int status) {
        groupStatusMap.put(groupPosition, status);
    }

    @Override
    public int getGroupClickStatus(int groupPosition) {
        if (groupStatusMap.keyAt(groupPosition) >= 0) {
            return groupStatusMap.get(groupPosition);
        } else {
            return 0;
        }
    }
}
