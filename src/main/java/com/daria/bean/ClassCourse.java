package com.daria.bean;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/5 -- 16:27
 */
@Data
public class ClassCourse {
    private int id;
    private String classId;
    private int courseId;
    private String courseName;
    private String teacherName;
    private String teacherNumber;

    public ClassCourse() {}

    public ClassCourse(int id, String classId, int courseId, String courseName, String teacherName, String teacherNumber) {
        this.id = id;
        this.classId = classId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.teacherNumber = teacherNumber;
    }
}
