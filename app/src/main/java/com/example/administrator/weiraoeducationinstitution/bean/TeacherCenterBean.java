package com.example.administrator.weiraoeducationinstitution.bean;

/**
 * Created by Administrator on 2016/6/13.老师主页
 */
public class TeacherCenterBean {

    /**
     * info : {"id":"6","name":"陈彬","phone":"18328381062","email":"cb198811@163.com","summary":"阿斯顿发送到阿斯蒂芬阿斯蒂芬阿斯蒂芬放四大行的飞阿斯顿发送到阿斯蒂芬阿斯蒂芬阿斯蒂芬放四大行的复合虎队底火锅底地方刚接电话的复合弓地方地方看过很快","authed":"1","edu":"6","sex":"1","birthday":"1940-01-01","edu_school":"斯丹福哈佛大学","edu_exp":"哈佛大学计算机2级甲等毕业，牛津放牛协会荣誉会长，世界BUG修复协会荣誉会员","edu_age":"100","res_share":"时光荏苒，唯有经典相随。好书，是一叶扁舟，横渡蒙昧的海洋，带领我们走向无限的未来。阅读，是一种情怀，含一口悠然之气，于浮华里追求真理。时间的洪流冲淡时光荏苒，唯有经典相随","class_video":"http://player.youku.com/player.php/sid/XMTQwOTMzNzE2NA==/v.swf","ctime":"","head":"http://211.149.226.242/./public/upload/images//teacher/6/201512/14/1450090856.jpg","label_admin":"英俊,潇洒,帅气,牛逼","label":"0","average_price":"0.00","address":"天津市下辖县静海县54565656","qualification":"身份认证、"}
     * course : []
     * courseNum : 0
     * comment : []
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 6
         * name : 陈彬
         * phone : 18328381062
         * email : cb198811@163.com
         * summary : 阿斯顿发送到阿斯蒂芬阿斯蒂芬阿斯蒂芬放四大行的飞阿斯顿发送到阿斯蒂芬阿斯蒂芬阿斯蒂芬放四大行的复合虎队底火锅底地方刚接电话的复合弓地方地方看过很快
         * authed : 1
         * edu : 6
         * sex : 1
         * birthday : 1940-01-01
         * edu_school : 斯丹福哈佛大学
         * edu_exp : 哈佛大学计算机2级甲等毕业，牛津放牛协会荣誉会长，世界BUG修复协会荣誉会员
         * edu_age : 100
         * res_share : 时光荏苒，唯有经典相随。好书，是一叶扁舟，横渡蒙昧的海洋，带领我们走向无限的未来。阅读，是一种情怀，含一口悠然之气，于浮华里追求真理。时间的洪流冲淡时光荏苒，唯有经典相随
         * class_video : http://player.youku.com/player.php/sid/XMTQwOTMzNzE2NA==/v.swf
         * ctime :
         * head : http://211.149.226.242/./public/upload/images//teacher/6/201512/14/1450090856.jpg
         * label_admin : 英俊,潇洒,帅气,牛逼
         * label : 0
         * average_price : 0.00
         * address : 天津市下辖县静海县54565656
         * qualification : 身份认证、
         */

        private InfoBean info;
        private int courseNum;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public int getCourseNum() {
            return courseNum;
        }

        public void setCourseNum(int courseNum) {
            this.courseNum = courseNum;
        }

        public static class InfoBean {
            private String id;
            private String name;
            private String phone;
            private String email;
            private String summary;
            private String authed;
            private String edu;
            private String sex;
            private String birthday;
            private String edu_school;
            private String edu_exp;
            private String edu_age;
            private String res_share;
            private String class_video;
            private String ctime;
            private String head;
            private String label_admin;
            private String label;
            private String average_price;
            private String address;
            private String qualification;

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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getAuthed() {
                return authed;
            }

            public void setAuthed(String authed) {
                this.authed = authed;
            }

            public String getEdu() {
                return edu;
            }

            public void setEdu(String edu) {
                this.edu = edu;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getEdu_school() {
                return edu_school;
            }

            public void setEdu_school(String edu_school) {
                this.edu_school = edu_school;
            }

            public String getEdu_exp() {
                return edu_exp;
            }

            public void setEdu_exp(String edu_exp) {
                this.edu_exp = edu_exp;
            }

            public String getEdu_age() {
                return edu_age;
            }

            public void setEdu_age(String edu_age) {
                this.edu_age = edu_age;
            }

            public String getRes_share() {
                return res_share;
            }

            public void setRes_share(String res_share) {
                this.res_share = res_share;
            }

            public String getClass_video() {
                return class_video;
            }

            public void setClass_video(String class_video) {
                this.class_video = class_video;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getLabel_admin() {
                return label_admin;
            }

            public void setLabel_admin(String label_admin) {
                this.label_admin = label_admin;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getAverage_price() {
                return average_price;
            }

            public void setAverage_price(String average_price) {
                this.average_price = average_price;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getQualification() {
                return qualification;
            }

            public void setQualification(String qualification) {
                this.qualification = qualification;
            }
        }
    }
}
