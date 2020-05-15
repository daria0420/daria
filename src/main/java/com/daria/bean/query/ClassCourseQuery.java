package com.daria.bean.query;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/5 -- 16:30
 */
@Data
public class ClassCourseQuery {
    private String classId;
    private int courseId;

    public ClassCourseQuery() {}

    public ClassCourseQuery(String classId, int courseId) {
        this.classId = classId;
        this.courseId = courseId;
    }
}
