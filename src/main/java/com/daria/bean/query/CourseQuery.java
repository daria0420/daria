package com.daria.bean.query;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/4 -- 22:54
 */
@Data
public class CourseQuery {
    private int courseId;
    private String courseName;
    private String teacherName;
    private String type; //课程类别
    private int countLimit; //选课的人数限制
    private int isCheck;
    private int isOpen; //选修课 是否开放选课

    public CourseQuery(int courseId,
                       String courseName,
                       String teacherName,
                       String type,
                       int countLimit,
                       int isOpen) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.type = type;
        this.countLimit = countLimit;
        this.isOpen = isOpen;
    }

    public CourseQuery() {}
}
