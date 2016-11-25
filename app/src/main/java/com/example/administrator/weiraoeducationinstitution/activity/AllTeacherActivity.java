package com.example.administrator.weiraoeducationinstitution.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.activity.teacher.Teacher_Select_AddActivity;
import com.example.administrator.weiraoeducationinstitution.activity.teacher.Teacher_Select_HadActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.AllTeacherAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AllTeacherBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ViewUtils;
import com.example.administrator.weiraoeducationinstitution.view.PopView_AllTeacherOrCourse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AllTeacherActivity extends BaseActivity {
    private static final String TAG = "AllTeacherActivity";
    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;
    @Bind(R.id.nodata_bg_text)
    TextView nodataBgText;
    @Bind(R.id.include_nodata_bgLL)
    LinearLayout includeNodataBgLL;

    private XRecyclerView mRecyclerView;
    private PopView_AllTeacherOrCourse pop;

    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;

    private AllTeacherAdapter mBookAdapter;
    private int mCurrentAction = ACTION_REFRESH;
    private String mCurrentKeyWord;
    private int mPageSize = 10;
    private int mCurrentPageIndex = 1;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_all_teacher;
    }

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
    }

    @Override
    protected void initData() {
        mRecyclerView.setRefreshing(true);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mRecyclerView = customFindViewById(R.id.allteacher_recyclerview);
        weiraoToolbarTop.setTitle("全部老师");
        nodataBgText.setText("暂无老师");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        mBookAdapter = new AllTeacherAdapter(this, new ArrayList<AllTeacherBean>());
        mRecyclerView.setAdapter(mBookAdapter);
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_allteacher_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbar_action_Add) {
            if (pop == null) {
                int ww = ViewGroup.LayoutParams.WRAP_CONTENT;
                int hh = ViewGroup.LayoutParams.WRAP_CONTENT;
                PopLintener paramOnClickListener = new PopLintener();
                pop = new PopView_AllTeacherOrCourse(AllTeacherActivity.this, paramOnClickListener, ww, hh);
                pop.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            pop.dismiss();
                        }
                    }
                });
            }
            pop.setFocusable(true);
            pop.showAsDropDown(weiraoToolbarTop, ViewUtils.getScreenWidth(this), 0);
            pop.update();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * popWindown监听
     */
    class PopLintener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_allTeacherOrCourse_add:
                    startActivityWithoutExtras(Teacher_Select_AddActivity.class);
                    pop.dismiss();
                    break;
                case R.id.pop_allTeacherOrCourse_had:
                    startActivityWithoutExtras(Teacher_Select_HadActivity.class);
                    pop.dismiss();
                    break;
                default:
                    break;
            }
        }
    }


    private void getData() {
        String reqUrl = Apis.GetMyTeacher(BaseApplication.user_info.getId());
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("code").equals("0")){
                        Gson gson = new Gson();
                        List<AllTeacherBean> list = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<List<AllTeacherBean>>() {
                                }.getType());
                        mBookAdapter.addAll(list);
                        loadComplete();
                        mRecyclerView.setLoadingMoreEnabled(false);
                        includeNodataBgLL.setVisibility(View.GONE);
                    }else {
                        mRecyclerView.refreshComplete();
                        ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "onError:" + e);
                ToastUtils.getInstance().showToast(e.getMessage());
                loadComplete();
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
                mBookAdapter.clear();
                mCurrentPageIndex = 1;
                break;
            case ACTION_LOAD_MORE:
                mCurrentPageIndex++;
                break;
        }
        getData();
    }


    @Override
    protected void onRestart() {
        switchAction(ACTION_REFRESH);
        super.onRestart();
    }
}
