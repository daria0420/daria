package com.daria.service.Impl;

import com.daria.bean.CourseDO;
import com.daria.bean.vo.BookVO;
import com.daria.bean.vo.StudentVO;
import com.daria.bean.vo.TeacherVO;
import com.daria.dao.PublicMethodMapper;
import com.daria.service.QueryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Daria
 * @Description
 * @Date 2020/4/7 -- 2:03
 */
@Service
public class PublicMethodServiceImpl implements QueryService {

    @Resource
    private PublicMethodMapper publicMethodMapper;


    @Override
    public List<StudentVO> queryAllStudent() {
        return publicMethodMapper.queryAllStudent();
    }

    @Override
    public List<StudentVO> queryStudentWithName(String studentName) {
        return publicMethodMapper.queryStudentWithName(studentName);
    }

    @Override
    public List<TeacherVO> queryAllTeacher() {
        return publicMethodMapper.queryAllTeacher();
    }

    @Override
    public List<TeacherVO> queryTeacherWithName(String teacherName) {
        return publicMethodMapper.queryTeacherWithName(teacherName);
    }

    //查询所有图书
    @Override
    public List<BookVO> queryAllBooks() {
        return publicMethodMapper.queryAllBooks();
    }

    //查询图书剩余数量
    @Override
    public int getRemainder_number(int bookId) {
        return publicMethodMapper.getRemainder_number(bookId);
    }

    //查询图书借出数量
    @Override
    public int getBorrowed_number(int bookId) {
        return publicMethodMapper.getBorrowed_number(bookId);
    }

    //查询一本书是否存在
    @Override
    public boolean bookIsExist(int bookId) {

        BookVO book = publicMethodMapper.bookIsExist(bookId);
        if (book != null) {
            return true;
        }
        return false;
    }

    //查询一本书
    @Override
    public List<BookVO> queryBook(String bookName) {
        return publicMethodMapper.queryBook(bookName);
    }

    @Override
    public List<CourseDO> queryAllCourse() {
        return publicMethodMapper.queryAllCourse();
    }

    @Override
    public List<CourseDO> queryCourse(String courseName) {
        return publicMethodMapper.queryCourse(courseName);
    }

    @Override
    public List<StudentVO> querySelectCourseStudent(int courseId) {
        return publicMethodMapper.querySelectCourseStudent(courseId);
    }

    @Override
    public boolean deleteCourse(int courseId) {
        int isSuccess = publicMethodMapper.deleteCourse(courseId);
        if (isSuccess > 0) {
            return true;
        } else {
            return false;
        }
    }
}
