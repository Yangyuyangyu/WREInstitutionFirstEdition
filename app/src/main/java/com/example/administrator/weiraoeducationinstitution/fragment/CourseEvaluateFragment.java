package com.example.administrator.weiraoeducationinstitution.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.CourseDetailActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.CourseEvalueteAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.CourseEvalueteBean;
import com.example.administrator.weiraoeducationinstitution.fragment.base.BaseFragment;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;


import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/9.
 */
public class CourseEvaluateFragment extends BaseFragment {
    private static final String TAG = "CourseEvaluateFragment";
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;
    private int mCurrentAction = ACTION_REFRESH;
    private XRecyclerView mRecyclerView;
    private CourseEvalueteAdapter mBookAdapter;
    private String mCurrentKeyWord;
    private int mPageSize = 10;
    private int mCurrentPageIndex = 1;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_leave_student;
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
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView = customFindViewById(R.id.askforleave_student_recyclerview);
        mBookAdapter = new CourseEvalueteAdapter(getMContext(), new ArrayList<CourseEvalueteBean>());
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


    private void getData() {
        if (CourseDetailActivity.commentList == null) {
            ToastUtils.getInstance().showToast("暂无评价");
            mRecyclerView.refreshComplete();
            return;
        }
        mBookAdapter.addAll(CourseDetailActivity.commentList);
        loadComplete();
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setPullRefreshEnabled(false);
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
