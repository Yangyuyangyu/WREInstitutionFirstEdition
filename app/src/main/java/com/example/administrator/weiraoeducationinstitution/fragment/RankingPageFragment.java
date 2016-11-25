package com.example.administrator.weiraoeducationinstitution.fragment;

import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.adapter.RankingAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AssociationBean;
import com.example.administrator.weiraoeducationinstitution.bean.ExamRankingBean;
import com.example.administrator.weiraoeducationinstitution.bean.RankingBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.fragment.base.BaseFragment;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.example.administrator.weiraoeducationinstitution.view.PopView_Ranking;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/4/28. 排名
 */
public class RankingPageFragment extends BaseFragment implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = "RankingPageFragment";
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;
    private TextView includeTitleText, filter, synthetic;
    private ImageView arrow;
    private RelativeLayout syntheticRe;
    private NavigationView rankingNavigationView;
    private DrawerLayout rankingDrawerLayout;
    private MenuItem mPreMenuItem;

    private XRecyclerView mRecyclerView;
    private RankingAdapter mBookAdapter;
    private int mCurrentAction = ACTION_REFRESH;
    private String mCurrentKeyWord;
    private int mPageSize = 10;
    private int mCurrentPageIndex = 1;

    private PopView_Ranking pop;
    private int PopIndex = 0;
    private RelativeLayout headRe;

    private static List<AssociationBean> associationList = null;
    private static List<ExamRankingBean> allcourseList = null;
    private static String isAsOrCs = "as";//判断是社团还是课程

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_ranking_page_drawlayout;
    }

    @Override
    protected void init() {
        associationList = new ArrayList<AssociationBean>();
        allcourseList = new ArrayList<ExamRankingBean>();
    }

    @Override
    protected void initView() {
        includeTitleText = customFindViewById(R.id.include_title_text);
        headRe = customFindViewById(R.id.ranking_head_Re);
        filter = customFindViewById(R.id.ranking_filter);
        arrow = customFindViewById(R.id.ranking_jiantou_img);
        synthetic = customFindViewById(R.id.ranking_text);
        syntheticRe = customFindViewById(R.id.ranking_textRe);
        includeTitleText.setText("排名");
        rankingNavigationView = customFindViewById(R.id.ranking_navigation_view);
        rankingDrawerLayout = customFindViewById(R.id.ranking_drawer_layout);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView = customFindViewById(R.id.ranking_recyclerview);
        mBookAdapter = new RankingAdapter(getMContext(), new ArrayList<RankingBean>());
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
        mPreMenuItem = rankingNavigationView.getMenu().getItem(0);
        mPreMenuItem.setChecked(false);
        rankingNavigationView.setNavigationItemSelectedListener(this);
        filter.setOnClickListener(this);
        syntheticRe.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mRecyclerView.setRefreshing(true);
        getDrawerMenue();
        getDrawerCourse();
    }

    private void getData(String id, String type, String groupId, String course) {
        String reqUrl = Apis.GetRank(id, type, groupId, course);
        Log.e("排名", reqUrl);
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
                        RankingObject ro = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<RankingObject>() {
                                }.getType());
                        mBookAdapter.addAll(ro.getList());
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
        getData(BaseApplication.user_info.getId(), null, null, null);
    }

    /**
     * 抽屉item的Onclick
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (null != mPreMenuItem) {
            mPreMenuItem.setChecked(false);
        }
//        switch (item.getItemId()) {
//        }
        item.setChecked(true);
        rankingDrawerLayout.closeDrawer(Gravity.RIGHT);
        mPreMenuItem = item;
        if (isAsOrCs.equals("as")) {
            mBookAdapter.clear();
            getData(BaseApplication.user_info.getId(), "1", String.valueOf(item.getItemId()), null);
        } else if (isAsOrCs.equals("cs")) {
            mBookAdapter.clear();
            getData(BaseApplication.user_info.getId(), "2", null, String.valueOf(item.getItemId()));
        }

        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ranking_filter:
                if (rankingDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {//当前抽屉是打开的，则关闭
                    rankingDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    rankingDrawerLayout.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.ranking_textRe:
                if (pop == null) {
                    int ww = ViewGroup.LayoutParams.WRAP_CONTENT;
                    int hh = ViewGroup.LayoutParams.WRAP_CONTENT;
                    PopRankingLintener paramOnClickListener = new PopRankingLintener();
                    pop = new PopView_Ranking(getActivity(), paramOnClickListener, ww, hh);
                    pop.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (!hasFocus) {
                                pop.dismiss();
                            }
                        }
                    });
                }
                Drawable img_up = getResources().getDrawable(R.drawable.icon_up);
                arrow.setImageDrawable(img_up);
                pop.setPopRankingView(PopIndex);
                pop.setFocusable(true);
                pop.showAsDropDown(headRe, 0, 0);
                pop.update();
                pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        Drawable img_down = getResources().getDrawable(R.drawable.icon_down);
                        arrow.setImageDrawable(img_down);
                    }
                });
                break;
        }
    }

    /**
     * popWindown监听
     */
    class PopRankingLintener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_ranking_dayLL:
                    PopIndex = 1;
                    synthetic.setText("日常排名");
                    pop.dismiss();
                    isAsOrCs = "as";
                    addDrawerAssociation();
                    break;
                case R.id.pop_ranking_examLL:
                    PopIndex = 2;
                    synthetic.setText("考试排名");
                    pop.dismiss();
                    isAsOrCs = "cs";
                    addDrawerCourse();
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 获取社团列表
     */
    private void getDrawerMenue() {
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
                    associationList = list;
                    addDrawerAssociation();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }

    /**
     * 获取课程列表
     */
    private void getDrawerCourse() {
        String reqUrl = Apis.GetExamList(BaseApplication.user_info.getId());
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Gson gson = new Gson();
                    List<ExamRankingBean> list = gson.fromJson(
                            jsonObject.getString("data"),
                            new TypeToken<List<ExamRankingBean>>() {
                            }.getType());
                    allcourseList = list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                ToastUtils.getInstance().showToast(e.getMessage());
            }
        });
    }

    /**
     * 添加抽屉社团列表
     */

    private void addDrawerAssociation() {
        rankingNavigationView.getMenu().clear();
        for (int i = 0; i < associationList.size(); i++) {
            rankingNavigationView.getMenu().add(i, Integer.valueOf(associationList.get(i).getId()), 0, associationList.get(i).getName());
        }

    }

    /**
     * 添加课程列表
     */

    private void addDrawerCourse() {
        rankingNavigationView.getMenu().clear();
        for (int i = 0; i < allcourseList.size(); i++) {
            rankingNavigationView.getMenu().add(associationList.size() + i + 1, Integer.valueOf(allcourseList.get(i).getCourse()), 0, allcourseList.get(i).getName());
        }
    }


    @Override
    public void onDestroy() {
        associationList = null;
        allcourseList = null;
        isAsOrCs = null;
        super.onDestroy();
    }


    class RankingObject {
        private List<RankingBean> list;

        public List<RankingBean> getList() {
            return list;
        }

        public void setList(List<RankingBean> list) {
            this.list = list;
        }
    }
}
