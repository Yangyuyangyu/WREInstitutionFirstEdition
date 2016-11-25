package com.example.administrator.weiraoeducationinstitution.activity.manage.dynamic;


import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;


import butterknife.Bind;
import butterknife.ButterKnife;

public class DynamicDetail extends BaseActivity {
    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;

    private String[] the_dynamicId = new String[2], the_associationId = new String[2], the_association_manageId = new String[2];
    private int isDynamic = 0;//是动态id则为0，是社团计划为1不显示图片,是管理制度为2不显示图片有标题时间

    private WebView introduction;
    private TextView title, time;
    private ImageView img;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_dynamic_detail;
    }

    @Override
    protected void init() {
        the_dynamicId = getIntent().getStringArrayExtra("the_dynamic_id");
        the_associationId = getIntent().getStringArrayExtra("the_association_id");
        the_association_manageId = getIntent().getStringArrayExtra("the_association_manage_id");
        if (the_dynamicId != null && the_associationId == null) {
            isDynamic = 0;
        } else if (the_dynamicId == null && the_associationId != null) {
            isDynamic = 1;
        } else if (the_dynamicId == null && the_associationId == null && the_association_manageId != null) {
            isDynamic = 2;
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        if (isDynamic == 0) {
            weiraoToolbarTop.setTitle(the_dynamicId[0] + "动态详情");
        } else if (isDynamic == 1) {
            weiraoToolbarTop.setTitle(the_associationId[0] + "训练计划");
        } else if (isDynamic == 2) {
            weiraoToolbarTop.setTitle(the_association_manageId[0] + "管理制度");
        }
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        introduction = customFindViewById(R.id.dynamic_detail_web);
        introduction.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        introduction.setBackgroundColor(Color.TRANSPARENT);
        title = customFindViewById(R.id.dynamic_detail_title);
        time = customFindViewById(R.id.dynamic_detail_time);
        img = customFindViewById(R.id.dynamic_detail_img);
    }

    @Override
    protected void initData() {
        if (isDynamic == 0) {
            getDynamicData();
        } else if (isDynamic == 1) {
            img.setVisibility(View.GONE);
            getAssociationData();
        } else if (isDynamic == 2) {
            img.setVisibility(View.GONE);
            getManageData();
        }
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
            introduction.loadDataWithBaseURL(strUrl.toString(), html.toString(), "text/html", "utf-8", null);
        }
    }

    //动态详情
    private void getDynamicData() {
        String reqUrl = Apis.GetNewsDetail(the_dynamicId[1]);
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Gson gson = new Gson();
                    dynamicDetail dd = gson.fromJson(
                            jsonObject.getString("data"),
                            new TypeToken<dynamicDetail>() {
                            }.getType());
                    webText(dd.getDetail());
                    title.setText(dd.getName());
                    time.setText(dd.getTime());
                    if (!dd.getImg().equals("")) {
                        HttpUtils.getInstance().loadImage(dd.getImg(), img);
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

    //课程规划详情
    private void getAssociationData() {
        String reqUrl = Apis.GetManageType(the_associationId[1], "2");
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Gson gson = new Gson();
                    AssociationDetail ad = gson.fromJson(
                            jsonObject.getString("data"),
                            new TypeToken<AssociationDetail>() {
                            }.getType());
                    webText(ad.getPlan());
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

    //管理制度详情
    private void getManageData() {
        String reqUrl = Apis.GetManageType(the_association_manageId[1], "11");
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Gson gson = new Gson();
                    ManageSysDetail ms = gson.fromJson(
                            jsonObject.getString("data"),
                            new TypeToken<ManageSysDetail>() {
                            }.getType());
                    webText(ms.getDetail());
                    title.setText(ms.getTitle());
                    time.setText(ms.getTime());
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
     * 动态详情
     */
    class dynamicDetail {
        //        "id": "8",
//                "name": "\u793e\u56e2\u52a8\u60011",
//                "send_to": "0",
//                "brief": "\u52a8\u60011",
//                "img": "http:\/\/192.168.10.122\/weirao\/public\/upload\/images\/admin\/1\/201603\/11\/1457676574.jpeg",
//                "detail": " \u5728v\u7eed\u5199\u77401<\/p>",
//                "time": "2016-03-11 14:15:55",
//                "group": "6",
//                "type": null,
//                "group_name": "\u5305\u5b50\u793e"
        private String id;
        private String name;
        private String time;
        private String detail;
        private String img;
        private String brief;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }
    }

    /**
     * 课程规划详情
     */
    class AssociationDetail {
        private String plan;

        public String getPlan() {
            return plan;
        }

        public void setPlan(String plan) {
            this.plan = plan;
        }
    }

    /**
     * 管理制度详情
     */

    class ManageSysDetail {
        //        title：标题
//        detail：详情，含html标签的字符串
//        time：添加时间
        private String title;
        private String detail;
        private String time;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
