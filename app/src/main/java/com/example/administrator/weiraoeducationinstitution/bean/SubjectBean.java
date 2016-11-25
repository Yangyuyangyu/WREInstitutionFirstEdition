package com.example.administrator.weiraoeducationinstitution.bean;

/**
 * Created by Administrator on 2016/4/29.
 */
public class SubjectBean {
//    "id": "18",
//            "name": "测试科目",
//            "brief": "测试科目简介",
//            "img": "",
//            "admin": "",
//            "group": "联调社团"

    private String id;
    private String name;
    private String brief;
    private String admin;
    private String img;
    private String group;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
