package com.daria.bean.query;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/3 -- 15:56
 */

@Data
public class TeacherQuery {
    private String teacherName;
    private String teacherNumber; //教师工号
    private String password;
    private String email;

    public TeacherQuery(String teacherName, String teacherNumber, String password, String email) {
        this.teacherName = teacherName;
        this.teacherNumber = teacherNumber;
        this.password = password;
        this.email = email;
    }

    public TeacherQuery() {}

}
