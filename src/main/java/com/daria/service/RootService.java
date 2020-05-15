package com.daria.service;

import com.daria.bean.CourseDO;
import com.daria.bean.Root;
import com.daria.bean.vo.CourseVO;

import java.util.List;

/**
 * @Author Daria
 * @Description
 * @Date 2020/3/26 -- 22:49
 */
public interface RootService {
    //登录
    Root login(Root root);

    //创建教师账号
    boolean createTeacherAccount(String tname, String teacherNumber, String passwd);

    //创建学生账号
    boolean createStudentAccount(String sname, String studentNumber, String passwd);

    //更新教师借书信息表circulation
    boolean updateTeacherCirculation(String teacherNumber, int num);

    //更新学生借书信息表circulation
    boolean updateStudentCirculation(String studentNumber, int num);

    //添加图书
    boolean addBook(String bookName, String author, String publishingHouse,
                    String category, double price, String putTime, int remainderNumber);

    //删除图书
    boolean deleteBook(int bookId);

    //修改班级
    boolean modificationClass(String studentNumber, String classId);

    //删除课程
    boolean deleteCourse(String courseId);

    //显示所有的课程
    List<CourseDO> queryAllCourse();

    //显示所有的必修课程
    List<CourseDO> queryCourseWithType(String type);

    //用课程名查询一门课程
    List<CourseDO> queryCourseWithCourseName(int courseId);

    //显示未审核/审核通过/未审核的课程
    List<CourseDO> querySchedule(int isCheck);



    //审核课程
    boolean handleApplication(int courseId, int isCheck, int isOpen);

    //查询开放/关闭的选修课  type， isOpen
    List<CourseVO> queryIsOpenCourse(int isOpen, String type);

//
//    //显示未审核的课程
//    List<CourseDO> queryUncheckedCourse(int check);
//    //查询发布通过的课程
//    List<CourseDO> queryApprovedCourse(int check);
//    //查询未通过的课程
//    List<CourseDO> queryUnapprovedCourse(int check);




















}
