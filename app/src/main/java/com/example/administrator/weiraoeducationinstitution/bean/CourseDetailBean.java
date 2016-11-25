package com.example.administrator.weiraoeducationinstitution.bean;

/**
 * Created by Administrator on 2016/5/12.
 */
public class CourseDetailBean {
//    "info": {
//        "id": "21",
//                "name": "课程测试（国语）",
//                "corporation": "8",
//                "subject": "5",
//                "address": "成都市青羊区",
//                "fit_crowd": "有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字",
//                "goal": "有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字",
//                "quit_rule": "有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字",
//                "join_rule": "有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字",
//                "detail": " 去委屈委屈委屈委屈我  ",
//                "img": "http://192.168.10.143/weirao/public/upload/images/admin/1/201605/11/1462936405.jpg",
//                "type": "1",
//                "brief": "有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字有一百个字",
//                "teacher": "47",
//                "teacher_name": "李天一",
//                "cycle": "2",
//                "day": "1,2,3,4,5",
//                "start": "13:30:00",
//                "course_num": "0",
//                "start_date": "2016-05-11",
//                "end_date": "2016-05-18",
//                "student": "36,35",
//                "agency": "维立方科技",
//                "agency_id": "1",
//                "longitude": "104.074818",
//                "latitude": "30.646156"
//    },
//            "tInfo": {
//        "id": "47",
//                "name": "李天一",
//                "head": "http://192.168.10.143/operate/public/upload/images/app/201605/11/1462948979.jpg",
//                "feature": ""
//    },
//            "comment": [
//
//            ]

    //}


    private String id;
    private String name;
    private String address;
    private String teacher_name;
    private String img;
    private String fit_crowd;
    private String brief;
    private String agency;
    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getFit_crowd() {
        return fit_crowd;
    }

    public void setFit_crowd(String fit_crowd) {
        this.fit_crowd = fit_crowd;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}

