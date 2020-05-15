package com.daria.bean;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/4 -- 23:14
 */
@Data
public class ScoresDO {

    private int id;
    private int courseId;
    private String courseName;
    private String teacherName;
    private String studentName;
    private String studentNumber;
    private String classId;
    private String score;
    private String type; //课程类型 0必修,1选修


    public ScoresDO() {}

    public ScoresDO(int id, int courseId, String courseName, String teacherName, String studentName, String studentNumber,
                  String classId, String score, String type) {
        this.id = id;
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.classId = classId;
        this.score = score;
        this.type = type;

    }

}
