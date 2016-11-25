package com.example.administrator.weiraoeducationinstitution.bean;

/**
 * Created by Administrator on 2016/5/3.
 */
public class Manage_ExamBean {
    //    "id": "2",
//            "course": "测试课程A",
//            "time": "2016-05-12 00:00:00",
//            "student": "34,28,27,26",
//            "agency": "1"
    private String id;
    private String course;
    private String time;
    private String student;
    private String agency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }
}
