package com.daria.dao;

import com.daria.bean.*;
import com.daria.bean.vo.BookVO;
import com.daria.bean.vo.ScoresVO;
import com.daria.bean.vo.StudentVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author Daria
 * @Description
 * @Date 2020/3/26 -- 22:47
 */
public interface TeacherMapper {

    //登录
    @Select("select * from teacher where teacherNumber = #{teacherNumber} and passwd = #{password}")
    TeacherDO login(@Param("teacherNumber") String teacherNumber, @Param("password") String password);


    //查询用户是否存在
    @Select("select * from teacher where teacherNumber = #{teacherNumber}")
    TeacherDO teacherIsExist(String teacherNumber);


    //用户注册：将用户的基本信息插入数据库
    @Insert("insert teacher(tName,password,email,tel,sex) " +
            "values (#{tName},#{password},#{email},#{email},#{sex})")
    int insertUser(@Param("tName") String tName, @Param("password") String password,
                   @Param("email") String email,@Param("tel") String tel, @Param("sex") String sex);


    //修改密码
    @Update("update teacher set password = #{newPassword} " +
                                                    "where teacherNumber = #{teacherNumber}")
    int modifyPwd(@Param("teacherNumber") String teacherNumber,
                  @Param("newPassword") String newPassword);


    //忘记密码 通过用户名和用户密保邮箱查询用户密码，返回值是用户的密码
    @Select("select password from teacher where teacherNumber = #{teacherNumber} and email = #{email}")
    String forgetPwd(@Param("teacherNumber") String teacherNumber,
                     @Param("email") String email);



    //查询已借阅图书
    @Select("select * from book where bookId in(" +
            "select bookId from teacherBorrowRecord where teacherNumber = #{teacherNumber})")
    List<BookVO> queryBorrowedBooks(String teacherNumber);


    //获取当前借书数量
    @Select("select borrowCount from teacherCirculation where teacherNumber = #{teacherNumber}")
    int getBorrowCount(String teacherNumber);

    //获取限制借书数量
    @Select("select borrowLimit from teacherCirculation where teacherNumber = #{teacherNumber}")
    int getBorrowLimit(String teacherNumber);

    //修改当前借书数量
    @Update("update teacherCirculation set borrowCount = #{borrowCount} " +
            "where teacherNumber = #{teacherNumber}")
    int modifyCirculation(String teacherNumber, int borrowCount);

    //借书  borrowRecord
    @Insert("insert borrow_record(bookName, bookId, teacherName, teacherNumber, borrowTime, returnTime) " +
            "values (#{bookName},#{bookId},#{teacherName},#{teacherNumber},#{borrowTime},#{returnTime})")
    int borrowBook(@Param("bookName")String bookName,
                   @Param("bookId") int bookId,
                   @Param("borrowerType") int borrowerType,
                   @Param("teacherName")String teacherName,
                   @Param("teacherNumber") String teacherNumber,
                   @Param("borrowTime")String borrowTime,
                   @Param("returnTime")String returnTime);


    //还书
    @Delete("delete from teacherBorrowRecord where teacherNumber = #{teacherNumber} and bookId = #{bookId}")
    int returnBook(@Param("teacherNumber") String teacherNumber,
                   @Param("bookId") int bookId);



    //申请课程
    @Insert("insert course(courseName, teacherName, teacherNumber, type, countLimit) " +
            "values (#{courseName},#{teacherName},#{teacherNumber},#{type},#{countLimit})")
    int applyCourse(@Param("courseName") String courseName,
                    @Param("teacherName") String teacherName,
                    @Param("teacherNumber") String teacherNumber,
                    @Param("type") String type,
                    @Param("countLimit") int countLimit);

    //重新申请课程

    @Update("update course set courseName = #{countLimit}, " +
                                "countLimit = #{countLimit}, " +
                                "type = #{type} " +
                    "where courseId = #{courseId}")
    int reapplyCourse(@Param("courseName") String courseName,
                    @Param("countLimit") int countLimit,
                    @Param("type") String type,
                    @Param("courseId") int courseId);

    //查询所有自己发布的课程
    @Select("select * from course where teacherNumber = #{teacherNumber}")
    List<CourseDO> queryOwnerCourse(String teacherNumber);

    //查询自己发布通过的课程信息
    @Select("select * from course where teacherNumber = #{teacherNumber} and isCheck = #{isCheck}")
    List<CourseDO> queryApprovedCourse(@Param("teacherNumber") String teacherNumber,
                                       @Param("isCheck") int isCheck);

    //查询自己发布通过的课程信息
    @Select("select * from course where teacherNumber = #{teacherNumber} and isCheck = #{isCheck}")
    List<CourseDO> queryUnapprovedCourse(@Param("teacherNumber") String teacherNumber,
                                         @Param("isCheck") int isCheck);

    //查询未审核的课程
    @Select("select * from course where teacherNumber = #{teacherNumber} and isCheck = #{isCheck}")
    List<CourseDO> queryUncheckedCourse(@Param("teacherNumber") String teacherNumber,
                                        @Param("isCheck") int isCheck);



    //查看选课学生信息
    @Select("select * from scores where courseId = #{courseId}")
    List<StudentVO> querySelectCourseStudent(String courseId);

    //给学生添加成绩   sc
    @Update("update scores set score = #{score} where " +
            "studentName = #{studentName} and courseId = #{courseId}")
    int addScore(String score, String studentName, int courseId);

    //查询一门课程学生成绩
    @Select("select * from scores where courseId = #{courseId}")
    List<ScoresVO> queryScore(int courseId);

    //查看一个班级某一门课程的成绩
    @Select("select * from scores where classId = #{classId} and courseId = #{courseId}")
    List<ScoresVO> queryClassScore(@Param("classId") String classId,
                                   @Param("courseId") int courseId);


    //发布作业
    //打分
    //上传课表
    //发布签到
    //查看全部作业分数
    //查看到课率
    //查看借阅率

}
