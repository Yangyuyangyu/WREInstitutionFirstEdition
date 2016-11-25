package com.example.administrator.weiraoeducationinstitution.bean;

/**
 * Created by Administrator on 2016/4/29.
 */
public class AssociationConstructionBean {
    //    "id": "10",
//            "name": "测试社团3001",
//            "img": "http://192.168.10.143/weirao/public/upload/images/admin/1/201605/07/1462612329.jpg",
//            "admins": "林枫",
//            "ctime": "2016-05-07",
//            "studentNum": "12",
//            "subjectNum": "2"
    private String id;
    private String name;
    private String admins;
    private String ctime;
    private String studentNum;
    private String img;
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

    public String getAdmins() {
        return admins;
    }

    public void setAdmins(String admins) {
        this.admins = admins;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSubjectNum() {
        return subjectNum;
    }

    public void setSubjectNum(String subjectNum) {
        this.subjectNum = subjectNum;
    }
}
