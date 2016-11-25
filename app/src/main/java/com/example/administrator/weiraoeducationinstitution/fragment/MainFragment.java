package com.example.administrator.weiraoeducationinstitution.fragment;

import android.support.v4.view.ViewPager;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.adapter.FindPagerAdapter;
import com.example.administrator.weiraoeducationinstitution.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:11:29
 */
public class MainFragment extends BaseFragment {

    private static String TAG = "MainFragment";
    private ViewPager mViewPager;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) customFindViewById(R.id.viewpager);
        List<String> titles = new ArrayList<>();
        titles.add("首页");
        FindPagerAdapter viewPagerAdapter = new FindPagerAdapter(getMContext(), titles, getChildFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);

    }

}
