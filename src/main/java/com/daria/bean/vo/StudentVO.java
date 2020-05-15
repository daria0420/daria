package com.daria.bean.vo;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/2 -- 21:48
 */
@Data
public class StudentVO {
    private String studentName;
    private String sex; //性别
    private String studentNumber; //学号
    private String classId; //班级ID
    private String academy; //学院
    private String major; //专业

    public StudentVO() {}

    public StudentVO(String studentName, String sex, String studentNumber, String classId, String academy, String major) {
        this.studentName = studentName;
        this.sex = sex;
        this.studentNumber = studentNumber;
        this.classId = classId;
        this.academy = academy;
        this.major = major;
    }

}
