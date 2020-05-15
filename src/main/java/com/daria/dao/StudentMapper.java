package com.daria.dao;

import com.daria.bean.StudentDO;
import com.daria.bean.vo.BookVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper {

    //登录
    @Select("select * from student where studentNumber = #{studentNumber} and password = #{password}")
    StudentDO login(@Param("studentNumber") String studentNumber,
                  @Param("password") String password);

//    //用户注册：将用户的基本信息插入数据库
//    @Insert("insert student(sname,passwd,email,tel,sex) values (#{sname},#{passwd},#{email},#{tel},#{sex})")
//    int insertUser(@Param("sname")String name,
//                   @Param("passwd")String passwd,
//                   @Param("email")String email,
//                   @Param("tel")String tel,
//                   @Param("sex")String sex);

    //通过学号判断学生是否存在
    @Select("select * from student where studentNumber = #{studentNumber}")
    StudentDO studentIsExist(String studentNumber);

    //修改密码
    @Update("update student set password = #{newPassword} " +
            "where studentNumber = #{studentNumber} and password = #{password}")
    int modifyPwd(@Param("studentNumber")String studentNumber,
                  @Param("password")String password,
                  @Param("newPassword")String newPassword);

    //忘记密码 通过用户名和用户密保邮箱查询用户密码
    @Select("select * from student where studentNumber = #{studentNumber} and email = #{email}")
    StudentDO forgetPwd(@Param("studentNumber")String studentNumber,
                        @Param("email")String email);


    //查询已借阅图书
    @Select("select * from book where bookId in(" +
            "select bookId from studentBorrowRecord where studentNumber = #{studentNumber})")
    List<BookVO> queryBorrowedBooks(String studentNumber);

    //获取当前借书数量
    @Select("select borrowCount from circulation where borrowerNumber = #{studentNumber}")
    int getBorrowCount(String studentNumber);

    //获取限制借书数量
    @Select("select borrowLimit from circulation where borrowerNumber = #{studentNumber}")
    int getBorrowLimit(String studentNumber);

    //修改当前借书数量
    @Update("update studentCirculation set borrowCount = #{borrowCount} " +
            "where borrowerNumber = #{studentNumber}")
    int modifyCirculation(@Param("studentNumber") String studentNumber,
                          @Param("borrowCount") int borrowCount);


    //借书  borrowRecord
    @Insert("insert borrow_record(bookName,bookId,borrowerType,borrowerName,borrowerNumber,borrowTime,returnTime) " +
            "values (#{bookName},#{bookId},#{borrowerType},#{studentName},#{studentNumber},#{borrowTime},#{returnTime})")
    int borrowBook(@Param("bookName") String bookName,
                   @Param("bookId") int bookId,
                   @Param("borrowerType") int borrowerType,
                   @Param("studentName") String studentName,
                   @Param("studentNumber") String studentNumber,
                   @Param("borrowTime")String borrowTime,
                   @Param("returnTime")String returnTime);



    //还书
    @Delete("delete from studentborrowrecord where studentNumber = #{studentNumber} and bookId = #{bookId}")
    int returnBook(@Param("studentNumber") String studentNumber,
                   @Param("bookId") int bookId);


    //查询课程人数限制
    @Select("select countLimit from course where courseId = #{courseId}")
    int queryCourseCountLimit(int courseId);

    //查询一门课程的选课人数
    @Select("select count from sourse where courseId = #{courseId}")
    int queryCourseCount(int courseId);


    //选课
    @Insert("insert selectcoursestudent(courseId,courseName,studentName,studentNumber,classId,teacherName) " +
            "values (#{courseId},#{courseName},#{studentName},#{studentNumber},#{classId},#{teacherName})")
    int selectCourse(@Param("courseId") int courseId,
                     @Param("courseName") String courseName,
                     @Param("studentName") String studentName,
                     @Param("studentNumber") String studentNumber,
                     @Param("classId") String classId,
                     @Param("teacherName") String teacherName);




    //提交作业
    //签到
    //查看自己作业分数
    //查看自己所有课程的成绩











}
