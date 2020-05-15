package com.daria.service;

import com.daria.bean.*;
import com.daria.bean.query.TeacherQuery;
import com.daria.bean.vo.BookVO;
import com.daria.bean.vo.ScoresVO;
import com.daria.bean.vo.StudentVO;

import java.util.List;

/**
 * @Author Daria
 * @Description
 * @Date 2020/3/26 -- 22:49
 */
public interface TeacherService {
    //登录
    TeacherDO login(TeacherQuery teacher);

    //通过用户名获取密码
    boolean teacherIsExist(String teacherNumber);

//    //用户注册：将用户的基本信息插入数据库
//    boolean insertUser(String name, String passwd, String email, String tel, String sex);

    //修改密码
    boolean modifyPwd(String teacherNumber, String newPassword);

    //忘记密码 通过用户名和用户密保邮箱查询用户密码，返回值是用户的密码
    String forgetPwd(String teacherNumber, String email);


    //查询已借阅图书
    List<BookVO> queryBorrowedBooks(String teacherNumber);

    //查询是否超出借书数量限制
    boolean isOverLimit(String teacherNumber);

    //借书bookName, bookId, teacherName, teacherNumber, borrowTime
    boolean borrowBook(String bookName, int bookId, int borrowerType, String teacherName,
                       String teacherNumber, String borrowTime, String returnTime);

    //还书
    boolean returnBook(String teacherNumber, int bookId);

    //申请课程   //CourseName, teacherName, teacherNumber, type, countLimit
    boolean applyCourse(String courseName, String teacherName, String teacherNumber,  String type, int countLimit);

    //重新申请课程
    boolean reapplyCourse(String courseName, int countLimit, String type, int courseId);


    //查询自己发布过的的课程信息
    List<CourseDO> queryOwnerCourse(String teacherNumber);

    //查询自己发布通过的课程信息
    List<CourseDO> queryApprovedCourse(String teacherNumber, int isCheck);

    //查询自己未发布通过的课程信息
    List<CourseDO> queryUnapprovedCourse(String teacherNumber, int isCheck);

    //查询未审核的课程
    List<CourseDO> queryUncheckedCourse(String teacherNumber, int isCheck);

    //查看选课学生信息
    List<StudentVO> querySelectCourseStudent(String courseId);

    //给学生添加成绩   sc
    boolean addScore(String score, String studentNumber, int courseId);

    //查询一个选修课的学生成绩
    List<ScoresVO> queryScore(int courseId);

    List<ScoresVO> queryClassScore(String  classId, int courseId);
}
