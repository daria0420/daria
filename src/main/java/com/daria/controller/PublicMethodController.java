package com.daria.controller;

import com.daria.bean.CourseDO;
import com.daria.bean.vo.BookVO;
import com.daria.bean.vo.CourseVO;
import com.daria.bean.vo.StudentVO;
import com.daria.bean.vo.TeacherVO;
import com.daria.service.QueryService;
import com.daria.util.BaseResponse;
import com.daria.util.CodeMsgUtil;
import com.daria.util.GetTimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Daria
 * @Description
 * @Date 2020/4/7 -- 2:25
 */
@Controller
public class PublicMethodController {
    @Resource
    private QueryService queryService;


    @RequestMapping(value = "/getTime", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<String> queryTime() {
        BaseResponse<String> response = new BaseResponse<>();
        String currentTime = GetTimeUtil.getCurrentTime();
        System.out.println(currentTime);
        response.setCodeMsg(CodeMsgUtil.QUERY_BOOK_SUCCESS,currentTime );
        return response;
    }
    //查询所有学生
    @RequestMapping(value = "/studentList", produces = "text/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<?> queryAllStudent() {
        BaseResponse<List<StudentVO>> response = new BaseResponse<>();
        List<StudentVO> list = queryService.queryAllStudent();
        response.setCodeMsg(CodeMsgUtil.QUERY_STUDENT_SUCCESS, list);
        return response;
    }

    //用学生姓名查找
    @RequestMapping(value = "query_student" ,produces = "text/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<?> queryStudentWithName(@RequestBody String studentName) {
        BaseResponse<List<StudentVO>> response = new BaseResponse<>();
        List<StudentVO> list = queryService.queryStudentWithName(studentName);
        response.setCodeMsg(CodeMsgUtil.QUERY_STUDENT_SUCCESS, list);
        return response;
    }

    //用教师姓名查找
    @RequestMapping(value = "query_teacher" ,produces = "text/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<?> queryTeacherWithName(@RequestBody String teacherName) {
        BaseResponse<List<TeacherVO>> response = new BaseResponse<>();
        List<TeacherVO> list = queryService.queryTeacherWithName(teacherName);
        response.setCodeMsg(CodeMsgUtil.QUERY_STUDENT_SUCCESS, list);
        return response;
    }

    //查询所有教师
    @RequestMapping(value = "/teacherList", produces = "text/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<?> queryAllTeacher() {
        BaseResponse<List<TeacherVO>> response = new BaseResponse<>();
        List<TeacherVO> list = queryService.queryAllTeacher();
        response.setCodeMsg(CodeMsgUtil.QUERY_TEACHER_SUCCESS, list);
        return response;
    }


    //查询所有图书
    @RequestMapping(value = "/query_all_books", produces = "text/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<?> queryAllBooks() {
        BaseResponse<List<BookVO>> response = new BaseResponse<>();
        List<BookVO> list = queryService.queryAllBooks();
        response.setCodeMsg(CodeMsgUtil.QUERY_BOOK_SUCCESS, list);
        return response;
    }

    //搜索一本图书
    @RequestMapping(value = "query_book" ,produces = "text/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<?> queryBook(@RequestBody String bookName) {
        BaseResponse<List<BookVO>> response = new BaseResponse<>();
        List<BookVO> list = queryService.queryBook(bookName);
        response.setCodeMsg(CodeMsgUtil.QUERY_BOOK_SUCCESS, list);
        return response;
    }


    //查询可选课程
    @RequestMapping(value = "/query_all_course", produces = "text/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<?> queryAllCourse() {
        BaseResponse<List<CourseVO>> response = new BaseResponse<>();
        List<CourseDO> list = queryService.queryAllCourse();
        response.setCodeMsg(CodeMsgUtil.QUERY_COURSE_SUCCESS);
        return response;
    }

    //查询一门课程
    @RequestMapping(value = "/query_course", produces = "text/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<?> queryCourse(@RequestBody String courseName) {
        BaseResponse<Object> response = new BaseResponse<>();
        List<CourseDO> list = queryService.queryCourse(courseName);
        response.setCodeMsg(CodeMsgUtil.QUERY_COURSE_SUCCESS, list);
        return response;
    }

    //
    //查看选课学生信息   用课程id去查询选课学生的信息
    @RequestMapping(value = "/querySelectCourseStudent", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> querySelectCourseStudent(@RequestBody int courseId) {
        BaseResponse<Object> response = new BaseResponse<>();
        List<StudentVO> list = queryService.querySelectCourseStudent(courseId);
        response.setCodeMsg(CodeMsgUtil.APPLY_COURSE_SUCCESS, list);
        return response;
    }

    //删除一门课程
    @RequestMapping(value = "/deleteCourse", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<?> deleteCourse(@RequestBody int courseId) {
        BaseResponse<Object> response = new BaseResponse<>();
        boolean isSuccess= queryService.deleteCourse(courseId);
        if (isSuccess) {
            response.setCodeMsg(CodeMsgUtil.DELETE_COURSE_SUCCESS);
        } else {
            response.setCodeMsg(CodeMsgUtil.DELETE_COURSE_FAIL);
        }
        return response;
    }


}
