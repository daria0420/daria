package com.daria.service.Impl;

import com.daria.bean.StudentDO;
import com.daria.bean.query.StudentQuery;
import com.daria.bean.vo.BookVO;
import com.daria.dao.PublicMethodMapper;
import com.daria.dao.StudentMapper;
import com.daria.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Daria
 * @Description
 * @Date 2020/3/17 -- 23:10
 */
//@Transactional开启注解
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    //注入Mapper对象
    @Resource
    private StudentMapper studentMapper;

    //注入Mapper对象
    @Resource
    private PublicMethodMapper publicMethodMapper;
    @Override

    public StudentDO login(StudentQuery student) {
        return studentMapper.login(student.getStudentNumber(), student.getPassword());
    }

    @Override
    public boolean studentIsExist(String studentNumber) {
        StudentDO student = studentMapper.studentIsExist(studentNumber);
        if (student != null) {
            return true;
        }
        return false;
    }

//    @Override
//    public boolean insertUser(String name, String passwd, String email, String tel, String sex) {
//        int isSuccess = studentMapper.insertUser(name, passwd, email, tel, sex);
//        if (isSuccess > 0) { //成功
//            return true;
//        }
//        return false;
//    }

    @Override
    public boolean modifyPwd(String studentNumber, String password, String newPassword) {
        int isSuccess = studentMapper.modifyPwd(studentNumber, password, newPassword);
        if (isSuccess > 0) { //成功
            return true;
        }
        return false;
    }

    @Override
    public String forgetPwd(String studentNumber, String email) {
        StudentDO student = studentMapper.forgetPwd(studentNumber, email);
        if (student != null) {
            return  student.getPassword();
        } else {
            return null;
        }
    }


    @Override
    public List<BookVO> queryBorrowedBooks(String studentNumber) {
        return studentMapper.queryBorrowedBooks(studentNumber);
    }

    @Override
    public boolean isOverLimit(String studentNumber) {
        //查询是否超出借书数量限制
        int borrowCount = studentMapper.getBorrowCount(studentNumber);
        int borrowLimit = studentMapper.getBorrowLimit(studentNumber);
        if (borrowLimit > borrowCount) {
            return false;
        }
        return true;
    }

    @Override
    public boolean borrowBook(String bookName, int bookId, int borrowerType, String studentName, String studentNumber, String borrowTime, String returnTime) {
        int remainder_number = publicMethodMapper.getRemainder_number(bookId); //查询库存
        int borrowed_number = publicMethodMapper.getBorrowed_number(bookId);//获取借出的数量
        if (remainder_number > 0) {
            int isSuccess = studentMapper.borrowBook(bookName, bookId, borrowerType, studentName, studentNumber, borrowTime, returnTime);
            if (isSuccess > 0) { //成功
                //增加borrowed_number
                //减少remainder_number
                publicMethodMapper.modifyInventory(bookId, borrowed_number + 1,
                        remainder_number - 1);

                //修改studentCirculation
                int borrowCount = studentMapper.getBorrowCount(studentNumber);
                studentMapper.modifyCirculation(studentNumber, borrowCount + 1);
                return true;
            }
        }
        return false;
    }



    @Override
    public boolean returnBook(String studentNumber, int bookId) {
        int isSuccess = studentMapper.returnBook(studentNumber, bookId);
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
            int borrowCount = studentMapper.getBorrowCount(studentNumber);
            studentMapper.modifyCirculation(studentNumber, borrowCount - 1);

            return true;
        }
        return false;
    }

    @Override
    public boolean selectCourse(int courseId, String courseName, String studentName, String studentNumber, String classId, String teacherName) {
        //首先判断当前选课人数是否超过限制，超过则不能选课
        int count = studentMapper.queryCourseCount(courseId); //选课人数
        int countLimit = studentMapper.queryCourseCountLimit(courseId); //限制人数
        if (count < countLimit) {
            int isSuccess = studentMapper.selectCourse(courseId, courseName, studentName, studentNumber, classId, teacherName);
            if (isSuccess > 0) { //成功
                return true;
            }
        }
        return false;
    }


    //    @Override
//    public void saveCustomer(Student student) {
//        studentMapper.saveCustomer(student);
//    }
//
//    @Override
//    public List<Student> findAll() {
//        return studentMapper.();
//    }
}
