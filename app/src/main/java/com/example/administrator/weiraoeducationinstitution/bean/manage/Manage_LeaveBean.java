package com.example.administrator.weiraoeducationinstitution.bean.manage;

import com.example.administrator.weiraoeducationinstitution.bean.manage.leave.LeaveBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class Manage_LeaveBean {
   private List<LeaveBean>  teacher;
    private List<LeaveBean> student;

    public List<LeaveBean> getTeacher() {
        return teacher;
    }

    public void setTeacher(List<LeaveBean> teacher) {
        this.teacher = teacher;
    }

    public List<LeaveBean> getStudent() {
        return student;
    }

    public void setStudent(List<LeaveBean> student) {
        this.student = student;
    }
}
