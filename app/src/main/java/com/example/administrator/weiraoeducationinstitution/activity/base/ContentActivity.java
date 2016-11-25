package com.example.administrator.weiraoeducationinstitution.activity.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.administrator.weiraoeducationinstitution.R;


/**
 * Created by _SOLID
 * Date:2016/4/22
 * Time:13:30
 * <p/>
 * include toolbar ,and you can set a fragment to show
 */
public abstract class ContentActivity extends BaseActivity {

    protected Toolbar mToolbar;
    protected FragmentManager mFragmentManager;

    @Override
    protected void init() {
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void initView() {
        mToolbar = customFindViewById(R.id.toolbar);
        mToolbar.setTitle("图片");

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setHomeButtonEnabled(true);//决定左上角的图标是否可以点击

        dynamicAddSkinEnableView(mToolbar, "background", R.color.colorPrimary);
    }

    @Override
    protected void initData() {
        mFragmentManager.beginTransaction().replace(R.id.fl_content, setFragment()).commit();
    }

    protected abstract Fragment setFragment();


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_content;
    }
}
