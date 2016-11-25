package com.example.administrator.weiraoeducationinstitution.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.FundAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.FundBean;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FundActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "FundActivity";
    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;
    private Button drawMoney;

    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;

    private XRecyclerView mRecyclerView;
    private FundAdapter mBookAdapter;
    private int mCurrentAction = ACTION_REFRESH;
    private String mCurrentKeyWord;
    private int mPageSize = 10;
    private int mCurrentPageIndex = 1;

    private String allbalance = null;
    private TextView income, balance_now, balance_kit, expense_money;//收入,当前余额，可提现余额，累计提现(开销);

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_fund;
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
        weiraoToolbarTop.setTitle("资金管理");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        drawMoney = customFindViewById(R.id.fund_draw_money);
        income = customFindViewById(R.id.fund_income);
        balance_now = customFindViewById(R.id.fund_balance_now);
        balance_kit = customFindViewById(R.id.fund_balance_kit);
        expense_money = customFindViewById(R.id.fund_expense);
        mRecyclerView = customFindViewById(R.id.fund_recyclerview);
        mBookAdapter = new FundAdapter(this, new ArrayList<FundBean>());
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
        drawMoney.setOnClickListener(this);
    }

    private void getData() {
        String reqUrl = Apis.GetFinance(BaseApplication.user_info.getId());
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
                        FundObject fo = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<FundObject>() {
                                }.getType());
                        if(fo==null){
                            return;
                        }
                        if (fo.getRecordList() != null) {
                            mBookAdapter.addAll(fo.getRecordList());
                        }
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        if (fo.getIncome() != null) {
                            income.setText(decimalFormat.format(Float.valueOf(fo.getIncome())));
                        } else if (fo.getBalance() != null) {
                            balance_now.setText(decimalFormat.format(Float.valueOf(fo.getBalance())));
                            balance_kit.setText(decimalFormat.format(Float.valueOf(fo.getBalance())));
                            allbalance = decimalFormat.format(Float.valueOf(fo.getBalance()));
                        } else if (fo.getExpense() != null) {
                            expense_money.setText(decimalFormat.format(Float.valueOf(fo.getExpense())));
                        }
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
            case R.id.fund_draw_money:
                Bundle bundle = new Bundle();
                bundle.putString("the_fund_allbalance", allbalance);
                startActivityWithExtras(FundDrawMonyActivity.class, bundle);
                break;
        }
    }

    @Override
    protected void onRestart() {
        switchAction(ACTION_REFRESH);
        super.onRestart();
    }

    class FundObject {
        //        "income": null,
//                "expense": "200.00",
//                "balance": "600.00"
        private List<FundBean> recordList;
        private String income;//收入
        private String expense;//开销
        private String balance;//余额

        public List<FundBean> getRecordList() {
            return recordList;
        }

        public void setRecordList(List<FundBean> recordList) {
            this.recordList = recordList;
        }

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getExpense() {
            return expense;
        }

        public void setExpense(String expense) {
            this.expense = expense;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }
    }
}
