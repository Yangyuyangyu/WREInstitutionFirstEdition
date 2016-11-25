package com.example.administrator.weiraoeducationinstitution.activity.student;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.student.StudentCenterAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.StudentGroupBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.view.TipsDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class StudentCenterActivity extends BaseActivity {
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private CircleImageView mHead;
    private TextView name, age;
    private XRecyclerView mRecyclerView;
    private StudentCenterAdapter mAdapter;
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;
    private int mCurrentAction = ACTION_REFRESH;
    private int mCurrentPageIndex = 1;

    private LinearLayout phoneLL;
    private TipsDialog dialog;
    private String theStudentId, thePhone;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_student_center;
    }

    @Override
    protected void init() {
        theStudentId = getIntent().getStringExtra("theStudentId");
    }

    @Override
    protected void initView() {
        mToolbar = customFindViewById(R.id.student_center_toolbar);
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

        name = customFindViewById(R.id.student_center_name);
        age = customFindViewById(R.id.student_center_age);
        mCollapsingToolbarLayout = customFindViewById(R.id.student_center_collapsing_toolbar_layout);
        mHead = customFindViewById(R.id.student_center_image);
        phoneLL = customFindViewById(R.id.student_center_TelephoneLL);
        dialog = new TipsDialog(this);
        mCollapsingToolbarLayout.setTitle("个人主页");

        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        mRecyclerView = customFindViewById(R.id.student_center_recyclerview);
        mAdapter = new StudentCenterAdapter(this, new ArrayList<StudentGroupBean>());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(LayoutManager);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                switchAction(ACTION_REFRESH);
            }

            @Override
            public void onLoadMore() {
                switchAction(ACTION_LOAD_MORE);
            }
        });
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
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
        mRecyclerView.setRefreshing(true);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//位于Title的颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);//扩张的颜色
        mCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.weirao_title_color));
    }


    private void getData(String sid) {
        String reqUrl = Apis.GetStudentInfo(sid);
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
                        StudentObject so = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<StudentObject>() {
                                }.getType());
                        name.setText(so.getInfo().getName());
//                    age.setText("");
                        thePhone = so.getInfo().getPhone();
                        if (!so.getInfo().getHead().equals("")) {
                            HttpUtils.getInstance().loadImage(so.getInfo().getHead(), mHead);
                        }
                        mAdapter.addAll(so.getGroups());
                        loadComplete();
                        mRecyclerView.setLoadingMoreEnabled(false);
                        mRecyclerView.setPullRefreshEnabled(false);
                    } else {
                        mRecyclerView.refreshComplete();
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

    private void loadComplete() {
        if (mCurrentAction == ACTION_REFRESH)
            mRecyclerView.refreshComplete();
        if (mCurrentAction == ACTION_LOAD_MORE)
            mRecyclerView.loadMoreComplete();
    }

    private void switchAction(int action) {
        mCurrentAction = action;
        switch (mCurrentAction) {
            case ACTION_REFRESH:
//                mBookAdapter.clear();
                mCurrentPageIndex = 1;
                break;
            case ACTION_LOAD_MORE:
                mCurrentPageIndex++;
                break;
        }
        getData(theStudentId);
    }


    /**
     * 学生主页实体类
     */
    class StudentObject {
        private StudentInfo info;
        private List<StudentGroupBean> groups;

        public StudentInfo getInfo() {
            return info;
        }

        public void setInfo(StudentInfo info) {
            this.info = info;
        }

        public List<StudentGroupBean> getGroups() {
            return groups;
        }

        public void setGroups(List<StudentGroupBean> groups) {
            this.groups = groups;
        }
    }

    class StudentInfo {
        private String id;
        private String name;
        private String phone;
        private String head;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }
    }


}
