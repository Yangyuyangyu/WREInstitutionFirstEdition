package com.example.administrator.weiraoeducationinstitution.bean.manage.leave;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.学生点名数据
 */
public class HavingCheckLeaveBean {

    /**
     * name : 学生二十
     * phone : 18600000020
     * head : ./public/static/home/images/touxiang.jpg
     */

    private List<List<DataBean>> data;

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
        this.data = data;
    }

    public static class DataBean {
        private String name;
        private String phone;
        private String head;

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
    }
}
