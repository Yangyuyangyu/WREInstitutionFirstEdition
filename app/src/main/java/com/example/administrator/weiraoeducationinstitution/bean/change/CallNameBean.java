package com.example.administrator.weiraoeducationinstitution.bean.change;


/**
 * Created by Administrator on 2016/4/15.
 */
public class CallNameBean {

    private String name;
    private String phone;
    private String head;
    private String call_state;//点名状态，1准时，2迟到，3请假，4未到

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getCall_state() {
        return call_state;
    }

    public void setCall_state(String call_state) {
        this.call_state = call_state;
    }
}
