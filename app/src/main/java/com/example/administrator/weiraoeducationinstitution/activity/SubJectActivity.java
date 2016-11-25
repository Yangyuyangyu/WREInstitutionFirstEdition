package com.example.administrator.weiraoeducationinstitution.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.SubjectAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.SubjectBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
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

import butterknife.Bind;
import butterknife.ButterKnife;

public class SubJectActivity extends BaseActivity {
    private static final String TAG = "SubJectActivity";

    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;

    private XRecyclerView mRecyclerView;
    private SubjectAdapter mBookAdapter;
    private int mCurrentAction = ACTION_REFRESH;
    private String mCurrentKeyWord;
    private int mPageSize = 10;
    private int mCurrentPageIndex = 1;

    private String isAssociationId = null;//判断是否是社团科目还是所有科目

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_sub_ject;
    }

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
        isAssociationId = getIntent().getStringExtra("the_association_id");
    }

    @Override
    protected void initData() {
        mRecyclerView.setRefreshing(true);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        mRecyclerView = customFindViewById(R.id.subject_recyclerview);
        mBookAdapter = new SubjectAdapter(this, new ArrayList<SubjectBean>());
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
        weiraoToolbarTop.setTitle("科目");
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
        String reqUrl;
        if (isAssociationId != null) {  //通过点击社团的查看科目进入
            reqUrl = Apis.GetSubjectOfGroup(isAssociationId);
        } else {
            reqUrl = Apis.GetMySubject(BaseApplication.user_info.getId());
        }

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
                        List<SubjectBean> list = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<List<SubjectBean>>() {
                                }.getType());
                        mBookAdapter.addAll(list);
                        loadComplete();
                        mRecyclerView.setLoadingMoreEnabled(false);
                    } else {
                        mRecyclerView.refreshComplete();
                        ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
                    }

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
    protected void onDestroy() {
        isAssociationId = null;
        super.onDestroy();
    }
}
