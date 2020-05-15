package com.daria.service.Impl;

import com.daria.bean.*;
import com.daria.bean.query.TeacherQuery;
import com.daria.bean.vo.BookVO;
import com.daria.bean.vo.ScoresVO;
import com.daria.bean.vo.StudentVO;
import com.daria.dao.PublicMethodMapper;
import com.daria.dao.TeacherMapper;
import com.daria.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Daria
 * @Description
 * @Date 2020/3/26 -- 22:49
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    //注入Mapper对象
    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private PublicMethodMapper publicMethodMapper;

    @Override
    public TeacherDO login(TeacherQuery teacher) {
        return teacherMapper.login(teacher.getTeacherNumber(), teacher.getPassword());
    }

    @Override
    public boolean teacherIsExist(String teacherNumber) {
        TeacherDO teacher = teacherMapper.teacherIsExist(teacherNumber);
        if (teacher != null) {
            return true;
        }
        return false;
    }

//    @Override
//    public boolean insertUser(String name, String passwd, String email, String tel, String sex) {
//        int isSuccess = teacherMapper.insertUser(name, passwd, email, tel, sex);
//        if (isSuccess > 0) { //成功
//            return true;
//        }
//        return false;
//    }

    @Override
    public boolean modifyPwd(String teacherNumber, String newPassword) {
        int isSuccess = teacherMapper.modifyPwd(teacherNumber, newPassword);
        if (isSuccess > 0) { //成功
            return true;
        }
        return false;
    }

    @Override
    public String forgetPwd(String teacherNumber, String email) {
        return teacherMapper.forgetPwd(teacherNumber, email);
    }

    @Override
    public boolean borrowBook(String bookName, int bookId, int borrowerType, String teacherName,  String teacherNumber, String borrowTime, String returnTime) {
        int remainder_number = publicMethodMapper.getRemainder_number(bookId); //查询库存
        int borrowed_number = publicMethodMapper.getBorrowed_number(bookId);//获取借出的数量
        if (remainder_number > 0) {
            int isSuccess = teacherMapper.borrowBook(bookName, bookId, borrowerType, teacherName, teacherNumber, borrowTime, returnTime);
            if (isSuccess > 0) { //成功
                //增加borrowed_number
                //减少remainder_number
                publicMethodMapper.modifyInventory(bookId, borrowed_number + 1,
                        remainder_number - 1);
                int borrowCount = teacherMapper.getBorrowCount(teacherNumber);
                teacherMapper.modifyCirculation(teacherNumber, borrowCount + 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<BookVO> queryBorrowedBooks(String teacherNumber) {
        return teacherMapper.queryBorrowedBooks(teacherNumber);
    }

    @Override
    public boolean isOverLimit(String teacherNumber) {
        //查询是否超出借书数量限制
        int borrowCount = teacherMapper.getBorrowCount(teacherNumber);
        int borrowLimit = teacherMapper.getBorrowLimit(teacherNumber);
        if (borrowLimit > borrowCount) {
            return false;
        }
        return true;
    }

    @Override
    public boolean returnBook(String teacherNumber, int bookId) {
        int isSuccess = teacherMapper.returnBook(teacherNumber, bookId);
        if (isSuccess > 0) { //还书成功
            //获取剩余的数量
            int remainder_number = publicMethodMapper.getRemainder_number(bookId);
            //获取借出的数量
            int borrowed_number = publicMethodMapper.getBorrowed_number(bookId);
            //减少borrowed_number
            //增加remainder_number
            publicMethodMapper.modifyInventory(bookId, borrowed_number - 1,
                    remainder_number + 1);

            //修改studentCirculation
            int borrowCount = teacherMapper.getBorrowCount(teacherNumber);
            teacherMapper.modifyCirculation(teacherNumber, borrowCount - 1);

            return true;
        }
        return false;
    }

    @Override
    public boolean applyCourse(String courseName, String teacherName, String teacherNumber, String type,  int countLimit) {
        int isSuccess = teacherMapper.applyCourse(courseName, teacherName, teacherNumber, type, countLimit);
        if (isSuccess > 0) { //成功
            return true;
        }
        return false;
    }

    @Override
    public boolean reapplyCourse(String courseName, int countLimit, String type, int courseId) {
        int isSuccess = teacherMapper.reapplyCourse(courseName, countLimit, type, courseId);
        if (isSuccess > 0) { //成功
            return true;
        }
        return false;
    }

    @Override
    public boolean addScore(String score, String studentNumber, int courseId) {
        int isSuccess = teacherMapper.addScore(score, studentNumber, courseId);
        if (isSuccess > 0) { //成功
            return true;
        }
        return false;
    }

    @Override
    public List<ScoresVO> queryScore(int courseId) {
        return teacherMapper.queryScore(courseId);
    }

    @Override
    public List<ScoresVO> queryClassScore(String classId, int courseId) {
        return teacherMapper.queryClassScore(classId, courseId);
    }

    @Override
    public List<StudentVO> querySelectCourseStudent(String courseId) {
        return teacherMapper.querySelectCourseStudent(courseId);
    }


    @Override
    public List<CourseDO> queryOwnerCourse(String teacherNumber) {
        return teacherMapper.queryOwnerCourse(teacherNumber);//查询自己发布的课程信息
    }

    @Override
    public List<CourseDO> queryApprovedCourse(String teacherNumber, int isCheck) {
        return teacherMapper.queryApprovedCourse(teacherNumber, isCheck);
    }

    @Override
    public List<CourseDO> queryUnapprovedCourse(String teacherNumber, int isCheck) {
        return teacherMapper.queryUnapprovedCourse(teacherNumber, isCheck);
    }

    @Override
    public List<CourseDO> queryUncheckedCourse(String teacherNumber, int isCheck) {
        return teacherMapper.queryUncheckedCourse(teacherNumber, isCheck);
    }
}

