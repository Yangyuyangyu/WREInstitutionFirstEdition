package com.example.administrator.weiraoeducationinstitution.bean.manage;

/**
 * Created by Administrator on 2016/5/17.
 */
public class RepairOneBean {
    //    "id": "2",
//            "user_name": "李四",
//            "subject": "科目A",
//            "instrument": "双方的首发",
//            "remark": "斯蒂芬斯蒂芬斯蒂芬",
//            "group": "10"
    private String id;
    private String user_name;
    private String subject;
    private String instrument;
    private String remark;
    private String group;
    private String time;
    private String course;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
