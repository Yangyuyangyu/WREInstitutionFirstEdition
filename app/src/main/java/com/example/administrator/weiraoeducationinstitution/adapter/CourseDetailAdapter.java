package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.weiraoeducationinstitution.bean.CourseDetailBean;
import com.example.administrator.weiraoeducationinstitution.fragment.CourseEvaluateFragment;
import com.example.administrator.weiraoeducationinstitution.fragment.base.StringFragment;
import com.example.administrator.weiraoeducationinstitution.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public class CourseDetailAdapter extends FragmentPagerAdapter {

    private final List<String> mTitleList;
    private final Context mContext;
    private CourseDetailBean mBean;

    public CourseDetailAdapter(Context context, CourseDetailBean bean, FragmentManager fm) {
        super(fm);
        mContext = context;
        mBean = bean;
        mTitleList = new ArrayList<>();
        mTitleList.add("课程说明");
        mTitleList.add("查看详情");
        mTitleList.add("评价");
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = ViewUtils.createFragment(StringFragment.class, false);
        Bundle bundle = new Bundle();
        if (getPageTitle(position).equals("课程说明")) {
            if(mBean!=null) {
                if (mBean.getBrief() != null) {
                    bundle.putString("text", mBean.getBrief());
                } else {
                    bundle.putString("text", "");
                }
            }
        } else if (getPageTitle(position).equals("查看详情")) {
            if(mBean!=null) {
                if (mBean.getDetail() != null) {
                    bundle.putString("text", mBean.getDetail());
                } else {
                    bundle.putString("text", "");
                }
            }
        } else if (getPageTitle(position).equals("评价")) {
            fragment = ViewUtils.createFragment(CourseEvaluateFragment.class, false);
        }
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return mTitleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
