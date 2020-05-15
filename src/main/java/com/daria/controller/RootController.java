package com.daria.controller;

import com.alibaba.fastjson.JSONObject;
import com.daria.bean.*;
import com.daria.bean.query.CourseQuery;
import com.daria.bean.query.StudentQuery;
import com.daria.bean.vo.CourseVO;
import com.daria.service.RootService;
import com.daria.service.StudentService;
import com.daria.service.TeacherService;
import com.daria.util.BaseResponse;
import com.daria.util.CodeMsgUtil;
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
 * @Date 2020/3/26 -- 21:51
 */
@Controller
public class RootController {
    @Resource
    private RootService service;
    @Resource
    private TeacherService teacherService;
    @Resource
    private StudentService studentService;

//======================================================================================================================
//1、账号管理

    //登录
    @RequestMapping(value = "/root_login", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<?> doLogin(@RequestBody Root root, HttpServletRequest request) {
//        String rName, String password

        BaseResponse<Object> response = new BaseResponse<>();
        Root rootDO = (Root)request.getSession().getAttribute("root");
        //判断登录是否成功
        if (root != null) {
            response.setCodeMsg(CodeMsgUtil.LOGIN_SUCCESS);
            return response;
        }
        Root user = service.login(root);
        //判断user是否是null
        if (user != null) { //登录成功
            HttpSession session = request.getSession(true);
            session.setAttribute("root", user);
            response.setCodeMsg(CodeMsgUtil.LOGIN_SUCCESS); //成功
        } else {
            response.setCodeMsg(CodeMsgUtil.LOGIN_FAIL); //失败
        }

        return response;
    }

    //创建教师账号
    @RequestMapping(value = "/create_t_account", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> teacherRegister(@RequestBody TeacherDO teacherDO) {

        BaseResponse<Object> response = new BaseResponse<>();
        //先判断用户是否存在
        boolean teacherIsExist = teacherService.teacherIsExist(teacherDO.getTeacherNumber());

        //用户已存在直接返回
        if (teacherIsExist) {
           response.setCodeMsg(CodeMsgUtil.USER_ALREADY_EXISTS); //用户已存在
            return response;
        }

        //用户不存在
        //将信息账户密码存入数据库
        boolean isSuccess = service.createTeacherAccount(teacherDO.getTeacherName(), teacherDO.getTeacherNumber(), teacherDO.getPassword());

        if (isSuccess) {
            service.updateTeacherCirculation(teacherDO.getTeacherNumber(), 10);
            response.setCodeMsg(CodeMsgUtil.REGISTER_SUCCESS);
        } else {
            response.setCodeMsg(CodeMsgUtil.REGISTER_FAIL); //注册失败
        }

        return response;
    }

    //批量创建

    //创建学生账号
    @RequestMapping(value = "/create_s_account", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> studentRegister(@RequestBody StudentDO studentDO) {

        BaseResponse<Object> response = new BaseResponse<>();
        //先判断用户是否存在
        boolean studentIsExist = studentService.studentIsExist(studentDO.getStudentNumber());

        //用户已存在直接返
        if (studentIsExist) {
            response.setCodeMsg(CodeMsgUtil.USER_ALREADY_EXISTS); //用户已存在
            return response;
        }

        //用户名不存在
        //将信息账户密码存入数据库
        boolean isSuccess = service.createStudentAccount(studentDO.getStudentName(),studentDO.getStudentNumber(), studentDO.getPassword());

        if (isSuccess) {
            service.updateStudentCirculation(studentDO.getStudentNumber(), 10);
            response.setCodeMsg(CodeMsgUtil.REGISTER_SUCCESS); //注册成功的确认码
        } else {
            response.setCodeMsg(CodeMsgUtil.REGISTER_FAIL); //注册失败
        }

        return response;
    }


//======================================================================================================================

//2、图书管理

    //查看书架

    //上架图书
    @RequestMapping(value = "/add_book", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> addBook(@RequestBody BookDO bookDO) {
//String bookName, String author, String publishingHouse, String category, double price, double remainderNumber

        BaseResponse<Object> response = new BaseResponse<>();
        String currentTime = GetTimeUtil.getCurrentTime();

        boolean isSuccess = service.addBook(bookDO.getBookName(), bookDO.getAuthor(), bookDO.getPublishingHouse(),
                bookDO.getCategory(), bookDO.getPrice(), currentTime, bookDO.getRemainderNumber());

        if (isSuccess) { //添加成功
            response.setCodeMsg(CodeMsgUtil.ADD_BOOK_SUCCESS); //确认码
        } else {
            response.setCodeMsg(CodeMsgUtil.ADD_BOOK_FAIL); //失败的确认码
        }

        return response;
    }


    //删除图书
    @RequestMapping(value = "/delete_book", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> deleteBook(@RequestBody int bookId) {
//String bookName, String author, String category, double price, String putTime, int remainderNumber
        BaseResponse<Object> response = new BaseResponse<>();

        boolean isSuccess = service.deleteBook(bookId);

        if (isSuccess) { //删除成功
            response.setCodeMsg(CodeMsgUtil.DELETE_BOOK_SUCCESS); //确认码
        } else {
            response.setCodeMsg(CodeMsgUtil.DELETE_BOOK_FAIL); //失败的确认码
        }

        return response;
    }


    //查看图书的借阅情况  借阅率、借阅人的信息
    @RequestMapping(value = "/library_information", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> libraryInformation() {
        BaseResponse<Object> response = new BaseResponse<>();
//        List<CourseDO> list = service.querySchedule();
//        response.setCodeMsg(CodeMsgUtil.QUERY_UNCHECK_COURSE_SUCCESS, list);
        return response;
    }

//======================================================================================================================
//3、班级管理

    //查看所有班级

    //修改学生班级
    @RequestMapping(value = "/modification_class", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> modificationClass(@RequestBody StudentQuery studentQuery) {
//        String studentNumber,  int classId
        BaseResponse<Object> response = new BaseResponse<>();

        String studentNumber = studentQuery.getStudentNumber();
        String classId = studentQuery.getClassId(); //班级
        boolean isSuccess = service.modificationClass(studentNumber, classId);
        if (isSuccess) {
            response.setCodeMsg(CodeMsgUtil.MODIFICATION_SUCCESS);
        } else {
            response.setCodeMsg(CodeMsgUtil.MODIFICATION_FAIL);
        }
        return response;
    }

    //创建班级


//======================================================================================================================
//4、课程管理

    //查询所有的课程
    @RequestMapping(value = "/query_all_course",method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<?> queryAllCourse() {
        BaseResponse<List<CourseDO>> response = new BaseResponse<>();
        List<CourseDO> list = service.queryAllCourse();
        response.setCodeMsg(CodeMsgUtil.QUERY_COURSE_SUCCESS, list);
        
        return response;
    }

    //显示所有的必修/选修课程
    @RequestMapping(value = "/show_course",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> queryCourseWithType(@RequestBody String type) {
        BaseResponse<List<CourseDO>> response = new BaseResponse<>();
        List<CourseDO> list = service.queryCourseWithType(type);
        response.setCodeMsg(CodeMsgUtil.QUERY_COURSE_SUCCESS, list);
        return response;
    }

    //用课程名查询一门课程
    @RequestMapping(value = "/query_courseName",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> queryCourseWithCourseName(@RequestBody int courseId) {
        BaseResponse<List<CourseDO>> response = new BaseResponse<>();
        List<CourseDO> list = service.queryCourseWithCourseName(courseId);
        response.setCodeMsg(CodeMsgUtil.QUERY_COURSE_SUCCESS, list);
        return response;
    }

    //显示未审核/审核通过/未审核的课程
    @RequestMapping(value = "/query_schedule",  method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> querySchedule(@RequestBody int isCheck) {
        BaseResponse<List<CourseDO>> response = new BaseResponse<>();
        List<CourseDO> list = service.querySchedule(isCheck);
        response.setCodeMsg(CodeMsgUtil.QUERY_UNCHECK_COURSE_SUCCESS, list);
        return response;
    }

    //处理申请  通过/不通过课程申请
    @RequestMapping(value = "/handle_application",  method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> handleApplication(CourseQuery courseQuery) {
        BaseResponse<Object> response = new BaseResponse<>();
        int courseId = courseQuery.getCourseId();
        int isCheck = courseQuery.getIsCheck(); //通过是1，不通过是2
        int isOpen = courseQuery.getIsOpen(); //在isCheck=1时开放是1，否则是0代表关闭
        boolean isSuccess = service.handleApplication(courseId, isCheck, isOpen);
        if (isSuccess) {
            response.setCodeMsg(CodeMsgUtil.OPERATE_SUCCESS);
        } else {
            response.setCodeMsg(CodeMsgUtil.OPERATE_FAIL);
        }
        return response;
    }

    //查询开放/关闭的选修课  type， isOpen
    @RequestMapping(value = "/queryIsOpenCourse",  method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> queryIsOpenCourse(@RequestBody CourseQuery courseQuery ) {
        BaseResponse<List<CourseVO>> response = new BaseResponse<>();
        int isOpen = courseQuery.getIsOpen(); //开放是1，关闭是0
        String type = "选修";
        List<CourseVO> list = service.queryIsOpenCourse(isOpen, type);
        response.setCodeMsg(CodeMsgUtil.QUERY_COURSE_SUCCESS, list);
        return response;
    }

    //课程审核：删除一个课程
    @RequestMapping(value = "/delete_Course", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCourse(String courseId) {
        JSONObject node = new JSONObject();
        boolean isSuccess = service.deleteCourse(courseId);
        if (isSuccess) {
            node.put("code", CodeMsgUtil.DELETE_COURSE_SUCCESS);
        } else {
            node.put("code", CodeMsgUtil.DELETE_COURSE_FAIL);
        }
        return node.toString();
    }

    //选修课管理
        //查询开放所有选课

    //查看选课的学生信息


    //开放选课
    //确认选课名单，之后学生和教师无法修改内容
    //关闭选课（选课结束）



    //删除一个学生的选课 courseId, studentNumber




    //分配必修课：给班级和科目建立对应关系    //给学生添加课程   在成绩表
        //给对应班级中的每一个人和科目建立关系：scores成绩表中

    //查看一个班级某个科目的成绩(必修)  classId, courseId
    //查看一门选修课成绩

    //修改成绩   studentNumber,courseId,score


    //查看某一节课的签到情况
    //查看所有课程的每一次签到的到课率



    //创建班级

//======================================================================================================================
//5、成绩管理

    //查看某个课程的成绩
    //修改成绩


//======================================================================================================================
//6、查看到课率



}

//    //显示未审核的课程
//    @RequestMapping(value = "/queryUncheckedCourse",  method = RequestMethod.POST)
//    @ResponseBody
//    public BaseResponse<?> queryUncheckedCourse(@RequestBody int isCheck) {
//        BaseResponse<List<CourseDO>> response = new BaseResponse<>();
//        List<CourseDO> list = service.queryUncheckedCourse(isCheck);
//        response.setCodeMsg(CodeMsgUtil.QUERY_UNCHECK_COURSE_SUCCESS, list);
//        return response;
//    }

//    //查询通过的课程
//    @RequestMapping(value = "/queryApprovedCourse", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
//    @ResponseBody
//    public BaseResponse<List<CourseDO>> queryApprovedCourse() {
//        BaseResponse<List<CourseDO>> response = new BaseResponse<>();
//        List<CourseDO> list = service.queryApprovedCourse(1);
//        response.setCodeMsg(CodeMsgUtil.QUERY_APPROVE_COURSE_SUCCESS, list);
//        return response;
//    }
//    //查询未通过的课程
//    @RequestMapping(value = "/queryUnapprovedCourse", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
//    @ResponseBody
//    public BaseResponse<List<CourseDO>> queryUnapprovedCourse() {
//        BaseResponse<List<CourseDO>> response = new BaseResponse<>();
//        List<CourseDO> list = service.queryUnapprovedCourse(2);
//        response.setCodeMsg(CodeMsgUtil.QUERY_APPROVE_COURSE_SUCCESS, list);
//        return response;
//    }