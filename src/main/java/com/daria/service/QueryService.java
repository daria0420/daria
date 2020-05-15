package com.daria.service;

import com.daria.bean.CourseDO;
import com.daria.bean.vo.BookVO;
import com.daria.bean.vo.StudentVO;
import com.daria.bean.vo.TeacherVO;

import java.util.List;

/**
 * @Author Daria
 * @Description
 * @Date 2020/4/7 -- 2:02
 */

public interface QueryService {

    //查询所有学生
    List<StudentVO> queryAllStudent();

    //查询一个学生
    List<StudentVO> queryStudentWithName(String studentName);

    //查询所有教师
    List<TeacherVO> queryAllTeacher();

    //查询一个教师
    List<TeacherVO> queryTeacherWithName(String teacherName);

    //查询所有图书
    List<BookVO> queryAllBooks();

    //查询一本书
    List<BookVO> queryBook(String bookName);

    //查询一本书的剩余数量
    int getRemainder_number(int bookId);

    //查询一本书的借出数量
    int getBorrowed_number(int bookId);

    //查询一本书是否存在
    boolean bookIsExist(int bookName);




    //查询所有课程
    List<CourseDO> queryAllCourse();

    //查询一门课程
    List<CourseDO> queryCourse(String courseName);

    //查看选课学生信息
    List<StudentVO> querySelectCourseStudent(int courseId);

    //删除课程
    boolean deleteCourse(int courseId);
}
