package com.example.administrator.weiraoeducationinstitution.activity.teacher;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.bean.TeacherCenterBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.view.TipsDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherCenterActivity extends BaseActivity {
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private CircleImageView mHead;
    private TextView name, age, intro;

    private LinearLayout phoneLL;
    private TipsDialog dialog;
    private String theTeacherId, thePhone;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_teacher_center;
    }

    @Override
    protected void init() {
        theTeacherId = getIntent().getStringExtra("theTeacherId");
    }

    @Override
    protected void initView() {
        mToolbar = customFindViewById(R.id.teacher_center_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//决定左上角的图标是否可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//决定左上角图标的右侧是否有向左的小箭头
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        intro = customFindViewById(R.id.teacher_center_introduction);
        name = customFindViewById(R.id.teacher_center_name);
        age = customFindViewById(R.id.teacher_center_age);
        mCollapsingToolbarLayout = customFindViewById(R.id.teacher_center_collapsing_toolbar_layout);
        mHead = customFindViewById(R.id.teacher_center_image);
        phoneLL = customFindViewById(R.id.teacher_center_TelephoneLL);
        dialog = new TipsDialog(this);
        mCollapsingToolbarLayout.setTitle("教师主页");

        phoneLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.myFunction(new TipsDialog.DialogCallBack() {
                    @Override
                    public void setContent(TextView v) {
                        v.setText("是否拨打电话 " + thePhone);
                    }

                    @Override
                    public void setConfirmOnClickListener(Button btn) {
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dissMiss();
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + thePhone));
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    protected void initData() {
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//位于Title的颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);//扩张的颜色
        mCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.weirao_title_color));
        getData(theTeacherId);
    }

    private void getData(String tid) {
        if (tid == null) {
            return;
        }
        String reqUrl = Apis.GetTeacher(tid);
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("code").equals("0")) {
                        Gson gson = new Gson();
                        TeacherCenterBean tcb = gson.fromJson(
                                jsonObject.toString(),
                                new TypeToken<TeacherCenterBean>() {
                                }.getType());
                        name.setText(tcb.getData().getInfo().getName());
                        if (tcb.getData().getInfo().getSex().equals("1")) {
                            name.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.iconfont_boy), null);
                        } else if (tcb.getData().getInfo().getSex().equals("0")) {
                            name.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.iconfont_girl), null);
                        }
//                    age.setText("");
                        thePhone = tcb.getData().getInfo().getPhone();
                        if (!tcb.getData().getInfo().getHead().equals("")) {
                            HttpUtils.getInstance().loadImage(tcb.getData().getInfo().getHead(), mHead);
                        }
                        intro.setText("手机号码:" + tcb.getData().getInfo().getPhone() + "\n" + "\n" +
                                "出生日期:" + tcb.getData().getInfo().getBirthday() + "\n" + "\n" +
                                "家庭地址:" + tcb.getData().getInfo().getAddress() + "\n" + "\n" +
                                "简介:" + tcb.getData().getInfo().getSummary() + "\n" + "\n" +
                                "毕业院校" + tcb.getData().getInfo().getEdu_school() + "\n" + "\n" +
                                "最近分享:" + tcb.getData().getInfo().getRes_share());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
