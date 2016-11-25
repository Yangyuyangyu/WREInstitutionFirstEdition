package com.example.administrator.weiraoeducationinstitution.bean;

/**
 * Created by Administrator on 2016/5/16.
 */
public class CourseDetailTInfo {
    //    "id": "47",
//            "name": "李天一",
//            "head": "http://192.168.10.143/operate/public/upload/images/app/201605/11/1462948979.jpg",
//            "feature": ""
    private String id;
    private String name;
    private String head;
    private String feature;

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
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

}
