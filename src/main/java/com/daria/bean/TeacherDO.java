package com.daria.bean;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/3 -- 16:06
 */

@Data
public class TeacherDO {
    private int tid;
    private String teacherName;
    private String sex;
    private String teacherNumber; //教师工号
    private String academy; //学院
    private String password; //
    private String email;
    private String tel;

    public TeacherDO() {}

    public TeacherDO(int tid, String teacherName, String sex, String teacherNumber, String academy, String password, String email, String tel) {
        this.tid = tid;
        this.teacherName = teacherName;
        this.sex = sex;
        this.teacherNumber = teacherNumber;
        this.academy = academy;
        this.password = password;
        this.email = email;
        this.tel = tel;
    }


}
