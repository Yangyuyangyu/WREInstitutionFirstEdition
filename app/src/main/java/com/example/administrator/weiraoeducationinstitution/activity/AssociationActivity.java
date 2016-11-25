package com.example.administrator.weiraoeducationinstitution.activity;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.AssociationAdapter;
import com.example.administrator.weiraoeducationinstitution.adapter.PopAssociationManagerAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AssociationBean;
import com.example.administrator.weiraoeducationinstitution.bean.PopAssociationManagerBean;
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

public class AssociationActivity extends BaseActivity {

    private static final String TAG = "AssociationActivity";
    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;

    private XRecyclerView mRecyclerView;
    private AssociationAdapter mBookAdapter;
    private int mCurrentAction = ACTION_REFRESH;
    private String mCurrentKeyWord;
    private int mPageSize = 10;
    private int mCurrentPageIndex = 1;

    private PopupWindow popWindow;// 弹窗
    private PopAssociationManagerAdapter mPopAdapter;
    private ListView pop_list;// 弹窗list
    private Button cancel, ok;

    public static String the_manage_id = null;
    private String the_association_id;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_association;
    }

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
    }

    @Override
    protected void initData() {
        mRecyclerView.setRefreshing(true);
        getManagerData();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        mRecyclerView = customFindViewById(R.id.association_recyclerview);
        mBookAdapter = new AssociationAdapter(this, new ArrayList<AssociationBean>());
        mPopAdapter = new PopAssociationManagerAdapter(this, new ArrayList<PopAssociationManagerBean>());
        mRecyclerView.setAdapter(mBookAdapter);
        mRecyclerView.setLayoutManager(LayoutManager);
        mBookAdapter.setChangeManagerListener(new AssociationAdapter.ChangeManagerListener() {
            @Override
            public void showPopView(int position) {
                showPopwindown();
                the_association_id = mBookAdapter.getItemAsId(position);
            }
        });
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
        weiraoToolbarTop.setTitle("社团");
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
                    if (jsonObject.getString("code").equals("0")) {
                        Gson gson = new Gson();
                        List<AssociationBean> list = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<List<AssociationBean>>() {
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


    private void showPopwindown() {
        if (popWindow == null) {
            View view = getLayoutInflater().inflate(
                    R.layout.pop_association_change_manager, null);
            popWindow = new PopupWindow(view,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            pop_list = (ListView) view
                    .findViewById(R.id.pop_association_list);
            cancel = (Button) view.findViewById(R.id.pop_association_cancel);
            ok = (Button) view.findViewById(R.id.pop_association_ok);
            pop_list.setAdapter(mPopAdapter);
            pop_list.setItemsCanFocus(false);
            pop_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
        ColorDrawable cd = new ColorDrawable(0x000000);
        popWindow.setBackgroundDrawable(cd);
        backgroundAlpha(0.4f);
        popWindow.setOutsideTouchable(false);
        popWindow.setFocusable(false);
        popWindow.showAtLocation(customFindViewById(R.id.activity_associatioLL), Gravity.BOTTOM, 0, 0);
        popWindow.setAnimationStyle(R.style.pop_anim);
        popWindow.update();
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                popWindow = null;
                backgroundAlpha(1f);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                the_manage_id = null;
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (the_manage_id != null) {
                    changeManage(the_association_id, the_manage_id);
                } else {
                    ToastUtils.getInstance().showToast("请选择管理员");
                }

            }
        });

    }

    private void getManagerData() {
        String reqUrl = Apis.GetMyAdmin(BaseApplication.user_info.getId());
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Gson gson = new Gson();
                    List<PopAssociationManagerBean> list = gson.fromJson(
                            jsonObject.getString("data"),
                            new TypeToken<List<PopAssociationManagerBean>>() {
                            }.getType());
                    mPopAdapter.changeManagerData(list);
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
     * 修改管理员
     */
    private void changeManage(String groupId, String adminId) {
        String reqUrl = Apis.GetSaveAdmin(groupId, adminId);
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("code").equals("0")) {
                        ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
                        popWindow.dismiss();
                        switchAction(ACTION_REFRESH);
                    } else {
                        ToastUtils.getInstance().showToast(jsonObject.getString("msg"));
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

    /**
     * 背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onDestroy() {
        the_manage_id = null;
        super.onDestroy();
    }
}
