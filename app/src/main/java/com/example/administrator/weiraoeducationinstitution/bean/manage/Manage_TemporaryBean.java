package com.example.administrator.weiraoeducationinstitution.bean.manage;

/**
 * Created by Administrator on 2016/4/29.
 */
public class Manage_TemporaryBean {
    //    "id": "5",
//            "name": "李天一",
//            "head": "http://192.168.10.143/operate/public/upload/images/app/201605/11/1462948979.jpg",
//            "phone": "18608260001",
//            "reason": "其他",
//            "course": "课程测试（国语）",
//            "remark": "反反复复反反复复发"
//    "account": "18608260002"
    private String id;
    private String name;
    private String head;
    private String phone;
    private String course;
    private String remark;
    private String reason;
    private String class_time;
    private String account;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClass_time() {
        return class_time;
    }

    public void setClass_time(String class_time) {
        this.class_time = class_time;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
