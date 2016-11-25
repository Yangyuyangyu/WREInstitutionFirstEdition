package com.example.administrator.weiraoeducationinstitution.activity.manage.grade;


import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.MySnackbar;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GradeTwoActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;

    private String getId;

    private List<String> list = new ArrayList<String>();
    private CheckBox[] cb = new CheckBox[5];
    private int[] cbIds = new int[]{R.id.grade_detail_checbox1, R.id.grade_detail_checbox2, R.id.grade_detail_checbox3, R.id.grade_detail_checbox4, R.id.grade_detail_checbox5};
    private Button ok, cancel;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_grade_two;
    }

    @Override
    protected void init() {
        getId = getIntent().getStringExtra("the_grade_association_id");
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        weiraoToolbarTop.setTitle("打分项目");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ok = customFindViewById(R.id.grade_detail_btn_ok);
        cancel = customFindViewById(R.id.grade_detail_btn_cancel);
        for (int i = 0; i < cb.length; i++) {
            cb[i] = customFindViewById(cbIds[i]);
            cb[i].setOnClickListener(this);
        }
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.grade_detail_checbox1:
                if (cb[0].isChecked()) {
                    list.add("1");
                } else {
                    list.remove("1");
                }
                break;
            case R.id.grade_detail_checbox2:
                if (cb[1].isChecked()) {
                    list.add("2");
                } else {
                    list.remove("2");
                }
                break;
            case R.id.grade_detail_checbox3:
                if (cb[2].isChecked()) {
                    list.add("3");
                } else {
                    list.remove("3");
                }
                break;
            case R.id.grade_detail_checbox4:
                if (cb[3].isChecked()) {
                    list.add("4");
                } else {
                    list.remove("4");
                }
                break;
            case R.id.grade_detail_checbox5:
                if (cb[4].isChecked()) {
                    list.add("5");
                } else {
                    list.remove("5");
                }
                break;
            case R.id.grade_detail_btn_ok:
                changeGrade();
                break;
            case R.id.grade_detail_btn_cancel:
                finish();
                break;
        }
    }

    private void getData() {
        String reqUrl = Apis.GetManageType(getId, "7");
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
                        List<GradeItem> list_ = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<List<GradeItem>>() {
                                }.getType());
                        if (list_ != null) {
                            int size = list_.size();
                            for (int i = 0; i < size; i++) {
                                if (!list_.get(i).getId().equals("")) {
                                    cb[Integer.valueOf(list_.get(i).getId()) - 1].setChecked(true);
                                    list.add(list_.get(i).getId());
                                }
                            }
                        }
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

    private void changeGrade() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            str.append(list.get(i));
            if (i != list.size() - 1) {
                str.append(",");
            }
        }
        String reqUrl = Apis.GetScoreItem(getId, str.toString());
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class GradeItem {
        private String id;
        private String name;

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
    }

}
