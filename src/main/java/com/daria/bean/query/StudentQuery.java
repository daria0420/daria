package com.daria.bean.query;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/3 -- 14:29
 */

@Data
public class StudentQuery {
    private String studentNumber; //学号
    private String password;
    private String email;
    private String studentName;
    private String classId;


    public StudentQuery() {
    }

    public StudentQuery(String studentNumber, String password, String email, String studentName, String classId) {
        this.studentNumber = studentNumber;
        this.password = password;
        this.email = email;
        this.studentName = studentName;
        this.classId = classId;
    }
}