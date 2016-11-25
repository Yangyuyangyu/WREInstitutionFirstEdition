package com.example.administrator.weiraoeducationinstitution.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FundDrawMoneySuccessActivity extends BaseActivity {


    @Bind(R.id.include_setting_introduction_back)
    ImageView back;
    @Bind(R.id.include_setting_introduction_title)
    TextView title;
    @Bind(R.id.include_setting_introduction_ok)
    TextView ok;

    private TextView bank, money;
    private String[] getResult_ = new String[2];

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_fund_draw_money_success;
    }

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            getResult_ = bundle.getStringArray("the_draw_money_result");
        }

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        title.setText("提现详情");
        ok.setText("完成");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bank = customFindViewById(R.id.fund_draw_money_result_bank);
        money = customFindViewById(R.id.fund_draw_money_result_money);
        if (getResult_ != null) {
            bank.setText(getResult_[0]);
            money.setText(getResult_[1]);
        }


    }


}
