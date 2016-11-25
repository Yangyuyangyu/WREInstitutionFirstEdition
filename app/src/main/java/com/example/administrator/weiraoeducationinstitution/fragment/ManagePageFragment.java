package com.example.administrator.weiraoeducationinstitution.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.weiraoeducationinstitution.R;
import com.example.administrator.weiraoeducationinstitution.activity.manage.Manage_AskForLeaveActivity;
import com.example.administrator.weiraoeducationinstitution.activity.manage.Manage_DynamicActivity;
import com.example.administrator.weiraoeducationinstitution.activity.manage.Manage_ExamControlActivity;
import com.example.administrator.weiraoeducationinstitution.activity.manage.Manage_GradeProjectActivity;
import com.example.administrator.weiraoeducationinstitution.activity.manage.Manage_HavingClassReview;
import com.example.administrator.weiraoeducationinstitution.activity.manage.Manage_MusicalRepairerActivity;
import com.example.administrator.weiraoeducationinstitution.activity.manage.Manage_OtherCourseActivity;
import com.example.administrator.weiraoeducationinstitution.activity.manage.Manage_TelePhone_Interview;
import com.example.administrator.weiraoeducationinstitution.activity.manage.Manage_TemporaryAccountActivity;
import com.example.administrator.weiraoeducationinstitution.activity.manage.Manage_TrainingPlanActivity;
import com.example.administrator.weiraoeducationinstitution.fragment.base.BaseFragment;
import com.example.administrator.weiraoeducationinstitution.utils.ToastUtils;


/**
 * Created by Administrator on 2016/4/27.管理fragment
 */
public class ManagePageFragment extends BaseFragment implements View.OnClickListener {
    private static String TAG = "ManagePageFragment";
    private RelativeLayout manageDynamicRe1;
    private RelativeLayout manageDynamicRe2;
    private RelativeLayout manageDynamicRe3;
    private RelativeLayout manageDynamicRe4;
    private RelativeLayout manageDynamicRe5;
    private RelativeLayout manageDynamicRe6;
    private RelativeLayout manageDynamicRe7;
    private RelativeLayout manageDynamicRe8;
    private RelativeLayout manageDynamicRe9;
    private RelativeLayout manageDynamicRe10;
    private TextView includeTitleText;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_manage_page;
    }

    @Override
    protected void initView() {
        manageDynamicRe1 = customFindViewById(R.id.manage_dynamic_Re1);
        manageDynamicRe2 = customFindViewById(R.id.manage_dynamic_Re2);
        manageDynamicRe3 = customFindViewById(R.id.manage_dynamic_Re3);
        manageDynamicRe4 = customFindViewById(R.id.manage_dynamic_Re4);
        manageDynamicRe5 = customFindViewById(R.id.manage_dynamic_Re5);
        manageDynamicRe6 = customFindViewById(R.id.manage_dynamic_Re6);
        manageDynamicRe7 = customFindViewById(R.id.manage_dynamic_Re7);
        manageDynamicRe8 = customFindViewById(R.id.manage_dynamic_Re8);
        manageDynamicRe9 = customFindViewById(R.id.manage_dynamic_Re9);
        manageDynamicRe10 = customFindViewById(R.id.manage_dynamic_Re10);
        includeTitleText = customFindViewById(R.id.include_title_text);
        includeTitleText.setText("日常管理");
        manageDynamicRe1.setOnClickListener(this);
        manageDynamicRe2.setOnClickListener(this);
        manageDynamicRe3.setOnClickListener(this);
        manageDynamicRe4.setOnClickListener(this);
        manageDynamicRe5.setOnClickListener(this);
        manageDynamicRe6.setOnClickListener(this);
        manageDynamicRe7.setOnClickListener(this);
        manageDynamicRe8.setOnClickListener(this);
        manageDynamicRe9.setOnClickListener(this);
        manageDynamicRe10.setOnClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //动态
            case R.id.manage_dynamic_Re1:
                startActivity(new Intent(getActivity(), Manage_DynamicActivity.class));
                break;
            //社团训练规划
            case R.id.manage_dynamic_Re2:
//                startActivity(new Intent(getActivity(), Manage_TrainingPlanActivity.class));
                ToastUtils.getInstance().showToastCenter("暂未开放");
                break;
            //请假审批
            case R.id.manage_dynamic_Re3:
                startActivity(new Intent(getActivity(), Manage_AskForLeaveActivity.class));
                break;
            //临时账号审批
            case R.id.manage_dynamic_Re4:
                startActivity(new Intent(getActivity(), Manage_TemporaryAccountActivity.class));
                break;
            //维修管理
            case R.id.manage_dynamic_Re5:
                startActivity(new Intent(getActivity(), Manage_MusicalRepairerActivity.class));
                break;
            //电话追访
            case R.id.manage_dynamic_Re6:
                startActivity(new Intent(getActivity(), Manage_TelePhone_Interview.class));
                break;
            //打分
            case R.id.manage_dynamic_Re7:
                startActivity(new Intent(getActivity(), Manage_GradeProjectActivity.class));
                break;
            //其他课
            case R.id.manage_dynamic_Re8:
//                startActivity(new Intent(getActivity(), Manage_OtherCourseActivity.class));
                ToastUtils.getInstance().showToastCenter("暂未开放");
                break;
            //上课记录审核
            case R.id.manage_dynamic_Re9:
                startActivity(new Intent(getActivity(), Manage_HavingClassReview.class));
                break;
            //考试
            case R.id.manage_dynamic_Re10:
//                startActivity(new Intent(getActivity(), Manage_ExamControlActivity.class));
                ToastUtils.getInstance().showToastCenter("暂未开放");
                break;
        }
    }


}
