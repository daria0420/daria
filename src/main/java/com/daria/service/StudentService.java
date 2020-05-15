package com.daria.service;


import com.daria.bean.StudentDO;
import com.daria.bean.query.StudentQuery;
import com.daria.bean.vo.BookVO;

import java.util.List;

/**
 * @Author Daria
 * @Description
 * @Date 2020/3/17 -- 23:09
 */
public interface StudentService {
    //登录
    StudentDO login(StudentQuery student);

    //判断学生是否存在
    boolean studentIsExist(String studentNumber);


//    //用户注册：将用户的基本信息插入数据库
//    boolean insertUser(String name, String passwd, String email, String tel, String sex);

    //修改密码
    boolean modifyPwd(String studentNumber, String password, String newPassword);

    //忘记密码 通过用户名和用户密保邮箱查询用户密码，返回值是用户的密码
    String forgetPwd(String studentNumber, String email);

    //查询已借阅图书
    List<BookVO> queryBorrowedBooks(String studentNumber);

    //查询是否超出借书数量限制
    boolean isOverLimit(String studentNumber);

    //借书bookName, bookId, studentName, studentNumber, borrowTime
    boolean borrowBook(String bookName, int bookId, int borrowerType, String studentName,
                       String studentNumber, String borrowTime, String returnTime);

    //还书
    boolean returnBook(String studentNumber, int bookId);

    //选课
    boolean selectCourse(int courseId, String courseName, String studentName,
                         String studentNumber, String classId, String teacherName);

}
