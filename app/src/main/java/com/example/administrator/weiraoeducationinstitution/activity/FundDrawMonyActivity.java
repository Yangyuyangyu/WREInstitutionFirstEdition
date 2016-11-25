package com.example.administrator.weiraoeducationinstitution.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.bean.BankCardSelectBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.MySnackbar;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import timeselector.Utils.TextUtil;

public class FundDrawMonyActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.include_setting_introduction_back)
    ImageView back;
    @Bind(R.id.include_setting_introduction_title)
    TextView title;
    @Bind(R.id.include_setting_introduction_ok)
    TextView ok;

    private TextView alldraw, allRemaining;
    protected static TextView bankcard = null;
    protected static String bankcardID = null;
    private Button drawMoneyBtn;
    private EditText inputMoney;
    private String allBalance = null;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_fund_draw_mony;
    }

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            allBalance = bundle.getString("the_fund_allbalance");
        }
    }

    @Override
    protected void initData() {
        getBankCardData();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        title.setText("提现");
        ok.setText("");
        inputMoney = customFindViewById(R.id.fund_draw_money_inputMoney);
        drawMoneyBtn = customFindViewById(R.id.fund_draw_money_btn);
        allRemaining = customFindViewById(R.id.fund_draw_money_remaining);
        alldraw = customFindViewById(R.id.fund_draw_money_alldraw);
        bankcard = customFindViewById(R.id.fund_draw_money_bankcard);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        drawMoneyBtn.setOnClickListener(this);
        alldraw.setOnClickListener(this);
        bankcard.setOnClickListener(this);

        allRemaining.setText(allBalance);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 提现按钮
             */
            case R.id.fund_draw_money_btn:
                if (TextUtil.isEmpty(inputMoney.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(inputMoney, "请输入现款金额");
                    return;
                }
                if (bankcardID == null) {
                    MySnackbar.getmInstance().showMessage(inputMoney, "请选择银行卡");
                    return;
                }
                drawMoney(inputMoney.getText().toString(), bankcardID, BaseApplication.user_info.getId());
                break;
            /**
             * 全部提现
             */
            case R.id.fund_draw_money_alldraw:
                inputMoney.setText(allRemaining.getText().toString());
                break;
            /**
             * 银行卡选择
             */
            case R.id.fund_draw_money_bankcard:
                startActivityWithoutExtras(FundCardTypeSelectActivity.class);
                break;
        }
    }

    private void getBankCardData() {
        String reqUrl = Apis.GetCashAccount(BaseApplication.user_info.getId());
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
                        List<BankCardSelectBean> list = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<List<BankCardSelectBean>>() {
                                }.getType());
                        String str = list.get(0).getAccount_num();
                        String ss = str.substring(str.length() - 4, str.length());
                        bankcardID = list.get(0).getId();
                        bankcard.setText(list.get(0).getBankName() + "(" + ss + ")");
                    } else {
                        ToastUtils.getInstance().showToast("获取银行卡信息失败");
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


    private void drawMoney(String money, String account, String id) {
        String reqUrl = Apis.GetAddCash(money, account, id);
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
                        Bundle bundle = new Bundle();
                        String[] str = new String[]{bankcard.getText().toString(), inputMoney.getText().toString()};
                        bundle.putStringArray("the_draw_money_result", str);
                        startActivityWithExtras(FundDrawMoneySuccessActivity.class, bundle);
                        finish();
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

    @Override
    protected void onDestroy() {
        bankcard = null;
        bankcardID = null;
        super.onDestroy();
    }
}
