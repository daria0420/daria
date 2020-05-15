package com.daria.bean.vo;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/3 -- 15:54
 */

@Data
public class TeacherVO {
    private String teacherName;
    private String teacherNumber;
    private String sex;
    private String academy; //学院

    public TeacherVO() {
    }

    public TeacherVO(String teacherName,
                      String teacherNumber,
                      String sex,
                      String academy) {

        this.teacherName = teacherName;
        this.sex = sex;
        this.academy = academy;
    }

}