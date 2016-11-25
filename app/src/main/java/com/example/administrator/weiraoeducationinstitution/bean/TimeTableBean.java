package com.example.administrator.weiraoeducationinstitution.bean;

/**
 * Created by Administrator on 2016/4/28.
 */
public class TimeTableBean {
    //    "id": "108",
//            "cid": "21",
//            "tid": "47",
//            "sid": "36,35",
//            "status": "0",
//            "date": "2016-05-16",
//            "start": "13:30:00",
//            "place": "\u6210\u90fd\u5e02\u9752\u7f8a\u533a",
//            "present": null,
//            "absent": null,
//            "longitude": null,
//            "latitude": null,
//            "start_time": null,
//            "name": "\u8bfe\u7a0b\u6d4b\u8bd5\uff08\u56fd\u8bed\uff09",
//            "img": "http:\/\/192.168.10.143\/weirao\/public\/upload\/images\/admin\/1\/201605\/11\/1462936405.jpg",
//            "brief": "\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57\u6709\u4e00\u767e\u4e2a\u5b57",
//            "type": "1",
//            "class_time": "2016-05-16 13:30:00~14:30",
//            "teacher_name": "\u674e\u5929\u4e00"
    private String id;
    private String cid;
    private String name;
    private String img;
    private String brief;
    private String type;
    private String class_time;
    private String teacher_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClass_time() {
        return class_time;
    }

    public void setClass_time(String class_time) {
        this.class_time = class_time;
    }
}
