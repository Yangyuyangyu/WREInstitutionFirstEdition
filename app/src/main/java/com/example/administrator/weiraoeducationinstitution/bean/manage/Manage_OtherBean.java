package com.example.administrator.weiraoeducationinstitution.bean.manage;

/**
 * Created by Administrator on 2016/5/3.
 */
public class Manage_OtherBean {
//    "id": "1",
//            "name": "合排课",
//            "class_time": "2016-03-30 16:35:00",
//            "teacher": "20",
//            "advice": "",老师意见
//            "execution": "dfd" 执行情况
    private String id;
    private String name;
    private String class_time;
    private String teacher;
    private String advice;
    private String execution;

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

    public String getClass_time() {
        return class_time;
    }

    public void setClass_time(String class_time) {
        this.class_time = class_time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getExecution() {
        return execution;
    }

    public void setExecution(String execution) {
        this.execution = execution;
    }
}
