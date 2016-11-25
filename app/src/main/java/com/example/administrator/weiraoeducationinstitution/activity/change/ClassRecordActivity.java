package com.example.administrator.weiraoeducationinstitution.activity.change;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.manage.havingclass.HavingClassOneAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.SubjectBean;
import com.example.administrator.weiraoeducationinstitution.bean.manage.havingclass.HavingClassOneBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timeselector.Utils.TextUtil;

/**
 * Created by waycubeoxa on 16/9/22.上课记录审核查询
 */
public class ClassRecordActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar weiraoToolbarTop;
    private XRecyclerView mRecyclerView;
    private HavingClassOneAdapter mBookAdapter;

    private TimePickerDialog timeDialog1, timeDialog2;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat ssff = new SimpleDateFormat("yyyy-MM-dd");
    private long twoYears = 20L * 365 * 1000 * 60 * 60 * 24L;

    private TextView sj1, sj2;
    private String sjStr1, sjStr2;
    private EditText className, state, teacherName;
    private LinearLayout pop1, pop2;//课程名称  审核状态
    private static int dialogIndex1 = -1;
    private static int dialogIndex2 = -1;

    private Button check;

    private String getId;

    private String[] items;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.change_classrecord_search;
    }

    @Override
    protected void init() {
        getId = getIntent().getStringExtra("the_association_id");
    }

    @Override
    protected void initView() {
        weiraoToolbarTop = customFindViewById(R.id.weirao_toolbar_top);
        weiraoToolbarTop.setTitle("上课记录查询");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pop1 = customFindViewById(R.id.all_student_pop1);
        pop2 = customFindViewById(R.id.all_student_pop2);
        check = customFindViewById(R.id.all_student_check);
        className = customFindViewById(R.id.all_student_subject);
        teacherName = customFindViewById(R.id.all_student_name);
        state = customFindViewById(R.id.all_student_course);

        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        mRecyclerView = customFindViewById(R.id.change_classrecord_recycleview);
        mBookAdapter = new HavingClassOneAdapter(this, new ArrayList<HavingClassOneBean>());
        mRecyclerView.setAdapter(mBookAdapter);
        mRecyclerView.setLayoutManager(LayoutManager);


        sj1 = customFindViewById(R.id.all_student_selectDateStart);
        sj2 = customFindViewById(R.id.all_student_selectDateEnd);
        timeDialog1 = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {


                        Date d = new Date(millseconds);
                        ssff.format(d);
                        sjStr1 = ssff.format(d);
                        sj1.setText(ssff.format(d));
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setYearText("年")
                .setMonthText("月")
                .setTitleStringId("")
                .setDayText("日")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis() - twoYears)
                .setMaxMillseconds(System.currentTimeMillis() + twoYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.weirao_title_color))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.weirao_title_color))
                .setWheelItemTextSize(16)
                .build();
        timeDialog2 = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        Date d = new Date(millseconds);
                        ssff.format(d);
                        sjStr2 = ssff.format(d);
                        sj2.setText(ssff.format(d));
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setYearText("年")
                .setMonthText("月")
                .setTitleStringId("")
                .setDayText("日")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis() - twoYears)
                .setMaxMillseconds(System.currentTimeMillis() + twoYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.weirao_title_color))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.weirao_title_color))
                .setWheelItemTextSize(16)
                .build();

        sj1.setOnClickListener(this);
        sj2.setOnClickListener(this);
        pop1.setOnClickListener(this);
        pop2.setOnClickListener(this);
        check.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getSubject();

        mRecyclerView.setPullRefreshEnabled(false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_student_selectDateStart:
                timeDialog1.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.all_student_selectDateEnd:
                timeDialog2.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.all_student_pop1:
                showPop1();
                break;
            case R.id.all_student_pop2:
                showPop2();
                break;
            case R.id.all_student_check:
                String state = null;
                if (dialogIndex2 != -1) {
                    state = String.valueOf(dialogIndex2);
                }
                checkOK(state, className.getText().toString(),
                        teacherName.getText().toString(), sj1.getText().toString(), sj2.getText().toString());
                break;
        }
    }

    private String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }


    private void showPop1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择查询课程名称");
        builder.setSingleChoiceItems(items, dialogIndex1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialogIndex1 = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                className.setText(items[dialogIndex1]);
                dialog.dismiss();
            }
        });
        builder.create().show();

    }

    private void showPop2() {
        final String[] items = {"未审核", "通过", "拒绝"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择查询审核状态");
        builder.setSingleChoiceItems(items, dialogIndex2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialogIndex2 = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                state.setText(items[dialogIndex2]);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    private void checkOK(String status, String courseName, String teacher, String startDate, String endDate) {

        final String reqUrl = Apis.GetClassCheck(getId, status, courseName, teacher, startDate, endDate);
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
                        List<HavingClassOneBean> list = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<List<HavingClassOneBean>>() {
                                }.getType());
                        mBookAdapter.clear();
                        mBookAdapter.addAll(list);
                        mRecyclerView.refreshComplete();
                        mRecyclerView.setLoadingMoreEnabled(false);
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
            }
        });
    }

    private void getSubject() {
        String reqUrl = Apis.GetSubjectOfGroup(getId);
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
                        List<SubjectBean> list = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<List<SubjectBean>>() {
                                }.getType());

                        int size = list.size();
                        items = new String[size];
                        if (size == 0) {
                            ToastUtils.getInstance().showToast("该机构暂无查询课程");
                            pop1.setEnabled(false);
                            return;
                        }
                        for (int i = 0; i < size; i++) {
                            items[i] = list.get(i).getName();
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
}
