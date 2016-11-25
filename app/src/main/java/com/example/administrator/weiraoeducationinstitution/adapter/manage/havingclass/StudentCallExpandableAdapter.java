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
import com.example.administrator.weiraoeducationinstitution.bean.manage.leave.HavingCheckLeaveBean;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.view.PinnedHeaderExpandableListView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/6/12.学生点名
 */
public class StudentCallExpandableAdapter extends BaseExpandableListAdapter implements PinnedHeaderExpandableListView.HeaderAdapter {
    private callItemPhoneClcik mcallItemPhoneClcik;
    private HavingCheckLeaveBean data;
    private String[] groupData;
    private Context context;
    private PinnedHeaderExpandableListView listView;
    private LayoutInflater inflater;

    public StudentCallExpandableAdapter(HavingCheckLeaveBean mdata, String[] groupData
            , Context context, PinnedHeaderExpandableListView listView) {
        this.data = mdata;
        this.groupData = groupData;
        this.context = context;
        this.listView = listView;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.getData().get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = createChildrenView();
        }
        TextView text = (TextView) view.findViewById(R.id.child_call_name);
        CircleImageView head = (CircleImageView) view.findViewById(R.id.child_call_head);
        ImageView phone = (ImageView) view.findViewById(R.id.child_call_phone);
        if (data.getData().get(groupPosition).get(childPosition) != null) {
            if (data.getData().get(groupPosition).get(childPosition).getHead() != null) {
                HttpUtils.getInstance().loadImage(data.getData().get(groupPosition).get(childPosition).getHead(), head);
            }
            if (data.getData().get(groupPosition).get(childPosition).getName() != null) {
                text.setText(data.getData().get(groupPosition).get(childPosition).getName());
            }
        }
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcallItemPhoneClcik.clickphone(data.getData().get(groupPosition).get(childPosition).getPhone());
            }
        });
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.getData().get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData[groupPosition];
    }

    @Override
    public int getGroupCount() {
        return groupData.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
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
        TextView text = (TextView) view.findViewById(R.id.group_call_name);
        text.setText(groupData[groupPosition]);
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
        return inflater.inflate(R.layout.child_call, null);
    }

    private View createGroupView() {
        return inflater.inflate(R.layout.group_call, null);
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
        ((TextView) header.findViewById(R.id.groupto)).setText(this.groupData[groupPosition]);

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

    public void secallItemPhoneClcik(callItemPhoneClcik listener) {
        this.mcallItemPhoneClcik = listener;
    }

    public interface callItemPhoneClcik {
        void clickphone(String phone);
    }
}
