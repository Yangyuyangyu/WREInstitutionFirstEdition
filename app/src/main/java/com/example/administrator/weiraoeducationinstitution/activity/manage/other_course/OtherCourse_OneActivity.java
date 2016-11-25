package com.example.administrator.weiraoeducationinstitution.activity.manage.other_course;


import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.course_pop.Course_PopAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AllCourseBean;
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
import timeselector.TimeSelector;

public class OtherCourse_OneActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;

    private PopupWindow popWindow;// 弹窗
    private ListView pop_list;// 弹窗list
    private Course_PopAdapter mAdapter;

    private TextView timeStart, t1, t2;
    private TimeSelector timeSelector1;
    private TextView course_name;
    private LinearLayout pop;
    private EditText teacher_content, feed;
    private Button ok, cancel;
    private String getTeachername;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_other_course_one;
    }

    @Override
    protected void init() {
        mAdapter = new Course_PopAdapter(OtherCourse_OneActivity.this, new ArrayList<AllCourseBean>());
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        weiraoToolbarTop.setTitle("其他课");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        course_name = customFindViewById(R.id.other_course_one_course_name);
        pop = customFindViewById(R.id.other_course_one_pop);
        teacher_content = customFindViewById(R.id.other_course_one_teacher_content);
        feed = customFindViewById(R.id.other_course_one_feed);
        ok = customFindViewById(R.id.other_course_one_ok);
        cancel = customFindViewById(R.id.other_course_one_cancel);
        t1 = customFindViewById(R.id.other_course_one_teacher1);
        t2 = customFindViewById(R.id.other_course_one_teacher2);

        timeStart = customFindViewById(R.id.other_course_one_selectDateStart);
        timeStart.setOnClickListener(this);
        pop.setOnClickListener(this);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);

        timeSelector1 = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                timeStart.setText(time);
            }
        }, "2016-01-01 00:00", "2022-01-01 00:00");
        timeSelector1.setScrollUnit(TimeSelector.SCROLLTYPE.YEAR, TimeSelector.SCROLLTYPE.MONTH, TimeSelector.SCROLLTYPE.DAY, TimeSelector.SCROLLTYPE.HOUR, TimeSelector.SCROLLTYPE.MINUTE);


    }

    @Override
    protected void initData() {
        getDrawerCourse();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.other_course_one_selectDateStart:
                timeSelector1.show();
                break;
            case R.id.other_course_one_pop:
                showPopwindown();
                break;
            case R.id.other_course_one_ok:
                if (course_name.getText().toString() == null) {
                    MySnackbar.getmInstance().showMessage(ok, "请选择课程");
                    return;
                } else if (timeStart.getText().toString() == null) {
                    MySnackbar.getmInstance().showMessage(ok, "请选择上课时间");
                    return;
                } else if (teacher_content.getText().toString() == null) {
                    MySnackbar.getmInstance().showMessage(ok, "请输入意见内容");
                    return;
                }
                addOtherCourse(BaseApplication.user_info.getId(), course_name.getText().toString(), timeStart.getText().toString()
                        , teacher_content.getText().toString(), feed.getText().toString(), getTeachername);
                break;
            case R.id.other_course_one_cancel:
                finish();
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
                    getTeachername = mAdapter.getItemTeacher(position);
                    course_name.setText(mAdapter.getItemName(position));
                    t1.setText(mAdapter.getItemTeacher(position));
                    t2.setText(mAdapter.getItemTeacher(position));
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

    /**
     * 添加其他课
     */
    private void addOtherCourse(String id, String name, String time, String advice, String execution, String teacher) {
        String reqUrl = Apis.GetAddOther(id, name, time, advice, execution, teacher);
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
