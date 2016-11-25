package com.example.administrator.weiraoeducationinstitution.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.administrator.weiraoeducationinstitution.activity.student.Student_Select_AddActivity;
import com.example.administrator.weiraoeducationinstitution.activity.student.Student_Select_HadActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.AllStudentAdapter;
import com.example.administrator.weiraoeducationinstitution.adapter.course_pop.Course_PopAdapter;
import com.example.administrator.weiraoeducationinstitution.adapter.course_pop.Subject_PopAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AllCourseBean;
import com.example.administrator.weiraoeducationinstitution.bean.AllStudentBean;
import com.example.administrator.weiraoeducationinstitution.bean.SubjectBean;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.utils.FinishUitl;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.StringUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.example.administrator.weiraoeducationinstitution.utils.ViewUtils;
import com.example.administrator.weiraoeducationinstitution.view.PopView_AllTeacherOrCourse;
import com.example.administrator.weiraoeducationinstitution.view.TipsDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
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

import butterknife.Bind;
import butterknife.ButterKnife;

public class AllStudentActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "AllStudentActivity";
    @Bind(R.id.weirao_toolbar_top)
    Toolbar weiraoToolbarTop;
    @Bind(R.id.nodata_bg_text)
    TextView nodataBgText;
    @Bind(R.id.include_nodata_bgLL)
    LinearLayout includeNodataBgLL;

    private XRecyclerView mRecyclerView;
    private PopView_AllTeacherOrCourse pop;
    private TipsDialog dialog;

    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;

    private AllStudentAdapter mBookAdapter;
    private int mCurrentAction = ACTION_REFRESH;
    private String mCurrentKeyWord;
    private int mPageSize = 10;
    private int mCurrentPageIndex = 1;

    //查询参数
    private LinearLayout checkLL;
    private TextView sj1, sj2;
    private TimePickerDialog timeDialog1, timeDialog2;

    private LinearLayout pop1, pop2;
    private EditText text_subject, text_course, user_name, phone;//科目，课程，姓名,电话
    private Button check;

    private PopupWindow popWindow;// 课程弹窗
    private ListView pop_list;// 课程弹窗list
    private Course_PopAdapter mAdapter;//课程adapter
    private String the_course_id;//课程id

    private PopupWindow popWindow_;// 科目弹窗
    private ListView pop_list_;// 科目弹窗list
    private Subject_PopAdapter mAdapter_;//科目adapter
    private String the_subject_id;//科目id

    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private long twoYears = 20L * 365 * 1000 * 60 * 60 * 24L;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_all_student;
    }

    @Override
    protected void init() {
        FinishUitl.activityList.add(this);
        mAdapter = new Course_PopAdapter(AllStudentActivity.this, new ArrayList<AllCourseBean>());
        mAdapter_ = new Subject_PopAdapter(AllStudentActivity.this, new ArrayList<SubjectBean>());

    }

    @Override
    protected void initData() {
        mRecyclerView.setRefreshing(true);
        getSubjectData();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        dialog = new TipsDialog(this);
        mRecyclerView = customFindViewById(R.id.allstudent_recyclerview);
        weiraoToolbarTop.setTitle("全部学生");
        nodataBgText.setText("暂无学生");
        setSupportActionBar(weiraoToolbarTop);
        weiraoToolbarTop.setNavigationIcon(R.mipmap.icon_back);
        weiraoToolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        mBookAdapter = new AllStudentAdapter(this, new ArrayList<AllStudentBean>());
        mBookAdapter.setPhoneItemClick(new AllStudentAdapter.phoneItemClick() {
            @Override
            public void callOnClick(final String phoneNumber) {
                dialog.myFunction(new TipsDialog.DialogCallBack() {
                    @Override
                    public void setContent(TextView v) {
                        v.setText("是否拨打电话 " + phoneNumber);
                    }

                    @Override
                    public void setConfirmOnClickListener(Button btn) {
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dissMiss();
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
        mRecyclerView.setAdapter(mBookAdapter);
        mRecyclerView.setLayoutManager(LayoutManager);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                switchAction(ACTION_REFRESH);
            }

            @Override
            public void onLoadMore() {
                switchAction(ACTION_LOAD_MORE);
            }
        });
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);


        //查询参数
        checkLL = customFindViewById(R.id.all_student_checkLL);
        sj1 = customFindViewById(R.id.all_student_selectDateStart);
        sj2 = customFindViewById(R.id.all_student_selectDateEnd);
        pop1 = customFindViewById(R.id.all_student_pop1);
        pop2 = customFindViewById(R.id.all_student_pop2);
        text_subject = customFindViewById(R.id.all_student_subject);
        text_course = customFindViewById(R.id.all_student_course);
        user_name = customFindViewById(R.id.all_student_name);
        phone = customFindViewById(R.id.all_student_phone);
        check = customFindViewById(R.id.all_student_check);
        sj1.setOnClickListener(this);
        sj2.setOnClickListener(this);
        pop1.setOnClickListener(this);
        pop2.setOnClickListener(this);
        check.setOnClickListener(this);

        timeDialog1 = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        String text = getDateToString(millseconds);
                        sj1.setText(text);
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
                        String text = getDateToString(millseconds);
                        sj2.setText(text);
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


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_student_selectDateStart:
//                timeSelector1.show();
                timeDialog1.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.all_student_selectDateEnd:
//                timeSelector2.show();
                timeDialog2.show(getSupportFragmentManager(), "year_month_day");
                break;
            //科目pop
            case R.id.all_student_pop1:
                showPopwindown_sub();
                break;
            //课程
            case R.id.all_student_pop2:
                if (the_subject_id == null) {
                    ToastUtils.getInstance().showToastCenter("请先选择科目");
                    return;
                }
                showPopwindown_course();
                break;
            //查询
            case R.id.all_student_check:
                String sj = null, sj_ = null, sub_id = null, courId = null;
                String name = null, mobile = null;
                Date d1 = null, d2 = null;
                if (sj1.getText().toString().trim().length() != 0) {
                    sj = sj1.getText().subSequence(0, 10).toString();

                }
                if (sj2.getText().toString().trim().length() != 0) {
                    sj_ = sj2.getText().subSequence(0, 10).toString();
                }
                try {
                    d1 = StringUtils.ConverToDate(sj);
                    d2 = StringUtils.ConverToDate(sj_);
                } catch (Exception e) {

                }
                if (d1 != null && d2 != null) {
                    if (Math.abs(d1.getTime()) > Math.abs(d2.getTime())) {
                        ToastUtils.getInstance().showToastCenter("开始时间不能大于结束时间");
                        return;
                    }
                }

                if (text_subject.getText().toString().trim().length() != 0) {
                    sub_id = the_subject_id;
                }
                if (text_course.getText().toString().trim().length() != 0) {
                    courId = the_course_id;
                }
                if (!TextUtils.isEmpty(user_name.getText())) {
                    name = user_name.getText().toString();
                }
                if (!TextUtils.isEmpty(phone.getText())) {
                    mobile = phone.getText().toString();
                }
                setCheck(BaseApplication.user_info.getId(), sub_id, courId, sj, sj_, name, mobile);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_allteacher_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbar_action_Add) {
            if (pop == null) {
                int ww = ViewGroup.LayoutParams.WRAP_CONTENT;
                int hh = ViewGroup.LayoutParams.WRAP_CONTENT;
                PopLintener paramOnClickListener = new PopLintener();
                pop = new PopView_AllTeacherOrCourse(AllStudentActivity.this, paramOnClickListener, ww, hh);
                pop.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            pop.dismiss();
                        }
                    }
                });
            }
            pop.setFocusable(true);
            pop.showAsDropDown(weiraoToolbarTop, ViewUtils.getScreenWidth(this), 0);
            pop.update();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * popWindown监听
     */
    class PopLintener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_allTeacherOrCourse_add:
                    startActivityWithoutExtras(Student_Select_AddActivity.class);
                    pop.dismiss();
                    break;
                case R.id.pop_allTeacherOrCourse_had:
                    startActivityWithoutExtras(Student_Select_HadActivity.class);
                    pop.dismiss();
                    break;
                default:
                    break;
            }
        }
    }


    private void getData() {
        String reqUrl = Apis.GetMyStudent(BaseApplication.user_info.getId());
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
                        List<AllStudentBean> list = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<List<AllStudentBean>>() {
                                }.getType());
                        mBookAdapter.addAll(list);
                        loadComplete();
                        mRecyclerView.setLoadingMoreEnabled(false);
                        includeNodataBgLL.setVisibility(View.GONE);
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
                loadComplete();
            }
        });
    }

    private void loadComplete() {
        if (mCurrentAction == ACTION_REFRESH)
            mRecyclerView.refreshComplete();
        if (mCurrentAction == ACTION_LOAD_MORE)
            mRecyclerView.loadMoreComplete();
    }

    private void switchAction(int action) {
        mCurrentAction = action;
        switch (mCurrentAction) {
            case ACTION_REFRESH:
                mBookAdapter.clear();
                mCurrentPageIndex = 1;
                break;
            case ACTION_LOAD_MORE:
                mCurrentPageIndex++;
                break;
        }
        getData();
    }


    private void showPopwindown_course() {
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
                    text_course.setText(mAdapter.getItemName(position));
                }
            });
        }
        ColorDrawable cd = new ColorDrawable(0x000000);
        popWindow.setBackgroundDrawable(cd);
        popWindow.setOutsideTouchable(true);
        popWindow.setFocusable(true);
        popWindow.showAsDropDown(pop2);
        popWindow.setAnimationStyle(R.style.pop_anim);
        popWindow.update();
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                popWindow = null;
            }
        });

    }

    private void showPopwindown_sub() {
        if (popWindow_ == null) {
            View view = getLayoutInflater().inflate(
                    R.layout.pop_exam_menue, null);
            popWindow_ = new PopupWindow(view,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            pop_list_ = (ListView) view
                    .findViewById(R.id.pop_exam_list);
            pop_list_.setAdapter(mAdapter_);
            pop_list_.setItemsCanFocus(false);
            pop_list_.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            pop_list_.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    popWindow_.dismiss();
                    if (the_course_id != null) {
                        the_course_id = null;
                        text_course.setText("");
                    }
                    the_subject_id = mAdapter_.getItemSubjectId(position);
                    text_subject.setText(mAdapter_.getItemName(position));
                    getDrawerCourse(the_subject_id);//获取课程
                }
            });
        }
        ColorDrawable cd = new ColorDrawable(0x000000);
        popWindow_.setBackgroundDrawable(cd);
        popWindow_.setOutsideTouchable(true);
        popWindow_.setFocusable(true);
        popWindow_.showAsDropDown(pop1);
        popWindow_.setAnimationStyle(R.style.pop_anim);
        popWindow_.update();
        popWindow_.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                popWindow_ = null;
            }
        });

    }

    /**
     * 获取课程列表
     */
    private void getDrawerCourse(String sub_id) {
        if (sub_id == null) {
            ToastUtils.getInstance().showToastCenter("该科目下暂无课程");
            return;
        }
        String reqUrl = Apis.GetCourses(sub_id);
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

    //获取所有科目
    private void getSubjectData() {
        String reqUrl = Apis.GetMySubject(BaseApplication.user_info.getId());
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
                        mAdapter_.changeSubjectData(list);
                        loadComplete();
                        mRecyclerView.setLoadingMoreEnabled(false);
                    } else {
                        mRecyclerView.refreshComplete();
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

    private void setCheck(String id, String sub_id, String course_id, String time_start, String time_end, String name, String phone) {
        String reqUrl = Apis.GetAllStudentCheck(id, sub_id, course_id, time_start, time_end, name, phone);
        Log.e("学生查询", reqUrl);
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
                        List<AllStudentBean> list = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<List<AllStudentBean>>() {
                                }.getType());
                        mBookAdapter.clear();
                        mBookAdapter.addAll(list);
                        loadComplete();
                        mRecyclerView.setLoadingMoreEnabled(false);
                        includeNodataBgLL.setVisibility(View.GONE);
                        ToastUtils.getInstance().showToastCenter(jsonObject.getString("msg"));
                    } else {
                        mRecyclerView.refreshComplete();
                        ToastUtils.getInstance().showToastCenter(jsonObject.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                ToastUtils.getInstance().showToast(e.getMessage());
                loadComplete();
            }
        });

    }


    private String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

    @Override
    protected void onRestart() {
        switchAction(ACTION_REFRESH);
        sj1.setText(null);
        sj2.setText(null);
        text_subject.setText(null);
        text_course.setText(null);
        user_name.setText(null);
        phone.setText(null);
        super.onRestart();
    }
}
