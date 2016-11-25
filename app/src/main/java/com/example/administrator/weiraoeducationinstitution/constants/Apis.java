package com.example.administrator.weiraoeducationinstitution.constants;


/**
 * Created by waycube-yyb-4-26
 * Time:17:25
 * <p>
 * 围绕教育机构移动端api来源waycube-冉顺秋
 * <p>
 */
public class Apis {
    /**
     * 请求服务器
     * 外网 http://www.weiraoedu.com/Api/AgencyApi/
     * 内网 http://192.168.10.138/weirao/Api/AgencyApi/
     * 网页浏览器
     */
    public static final String WeiRao_Url = "http://www.weiraoedu.com/Api/AgencyApi/";
    public static final String WeiRao_http = "http://www.weiraoedu.com/weirao/";

    /**
     * 网页浏览器
     */
    public static final String WeiRao_WebHttp = "http://www.weiraoedu.com/Admin/Account/login.html";

    /**
     * 上传图片
     */
    public static final String WeiRao_UpImg = "http://www.weiraoedu.com/Api/CommonApi/imgUpload";

    /**
     * 短信验证码
     */
    public static String GetSmsCode(String mobile) {
        return WeiRao_Url + "smsCode?mobile=" + mobile;
    }
//    {code：0，msg：“验证码发送成功，请注意查收”，log_id：短信记录id}   = WeiRao_Url + "smsCode?mobile=";

    /**
     * 注册
     */
    public static String GetRegister(String account, String name, String mobile, String code, String pass, String log_id) {
        return WeiRao_Url + "register?account=" + account + "&name=" + name + "&mobile=" + mobile + "&code=" + code + "&pass=" + pass + "&log_id=" + log_id + "&type=1";
    }
//    {code:0，msg:”注册成功” ,code为-1表示号码已经注册，-2表示验证码错误，-3注册失败}

    /**
     * 登录并返回信息
     */
    public static String GetLogin(String mobile, String pass) {
        return WeiRao_Url + "login?mobile=" + mobile + "&pass=" + pass;
    }
//    {code：0，msg：“登录成功”，data：{}}Data：id：机构id name：机构名 type：类型，1机构，2学校 account：账号 img：机构图片 location：地址 brief：简介 feature：特点 qualification：资质

    /**
     * 忘记密码
     */
    public static String GetEditPwd(String mobile, String code, String pass, String log_id) {
        return WeiRao_Url + "editPwd?mobile=" + mobile + "&code=" + code + "&pass=" + pass + "&log_id=" + log_id;
    }
//    {code：0，msg：“修改成功”}

    /**
     * 首页统计老师和课程数
     */
    public static String GetCount(String id) {
        return WeiRao_Url + "count?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：teacherNum：老师人数 courseNum：课程数量

    /**
     * 查询已加入该机构的老师
     */
    public static String GetMyTeacher(String id) {
        return WeiRao_Url + "myTeacher?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：老师id name：老师姓名 head：老师头像 phone：电话 courseNum：老师上课数

    /**
     * 搜索已有老师
     */
    public static String GetChoose(String mobile) {
        if (mobile == null) {
            return WeiRao_Url + "choose?mobile=";
        } else {
            return WeiRao_Url + "choose?mobile=" + mobile;
        }
    }
//    mobile：手机号，初始化获取最近新增老师时可不传此参数
//    {code：0，msg：“获取数据成功”，data：{}}Data数据说明：id：老师id name：老师姓名 head：老师头像 phone：电话

    /**
     * 选择老师后保存
     */
    public static String GetSave(String id, String tid) {
        return WeiRao_Url + "save?id=" + id + "&tid=" + tid;
    }
//    {code：0，msg：“保存成功”}

    /**
     * 直接新增老师
     */
    public static String GetAddTeacher(String id, String head_img, String name, String mobile, String pass, String email, String feature, String intro) {
        return WeiRao_Url + "addTeacher?id=" + id + "&head_img=" + head_img + "&name=" + name + "&mobile=" + mobile + "&pass=" + pass + "&email=" + email + "&feature=" + feature + "&intro=" + intro;
    }
//    {code：0，msg：“保存成功”}


    /**
     * 查询已加入机构的学生
     */
    public static String GetMyStudent(String id) {
        return WeiRao_Url + "myStudent?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：学生id name：姓名 head：头像 phone：手机号 age：年龄 group_name：加入的社团

    /**
     * 学生主页
     */
    public static String GetStudentInfo(String sid) {
        return WeiRao_Url + "studentInfo?sid=" + sid;
    }
//    {code：0，msg：“获取数据成功”，data：{}}
//    Data数据说明：
//    info：学生个人信息，数据如下：
//    {
//        id：学生id
//        name：姓名
//        head：头像
//        phone：手机号
//    }
//    groups：加入的社团，数据如下：
//    {
//        id：社团id
//        name：社团名
//        img：社团图片
//        brief：简介
//        admin_name：管理员姓名
//        subjectNum：科目数量
//    }

    /**
     * 搜索已有学生
     */
    public static String GetChooseStudent(String mobile) {
        if (mobile == null) {
            return WeiRao_Url + "chooseStudent?mobile=";
        } else {
            return WeiRao_Url + "chooseStudent?mobile=" + mobile;
        }
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：学生id name：姓名 head：头像 phone：电话


    /**
     * 读取已选择的学生信息
     */
    public static String GetStudentSelected(String sid) {
        return WeiRao_Url + "studentSelected?sid=" + sid;
    }
//    {code：0，msg：“获取数据成功”，data：{}}Data数据说明：id：学生id name：姓名 head：头像 phone：手机

    /**
     * 查询机构下的社团
     */
    public static String GetGetGroups(String agencyId) {
        return WeiRao_Url + "getGroups?agencyId=" + agencyId;
    }
//    {code：0，msg：“获取数据成功”，data：{}}Data数据说明：id：社团id name：社团名

    /**
     * 保存学生信息
     */
    public static String GetAddStudent(String id, String sid, String subject, String name, String mobile, String pass, String email) {
        return WeiRao_Url + "addStudent?id=" + id + "&sid=" + sid + "subject=" + subject + "&name=" + name + "&mobile=" + mobile + "&pass=" + pass + "&email=" + email;
    }
//    {code：0，msg：“保存成功”}

    /**
     * 课程列表
     */
    public static String GetMyCourse(String id) {
        return WeiRao_Url + "myCourse?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：课程id name：课程名 img：图片 brief：简介 teacher：老师姓名 type：类型，1一对多，2一对一 class_time：上课时间

    /**
     * 课程详情
     */
    public static String GetCourseInfo(String id) {
        return WeiRao_Url + "courseInfo?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}
//    Data数据说明：info：课程详情，数据如下
//    {
//        id：课程id
//        name：课程名
//        img：课程图
//    }
//    tInfo：老师信息，数据如下
//    {
//        id：老师id
//        name：老师姓名
//        head：头像
//        phone：电话
//    }
//    comment：评论信息，数据如下
//    {
//        user_img：用户头像
//        user_name：姓名
//        time：评论时间
//        content：内容
//    }

    /**
     * 社团建设下的社团列表
     */
    public static String GetGroupBuild(String id) {
        return WeiRao_Url + "groupBuild?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：社团id name：社团名 img：图片 admins：管理员姓名 ctime：创建时间 studentNum：学生人数 subjectNum：科目数量

    /**
     * 查询管理员
     */
    public static String GetMyAdmin(String id) {
        return WeiRao_Url + "myAdmin?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：Id：管理员id name：姓名

    /**
     * 保存修改后的管理员
     */
    public static String GetSaveAdmin(String groupId, String adminId) {
        return WeiRao_Url + "saveAdmin?groupId=" + groupId + "&adminId=" + adminId;
    }
//    {code：0，msg：“修改成功”}

    /**
     * 社团的科目
     */
    public static String GetSubjectOfGroup(String id) {
        return WeiRao_Url + "subjectOfGroup?id=" + id;
    }
//    {code：0，msg：“获取数据成功”，data：{}}Data数据说明：id：科目id name：科目名 brief：简介 img：图片 admin：管理员姓名

    /**
     * 资金管理
     */
    public static String GetFinance(String id) {
        return WeiRao_Url + "finance?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：income：总收入金额 expense：总支付金额 balance：余额
//    recordList：资金记录，数据如下
//    {
//        money：金额
//        type：类型，1收入，2提现
//        time：时间
//    }

    /**
     * 提现时查询银行卡
     */
    public static String GetCashAccount(String id) {
        return WeiRao_Url + "cashAccount?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：银行卡id bankName：银行名 account_name：账户名 account_num：账号

    /**
     * 保存提现申请
     */
    public static String GetAddCash(String money, String account, String id) {
        return WeiRao_Url + "addCash?money=" + money + "&account=" + account + "&id=" + id;
    }
//    {code：0，msg：“保存成功”，data：{}}Data数据说明：bank：提现银行 money：金额

    /**
     * 课程表
     */

    public static String GetCourseList(String id) {
        return WeiRao_Url + "courseList?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：week：星期几，1到7分别表示星期一到星期日
//    course：课程，数据如下
//    {
//        id：上课记录id
//        cid：课程id
//        name：课程名
//        img：课程图片
//        type：类型，1一对多，2一对一
//        class_time：上课时间
//    }

    /**
     * 机构下的所有科目
     */
    public static String GetMySubject(String id) {
        return WeiRao_Url + "mySubject?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：科目id name：科目名 brief：简介 img：图片 admin：管理员姓名 group：社团名

    /**
     * 机构信息
     */
    public static String GetInfo(String id) {
        return WeiRao_Url + "info?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：机构id name：机构名 img：机构图片

    /**
     * 修改信息
     */
    public static String GetEditInfo(String id, String type, String content) {
        return WeiRao_Url + "editInfo?id=" + id + "&type=" + type + "&content=" + content;
    }
//    type：要修改的数据，1修改地址，2修改特点，3修改简介
//    {code：0，msg：“修改成功”}

    /**
     * 已绑定的银行卡
     */
    public static String GetMyCards(String id) {
        return WeiRao_Url + "myCards?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：银行卡id bankName：银行名 account_name：账户名 account_num：账号

    /**
     * 添加银行卡
     */
    public static String GetAddCard(String holder, String cardNo, String type, String id) {
        return WeiRao_Url + "addCard?holder=" + holder + "&cardNo=" + cardNo + "&type=" + type + "&id=" + id;
    }
//    {code：0，msg：“添加成功”}

    /**
     * 解绑银行卡
     */
    public static String GetDelCard(String id) {
        return WeiRao_Url + "delCard?id=" + id;
    }
//    {code：0，msg：“操作成功”}

    /**
     * 修改密码
     */
    public static String GetEditPass(String id, String oldPass, String newPass) {
        return WeiRao_Url + "editPass?id=" + id + "&oldPass=" + oldPass + "&newPass=" + newPass;
    }
//    {code：0，msg：“修改成功”}

    /**
     * 意见反馈
     */
    public static String GetFeedback(String id, String content, String email) {
        if (email == null) {
            return WeiRao_Url + "feedback?id=" + id + "&content=" + content;
        } else {
            return WeiRao_Url + "feedback?id=" + id + "&content=" + content + "&email=" + email;
        }

    }
//    {code：0，msg：“保存成功”}


    /**
     * 关于我们
     */
    public static final String GetAboutUs = WeiRao_Url + "aboutUs";
//    {code：0，msg：“数据获取成功”，data：{}}telephone：客服电话 detail：详情


    /**
     * 排名  这里改动，考试排名默认不传机构id， 课程id唯一判断所属机构
     */
    public static String GetRank(String id, String type, String groupId, String course) {
        if (type == null) {
            return WeiRao_Url + "rank?id=" + id;
        } else if (type.equals("1")) {
            return WeiRao_Url + "rank?id=" + id + "&type=" + type + "&groupId=" + groupId;
        } else if (type.equals("2")) {
            return WeiRao_Url + "rank?id=" + "&type=" + type + "&course=" + course;
        } else {
            return null;
        }
    }
//    type：排名类型，日常排名为1，考试排名为2
//    groupId：社团id，当type为1时传入
//    course：课程id，当type为2时传入

    /**
     * 选择社团后查询科目
     */
    public static String GetSubjectList(String id) {
        return WeiRao_Url + "subjectList?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：科目id name：科目名

    /**
     * 选择科目后查询课程
     */
    public static String GetCourses(String id) {
        return WeiRao_Url + "courses?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：课程id name：课程名


    /**
     * 日常管理
     * type为1时表示动态管理，id为机构id
     * type为2时表示课程规划，id为社团id
     * type为3时表示请假审批，id为机构id
     * type为4时表示临时账号审批，id为机构id
     * type为5时表示维修管理，id为社团id
     * type为6时表示电话追访，id为社团id
     * type为7时表示打分项，id为社团id
     * type为8时表示其他课，id为机构id
     * type为9时表示课程报告，id为社团id
     * type为10时表示考试管理，id为机构id
     * type 为11时表示管理制度，id为社团id
     */
    public static String GetManageType(String id, String type) {
        return WeiRao_Url + "manage?type=" + type + "&id=" + id;
    }


    /**
     * 动态管理
     */
    public static String GetNews(String id) {
        return WeiRao_Url + "news?id=" + id;
    }
//    id：动态id
//    img：图片
//    title：标题
//    brief：简介
//    time：添加时间

    /**
     * 添加动态
     */
    public static String GetAddNews(String name, String type, String img, String brief, String detail) {
        return WeiRao_Url + "addNews?name=" + name + "&type=" + type + "&img=" + img + "&brief=" + brief + "&detail=" + detail;
    }
//    {code：0，msg：“保存成功”}

    /**
     * 动态详情
     */
    public static String GetNewsDetail(String id) {
        return WeiRao_Url + "newsDetail?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}title：标题 group_name：社团名 detail：详情

    /**
     * 课程规划
     */
    public static String GetCoursePlan(String groupId) {
        return WeiRao_Url + "coursePlan?groupId=" + groupId;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：plan：规划详情

    /**
     * 添加课程规划
     */
    public static String GetAddTrain(String groupId, String content) {
        return WeiRao_Url + "addTrain?groupId=" + groupId + "&content=" + content;
    }
//    {code：0，msg：“保存成功”}


    /**
     * 请假数据
     */
    public static String GetLeaveData(String id) {
        return WeiRao_Url + "leaveData?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：
//    teacher：老师请假数据，如下
//    {
//        id：数据id
//        name：姓名
//        img：头像
//        reason：原因
//        phone：手机
//        course：课程名
//        start：请假开始时间
//        end：请假结束时间
//    }
//    Student：学生请假数据，如下
//    {
//        id：请假记录id
//        name：学生姓名
//        img：学生头像
//        reason：原因
//        phone：手机
//        course：课程名
//        start：请假开始时间
//        end：请假结束时间
//    }

    /**
     * 请假详情
     */
    public static String GetLeaveInfo(String id) {
        return WeiRao_Url + "leaveInfo?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：name：姓名 img：头像 reason：原因 phone：手机 course：课程名 start：请假开始时间 end：请假结束时间


    /**
     * 请假审批
     */
    public static String GetLeaveAuth(String id, String result, String refuse) {
        if (result.equals("1")) {
            return WeiRao_Url + "leaveAuth?id=" + id + "&result=" + result;
        } else {
            return WeiRao_Url + "leaveAuth?id=" + id + "&result=" + result + "&refuse=" + refuse;
        }
    }
//    id：请假记录id
//    result：结果，通过传1，拒绝传2
//    refuse：拒绝原因
//    返回数据：{code：0，msg：“操作成功”}

    /**
     * 临时账号数据
     */
    public static String GetTmpAccount(String id) {
        return WeiRao_Url + "tmpAccount?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：数据id name：姓名 img：头像 reason：原因 phone：手机 course：课程名

    /**
     * 临时账号详情
     */
    public static String GetTmpInfo(String id) {
        return WeiRao_Url + "tmpInfo?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：name：姓名 img：头像 reason：原因 phone：手机 course：课程名

    /**
     * 临时账号审核
     */
    public static String GetTmpAuth(String id, String result, String refuse) {
        if (result.equals("1")) {
            return WeiRao_Url + "tmpAuth?id=" + id + "&result=" + result;
        } else {
            return WeiRao_Url + "tmpAuth?id=" + id + "&result=" + result + "&refuse=" + refuse;
        }
    }
//    id：数据id
//    result：结果，通过传1，拒绝传2
//    refuse：拒绝原因
//    返回数据：{code：0，msg：“操作成功”}


    /**
     * 乐器维修记录
     */
    public static String GetRepair(String id) {
        return WeiRao_Url + "repair?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：数据id user_name：报修人 subject：科目 instrument：乐器 remark：说明


    /**
     * 维修详情
     */
    public static String GetRepairInfo(String id) {
        return WeiRao_Url + "repairInfo?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：user_name：报修人 subject：科目 instrument：乐器 remark：说明


    /**
     * 添加维修记录
     */
    public static String GetAddRepair(String user_name, String subject, String group, String instrument, String remark, String course) {
        return WeiRao_Url + "addRepair?user_name=" + user_name + "&subject=" + subject + "&group=" + group + "&instrument=" + instrument + "&remark=" + remark + "&course=" + course;
    }
//    {code：0，msg：“保存成功”}


    /**
     * 追访记录
     */
    public static String GetFollowUp(String id) {
        return WeiRao_Url + "followUp?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：数据id teacher：指导老师 content：内容 feedback：反馈 solution：解决办法 is_solved：是否解决，1是，2否


    /**
     * 追访详情
     */
    public static String GetFollowInfo(String id) {
        return WeiRao_Url + "followInfo?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：eacher：指导老师 content：内容 feedback：反馈 solution：解决办法 is_solved：是否解决，1是，2否

    /**
     * 添加追访
     */
    public static String GetAddFollow(String student, String teacher, String groupId, String content, String feedback, String solution, String reason, String solved) {
        return WeiRao_Url + "addFollow?student=" + student + "&teacher=" + teacher + "&groupId=" + groupId + "&content=" + content + "&feedback=" + feedback + "&solution=" + solution + "&reason=" + reason + "&solved=" + solved;
    }
//    student：学生 teacher：老师 groupId：社团id content：内容 feedback：反馈 solution：解决办法 reason：原因 solved：是否解决，1是，2否 返回数据：{code：0，msg：“保存成功”}

    /**
     * 修改社团打分项
     */
    public static String GetScoreItem(String groupId, String item) {
        return WeiRao_Url + "scoreItem?groupId=" + groupId + "&item=" + item;
    }
//    {code：0，msg：“修改成功”}


    /**
     * 其他课
     */
    public static String GetOtherCourse(String id) {
        return WeiRao_Url + "otherCourse?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：数据id name：课程名 class_time：上课时间

    /**
     * 其他课详情
     */
    public static String GetOtherInfo(String id) {
        return WeiRao_Url + "otherInfo?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：name：课程名 class_time：上课时间

    /**
     * 添加其他课
     */
    public static String GetAddOther(String id, String name, String time, String advice, String execution, String teacher) {
        return WeiRao_Url + "addOther?id=" + id + "&name=" + name + "&time=" + time + "&advice=" + advice + "&execution=" + execution + "&teacher=" + teacher;
    }
//    name：名称 time：上课时间 advice：老师意见 execution：执行情况 返回数据：{code：0，msg：“保存成功”}


    /**
     * 课程报告列表
     */
    public static String GetCourseReport(String groupId) {
        return WeiRao_Url + "courseReport?groupId=" + groupId;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：数据id img：课程图 name：课程名 teacher：上课老师 class_time：上课时间 status：状态，0待审核，1通过，2拒绝

    /**
     * 上课汇报管理新接口带参数reportGroup?id=1
     */

    public static String GetReportGroup(String id) {
        return WeiRao_Url + "getGroups?agencyId=" + id;
    }

    /**
     * 课程报告详情
     */
    public static String GetReportInfo(String id) {
        return WeiRao_Url + "reportInfo?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：mg：课程图 name：课程名 teacher：上课老师 class_time：上课时间 status：状态，0待审核，1通过，2拒绝


    /**
     * 审核报告
     */
    public static String GetReportAuth(String id, String result, String reason) {
        if (result.equals("2")) {
            return WeiRao_Url + "reportAuth?id=" + id + "&result=" + result + "&reason=" + reason;
        } else {
            return WeiRao_Url + "reportAuth?id=" + id + "&result=" + result;
        }
    }
//    {code：0，msg：“操作成功”}


    /**
     * 考试列表
     */
    public static String GetExamList(String id) {
        return WeiRao_Url + "examLists?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：数据id course：课程名 time：考试时间

    /**
     * 添加考试
     */
    public static String GetAddExam(String id, String courseId, String time) {
        return WeiRao_Url + "addExam?id=" + id + "&courseId=" + courseId + "&time=" + time;
    }
//    {code：0，msg：“保存成功”}


    /**
     * 录入成绩时查询学生
     */
    public static String GetExamStudent(String id) {
        return WeiRao_Url + "examStudent?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：id：学生id name：学生姓名


    /**
     * 保存成绩
     */
    public static String GetSaveScore(String id, String sid, String score) {
        return WeiRao_Url + "saveScore?id=" + id + "&sid=" + sid.toString() + "&score=" + score.toString();
    }
//    {code：0，msg：“保存成功”}

    /**
     * 管理制度详情
     */
    public static String GetRule(String id) {
        return WeiRao_Url + "rule?id=" + id;
    }
//    {code：0，msg：“数据获取成功”，data：{}}Data数据说明：title：标题 img：图片 detail：详情


    /**
     * 添加管理制度
     */
    public static String GetAddRule(String groupId, String name, String img, String detail) {
        return WeiRao_Url + "addRule?groupId=" + groupId + "&name=" + name + "&img=" + img + "&detail=" + detail;
    }
//    {code：0，msg：“保存成功”}

    /**
     * 点击课程详情查看学生点名情况
     */
    public static String GetCourseStudent(String id) {
        return WeiRao_Url + "callInfo?classId=" + id;
    }

    /**
     * 点击查看审核报告学生打分情况
     */
    public static String GetCheckGrade(String id) {
        return WeiRao_Url + "scoreInfo?id=" + id;
    }

    /**
     * 搜索乐器维修、电话追访
     */
    public static String GetSearch(int type, String content) {
        String searchStr = null;
        switch (type) {
            /**
             * 乐器
             */
            case 1://报修人
                searchStr = WeiRao_Url + "manage?type=5&user=" + content;
                break;
            case 2://科目
                searchStr = WeiRao_Url + "manage?type=5&subject=" + content;
                break;
            case 3://维修器材
                searchStr = WeiRao_Url + "manage?type=5&instrument=" + content;
                break;

            /**
             * 追访
             */
            case 4:
                searchStr = WeiRao_Url + "manage?type=6&name=" + content;
                break;
            case 5:
                searchStr = WeiRao_Url + "manage?type=6&agency=" + content;
                break;
        }
        return searchStr;
    }

    /**
     * 老师主页
     */
    public static String GetTeacher(String id) {
        return WeiRao_Url + "teacherInfo?id=" + id;
    }

    /**
     * 维修管理查询
     */
    public static String GetManageCheck(String theRepairId, String sj, String sj_, String the_subject_id, String the_course_id, String user_name) {
        StringBuilder sb = new StringBuilder();
        sb.append(WeiRao_Url);
        sb.append("manage?type=5");
        if (theRepairId == null) {
            return null;
        }
        sb.append("&id=" + theRepairId);
        if (sj != null) {
            sb.append("&start_date=" + sj);
        }
        if (sj_ != null) {
            sb.append("&end_date=" + sj_);
        }
        if (the_subject_id != null) {
            sb.append("&subject_id=" + the_subject_id);
        }
        if (the_course_id != null) {
            sb.append("&course_id=" + the_course_id);
        }
        if (user_name != null) {
            sb.append("&user=" + user_name);
        }
        return sb.toString();
    }

    /**
     * 全部学生查询 myStudent
     */
//    subject_id：科目id
//    course_id：课程id
//    start_time：注册开始时间，格式如2016-07-01
//    end_time：注册结束时间，格式如2016-07-01
//    name：学生姓名
//    mobile：电话
    public static String GetAllStudentCheck(String id, String sub_id, String course_id, String time_start, String time_end, String name, String phone) {
        StringBuilder sb = new StringBuilder();
        sb.append(WeiRao_Url);
        sb.append("myStudent");
        if (id == null) {
            return null;
        }
        sb.append("?id=" + id);
        if (sub_id != null) {
            sb.append("&subject_id=" + sub_id);
        }
        if (course_id != null) {
            sb.append("&course_id=" + course_id);
        }
        if (time_start != null) {
            sb.append("&start_time=" + time_start);
        }
        if (time_end != null) {
            sb.append("&end_time=" + time_end);
        }
        if (name != null) {
            sb.append("&name=" + name);
        }
        if (phone != null) {
            sb.append("&mobile=" + phone);
        }
        return sb.toString();
    }


    /**
     * 新增 课程查询
     */

    public static String GetClassCheck(String groupId, String status, String courseName, String teacher, String startDate, String endDate) {
        StringBuilder sb = new StringBuilder();
        sb.append(WeiRao_Url);
        sb.append("manage?type=9");
        if (groupId == null) {
            return null;
        }
        sb.append("&id=" + groupId);
        if (status != null) {
            sb.append("&status=" + status);
        }
        if (courseName != null) {
            sb.append("&courseName=" + courseName);
        }
        if (teacher != null) {
            sb.append("&teacher=" + teacher);
        }
        if (startDate != null) {
            sb.append("&startDate=" + startDate);
        }
        if (endDate != null) {
            sb.append("&endDate=" + endDate);
        }

        return sb.toString();
    }

    /**
     * 课程详情 学生考勤
     */
    public static String GetStudentCallInfo(String id) {
        return WeiRao_Url + "callInfo2?classId=" + id;
    }

}
