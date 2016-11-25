package com.example.administrator.weiraoeducationinstitution.bean;

/**
 * Created by Administrator on 2016/5/16.
 */
public class AllCourseBean {
    //    "id": "26",
//            "name": "课程测试（政治一对一）",
//            "img": "http://192.168.10.143/weirao/public/upload/images/admin/1/201605/13/1463106362.jpg",
//            "brief": "玩儿玩儿玩儿完了",
//            "teacher": "李天一",
//            "type": "2",
//            "class_time": ""
    private String id;
    private String name;
    private String img;
    private String brief;
    private String teacher;
    private String type;
    private String class_time;

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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
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
