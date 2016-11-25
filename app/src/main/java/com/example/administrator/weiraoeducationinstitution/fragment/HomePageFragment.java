package com.example.administrator.weiraoeducationinstitution.fragment;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.AllCourseActivity;
import com.example.administrator.weiraoeducationinstitution.activity.AllStudentActivity;
import com.example.administrator.weiraoeducationinstitution.activity.AllTeacherActivity;
import com.example.administrator.weiraoeducationinstitution.activity.AssociationActivity;
import com.example.administrator.weiraoeducationinstitution.activity.AssociationConstructionActivity;
import com.example.administrator.weiraoeducationinstitution.activity.FundActivity;
import com.example.administrator.weiraoeducationinstitution.activity.SubJectActivity;
import com.example.administrator.weiraoeducationinstitution.activity.TimeTableActivity;
import com.example.administrator.weiraoeducationinstitution.activity.manage.Manage_AskForLeaveActivity;
import com.example.administrator.weiraoeducationinstitution.activity.manage.Manage_HavingClassReview;
import com.example.administrator.weiraoeducationinstitution.activity.manage.Manage_TelePhone_Interview;
import com.example.administrator.weiraoeducationinstitution.adapter.MyGridAdapter;
import com.example.administrator.weiraoeducationinstitution.constants.Apis;
import com.example.administrator.weiraoeducationinstitution.fragment.base.BaseFragment;
import com.example.administrator.weiraoeducationinstitution.utils.HttpUtils;
import com.example.administrator.weiraoeducationinstitution.utils.MySnackbar;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;
import com.example.administrator.weiraoeducationinstitution.view.MyGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by waycube-yyb on 2016/4/27. 首页fragment
 */
public class HomePageFragment extends BaseFragment implements View.OnClickListener {
    private static String TAG = "HomePageFragment";
    TextView teacherNumber, studentNumber;
    private LinearLayout mainMidStudentLL, mainMidTeacherLL;
    private MyGridView gridview;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home_page;
    }


    @Override
    protected void initData() {
        getNumber();
    }

    @Override
    protected void initView() {
        teacherNumber = customFindViewById(R.id.main_mid_teacher_number);
        studentNumber = customFindViewById(R.id.main_mid_student_number);
        mainMidStudentLL = (LinearLayout) customFindViewById(R.id.main_mid_student_LL);
        mainMidTeacherLL = (LinearLayout) customFindViewById(R.id.main_mid_teacher_LL);
        mainMidStudentLL.setOnClickListener(this);
        mainMidTeacherLL.setOnClickListener(this);
        gridview = (MyGridView) customFindViewById(R.id.gridview);
        gridview.setAdapter(new MyGridAdapter(getMContext()));


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
//                    学生", "社团建设表", "社团", "上课记录", "课程表", "科目", "请假审批", "电话追访", "资金"
                    //学生
                    case 0:
                        startActivity(new Intent(getActivity(), AllStudentActivity.class));
                        break;
                    //建设表
                    case 1:
                        startActivity(new Intent(getActivity(), AssociationConstructionActivity.class));
                        break;
                    //社团
                    case 2:
                        startActivity(new Intent(getActivity(), AssociationActivity.class));
                        break;
                    //上课记录
                    case 3:
                        startActivity(new Intent(getActivity(), Manage_HavingClassReview.class));
                        break;
                    //课程表
                    case 4:
                        startActivity(new Intent(getActivity(), TimeTableActivity.class));
                        break;
                    //科目
                    case 5:
                        startActivity(new Intent(getActivity(), SubJectActivity.class));
                        break;
                    //请假审批
                    case 6:
                        startActivity(new Intent(getActivity(), Manage_AskForLeaveActivity.class));
                        break;
                    //电话追访
                    case 7:
                        startActivity(new Intent(getActivity(), Manage_TelePhone_Interview.class));
                        break;
                    //资金
                    case 8:
//                        startActivity(new Intent(getActivity(), FundActivity.class));
                        ToastUtils.getInstance().showToastCenter("暂未开放");
                        break;
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_mid_teacher_LL:
                startActivity(new Intent(getActivity(), AllTeacherActivity.class));
                break;
            case R.id.main_mid_student_LL:
                startActivity(new Intent(getActivity(), AllCourseActivity.class));
                break;
        }
    }

    private void getNumber() {
        String reqUrl = Apis.GetCount(BaseApplication.user_info.getId());
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
                        TSnumber ts = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<TSnumber>() {
                                }.getType());
                        if (ts.getTeacherNum() != null || !ts.getTeacherNum().equals("")) {
                            teacherNumber.setText(ts.getTeacherNum());
                        }
                        studentNumber.setText(ts.getCourseNum());
                    } else {
                        MySnackbar.getmInstance().showMessage(mainMidTeacherLL, jsonObject.getString("msg"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "onError:" + e);
            }
        });
    }

    /**
     * 首页老师和课程数量
     */
    class TSnumber {
        private String teacherNum;
        private String courseNum;

        public String getTeacherNum() {
            return teacherNum;
        }

        public void setTeacherNum(String teacherNum) {
            this.teacherNum = teacherNum;
        }

        public String getCourseNum() {
            return courseNum;
        }

        public void setCourseNum(String courseNum) {
            this.courseNum = courseNum;
        }
    }
}
