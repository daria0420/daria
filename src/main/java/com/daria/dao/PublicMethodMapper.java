package com.daria.dao;

import com.daria.bean.CourseDO;;
import com.daria.bean.vo.BookVO;
import com.daria.bean.vo.StudentVO;
import com.daria.bean.vo.TeacherVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author Daria
 * @Description
 * @Date 2020/4/7 -- 2:02
 */
public interface PublicMethodMapper {

    //查询所有学生
    @Select("select * from student")
    List<StudentVO> queryAllStudent();

    //查询一个学生
    @Select("select * from student where studentName = #{studentName}")
    List<StudentVO> queryStudentWithName(String studentName);

    //查询所有老师
    @Select("select * from student")
    List<TeacherVO> queryAllTeacher();

    //查询一个教师
    @Select("select * from teacher where teacherName = #{teacherName}")
    List<TeacherVO> queryTeacherWithName(String teacherName);


//    =====================================================================================
    //查询所有图书
    @Select("select * from book")
    List<BookVO> queryAllBooks();


    //查询一本书的剩余数量
    @Select("select remainderNumber from book where bookId = #{bookId}")
    int getRemainder_number(int bookId);

    //查询一本书的借出数量
    @Select("select borrowedNumber from book where bookId = #{bookId}")
    int getBorrowed_number(int bookId);

    //借书/还书 之后修改库存
    @Update("update book set borrowedNumber = #{borrowedNumber},remainderNumber = #{remainderNumber} " +
            "where bookId = #{bookId}")
    int modifyInventory(@Param("bookId")int bookId,
                        @Param("borrowedNumber") int borrowedNumber,
                        @Param("remainderNumber") int remainderNumber);

    //查询一本书是否存在
    @Select("select * from student where bookId = #{bookId}")
    BookVO bookIsExist(int bookId);

    //查询一本书
    @Select("select * from student where bookName = #{bookName}")
    List<BookVO> queryBook(String bookName);


    //查询可选课程
    @Select("select courseId, courseName, teacherName, int count, int countLimit from course")
    List<CourseDO>  queryAllCourse();

    //查询一门课程
    @Select("select courseId, courseName, teacherName, int count, int countLimit from course where courseName = #{course}")
    List<CourseDO> queryCourse(String courseName);

    //查看选课学生信息
    @Select("select sname,studentNumber,classId from selectcoursestudent where courseId = #{courseId}")
    List<StudentVO> querySelectCourseStudent(int courseId);

    //删除课程
    @Delete("delete from course where courseId = #{courseId}")
    int deleteCourse(int courseId);
}
