package com.example.administrator.weiraoeducationinstitution.activity.manage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.PhotoActivity;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.manage.Manage_TrainingPlanAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AssociationBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Manage_TrainingPlanActivity extends BaseActivity {
    private static final String TAG = "Manage_TrainingPlanActivity";
    private Toolbar weiraoToolbarTop;
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;

    private XRecyclerView mRecyclerView;
    private Manage_TrainingPlanAdapter mBookAdapter;
    private int mCurrentAction = ACTION_REFRESH;
    private String mCurrentKeyWord;
    private int mPageSize = 10;
    private int mCurrentPageIndex = 1;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_manage__training_plan;
    }

    @Override
    protected void init() {
    }

    @Override
    protected void initData() {
        mRecyclerView.setRefreshing(true);
    }

    @Override
    protected void initView() {
        weiraoToolbarTop = customFindViewById(R.id.weirao_toolbar_top);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        mRecyclerView = customFindViewById(R.id.manage_traingPlan_recyclerview);
        mBookAdapter = new Manage_TrainingPlanAdapter(this, new ArrayList<AssociationBean>());
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
        weiraoToolbarTop.setTitle("社团训练规划");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {
        String reqUrl = Apis.GetGetGroups(BaseApplication.user_info.getId());
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Gson gson = new Gson();
                    List<AssociationBean> list = gson.fromJson(
                            jsonObject.getString("data"),
                            new TypeToken<List<AssociationBean>>() {
                            }.getType());
                    mBookAdapter.addAll(list);
                    loadComplete();
                    mRecyclerView.setLoadingMoreEnabled(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_allteacher_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbar_action_Add) {
            Bundle bundle =new Bundle();
            bundle.putString("bundle_value","this_is_training_plan");
            startActivityWithExtras(PhotoActivity.class, bundle);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        switchAction(ACTION_REFRESH);
        super.onRestart();
    }
}