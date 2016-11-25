package com.example.administrator.weiraoeducationinstitution.activity;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.MySnackbar;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.example.administrator.weiraoeducationinstitution.view.TipsDialog;
import com.example.administrator.weiraoeducationinstitution.view.XEditText;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timeselector.Utils.TextUtil;

public class BankCardAddActivity extends BaseActivity {

    @Bind(R.id.include_setting_introduction_back)
    ImageView back;
    @Bind(R.id.include_setting_introduction_title)
    TextView title;
    @Bind(R.id.include_setting_introduction_ok)
    TextView ok;
    private TipsDialog dialog;
    private EditText name, bankname;
    private XEditText number;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_bank_card_add;
    }

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        dialog = new TipsDialog(this);
        title.setText("修改");
        ok.setText("绑定");
        name = customFindViewById(R.id.bankcard_add_name);
        number = customFindViewById(R.id.bankcard_add_number);
        bankname = customFindViewById(R.id.bankcard_add_bankname);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.myFunction(new TipsDialog.DialogCallBack() {
                    @Override
                    public void setContent(TextView v) {
                        v.setText("是否放弃绑定银行卡？");
                    }

                    @Override
                    public void setConfirmOnClickListener(Button btn) {
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dissMiss();
                                finish();
                            }
                        });
                    }
                });
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtil.isEmpty(name.getText().toString()) || TextUtil.isEmpty(number.getText().toString()) || TextUtil.isEmpty(bankname.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(bankname, "请您完善信息");
                    return;
                }
                addBankCard(name.getText().toString(), number.getNonSeparatorText().toString(), bankname.getText().toString(), BaseApplication.user_info.getId());
            }
        });

        number.setPattern(new int[]{4, 5, 5, 5, 4});
        number.setSeparator("  ");
    }

    private void addBankCard(String holder, String cardNo, String type, String id) {
        String reqUrl = Apis.GetAddCard(holder, cardNo, type, id);
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
                ToastUtils.getInstance().showToast(e.getMessage());
            }
        });
    }

}
