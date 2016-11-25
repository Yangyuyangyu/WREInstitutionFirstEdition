package com.example.administrator.weiraoeducationinstitution.bean;

/**
 * Created by Administrator on 2016/4/28.
 */
public class RankingBean {
    //    "score": "4",
//            "user_id": "32",
//            "search_name": "测试社团3001",
//            "my_order": "1",
//            "head": "",
//            "name": ""
    private String id;
    private String user_id;
    private String search_name;
    private String score;
    private String my_order;
    private String head;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSearch_name() {
        return search_name;
    }

    public void setSearch_name(String search_name) {
        this.search_name = search_name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getMy_order() {
        return my_order;
    }

    public void setMy_order(String my_order) {
        this.my_order = my_order;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
