package com.example.administrator.weiraoeducationinstitution.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.BankCardAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.BankCardBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.example.administrator.weiraoeducationinstitution.view.TipsDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BankCardActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.include_setting_introduction_back)
    ImageView includeSettingIntroductionBack;
    @Bind(R.id.include_setting_introduction_title)
    TextView includeSettingIntroductionTitle;
    @Bind(R.id.include_setting_introduction_ok)
    TextView includeSettingIntroductionOk;
    private TipsDialog dialog;
    private LinearLayout addLL, removeLL;
    public static boolean isEdit = false;

    private XRecyclerView mRecyclerView;
    private BankCardAdapter mAdapter;
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;
    private int mCurrentAction = ACTION_REFRESH;
    private String mCurrentKeyWord;
    private int mPageSize = 10;
    private int mCurrentPageIndex = 1;
    public static HashMap<Integer, String> map_carId = null;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_bank_card;
    }

    @Override
    protected void init() {
        map_carId = new HashMap<Integer, String>();
        FinishUitl.activityList.add(this);
    }

    @Override
    protected void initData() {
        mRecyclerView.setRefreshing(true);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        dialog = new TipsDialog(this);
        addLL = customFindViewById(R.id.bankcard_addLL);
        removeLL = customFindViewById(R.id.bankcard_removeLL);

        mRecyclerView = customFindViewById(R.id.bankcard_recyclerview);
        includeSettingIntroductionTitle.setText("银行卡");
        includeSettingIntroductionOk.setText("解绑");

        includeSettingIntroductionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        includeSettingIntroductionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addLL.getVisibility() == View.VISIBLE) {
                    addLL.setVisibility(View.GONE);
                    removeLL.setVisibility(View.VISIBLE);
                    includeSettingIntroductionOk.setText("取消");
                    mAdapter.notifyDataSetChanged();
                    isEdit = !isEdit;
                } else {
                    includeSettingIntroductionOk.setText("解绑");
                    addLL.setVisibility(View.VISIBLE);
                    removeLL.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();
                    isEdit = !isEdit;
                }

            }
        });

        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        mAdapter = new BankCardAdapter(this, new ArrayList<BankCardBean>());
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
        addLL.setOnClickListener(this);
        removeLL.setOnClickListener(this);
    }

    private void getData() {
        String reqUrl = Apis.GetMyCards(BaseApplication.user_info.getId());
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
                        List<BankCardBean> list = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<List<BankCardBean>>() {
                                }.getType());
                        mAdapter.addAll(list);
                        loadComplete();
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
                mAdapter.clear();
                mCurrentPageIndex = 1;
                break;
            case ACTION_LOAD_MORE:
                mCurrentPageIndex++;
                break;
        }
        getData();
    }

    private void removeCard(String id) {
        String reqUrl = Apis.GetDelCard(id);
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
                ToastUtils.getInstance().showToast(e.getMessage());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bankcard_removeLL:
                dialog.myFunction(new TipsDialog.DialogCallBack() {
                    @Override
                    public void setContent(TextView v) {
                        v.setText("亲!解除绑定将不能进行提款操作。");
                    }

                    @Override
                    public void setConfirmOnClickListener(Button btn) {
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                StringBuilder idStr = new StringBuilder();
                                for (int i = 0; i < map_carId.size(); i++) {
                                    idStr.append(map_carId.get(i));
                                    if (i != map_carId.size() - 1) {
                                        idStr.append(",");
                                    }
                                }
                                removeCard(idStr.toString());
                                includeSettingIntroductionOk.setText("解绑");
                                addLL.setVisibility(View.VISIBLE);
                                removeLL.setVisibility(View.GONE);
                                mAdapter.notifyDataSetChanged();
                                isEdit = !isEdit;
                                dialog.dissMiss();
                            }
                        });
                    }
                });
                break;
            case R.id.bankcard_addLL:
                startActivityWithoutExtras(BankCardAddActivity.class);
                break;
        }
    }

    @Override
    protected void onRestart() {
        switchAction(ACTION_REFRESH);
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        isEdit = false;
        map_carId = null;
        super.onDestroy();
    }
}
