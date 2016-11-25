package com.example.administrator.weiraoeducationinstitution.activity.student;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.BaseApplication;
import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.base.BaseActivity;
import com.example.administrator.weiraoeducationinstitution.adapter.course_pop.Association_PopAdapter;
import com.example.administrator.weiraoeducationinstitution.adapter.course_pop.Subject_PopAdapter;
import com.example.administrator.weiraoeducationinstitution.bean.AssociationBean;
import com.example.administrator.weiraoeducationinstitution.bean.SubjectBean;
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
import de.hdodenhof.circleimageview.CircleImageView;
import timeselector.Utils.TextUtil;

public class Student_Select_AddActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.include_setting_introduction_back)
    ImageView back;
    @Bind(R.id.include_setting_introduction_title)
    TextView title;
    @Bind(R.id.include_setting_introduction_ok)
    TextView includeSettingIntroductionOk;

    private String theStudentId = null, association_Id, the_subjectId;
    private CircleImageView head;
    private EditText name, pass, phone, email;
    private CheckBox cb;
    private LinearLayout selectLL, headLL, subjectLL;
    private TextView association_name, subject_name;

    private PopupWindow popWindow, popSubject;// 弹窗 社团
    private ListView pop_list;// 弹窗list
    private Association_PopAdapter mAdapter;
    private Subject_PopAdapter mBdapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_student_select_add;
    }


    @Override
    protected void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            theStudentId = bundle.getString("the_sutdent_id");
        }
        mAdapter = new Association_PopAdapter(Student_Select_AddActivity.this, new ArrayList<AssociationBean>());
        mBdapter = new Subject_PopAdapter(Student_Select_AddActivity.this, new ArrayList<SubjectBean>());

    }

    @Override
    protected void initData() {
        if (theStudentId != null) {
            cb.setVisibility(View.INVISIBLE);
            name.setFocusable(false);
            pass.setFocusable(false);
            phone.setFocusable(false);
            getData();
        }
        getAssociation();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        title.setText("新增学生");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        includeSettingIntroductionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (association_Id == null) {
                    MySnackbar.getmInstance().showMessage(name, "社团不能为空");
                } else if (the_subjectId == null) {
                    MySnackbar.getmInstance().showMessage(name, "科目不能为空");
                    return;
                } else if (TextUtil.isEmpty(name.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(name, "姓名不能为空");
                    return;
                } else if (TextUtil.isEmpty(phone.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(name, "电话不能为空");
                    return;
                } else if (TextUtil.isEmpty(pass.getText().toString())) {
                    MySnackbar.getmInstance().showMessage(name, "密码不能为空");
                    return;
                }
                addStudent(association_Id, theStudentId, the_subjectId, name.getText().toString(), phone.getText().toString(), pass.getText().toString(), email.getText().toString());
            }
        });
        name = customFindViewById(R.id.student_add_name);
        pass = customFindViewById(R.id.student_add_pass);
        phone = customFindViewById(R.id.student_add_phone);
        email = customFindViewById(R.id.student_add_email);
        cb = customFindViewById(R.id.student_add_cb);
        head = customFindViewById(R.id.student_add_head);
        headLL = customFindViewById(R.id.student_add_headLL);
        association_name = customFindViewById(R.id.student_add_association);
        selectLL = customFindViewById(R.id.student_add_selectLL);
        subjectLL = customFindViewById(R.id.student_add_selectSubLL);
        subject_name = customFindViewById(R.id.student_add_subject);
        selectLL.setOnClickListener(this);
        subjectLL.setOnClickListener(this);
        cb.setOnClickListener(this);

        if (theStudentId == null) {
            headLL.setVisibility(View.GONE);
        }
    }

    private void getData() {
        //通过搜索新增设置学生信息
        String reqUrl = Apis.GetStudentSelected(theStudentId);
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
                        StudentHadBean shb = gson.fromJson(
                                jsonObject.getString("data"),
                                new TypeToken<StudentHadBean>() {
                                }.getType());
                        if (!shb.getHead().equals("")) {
                            HttpUtils.getInstance().loadImage(shb.getHead(), head);
                        }
                        name.setText(shb.getName());
                        pass.setText(shb.getPwd());
                        phone.setText(shb.getPhone());
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.student_add_cb:
                if (cb.isChecked()) {
                    //设置EditText的密码为可见的
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置密码为隐藏的
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.student_add_selectLL:
                showPopwindown();
                break;
            case R.id.student_add_selectSubLL:
                showPopSubject();
                break;
        }
    }


    private void addStudent(String id, String sid, String subject, String name, String mobile, String pass, String email) {
        String reqUrl = Apis.GetAddStudent(id, sid, subject, name, mobile, pass, email);
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

            }
        });
    }

    private void showPopwindown() {
        if (popSubject != null) {
            popWindow.dismiss();
        }
        if (popWindow == null) {
            View view = getLayoutInflater().inflate(
                    R.layout.pop_exam_menue, null);
            popWindow = new PopupWindow(view,
                    ViewGroup.LayoutParams.MATCH_PARENT,
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
                    association_Id = mAdapter.getItemAssociationId(position);
                    association_name.setText(mAdapter.getItemName(position));
                    getSubject();
                }
            });
        }
        ColorDrawable cd = new ColorDrawable(0x000000);
        popWindow.setBackgroundDrawable(cd);
        popWindow.setOutsideTouchable(true);
        popWindow.setFocusable(true);
        popWindow.showAtLocation(customFindViewById(R.id.student_add_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popWindow.setAnimationStyle(R.style.pop_anim);
        popWindow.update();
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                popWindow = null;
            }
        });

    }

    private void showPopSubject() {
        if (association_Id == null) {
            ToastUtils.getInstance().showToast("请您先选择社团");
            return;
        }
        if (popWindow != null) {
            popWindow.dismiss();
        }
        if (popSubject == null) {
            View view = getLayoutInflater().inflate(
                    R.layout.pop_exam_menue, null);
            popSubject = new PopupWindow(view,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            pop_list = (ListView) view
                    .findViewById(R.id.pop_exam_list);
            pop_list.setAdapter(mBdapter);
            pop_list.setItemsCanFocus(false);
            pop_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            pop_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    popSubject.dismiss();
                    the_subjectId = mBdapter.getItemSubjectId(position);
                    subject_name.setText(mBdapter.getItemName(position));
                }
            });
        }
        ColorDrawable cd = new ColorDrawable(0x000000);
        popSubject.setBackgroundDrawable(cd);
        popSubject.setOutsideTouchable(true);
        popSubject.setFocusable(true);
        popSubject.showAtLocation(customFindViewById(R.id.student_add_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popSubject.setAnimationStyle(R.style.pop_anim);
        popSubject.update();
        popSubject.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                popSubject = null;
            }
        });
    }

    /**
     * 获取社团列表
     */
    private void getAssociation() {
        String reqUrl = Apis.GetGetGroups(BaseApplication.user_info.getId());
        HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Gson gson = new Gson();
                    List<AssociationBean> list = gson.fromJson(
                            jsonObject.getString("data"),
                            new TypeToken<List<AssociationBean>>() {
                            }.getType());
                    mAdapter.changeManagerData(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }

    /**
     * 获取社团下的科目列表
     */
    private void getSubject() {
        String reqUrl = Apis.GetSubjectOfGroup(association_Id);
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
                        mBdapter.changeSubjectData(list);
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

    class StudentHadBean {
        private String id;
        private String name;
        private String head;
        private String phone;
        private String pwd;

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

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

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
