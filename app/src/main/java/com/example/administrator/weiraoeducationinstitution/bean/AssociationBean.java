package com.example.administrator.weiraoeducationinstitution.bean;

/**
 * Created by Administrator on 2016/4/29.
 */
public class AssociationBean {
//    "id": "10",
//            "name": "测试社团3001",
//            "img": "http://192.168.10.143/weirao/public/upload/images/admin/1/201605/07/1462612329.jpg",
//            "admins": "林枫",
//            "brief": "用于测试",
//            "subjectNum": "2"
    private String id;
    private String name;
    private String img;
    private String admins;
    private String brief;
    private String subjectNum;

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

    public String getAdmins() {
        return admins;
    }

    public void setAdmins(String admins) {
        this.admins = admins;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getSubjectNum() {
        return subjectNum;
    }

    public void setSubjectNum(String subjectNum) {
        this.subjectNum = subjectNum;
    }
}
