package com.daria.bean;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/3 -- 14:22
 */
@Data
public class StudentDO {
    private int sid;
    private String studentName;
    private String sex; //性别
    private String studentNumber; //学号
    private String classId; //班级ID
    private String password;
    private String academy; //学院
    private String major; //专业
    private String nativePlace; //籍贯
    private String email;
    private String tel;


    public StudentDO() {
    }

    public StudentDO(int sid, String studentName, String sex, String studentNumber, String classId, String password,
                     String academy, String major, String nativePlace, String email, String tel) {
        this.sid = sid;
        this.studentName = studentName;
        this.sex = sex;
        this.studentNumber = studentNumber;
        this.classId = classId;
        this.password = password;
        this.academy = academy;
        this.major = major;
        this.nativePlace = nativePlace;
        this.email = email;
        this.tel = tel;
    }


}