package com.example.administrator.weiraoeducationinstitution.activity.search;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.manage.telephone.TelephoneOneAdapter;
import com.example.administrator.weiraoeducationinstitution.adapter.repair.RepairOneAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.manage.RepairOneBean;
import com.example.administrator.weiraoeducationinstitution.bean.manage.TelephoneOneBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.example.administrator.weiraoeducationinstitution.view.ClearEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import timeselector.Utils.TextUtil;

/**
 * 搜索
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private XRecyclerView mRecyclerView;
    private ImageView back;
    private TextView type;
    private ClearEditText input;

    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;
    private int mCurrentAction = ACTION_REFRESH;

    private static int typeInt = 0;
    private static int dialogIndex=0;
    //乐器维修
    private RepairOneAdapter mRepairAdapter;
    //电话追访
    private TelephoneOneAdapter mTeleAdapter;

    private String theRepairOrTel;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_search;
    }

    @Override
    protected void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            theRepairOrTel = bundle.getString("isRepairOrTel");
        }

    }

    @Override
    protected void initView() {
        mRecyclerView = customFindViewById(R.id.search_recyclerView);
        back = customFindViewById(R.id.search_back);
        type = customFindViewById(R.id.search_type);
        input = customFindViewById(R.id.search_input);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        mRepairAdapter = new RepairOneAdapter(this, new ArrayList<RepairOneBean>());
        mTeleAdapter = new TelephoneOneAdapter(this, new ArrayList<TelephoneOneBean>());
        if (theRepairOrTel.equals("thisIsRepair")) {
            mRecyclerView.setAdapter(mRepairAdapter);
            type.setText("维修人");
            typeInt = 1;
        } else if (theRepairOrTel.equals("thisIsTel")) {
            mRecyclerView.setAdapter(mTeleAdapter);
            type.setText("学生姓名");
            input.setHint("追访记录查询");
            typeInt = 4;
        }

        mRecyclerView.setLayoutManager(LayoutManager);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtil.isEmpty(input.getText().toString())) {
                    mRepairAdapter.clear();
                    mTeleAdapter.clear();
                    getData(typeInt, s.toString());
                } else {
                    mRepairAdapter.clear();
                    mTeleAdapter.clear();
                    mRecyclerView.setVisibility(View.INVISIBLE);
                }
            }
        });
        back.setOnClickListener(this);
        type.setOnClickListener(this);

    }

    private void getData(int type, String content) {
        String reqUrl = Apis.GetSearch(type, content);
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
                        if (theRepairOrTel.equals("thisIsRepair")) {
                            List<RepairOneBean> list = gson.fromJson(
                                    jsonObject.getString("data"),
                                    new TypeToken<List<RepairOneBean>>() {
                                    }.getType());
                            mRepairAdapter.addAll(list);
                        } else if (theRepairOrTel.equals("thisIsTel")) {
                            List<TelephoneOneBean> listTele = gson.fromJson(
                                    jsonObject.getString("data"),
                                    new TypeToken<List<TelephoneOneBean>>() {
                                    }.getType());
                            mTeleAdapter.addAll(listTele);
                        }
                        loadComplete();
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mRecyclerView.setLoadingMoreEnabled(false);
                        mRecyclerView.setPullRefreshEnabled(false);
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

    private void loadComplete() {
        if (mCurrentAction == ACTION_REFRESH)
            mRecyclerView.refreshComplete();
        if (mCurrentAction == ACTION_LOAD_MORE)
            mRecyclerView.loadMoreComplete();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_back:
                finish();
                break;
            case R.id.search_type:
                if (theRepairOrTel.equals("thisIsRepair")) {
                    showPopRepair();
                } else if (theRepairOrTel.equals("thisIsTel")) {
                    showPopTel();
                }

                break;
        }
    }

    private void showPopRepair() {
        final String[] items = {"维修人", "科目", "维修器材"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择乐器维修查询条件");
//        builder.setIcon(R.mipmap.ic_launcher);
        builder.setSingleChoiceItems(items, dialogIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialog.dismiss();
                switch (items[which]) {
                    case "维修人":
                        typeInt = 1;
                        type.setText("维修人");
                        dialogIndex=0;
                        break;
                    case "科目":
                        typeInt = 2;
                        type.setText("科目");
                        dialogIndex=1;
                        break;
                    case "维修器材":
                        typeInt = 3;
                        type.setText("维修器材");
                        dialogIndex=2;
                        break;
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void showPopTel() {
        final String[] items = {"学生姓名", "所在机构/学校"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择追访记录查询条件");
//        builder.setIcon(R.mipmap.ic_launcher);
        builder.setSingleChoiceItems(items, dialogIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialog.dismiss();
                switch (items[which]) {
                    case "学生姓名":
                        typeInt = 4;
                        type.setText("学生姓名");
                        dialogIndex=0;
                        break;
                    case "所在机构/学校":
                        typeInt = 5;
                        type.setText("所在机构/学校");
                        dialogIndex=1;
                        break;
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onDestroy() {
        typeInt = 0;
        super.onDestroy();
    }
}
