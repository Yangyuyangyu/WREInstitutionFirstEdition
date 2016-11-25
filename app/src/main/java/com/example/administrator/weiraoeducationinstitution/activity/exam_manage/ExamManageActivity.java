package com.example.administrator.weiraoeducationinstitution.activity.exam_manage;


import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.ExamMenueAdapter;
import com.example.administrator.weiraoeducationinstitution.adapter.course_pop.Course_PopAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AllCourseBean;
import com.example.administrator.weiraoeducationinstitution.bean.ExamMenueBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import timeselector.TimeSelector;

public class ExamManageActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;
    private TimeSelector timeSelector1, timeSelector2;
    private TextView timeStart, timeEnd, name;
    private Button ok;
    private String the_course_id;

    private LinearLayout pop;
    private PopupWindow popWindow;// 弹窗
    private Course_PopAdapter mAdapter;//课程adapter
    private ExamMenueAdapter examMenueAdapter;
    private ListView pop_list;// 弹窗list


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_exam_manage;
    }

    @Override
    protected void init() {
        mAdapter = new Course_PopAdapter(ExamManageActivity.this, new ArrayList<AllCourseBean>());
    }

    @Override
    protected void initData() {
        getDrawerCourse();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        weiraoToolbarTop.setTitle("考试管理");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        examMenueAdapter = new ExamMenueAdapter(this, new ArrayList<ExamMenueBean>());
        pop = customFindViewById(R.id.exam_manage_popLL);
        timeStart = customFindViewById(R.id.exam_manage_selectDateStart);
        timeEnd = customFindViewById(R.id.exam_manage_selectDateEnd);
        name = customFindViewById(R.id.exam_manage_add_name);
        ok = customFindViewById(R.id.exam_manage_add_ok);
        timeEnd.setOnClickListener(this);
        timeStart.setOnClickListener(this);
        pop.setOnClickListener(this);
        ok.setOnClickListener(this);

        timeSelector1 = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                timeStart.setText(time);
            }
        }, "2016-01-01 00:00", "2022-01-01 00:00");
        timeSelector1.setScrollUnit(TimeSelector.SCROLLTYPE.YEAR, TimeSelector.SCROLLTYPE.MONTH, TimeSelector.SCROLLTYPE.DAY, TimeSelector.SCROLLTYPE.HOUR, TimeSelector.SCROLLTYPE.MINUTE);

        timeSelector2 = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                timeEnd.setText(time);
            }
        }, "2016-01-01 00:00", "2022-01-01 00:00");
        timeSelector2.setScrollUnit(TimeSelector.SCROLLTYPE.YEAR, TimeSelector.SCROLLTYPE.MONTH, TimeSelector.SCROLLTYPE.DAY, TimeSelector.SCROLLTYPE.HOUR, TimeSelector.SCROLLTYPE.MINUTE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exam_manage_selectDateStart:
                timeSelector1.show();
                break;
            case R.id.exam_manage_selectDateEnd:
                timeSelector2.show();
                break;
            case R.id.exam_manage_popLL:
                showPopwindown();
                break;
            case R.id.exam_manage_add_ok:
                String str = timeEnd.getText().toString();
                String endTimeStr = str.substring(5, str.length());
                add(BaseApplication.user_info.getId(), the_course_id, timeStart.getText().toString() + "~" + endTimeStr);
                break;
        }
    }

    private void showPopwindown() {
        if (popWindow == null) {
            View view = getLayoutInflater().inflate(
                    R.layout.pop_exam_menue, null);
            popWindow = new PopupWindow(view,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            pop_list = (ListView) view
                    .findViewById(R.id.pop_exam_list);
            pop_list.setAdapter(mAdapter);
            pop_list.setItemsCanFocus(false);
            pop_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            pop_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    popWindow.dismiss();
                    the_course_id = mAdapter.getItemCourseId(position);
                    name.setText(mAdapter.getItemName(position));
                }
            });
        }
        ColorDrawable cd = new ColorDrawable(0x000000);
        popWindow.setBackgroundDrawable(cd);
        popWindow.setOutsideTouchable(true);
        popWindow.setFocusable(true);
        popWindow.showAsDropDown(pop);
        popWindow.setAnimationStyle(R.style.pop_anim);
        popWindow.update();
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                popWindow = null;
            }
        });

    }

    /**
     * 获取课程列表
     */
    private void getDrawerCourse() {
        String reqUrl = Apis.GetMyCourse(BaseApplication.user_info.getId());
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Gson gson = new Gson();
                    List<AllCourseBean> list = gson.fromJson(
                            jsonObject.getString("data"),
                            new TypeToken<List<AllCourseBean>>() {
                            }.getType());
                    mAdapter.changeManagerData(list);
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

    private void add(String id, String courseId, String time) {
        String reqUrl = Apis.GetAddExam(id, courseId, time);
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










