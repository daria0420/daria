package com.daria.util;

/**
 * @Author Daria
 * @Description
 * @Date 2020/4/5 -- 10:54
 */
public class CodeMsgUtil {
    private int code; //状态码
    private String msg; //信息

    private CodeMsgUtil( ) {
    }

    private CodeMsgUtil( int code,String msg ) {
        this.code = code;
        this.msg = msg;
    }
    //通用的错误码
    public static CodeMsgUtil SUCCESS = new CodeMsgUtil(0, "success");
    public static CodeMsgUtil SERVER_ERROR = new CodeMsgUtil(500100, "服务端异常");
    public static CodeMsgUtil BIND_ERROR = new CodeMsgUtil(500101, "参数校验异常");
    public static CodeMsgUtil REQUEST_ILLEGAL = new CodeMsgUtil(500102, "请求非法");
    public static CodeMsgUtil ACCESS_LIMIT_REACHED= new CodeMsgUtil(500104, "访问太频繁");


//账号管理模块 5002XX
    //注册
    public static CodeMsgUtil USER_ALREADY_EXISTS = new CodeMsgUtil(500201, "用户已存在");
    public static CodeMsgUtil USER_NOT_EXISTS = new CodeMsgUtil(500202, "用户不存在");
    public static CodeMsgUtil REGISTER_SUCCESS = new CodeMsgUtil(500203, "注册成功");
    public static CodeMsgUtil REGISTER_FAIL = new CodeMsgUtil(500204, "注册失败");

    //登录
    public static CodeMsgUtil LOGIN_SUCCESS = new CodeMsgUtil(500205, "登录成功");
    public static CodeMsgUtil LOGIN_FAIL = new CodeMsgUtil(500206, "登录失败");
//    public static CodeMsgUtil SESSION_ERROR = new CodeMsgUtil(500310, "Session不存在或者已经失效");
    public static CodeMsgUtil PASSWORD_EMPTY = new CodeMsgUtil(500207, "登录密码不能为空");
    public static CodeMsgUtil PASSWORD_ERROR = new CodeMsgUtil(500208, "密码错误");

    //修改密码
    public static CodeMsgUtil MODIFY_PASSWORD_SUCCESS = new CodeMsgUtil(500209, "修改密码成功");
    public static CodeMsgUtil MODIFY_PASSWORD_FAIL = new CodeMsgUtil(500210, "修改密码失败");

    //忘记密码
    public static CodeMsgUtil EMAIL_ERROR = new CodeMsgUtil(500211, "邮箱错误");
    public static CodeMsgUtil EMAIL_SEND_SUCCESS = new CodeMsgUtil(500212, "邮件发送成功");
    public static CodeMsgUtil EMAIL_SEND_FAIL = new CodeMsgUtil(500213, "邮件发送失败");

//班级管理 5003XX
    public static CodeMsgUtil MODIFICATION_SUCCESS = new CodeMsgUtil(5003, "成功修改学生班级");
    public static CodeMsgUtil MODIFICATION_FAIL = new CodeMsgUtil(5003, "修改学生班级失败");


//课程管理 5004XX
    public static CodeMsgUtil APPLY_COURSE_SUCCESS = new CodeMsgUtil(500, "课程申请完成");
    public static CodeMsgUtil APPLY_COURSE_FAIL = new CodeMsgUtil(500, "课程申请失败");
    public static CodeMsgUtil QUERY_COURSE_SUCCESS = new CodeMsgUtil(500, "成功查询所有课程");
    public static CodeMsgUtil QUERY_APPROVE_COURSE_SUCCESS = new CodeMsgUtil(500, "成功查询审核通过的课程");
    public static CodeMsgUtil OPERATE_SUCCESS = new CodeMsgUtil(500, "处理成功");
    public static CodeMsgUtil OPERATE_FAIL = new CodeMsgUtil(500, "处理失败");
    public static CodeMsgUtil QUERY_UNCHECK_COURSE_SUCCESS = new CodeMsgUtil(500, "成功查询未审核的课程");
    public static CodeMsgUtil QUERY_UNAPPROVED_COURSE_SUCCESS = new CodeMsgUtil(500, "成功查询未审核通过课程");
    public static CodeMsgUtil REAPPLY_COURSE_SUCCESS = new CodeMsgUtil(500, "成功重新申请课程");
    public static CodeMsgUtil REAPPLY_COURSE_FAIL = new CodeMsgUtil(500, "无法重新申请课程");
    public static CodeMsgUtil NON_SElECT_COURSE = new CodeMsgUtil(500, "无可选课程");
    public static CodeMsgUtil COURSE_NOT_EXIST = new CodeMsgUtil(500, "课程不存在");
    public static CodeMsgUtil NON_RELEASE_COURSE = new CodeMsgUtil(500, "教师尚未发布课程");
    public static CodeMsgUtil QUERY_OWNER_COURSE_SUCCESS = new CodeMsgUtil(500, "成功查询教师发布课程");
    public static CodeMsgUtil DELETE_COURSE_SUCCESS = new CodeMsgUtil(500, "选课删除成功");
    public static CodeMsgUtil DELETE_COURSE_FAIL = new CodeMsgUtil(500, "选课删除失败");
    public static CodeMsgUtil SELECT_COURSE_SUCCESS = new CodeMsgUtil(500, "选课成功");
    public static CodeMsgUtil SELECT_COURSE_FAIL = new CodeMsgUtil(500, "选课失败");

//成绩管理
    public static CodeMsgUtil INSERT_SCORE_SUCCESS = new CodeMsgUtil(500, "添加成绩成功");
    public static CodeMsgUtil INSERT_SCORE_FAIL = new CodeMsgUtil(500, "添加成绩失败");
    public static CodeMsgUtil QUERY_SCORE_SUCCESS = new CodeMsgUtil(500, "查询成绩成功");
    public static CodeMsgUtil QUERY_SCORE_FAIL = new CodeMsgUtil(500, "查询成绩失败");

//图书模块 5005XX
    public static CodeMsgUtil DELETE_BOOK_SUCCESS = new CodeMsgUtil(500501, "成功下架图书");
    public static CodeMsgUtil DELETE_BOOK_FAIL = new CodeMsgUtil(500502, "下架图书失败");
    public static CodeMsgUtil ADD_BOOK_SUCCESS = new CodeMsgUtil(500503, "成功上架图书");
    public static CodeMsgUtil ADD_BOOK_FAIL = new CodeMsgUtil(500504, "上架图书失败");
    public static CodeMsgUtil QUERY_BOOK_SUCCESS = new CodeMsgUtil(500505, "成功查询借阅图书");
    public static CodeMsgUtil BORROW_SUCCESS = new CodeMsgUtil(500506, "借阅成功");
    public static CodeMsgUtil BORROW_FAIL = new CodeMsgUtil(500507, "借阅失败");
    public static CodeMsgUtil BOOK_NOT_EXIST = new CodeMsgUtil(500508, "图书不存在");
    public static CodeMsgUtil RETURN_BOOK_SUCCESS = new CodeMsgUtil(500509, "还书成功");
    public static CodeMsgUtil RETURN_BOOK_FAIL = new CodeMsgUtil(500510, "还书失败");
    public static CodeMsgUtil UPDATE_CIRCULATION_SUCCESS = new CodeMsgUtil(500511, "成功更新借书信息表");
    public static CodeMsgUtil UPDATE_CIRCULATION_FAIL = new CodeMsgUtil(500512, "成功更新借书信息表失败");




    public static CodeMsgUtil QUERY_STUDENT_SUCCESS = new CodeMsgUtil(500, "成功查询所有学生");
    public static CodeMsgUtil QUERY_STUDENT_NOT_EXIST = new CodeMsgUtil(500, "该学生不存在");
    public static CodeMsgUtil QUERY_TEACHER_SUCCESS = new CodeMsgUtil(500, "成功查询所有学生");
    public static CodeMsgUtil QUERY_TEACHER_NOT_EXIST = new CodeMsgUtil(500, "该学生不存在");

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }


//    public CodeMsgUtil fillArgs(Object... args) {
//        int code = this.code;
//        String message = String.format(this.msg, args);
//        return new CodeMsgUtil(code, message);
//    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }

}
