package com.daria.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.daria.bean.*;
import com.daria.bean.dto.TeacherDTO;
import com.daria.bean.query.BookQuery;
import com.daria.bean.query.ClassCourseQuery;
import com.daria.bean.query.TeacherQuery;
import com.daria.bean.vo.BookVO;
import com.daria.bean.vo.ScoresVO;
import com.daria.bean.vo.StudentVO;
import com.daria.service.TeacherService;
import com.daria.util.BaseResponse;
import com.daria.util.CodeMsgUtil;
import com.daria.util.EmailUtil;
import com.daria.util.GetTimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @Author Daria
 * @Description
 * @Date 2020/3/26 -- 21:50
 */
@Controller
public class TeacherController {
    @Resource
    private TeacherService teacherService;


//1、账号管理

    //登录
    @RequestMapping(value = "/teacher_login", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<?> doLogin(@RequestBody TeacherQuery teacherQuery, HttpServletRequest request) {
        //String teacherNumber, String password

        BaseResponse<Object> response = new BaseResponse<>();
        //判断用户是否已经登录
        TeacherDO teacherDO = (TeacherDO)request.getSession().getAttribute("teacher");
        if (teacherDO != null) {
            response.setCodeMsg(CodeMsgUtil.LOGIN_SUCCESS); //成功确认码
            return response;
        }

        //登录操作
        //判断用户是否存在
        boolean teacherIsExist = teacherService.teacherIsExist(teacherQuery.getTeacherNumber());
        if (!teacherIsExist) {
            response.setCodeMsg(CodeMsgUtil.USER_NOT_EXISTS);
            return response;
        }

        //判断登录是否成功，成功则将信息写入session
        TeacherDO user = teacherService.login(teacherQuery);

        //判断user是否是null
        if (user != null) { //登录成功
            HttpSession session = request.getSession(true);
            session.setAttribute("teacher", user);
            response.setCodeMsg(CodeMsgUtil.LOGIN_SUCCESS); //登录成功
        } else {
            response.setCodeMsg(CodeMsgUtil.PASSWORD_ERROR); //密码错误
        }

        return response;
    }

    //修改密码
    @RequestMapping("/teacher_modifyPwd")
    @ResponseBody
    public BaseResponse<?> modifyPwd(@RequestBody TeacherDTO teacherDTO, HttpServletRequest request) {
        //        String srcPassword, String newPassword
        HttpSession session = request.getSession();
        TeacherDO teacherDO = (TeacherDO)session.getAttribute("student");

        String srcPassword = teacherDTO.getSrcPassword();
        String newPassword = teacherDTO.getNewPassword();

        BaseResponse<Object> response = new BaseResponse<>();

        if (teacherDO.getPassword() != srcPassword) {
            //原始密码错误
            response.setCodeMsg(CodeMsgUtil.PASSWORD_ERROR);
            return response;
        }

        //修改密码
        boolean isSuccess = teacherService.modifyPwd(teacherDO.getTeacherNumber(), newPassword);

        if (isSuccess) { //修改成功
            request.getSession().removeAttribute("teacher"); //删除信息，需要重新登录
            response.setCodeMsg(CodeMsgUtil.MODIFY_PASSWORD_SUCCESS);
        } else {
            response.setCodeMsg(CodeMsgUtil.MODIFY_PASSWORD_FAIL);
        }

        return response;
    }

    //忘记密码
    @RequestMapping("/teacher_forgetPwd")
    @ResponseBody
    public BaseResponse<?> forgetPwd(@RequestBody TeacherQuery teacherQuery) {
        //        String teacherName， String email,

        BaseResponse<Object> response = new BaseResponse<>();

        String teacherNumber = teacherQuery.getTeacherNumber();
        String teacherName = teacherQuery.getTeacherName();
        String email = teacherQuery.getEmail();


        //先判断当前用户是否存在，不存在直接返回
        boolean isExist = teacherService.teacherIsExist(teacherNumber);
        if (!isExist) {
            response.setCodeMsg(CodeMsgUtil.USER_NOT_EXISTS); //用户不存在
            return response;
        }

        //用户存在
        //则返回密码
        String password = teacherService.forgetPwd(teacherNumber, email);
        if (password == null) { //判断返回结果是否为null，空则说明查询失败
            response.setCodeMsg(CodeMsgUtil.EMAIL_ERROR); //输入邮箱有误
            return response;
        }

        //正确则发送密码到邮箱
        try { //邮件发送可能会抛出异常
            EmailUtil.toEmail(email, teacherName, password);
            response.setCodeMsg(CodeMsgUtil.EMAIL_SEND_SUCCESS); //发送成功
        }  catch (Exception e) {
            response.setCodeMsg(CodeMsgUtil.EMAIL_SEND_FAIL); //发送失败
        } finally {
            return response;
        }
    }

//======================================================================================================================

//2、图书管理

    //查看书架

    //查询已借阅图书
    @RequestMapping(value = "/teacher_query_borrowed_books", produces = "text/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<?> queryBorrowedBooks(@RequestBody HttpServletRequest request) {
        BaseResponse<Object> response = new BaseResponse<>();
        TeacherDO teacherDO = (TeacherDO)request.getSession().getAttribute("teacher");
        List<BookVO> list = teacherService.queryBorrowedBooks(teacherDO.getTeacherNumber());
        response.setCodeMsg(CodeMsgUtil.QUERY_BOOK_SUCCESS, list);
        return response;
    }
//    {"studentName":"小袁","email":"1375476814@qq.com"}

    //借书
    @RequestMapping(value = "/teacher_borrow_book", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> borrowBook(@RequestBody BookQuery bookQuery, HttpServletRequest request) {
//        String teacherNumber, int bookId
        BaseResponse<Object> response = new BaseResponse<>();

        TeacherDO teacherDO = (TeacherDO) request.getSession().getAttribute("teacher");
        String teacherName = teacherDO.getTeacherName();
        String teacherNumber = teacherDO.getTeacherNumber();

        //先判断是否能够借书
        boolean isOver = teacherService.isOverLimit(teacherNumber);
        if (isOver) {
            response.setCodeMsg(CodeMsgUtil.BORROW_FAIL);
            return response;
        }

        //未超出借书数量限制
        String bookName = bookQuery.getBookName();

        int bookId = bookQuery.getBookId();

        String borrowTime = GetTimeUtil.getCurrentTime();
        String returnTime = GetTimeUtil.getReturnBookTime();

        boolean isSuccess = teacherService.borrowBook(bookName, bookId, "教师",
                                                        teacherName, teacherNumber,
                                                        borrowTime, returnTime);
        if (isSuccess) { //借书成功
            response.setCodeMsg(CodeMsgUtil.BORROW_SUCCESS);
        } else {
            response.setCodeMsg(CodeMsgUtil.BORROW_FAIL);
        }
        return response;
    }

    //还书
    @RequestMapping(value = "/teacher_return_book", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> returnBook(@RequestBody BookQuery bookQuery, HttpServletRequest request) {
//        String teacherNumber, String bookId

        BaseResponse<Object> response = new BaseResponse<>();
        TeacherDO teacher = (TeacherDO) request.getSession().getAttribute("teacher");
        String teacherNumber = teacher.getTeacherNumber();
        int bookId = bookQuery.getBookId();

        boolean isSuccess = teacherService.returnBook(teacherNumber, bookId);

        if (isSuccess) { //借书成功
            response.setCodeMsg(CodeMsgUtil.RETURN_BOOK_SUCCESS);
        } else {
            response.setCodeMsg(CodeMsgUtil.RETURN_BOOK_FAIL);
        }

        return response;
    }


    //还书提醒   =======
    @RequestMapping(value = "/teacher_remind_return_book", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<List<BookVO>> remindReturnBook(HttpServletRequest request) {
        TeacherDO teacherDO = (TeacherDO)request.getSession().getAttribute("teacher");
        String currentTime = GetTimeUtil.getCurrentTime();
        return null;

    }

//======================================================================================================================

//3、班级管理
    //查看所有班级

//======================================================================================================================

//4、课程管理

    //查看课程

    //申请课程
    @RequestMapping(value = "/releaseCourse", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> applyCourse(@RequestBody CourseDO courseDO, HttpServletRequest request) {
        //CourseName, type, countLimit

        BaseResponse<Object> response = new BaseResponse<>();
        TeacherDO teacherDO = (TeacherDO)request.getSession().getAttribute("teacher");

        String courseName = courseDO.getCourseName();
        String type = courseDO.getType(); //课程类别
        int countLimit = courseDO.getCountLimit(); //选课的人数限制

        String teacherName = teacherDO.getTeacherName();
        String teacherNumber = teacherDO.getTeacherNumber();

        boolean isSuccess = teacherService.applyCourse(courseName, teacherName,
                teacherNumber, type, countLimit);
        if (isSuccess) {
            response.setCodeMsg(CodeMsgUtil.APPLY_COURSE_SUCCESS);
        } else {
            response.setCodeMsg(CodeMsgUtil.APPLY_COURSE_FAIL);
        }

        return response;

    }


    //申请进度查询
    //教师查询自己发布的课程  //查看课程的审核状态
    @RequestMapping(value = "/queryOwnerCourse", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> queryOwnerCourse(@RequestBody HttpServletRequest request) {
        TeacherDO teacherDO = (TeacherDO)request.getSession().getAttribute("teacher");
        BaseResponse<List<CourseDO>> response = new BaseResponse<>();
        List<CourseDO> list = teacherService.queryOwnerCourse(teacherDO.getTeacherNumber());
        response.setCodeMsg(CodeMsgUtil.QUERY_OWNER_COURSE_SUCCESS, list);
        return response;
    }

    //查询发布通过的课程
    @RequestMapping(value = "/queryApprovedCourse", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<List<CourseDO>> queryApprovedCourse(@RequestBody HttpServletRequest request) {
        TeacherDO teacherDO = (TeacherDO)request.getSession().getAttribute("teacher");
        BaseResponse<List<CourseDO>> response = new BaseResponse<>();
        List<CourseDO> list = teacherService.queryApprovedCourse(teacherDO.getTeacherNumber(), 1);
        response.setCodeMsg(CodeMsgUtil.QUERY_APPROVE_COURSE_SUCCESS, list);
        return response;
    }

    //查询自己未审核通过的课程信息
    @RequestMapping(value = "/queryUnapprovedCourse", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<List<CourseDO>> queryUnapprovedCourse(@RequestBody HttpServletRequest request) {
        TeacherDO teacherDO = (TeacherDO)request.getSession().getAttribute("teacher");

        BaseResponse<List<CourseDO>> response = new BaseResponse<>();

        //
        List<CourseDO> list = teacherService.queryUnapprovedCourse(teacherDO.getTeacherNumber(), 2);
        if (list != null) {
            response.setCodeMsg(CodeMsgUtil.QUERY_UNAPPROVED_COURSE_SUCCESS, list);
        } else {
            response.setCodeMsg(CodeMsgUtil.NON_RELEASE_COURSE);
        }

        return response;

    }

    //查询自己未审核的课程信息  //查看自己正在申请的课程
    @RequestMapping(value = "/queryUncheckedCourse", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> queryUncheckedCourse(@RequestBody HttpServletRequest request) {
        TeacherDO teacherDO = (TeacherDO)request.getSession().getAttribute("teacher");

        BaseResponse<List<CourseDO>> response = new BaseResponse<>();
        List<CourseDO> list = teacherService.queryUncheckedCourse(teacherDO.getTeacherNumber(), 0);
        response.setCodeMsg(CodeMsgUtil.QUERY_UNCHECK_COURSE_SUCCESS, list);
        return response;

    }

    //重新申请课程
    @RequestMapping(value = "/reapplyCourse", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> reapplyCourse(@RequestBody CourseDO courseDO, HttpServletRequest request) {
        BaseResponse<Object> response = new BaseResponse<>();
        boolean isSuccess = teacherService.reapplyCourse(courseDO.getCourseName(),
                                                            courseDO.getCountLimit(),
                                                            courseDO.getType(),
                                                            courseDO.getCourseId());
        if (isSuccess) {
            response.setCodeMsg(CodeMsgUtil.REAPPLY_COURSE_SUCCESS);
        } else {
            response.setCodeMsg(CodeMsgUtil.REAPPLY_COURSE_FAIL);
        }

        return response;

    }



    //查看选课学生信息   用课程id去查询选课学生的信息 Scores
//    //查看选课学生信息   用课程id去查询选课学生的信息
//    @RequestMapping(value = "/releaseCourse", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
//    @ResponseBody
//    public BaseResponse<?> querySelectCourseStudent(@RequestBody String courseId) {
//        BaseResponse<List<StudentVO>> response = new BaseResponse<>();
//        List<StudentVO> list = teacherService.querySelectCourseStudent(courseId);
//        response.setCodeMsg(CodeMsgUtil.QUERY_STUDENT_SUCCESS, list);
//        return response;
//
//    }

//======================================================================================================================

//5、作业管理

    //发布作业
    //查看作业

//======================================================================================================================

//6、成绩管理

    //查看课程对应班级的成绩
    @RequestMapping("/queryScore")
    @ResponseBody
    public BaseResponse<?> queryScore(@RequestBody int courseId) {
        List<ScoresVO> list = teacherService.queryScore(courseId);
        BaseResponse<List<ScoresVO>> response = new BaseResponse<>();
        response.setCodeMsg(CodeMsgUtil.QUERY_SCORE_SUCCESS, list);
        return null;
    }

    //查看一个班级某一门课程的成绩
    @RequestMapping("/queryClassScore")
    @ResponseBody
    public BaseResponse<?> queryClassScore(@RequestBody ClassCourseQuery classCourseQuery) {
        String classId = classCourseQuery.getClassId();
        int courseId = classCourseQuery.getCourseId();
        List<ScoresVO> list = teacherService.queryClassScore(classId, courseId);
        BaseResponse<List<ScoresVO>> response = new BaseResponse<>();
        response.setCodeMsg(CodeMsgUtil.QUERY_SCORE_SUCCESS, list);
        return null;
    }

    //添加成绩   给学生的某门课程添加成绩，在成绩表，不支持修改
    @RequestMapping("/teacher_addScore_inSC")
    @ResponseBody
    public BaseResponse<?> addScore(@RequestBody ScoresDO scoresDO, HttpServletRequest request) {
//        int score, String studentNumber, String courseId

        BaseResponse<Object> response = new BaseResponse<>();
        boolean isSuccess = teacherService.addScore(scoresDO.getScore(),
                                                    scoresDO.getStudentNumber(),
                                                    scoresDO.getCourseId());
        if (isSuccess) { //添加成功
            response.setCodeMsg(CodeMsgUtil.INSERT_SCORE_SUCCESS);
        } else {
            response.setCodeMsg(CodeMsgUtil.INSERT_SCORE_FAIL);
        }

        return response;
    }

//======================================================================================================================

//7、签到模块

    //发布签到

    //关闭签到

    //查看签到情况

    //展示本节课到课率
}
