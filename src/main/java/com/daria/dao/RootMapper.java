package com.daria.dao;

import com.daria.bean.CourseDO;
import com.daria.bean.Root;
import com.daria.bean.vo.CourseVO;
import org.apache.ibatis.annotations.*;

import javax.ws.rs.DELETE;
import java.util.List;

/**
 * @Author Daria
 * @Description
 * @Date 2020/3/26 -- 22:47
 */
public interface RootMapper {
    //登录
    @Select("select * from root where rname = #{rname} and passwd = #{passwd}")
    Root login(@Param("rname") String rname, @Param("passwd") String passwd);

    //创建教师账号
    @Insert("insert teacher(tname,teacherNumber,passwd) values (#{tname},#{teacherNumber},#{passwd})")
    int createTeacherAccount(@Param("tname") String tname,
                             @Param("teacherNumber")String teacherNumber,
                             @Param("passwd") String passwd);

    //创建学生账号
    @Insert("insert student(sname,studentNumber,passwd) values (#{sname},#{studentNumber},#{passwd})")
    int createStudentAccount(@Param("tname") String sname,
                             @Param("studentNumber") String studentNumber,
                             @Param("passwd") String passwd);


    //更新教师借书信息表circulation
    @Insert("insert teachercirculation(teacherNumber,bookLimit) values(#{teacherNumber},#{bookLimit})")
    int updateTeacherCirculation(@Param("teacherNumber") String teacherNumber,
                              @Param("bookLimit") int num);

    //更新学生借书信息表circulation
    @Insert("insert teachercirculation(studentNumber,bookLimit) values(#{studentNumber},#{bookLimit})")
    int updateStudentCirculation(@Param("studentNumber") String studentNumber,
                              @Param("bookLimit") int num);

    //上架图书
    //num存入剩余数量
    @Insert("insert book(bookName, author, publishingHouse, category, price, putTime, remainderNumber)" +
            " values (#{bookName},#{author},#{publishingHouse},#{category},#{price},#{putTime},#{remainderNumber})")
    int addBook(@Param("bookName")String bookName,
                @Param("author")String author,
                @Param("publishingHouse")String publishingHouse,
                @Param("category")String category,
                @Param("price")double price,
                @Param("putTime")String putTime,
                @Param("remainderNumber")int remainderNumber);

    //删除图书
    @Delete("delete from book where bookId = #{bookId}")
    int deleteBook(int bookId);

    //给教师添加班级、课程

    //给学生添加班级、课程

    //修改班级
    @Update("update student set classId = #{classId} where studentNumber = #{studentNumber}")
    int modificationClass(@Param("studentNumber")String studentNumber,
                          @Param("classId") String classId);

    //删除课程
    @Delete("delete from course where courseId = #{courseId}")
    int deleteCourse(String courseId);

    //显示所有课程
    @Select("select * from course")
    List<CourseDO> queryAllCourse();

    //显示所有的必修课程
    @Select("select * from course where type = #{type}")
    List<CourseDO> queryCourseWithType(String type);


    //用课程名查询一门课程
    @Select("select * from course where courseId = #{courseId}")
    List<CourseDO> queryCourseWithCourseName(int courseId);

    //显示未审核/审核通过/未审核的课程
    @Select("select * from course where isCheck = #{isCheck}")
    List<CourseDO> querySchedule(int isCheck);


    //审核课程
    @Update("update course set isCheck = #{isCheck} and isOpen = #{isOpen} where courseId = #{courseId}")
    int handleApplication(@Param("courseId") int courseId,
                             @Param("isCheck") int check,
                             @Param("isOpen") int isOpen);


    @Select("select * from course where isOpen = #{isOpen} and type = #{type}")
    List<CourseVO> queryIsOpenCourse(@Param("isOpen") int isOpen,
                                     @Param("type") String type);
//    //显示未审核的课程
//    @Select("select * from course where isCheck = check")
//    List<CourseDO> queryUncheckedCourse(int check);
//    //查询发布通过的课程
//    @Select("select * from course where isCheck = check")
//    List<CourseDO> queryApprovedCourse(int check);
//    //查询发布通过的课程
//    @Select("select * from course where isCheck = check")
//    List<CourseDO> queryUnapprovedCourse(int check);

}
