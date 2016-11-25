package com.example.administrator.weiraoeducationinstitution.activity.manage;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.manage.Manage_TemporaryAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.manage.Manage_TemporaryBean;
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

public class Manage_TemporaryAccountActivity extends BaseActivity {
    private static final String TAG = "Manage_TemporaryAccountActivity";
    private Toolbar weiraoToolbarTop;
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;

    private XRecyclerView mRecyclerView;
    private Manage_TemporaryAdapter mBookAdapter;
    private int mCurrentAction = ACTION_REFRESH;
    private String mCurrentKeyWord;
    private int mPageSize = 10;
    private int mCurrentPageIndex = 1;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_manage_temporary_account;
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
        mRecyclerView = customFindViewById(R.id.manage_temporaryAccount_recyclerview);
        mBookAdapter = new Manage_TemporaryAdapter(this, new ArrayList<Manage_TemporaryBean>());
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
        weiraoToolbarTop.setTitle("临时账号审批");
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
        String reqUrl = Apis.GetManageType(BaseApplication.user_info.getId(), "4");
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Gson gson = new Gson();
                    List<Manage_TemporaryBean> list = gson.fromJson(
                            jsonObject.getString("data"),
                            new TypeToken<List<Manage_TemporaryBean>>() {
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


}