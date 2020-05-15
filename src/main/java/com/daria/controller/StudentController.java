package com.daria.controller;

import com.alibaba.fastjson.JSONObject;
import com.daria.bean.*;
import com.daria.bean.dto.StudentDTO;
import com.daria.bean.query.BookQuery;
import com.daria.bean.query.StudentQuery;
import com.daria.bean.vo.BookVO;
import com.daria.service.QueryService;
import com.daria.service.StudentService;
import com.daria.util.BaseResponse;
import com.daria.util.CodeMsgUtil;
import com.daria.util.EmailUtil;
import com.daria.util.GetTimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;



/**
 * @Author Daria
 * @Description
 * @Date 2020/3/18 -- 0:03
 */
@Controller
public class StudentController {
    @Resource
    private StudentService studentService;
    @Resource
    private QueryService queryService;

//======================================================================================================================
//1、账号管理
    //登录
    @RequestMapping(path = "/student_login", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> doLogin(@RequestBody StudentQuery student, HttpServletRequest request) {

//        JSONObject node = new JSONObject();

        BaseResponse<Object> response = new BaseResponse<>();
        //判断用户是否已经登录
        StudentDO studentDO = (StudentDO) request.getSession().getAttribute("student");
        if (studentDO != null) {
            response.setCodeMsg(CodeMsgUtil.LOGIN_SUCCESS);
            return response;
        }

        //登录操作
        //判断用户是否存在
        boolean studentIsExist = studentService.studentIsExist(student.getStudentNumber());
        if (!studentIsExist) {
            response.setCodeMsg(CodeMsgUtil.USER_NOT_EXISTS);
            return response;
        }

        //判断登录是否成功，成功则将信息写入session
        StudentDO user = studentService.login(student);

        if (user != null) { //登录成功
            HttpSession session = request.getSession(true);
            session.setAttribute("student", user);
            response.setCodeMsg(CodeMsgUtil.LOGIN_SUCCESS);  //登录成功
        } else {
            response.setCodeMsg(CodeMsgUtil.PASSWORD_ERROR);  //密码错误
        }
        return response;
    }

    //修改密码
    @RequestMapping(value = "/student_modifyPwd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> modifyPwd(@RequestBody StudentDTO studentDTO, HttpServletRequest request) {
//        String srcPassword, String newPassword
        BaseResponse<Object> response = new BaseResponse<>();

        HttpSession session = request.getSession();
        StudentDO studentDO = (StudentDO)session.getAttribute("student");


        String srcPassword = studentDTO.getSrcPassword();
        String newPassword = studentDTO.getNewPassword();


        System.out.println();
        if (!studentDO.getPassword().equals(srcPassword)) {
            //原始密码错误
            response.setCodeMsg(CodeMsgUtil.PASSWORD_ERROR);
            return response;
        }

        System.out.println(newPassword);
        System.out.println(srcPassword);
        //正确就修改密码
        boolean isSuccess = studentService.modifyPwd(studentDO.getStudentNumber(), studentDO.getPassword(), newPassword);
        if (isSuccess) {
            request.getSession().removeAttribute("student"); //删除信息，需要重新登录
            response.setCodeMsg(CodeMsgUtil.MODIFY_PASSWORD_SUCCESS);//修改成功
        } else {
            response.setCodeMsg(CodeMsgUtil.MODIFY_PASSWORD_FAIL);//失败
        }

        return response;
    }

    //忘记密码
    @RequestMapping(value = "/student_forgetPwd", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<?> forgetPwd(@RequestBody StudentQuery studentQuery) {
//        String studentName， String email,

        BaseResponse<Object> response = new BaseResponse<>();

        String studentNumber = studentQuery.getStudentNumber();
        String studentName = studentQuery.getStudentName();
        String email = studentQuery.getEmail();


        //先判断当前学生用户是否存在，不存在直接返回
        boolean studentIsExist = studentService.studentIsExist(studentNumber);
        //数据库判断用户名和密保邮箱是否正确，正确则返回密码

        String password = studentService.forgetPwd(studentNumber, email);

        if (!studentIsExist ) {
            response.setCodeMsg(CodeMsgUtil.USER_NOT_EXISTS); //学生不存在
            return response;
        } else if (password == null) {
            response.setCodeMsg(CodeMsgUtil.EMAIL_ERROR); //邮箱错误
            return response;
        }


        //发送邮件
        try { //邮件发送可能会抛出异常
            response.setCodeMsg(CodeMsgUtil.EMAIL_SEND_SUCCESS); //邮件发送成功
            EmailUtil.toEmail(email, studentName, password);
        }  catch (Exception e) {
            response.setCodeMsg(CodeMsgUtil.EMAIL_SEND_FAIL); //发送失败
        } finally {
            return response;
        }
    }

//======================================================================================================================
//2、图书管理

    //查看书架

    //存在空串问题
    //查询已借阅图书  
    @RequestMapping(value = "/query_borrowed_books", produces = "text/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String queryBorrowedBooks(HttpServletRequest request) {
        JSONObject node = new JSONObject();
        StudentDO studentDO = (StudentDO)request.getSession().getAttribute("student");
        List<BookVO> list = studentService.queryBorrowedBooks(studentDO.getStudentNumber());
        node.put("code", CodeMsgUtil.QUERY_BOOK_SUCCESS);
        node.put("list", list);
        return node.toString();
    }
//    {"studentName":"小袁","email":"1375476814@qq.com"}

    //借书
    @RequestMapping(value = "/borrow_book", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> borrowBook(BookQuery book, HttpServletRequest request) {

        BaseResponse<Object> response = new BaseResponse<>();

        HttpSession session = request.getSession();
        StudentDO studentDO = (StudentDO) session.getAttribute("student");
        String studentName = studentDO.getStudentName();
        String studentNumber = studentDO.getStudentNumber();

        //先判断是否能够借书
        boolean isOver = studentService.isOverLimit(studentNumber);
        if (isOver) {
            response.setCodeMsg(CodeMsgUtil.BORROW_FAIL);
            return response;
        }

        //未超出借书数量限制
        String bookName = book.getBookName();
        int bookId = book.getBookId();

        String borrowTime = GetTimeUtil.getCurrentTime();
        String returnTime = GetTimeUtil.getReturnBookTime();

        boolean isSuccess = studentService.borrowBook(bookName, bookId, "学生",
                                                        studentName, studentNumber,
                                                        borrowTime, returnTime);
        if (isSuccess) { //借书成功
            response.setCodeMsg(CodeMsgUtil.BORROW_SUCCESS);
        } else {
            response.setCodeMsg(CodeMsgUtil.BORROW_FAIL);
        }
        return response;
    }

    //还书
    @RequestMapping(value = "/student_return_book", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> returnBook(BookQuery bookQuery, HttpServletRequest request) {
//        String studentNumber, int bookId

        BaseResponse<Object> response = new BaseResponse<>();

        StudentDO student = (StudentDO)request.getSession().getAttribute("student");
        String studentNumber = student.getStudentNumber();
        int bookId = bookQuery.getBookId();

        boolean isSuccess = studentService.returnBook(studentNumber, bookId);
        if (isSuccess) {
            response.setCodeMsg(CodeMsgUtil.RETURN_BOOK_SUCCESS); //还书成功
        } else {
            response.setCodeMsg(CodeMsgUtil.RETURN_BOOK_FAIL); //还书失败
        }
        return response;
    }


//======================================================================================================================
//3、课程管理

    //查看自己的必修课


    //查询开放的选修课程

    //选择课程
    @RequestMapping(value = "/selectCourse", produces = "text/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String selectCourse(@RequestBody ScoresDO student) {


        JSONObject node = new JSONObject();
        boolean isSuccess = studentService.selectCourse(student.getCourseId(),
                student.getCourseName(),
                student.getStudentName(),
                student.getStudentNumber(),
                student.getClassId(),
                student.getTeacherName());
        if (isSuccess) {
            node.put("code", CodeMsgUtil.SELECT_COURSE_SUCCESS);
        } else {
            node.put("code", CodeMsgUtil.SELECT_COURSE_FAIL);
        }
        return node.toString();
    }

    //删除选课  选修


    //查看自己的选修课

//======================================================================================================================
//4、班级作业
    //查看作业
    //提交作业

//======================================================================================================================
//5、成绩管理

    //查看所有课程的成绩

//======================================================================================================================
//5、成绩管理
    //签到
    //查看签到信息
}
 /*//注册
    @RequestMapping(value = "/student_regist",  method = RequestMethod.POST)
    @ResponseBody
    public String regist(@RequestBody String jsonNode) {

        JSONObject jsonObject = JSONObject.parseObject(jsonNode);
        Student student = JSON.toJavaObject(jsonObject, Student.class);

        JSONObject node = new JSONObject();
        System.out.println("学生注册");
        //先判断用户是否存在
        boolean studentIsExist = studentService.studentIsExist(student.getSname());

        //用户已存在直接返
        if (studentIsExist) {
            node.put("code", CodeMsgUtil.USER_ALREADY_EXISTS); //用户已存在
            return node.toString();
        }

        //用户名不存在
        //将信息账户密码存入数据库
        boolean isSuccess = studentService.insertUser(student.getSname(), student.getPasswd(),
                                                        student.getEmail(), student.getTel(), student.getSex());

        if (isSuccess) {
            node.put("code", CodeMsgUtil.REGISTER_SUCCESS); //注册成功的确认码
        } else {
            node.put("code", CodeMsgUtil.REGISTER_FAIL); //注册失败
        }

        return node.toString();
    }*/
