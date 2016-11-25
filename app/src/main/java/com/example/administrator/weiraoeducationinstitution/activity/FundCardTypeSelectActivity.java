package com.example.administrator.weiraoeducationinstitution.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.BankCardSelectAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.BankCardSelectBean;
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

public class FundCardTypeSelectActivity extends BaseActivity {

    @Bind(R.id.include_setting_introduction_back)
    ImageView back;
    @Bind(R.id.include_setting_introduction_title)
    TextView title;
    @Bind(R.id.include_setting_introduction_ok)
    TextView ok;

    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;
    private int mCurrentAction = ACTION_REFRESH;
    private XRecyclerView mRecyclerView;
    private BankCardSelectAdapter mAdapter;

    private String mCurrentKeyWord;
    private int mPageSize = 10;
    private int mCurrentPageIndex = 1;

    public static String indexId = null;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_fund_card_type_select;
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
        mRecyclerView = customFindViewById(R.id.bankcardtype_recyclerview);
        title.setText("选择银行卡");
        ok.setText("");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new BankCardSelectAdapter(this, new ArrayList<BankCardSelectBean>());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
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
        mAdapter.setBankCardCheckedClick(new BankCardSelectAdapter.BankCardCheckedClick() {
            @Override
            public void setBankcard(int position) {
                if (FundDrawMonyActivity.bankcard != null) {
                    if (mAdapter.getItemName(position).equals("使用新卡提现")) {
                        startActivityWithoutExtras(BankCardAddActivity.class);
                    } else {
                        String str = mAdapter.getItemBankNumber(position);
                        String ss = str.substring(str.length() - 4, str.length());
                        FundDrawMonyActivity.bankcard.setText(mAdapter.getItemName(position) + "(" + ss + ")");
                        FundDrawMonyActivity.bankcardID = mAdapter.getItemBankID(position);
                        indexId = mAdapter.getItemBankID(position);
                    }
                }
                finish();
            }
        });

    }

    private void getData() {
        String reqUrl = Apis.GetCashAccount(BaseApplication.user_info.getId());
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Gson gson = new Gson();
                    List<BankCardSelectBean> list = gson.fromJson(
                            jsonObject.getString("data"),
                            new TypeToken<List<BankCardSelectBean>>() {
                            }.getType());
                    BankCardSelectBean newCard = new BankCardSelectBean();
                    newCard.setBankName("使用新卡提现");
                    newCard.setId("9998");
                    list.add(newCard);
                    mAdapter.addAll(list);
                    loadComplete();
                    indexId = list.get(0).getId();
                    mRecyclerView.setLoadingMoreEnabled(false);
                    mRecyclerView.setPullRefreshEnabled(false);
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

    @Override
    protected void onDestroy() {
        indexId = null;
        super.onDestroy();
    }
}
