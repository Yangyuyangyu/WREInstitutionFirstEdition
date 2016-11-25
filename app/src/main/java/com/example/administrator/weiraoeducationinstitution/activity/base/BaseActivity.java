package com.example.administrator.weiraoeducationinstitution.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.utils.StatusBarUtil;

import ren.solid.skinloader.base.SkinBaseActivity;

/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:9:40
 */
public abstract class BaseActivity extends SkinBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceID());
        setStatusBar();
        init();
        initView();
        initData();

    }

    protected void initData() {
    }

    /***
     * 用于在初始化View之前做一些事
     */
    protected void init() {

    }

    protected abstract void initView();

    protected abstract int setLayoutResourceID();

    protected <T extends View> T customFindViewById(int id) {
        return (T) super.findViewById(id);
    }


    protected void startActivityWithoutExtras(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
    }

    protected void startActivityWithExtras(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(extras);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);

    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.weirao_title_color));
    }

}
