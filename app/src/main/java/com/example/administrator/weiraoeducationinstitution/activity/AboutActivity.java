package com.example.administrator.weiraoeducationinstitution.activity;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.MySnackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;

    private ImageView img;
    private TextView telephone;
    private RelativeLayout telePhoneRe;
    private WebView detail;
    private String teleNumber;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        weiraoToolbarTop.setTitle("关于我们");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        telePhoneRe = customFindViewById(R.id.about_us_telephoneRe);
        telephone = customFindViewById(R.id.about_us_telephone);
        detail = customFindViewById(R.id.about_us_detail);
        img = customFindViewById(R.id.about_us_img);
        detail.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        detail.setBackgroundColor(Color.TRANSPARENT);
        telePhoneRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + teleNumber));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
    }

    @Override
    protected void initData() {
        aboutUs();
    }

    private void aboutUs() {
        String reqUrl = Apis.GetAboutUs;
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
                        AboutUsBean au = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<AboutUsBean>() {
                                }.getType());
                        if (au == null) {
                            return;
                        }
                        telephone.setText("客服热线: " + au.getTelephone());
                        teleNumber = au.getTelephone();
                        webText(au.getDetail());
                    } else {
                        MySnackbar.getmInstance().showMessage(customFindViewById(R.id.forgetPwd_btn), jsonObject.getString("msg"));
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

    private void webText(String str) {
        if (str != null) {
            String strUrl = Apis.WeiRao_http;
            StringBuilder html = new StringBuilder();
            html.append("<html>");
            html.append("<head>");
            html.append("<title>活动简介</title>");
            html.append("<style>img{width:100% !important;}</style></head>");
            html.append("<body>");
            html.append(str);
            html.append("</body>");
            html.append("</html>");
            detail.loadDataWithBaseURL(strUrl.toString(), html.toString(), "text/html", "utf-8", null);
        }
    }

    class AboutUsBean {
        //        "telephone": "400-88-20",
//                "detail": " 正
        private String telephone;
        private String detail;

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }
}
