package com.daria.bean.vo;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/4 -- 22:52
 */
@Data
public class CourseVO {
    private int courseId;
    private String courseName;
    private String teacherName;
    private String type;
    private int count; //选课人数
    private int countLimit; //选课的人数限制

    public CourseVO() {}

    public CourseVO(int courseId, String courseName, String teacherName, String type, int count, int countLimit) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.type = type;
        this.count = count;
        this.countLimit = countLimit;
    }
}
