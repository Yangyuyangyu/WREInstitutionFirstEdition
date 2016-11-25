package com.example.administrator.weiraoeducationinstitution.bean.manage.havingclass;

import java.util.List;

/**
 * Created by Administrator on 2016/6/8.
 */
public class HavingCheckGradeBean {

    /**
     * id : 17
     * student : 227
     * class_id : 4
     * score_info : [{"item":"1","score":"1.0","name":"纪律卫生与学具状态"}]
     * average : 1.0
     * course : 1
     * name : 学生二
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String student;
        private String class_id;
        private String average;
        private String course;
        private String name;
        /**
         * item : 1
         * score : 1.0
         * name : 纪律卫生与学具状态
         */

        private List<ScoreInfoBean> score_info;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStudent() {
            return student;
        }

        public void setStudent(String student) {
            this.student = student;
        }

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ScoreInfoBean> getScore_info() {
            return score_info;
        }

        public void setScore_info(List<ScoreInfoBean> score_info) {
            this.score_info = score_info;
        }

        public static class ScoreInfoBean {
            private String item;
            private String score;
            private String name;

            public String getItem() {
                return item;
            }

            public void setItem(String item) {
                this.item = item;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
