package com.example.administrator.weiraoeducationinstitution.activity;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.TimeTableAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.TimeTableBean;
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
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TimeTableActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "TimeTableActivity";
    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;

    private XRecyclerView mRecyclerView;
    private TimeTableAdapter mBookAdapter;
    private int mCurrentAction = ACTION_REFRESH;
    private String mCurrentKeyWord;
    private int mPageSize = 10;
    private int mCurrentPageIndex = 1;

    private int[] btnIds = new int[]{R.id.time_table_btn_1, R.id.time_table_btn_2, R.id.time_table_btn_3,
            R.id.time_table_btn_4, R.id.time_table_btn_5, R.id.time_table_btn_6, R.id.time_table_btn_7};
    private Button[] btn = new Button[7];
    private int selectedIndex = 0;// 选择下标
    private static TimeTableObject couseObject = null;

    private LinearLayout weekLL;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_time_table;
    }

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
        couseObject = new TimeTableObject();
    }

    @Override
    protected void initData() {
        mRecyclerView.setRefreshing(true);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initBtnId();
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        mRecyclerView = customFindViewById(R.id.time_table_recyclerview);
        weekLL = customFindViewById(R.id.include_time_tableLL);
        mBookAdapter = new TimeTableAdapter(this, new ArrayList<TimeTableBean>());
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
        weiraoToolbarTop.setTitle("课程表");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initBtnId() {
        for (int i = 0; i < 7; i++) {
            btn[i] = customFindViewById(btnIds[i]);
            btn[i].setOnClickListener(this);
        }
    }

    private void getData() {
        String reqUrl = Apis.GetCourseList(BaseApplication.user_info.getId());
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
                        TimeTableObject tto = gson.fromJson(
                                jsonObject.toString(),
                                new TypeToken<TimeTableObject>() {
                                }.getType());
                        couseObject = tto;
                        getWeekAndDay();
                        mRecyclerView.setLoadingMoreEnabled(false);
                        mRecyclerView.setPullRefreshEnabled(false);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.time_table_btn_1:
                selectedIndex = 0;
                setBtnBg(v, selectedIndex);
                refreshWeekCourse(0);
                break;
            case R.id.time_table_btn_2:
                selectedIndex = 1;
                setBtnBg(v, selectedIndex);
                refreshWeekCourse(1);
                break;
            case R.id.time_table_btn_3:
                selectedIndex = 2;
                setBtnBg(v, selectedIndex);
                refreshWeekCourse(2);
                break;
            case R.id.time_table_btn_4:
                selectedIndex = 3;
                setBtnBg(v, selectedIndex);
                refreshWeekCourse(3);
                break;
            case R.id.time_table_btn_5:
                selectedIndex = 4;
                setBtnBg(v, selectedIndex);
                refreshWeekCourse(4);
                break;
            case R.id.time_table_btn_6:
                selectedIndex = 5;
                setBtnBg(v, selectedIndex);
                refreshWeekCourse(5);
                break;
            case R.id.time_table_btn_7:
                selectedIndex = 6;
                setBtnBg(v, selectedIndex);
                refreshWeekCourse(6);
                break;
        }

    }

    private void setBtnBg(View v, int selectIndex) {
        if (v.getId() == btnIds[selectIndex]) {
            Drawable bg = getResources().getDrawable(R.drawable.rounded_red_frame_bold);
            btn[selectIndex].setBackgroundDrawable(bg);
            for (int i = 0; i < 7; i++) {
                if (i != selectIndex) {
                    Drawable bg_gone = getResources().getDrawable(R.drawable.rounded_white_frame_gone);
                    btn[i].setBackgroundDrawable(bg_gone);
                }
            }
        }
    }

    private void refreshWeekCourse(int i) {
        mBookAdapter.clear();
        mBookAdapter.addAll(couseObject.getData().get(i).getCourse());
        loadComplete();
    }

    /**
     * 判断今天是周几
     */
    private void getWeekAndDay() {
        Calendar calendar = Calendar.getInstance();
//        //获取当前时间为本月的第几周
//        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        //获取当前时间为本周的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 7;
//            week = week - 1;
        } else {
            day = day - 1;
        }
        Drawable bg = getResources().getDrawable(R.drawable.rounded_red_frame_bold);
        btn[day - 1].setBackgroundDrawable(bg);
        for (int i = 0; i < 7; i++) {
            if (i != day - 1) {
                Drawable bg_gone = getResources().getDrawable(R.drawable.rounded_white_frame_gone);
                btn[i].setBackgroundDrawable(bg_gone);
            }
        }
        refreshWeekCourse(day - 1);
    }


    @Override
    protected void onDestroy() {
        couseObject = null;
        super.onDestroy();
    }

    /**
     * 课程表结构
     */
    //    "data": [
//    {
//        "week": "1",
//            "course": [
//        {

    class TimeTableObject {
        private List<TimeTableData> data;

        public List<TimeTableData> getData() {
            return data;
        }

        public void setData(List<TimeTableData> data) {
            this.data = data;
        }
    }

    class TimeTableData {
        private String week;
        private List<TimeTableBean> course;

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public List<TimeTableBean> getCourse() {
            return course;
        }

        public void setCourse(List<TimeTableBean> course) {
            this.course = course;
        }
    }


}
