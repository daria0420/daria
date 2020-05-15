package com.daria.service.Impl;

import com.daria.bean.CourseDO;
import com.daria.bean.Root;
import com.daria.bean.vo.CourseVO;
import com.daria.dao.RootMapper;
import com.daria.service.RootService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Daria
 * @Description
 * @Date 2020/3/26 -- 22:49
 */
@Service
public class RootServiceImpl implements RootService {
    @Resource
    private RootMapper rootMapper;


    //管理员登录
    @Override
    public Root login(Root root) {
        return rootMapper.login(root.getRootName(), root.getPassword());
    }

    @Override
    public boolean createTeacherAccount(String tname, String teacherNumber, String passwd) {
        int isSuccess = rootMapper.createTeacherAccount(tname, teacherNumber, passwd);
        if (isSuccess > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean createStudentAccount(String sname, String studentNumber, String passwd) {
        int isSuccess = rootMapper.createStudentAccount(sname, studentNumber, passwd);
        if (isSuccess > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateTeacherCirculation(String teacherNumber, int num) {
        int isSuccess = rootMapper.updateTeacherCirculation(teacherNumber, num);
        if (isSuccess > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateStudentCirculation(String studentNumber, int num) {
        int isSuccess = rootMapper.updateTeacherCirculation(studentNumber, num);
        if (isSuccess > 0) {
            return true;
        }
        return false;
    }

    //管理员上架图书
    @Override
    public boolean addBook(String bookName, String author, String publishingHouse, String category,
                           double price, String putTime, int remainderNumber) {

        int isSuccess = rootMapper.addBook(bookName, author, publishingHouse, category, price, putTime, remainderNumber);
        if (isSuccess > 0) {
            return true;
        }
        return false;
    }

    //管理员下架图书
    @Override
    public boolean deleteBook(int bookId) {
        int isSuccess = rootMapper.deleteBook(bookId);
        if (isSuccess > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean modificationClass(String studentNumber, String classId) {
        int isSuccess = rootMapper.modificationClass(studentNumber, classId);
        if (isSuccess > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCourse(String courseId) {
        int isSuccess = rootMapper.deleteCourse(courseId);
        if (isSuccess > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<CourseDO> queryAllCourse() {
        return rootMapper.queryAllCourse();
    }

    @Override
    public List<CourseDO> queryCourseWithType(String type) {
        return rootMapper.queryCourseWithType(type);
    }

    @Override
    public List<CourseDO> queryCourseWithCourseName(int courseId) {
        return rootMapper.queryCourseWithCourseName(courseId);
    }

    @Override
    public List<CourseDO> querySchedule(int isCheck) {
        return rootMapper.querySchedule(isCheck);
    }

//    @Override
//    public List<CourseDO> queryUncheckedCourse(int check) {
//        return rootMapper.queryUncheckedCourse(check);
//    }
//
//    @Override
//    public List<CourseDO> queryApprovedCourse(int check) {
//        return rootMapper.queryApprovedCourse(check);
//    }
//
//    @Override
//    public List<CourseDO> queryUnapprovedCourse(int check) {
//        return rootMapper.queryUnapprovedCourse(check);
//    }

    @Override
    public boolean handleApplication(int courseId, int isCheck, int isOpen) {
        int isSuccess = rootMapper.handleApplication(courseId, isCheck, isOpen);
        if (isSuccess > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<CourseVO> queryIsOpenCourse(int isOpen, String type) {
        return rootMapper.queryIsOpenCourse(isOpen, type);
    }
}
