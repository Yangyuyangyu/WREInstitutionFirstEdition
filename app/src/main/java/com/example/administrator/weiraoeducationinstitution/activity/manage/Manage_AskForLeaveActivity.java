package com.example.administrator.weiraoeducationinstitution.activity.manage;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.fragment.manage.Manage_Leave_StudentFragment;
import com.example.administrator.weiraoeducationinstitution.fragment.manage.Manage_Leave_TeacherFragment;
import com.example.administrator.weiraoeducationinstitution.utils.ViewUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Manage_AskForLeaveActivity extends BaseActivity implements View.OnClickListener {
    private static String TAG = "Manage_AskForLeaveActivity";
    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;
    private FrameLayout frameLayout;
    private RadioButton teacher, student;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_manage_ask_for_leave;
    }

    @Override
    protected void initData() {
        student.setChecked(true);
        teacher.setChecked(false);
        student.setTextColor(Color.rgb(255, 255, 255));
        teacher.setTextColor(Color.rgb(51, 51, 51));
    }

    @Override
    protected void init() {
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        frameLayout = (FrameLayout) customFindViewById(R.id.manage_askforleave_frameLayout);
        teacher = (RadioButton) customFindViewById(R.id.manage_askforleave_teacher);
        student = (RadioButton) customFindViewById(R.id.manage_askforleave_student);
        weiraoToolbarTop.setTitle("请假审批");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        teacher.setOnClickListener(this);
        student.setOnClickListener(this);
        mCurrentFragment = ViewUtils.createFragment(Manage_Leave_StudentFragment.class);
        mFragmentManager.beginTransaction().add(R.id.manage_askforleave_frameLayout, mCurrentFragment).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.manage_askforleave_teacher:
                switchFragment(Manage_Leave_TeacherFragment.class);
                teacher.setChecked(true);
                student.setChecked(false);
                teacher.setTextColor(Color.rgb(255, 255, 255));
                student.setTextColor(Color.rgb(51, 51, 51));
                break;
            case R.id.manage_askforleave_student:
                switchFragment(Manage_Leave_StudentFragment.class);
                student.setChecked(true);
                teacher.setChecked(false);
                student.setTextColor(Color.rgb(255, 255, 255));
                teacher.setTextColor(Color.rgb(51, 51, 51));
                break;
        }

    }

    /**
     * fragment切换
     */
    private void switchFragment(Class<?> clazz) {
        Fragment to = ViewUtils.createFragment(clazz);
        if (to.isAdded()) {
            Log.i(TAG, "Added");
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(to).commitAllowingStateLoss();
        } else {
            Log.i(TAG, "Not Added");
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.manage_askforleave_frameLayout, to).commitAllowingStateLoss();
        }
        mCurrentFragment = to;
    }
}
