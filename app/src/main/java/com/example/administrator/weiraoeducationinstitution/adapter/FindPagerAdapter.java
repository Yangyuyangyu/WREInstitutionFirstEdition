package com.example.administrator.weiraoeducationinstitution.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.weiraoeducationinstitution.fragment.HomePageFragment;
import com.example.administrator.weiraoeducationinstitution.fragment.ManagePageFragment;
import com.example.administrator.weiraoeducationinstitution.utils.ViewUtils;

import java.util.List;


/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:11:48
 */
public class FindPagerAdapter extends FragmentPagerAdapter {


    private final List<String> mTitleList;
    private final Context mContext;

    public FindPagerAdapter(Context context, List<String> titles, FragmentManager fm) {
        super(fm);
        mContext = context;
        mTitleList = titles;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = createFragmentByTitle(mTitleList.get(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitleList.size();
    }

    private Fragment createFragmentByTitle(String title) {

        Fragment result = new HomePageFragment();//这里主要是一个防止没有找到Fragment，给一个默认
        if (title.equals("首页")) {
            result = ViewUtils.createFragment(HomePageFragment.class, false);
        }
        return result;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
